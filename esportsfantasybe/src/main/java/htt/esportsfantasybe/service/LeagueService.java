package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.pojos.JoinLeaguePOJO;
import htt.esportsfantasybe.model.pojos.UserLeagueInfoPOJO;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.complexservices.UserXLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    private UserService userService;
    private UserXLeagueService userXLeagueService;

    private RealLeagueService realLeagueService;

    // invitation codes:
    private HashMap<String, UUID> invitationCodes = new HashMap<>();
    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int CODE_SIZE = 6;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Autowired
    public LeagueService( UserService userService, UserXLeagueService userXLeagueService, RealLeagueService realLeagueService) {
        this.userService = userService;
        this.userXLeagueService = userXLeagueService;
        this.realLeagueService = realLeagueService;

    }

    public byte[] getLeagueIcon(String uuid){
        League league = this.leagueRepository.findById(UUID.fromString(uuid)).orElseThrow(RuntimeException::new);

        return realLeagueService.getRLeagueIcon(league.getRealLeague().getUuid().toString());
    }

    public List<UserLeagueInfoPOJO> getUserLeagues(String mail){

        UserDTO userDTO = userService.getUser(mail);

        List<UserXLeague> userLeagues = this.userXLeagueService.getUserXLeaguesForUser(userDTO.getUuid());

        List<UserLeagueInfoPOJO> userLeagueInfoPOJOS = new ArrayList<>();

        userLeagues.forEach(userXLeague -> {
            League league = this.leagueRepository.findById(userXLeague.getId().getLeagueuuid()).orElseThrow(RuntimeException::new);

            if(league != null) userLeagueInfoPOJOS.add(new UserLeagueInfoPOJO(league.getUuid().toString(), league.getName(), userXLeague.isAdmin()));
        });

        return userLeagueInfoPOJOS;
    }

    public String generateInvitationCode(UUID leagueUUID) {
        String code;
        do {
            code = generateCode();
        } while (invitationCodes.containsKey(code));

        invitationCodes.put(code, leagueUUID);

        String finalCode = code;
        scheduler.schedule(() -> invitationCodes.remove(finalCode), 24, TimeUnit.HOURS);

        return code;
    }

    private String generateCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < CODE_SIZE; i++)
            codeBuilder.append(CHARS.charAt((int) (Math.random() * CHARS.length())));

        return codeBuilder.toString();
    }

    public void joinLeague(JoinLeaguePOJO joinLeaguePOJO){

        UserDTO userDTO = userService.getUser(joinLeaguePOJO.getUserMail());

        switch (joinLeaguePOJO.getLeagueType()){
            case 1:

                RealLeague rl = realLeagueService.getRLeague(joinLeaguePOJO.getCompetition());

                League league = leagueRepository.save(new League(joinLeaguePOJO.getLeagueName(), joinLeaguePOJO.isClauseActive(), joinLeaguePOJO.getStartType(), rl, joinLeaguePOJO.isPublicLeague()));

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), league.getUuid(), true);

                System.out.println(generateInvitationCode(league.getUuid()));

                break;

            case 2:
                Set<League> leagues = getPublicLeaguesWithTournament(joinLeaguePOJO.getCompetition());

                League randomLeague;
                int trycount = 0;

                do{
                    int randomLeagueIndex = (int) (Math.random() * leagues.size());
                    randomLeague = leagues.stream().toList().get(randomLeagueIndex);
                    trycount++;
                }while(userXLeagueService.isUserInLeague(userDTO.getUuid(), randomLeague.getUuid()) && trycount < 5);

                if(trycount == 5) throw new RuntimeException("1011");

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), randomLeague.getUuid(), false);

                break;

            case 3:

               UUID leagueuuid = this.invitationCodes.get(joinLeaguePOJO.getCode());

               if(leagueuuid == null) throw new RuntimeException("1012");

               League leagueUnionByCode = leagueRepository.findById(leagueuuid).orElse(null);

                if(leagueUnionByCode == null) throw new RuntimeException("1013");

                if(userXLeagueService.isUserInLeague(userDTO.getUuid(), leagueUnionByCode.getUuid())) throw new RuntimeException("1014");

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), leagueUnionByCode.getUuid(), false);

                break;
            default:
                System.out.println("Invalid league type");
        }



    }

    public Set<League> getPublicLeaguesWithTournament(String tournamentID){
        RealLeague rl = realLeagueService.getRLeague(tournamentID);

        if(rl == null) throw new RuntimeException("1009");

        Set<League> leagues = this.leagueRepository.findAllByRealLeagueAndPublicleague(rl, true);

        if (leagues == null || leagues.isEmpty()) throw new RuntimeException("1010");

        return leagues;
    }




}
