package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.pojos.JoinLeaguePOJO;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.complexservices.UserXLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    private UserService userService;
    private UserXLeagueService userXLeagueService;

    private RealLeagueService realLeagueService;

    @Autowired
    public LeagueService( UserService userService, UserXLeagueService userXLeagueService, RealLeagueService realLeagueService) {
        this.userService = userService;
        this.userXLeagueService = userXLeagueService;
        this.realLeagueService = realLeagueService;

    }


    public void joinLeague(JoinLeaguePOJO joinLeaguePOJO){

        UserDTO userDTO = userService.getUser(joinLeaguePOJO.getUserMail());

        switch (joinLeaguePOJO.getLeagueType()){
            case 1:
                RealLeague rl = realLeagueService.getRLeague(joinLeaguePOJO.getCompetition());


                League league = leagueRepository.save(new League(joinLeaguePOJO.getLeagueName(), joinLeaguePOJO.isClauseActive(), joinLeaguePOJO.getStartType(), rl, joinLeaguePOJO.isPublicLeague()));

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), league.getUuid(), true);

                break;
            case 2:
                Set<League> leagues = getPublicLeaguesWithTournament(joinLeaguePOJO.getCompetition());

                League randomLeague = null;
                int trycount = 0;

                do{
                    int randomLeagueIndex = (int) (Math.random() * leagues.size());
                    randomLeague = leagues.stream().toList().get(randomLeagueIndex);
                    trycount++;
                }while(userXLeagueService.isUserInLeague(userDTO.getUuid(), randomLeague.getUuid()) && trycount < 5);

                if(trycount == 5)
                    throw new RuntimeException("error encontrnado liga publica.");

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), randomLeague.getUuid(), false);

                break;
            case 3:

            default:
                System.out.println("Invalid league type");
        }



    }


    public Set<League> getPublicLeaguesWithTournament(String tournamentID){
        RealLeague rl = realLeagueService.getRLeague(tournamentID);
        if(rl != null)
            return this.leagueRepository.findAllByRealLeagueAndPublicleague(rl, true);
        else return null;
    }




}
