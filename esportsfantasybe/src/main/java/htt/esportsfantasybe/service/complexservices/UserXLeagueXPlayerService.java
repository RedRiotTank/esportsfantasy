package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import htt.esportsfantasybe.model.pojos.PlayerTeamInfoPOJO;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.complexrepositories.UserXLeagueXPlayerRepository;
import htt.esportsfantasybe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserXLeagueXPlayerService {

    @Autowired
    private UserXLeagueXPlayerRepository userXLeagueXPlayerRepository;

    private LeagueRepository leagueRepository;

    private PlayerService playerService;


    @Autowired
    public UserXLeagueXPlayerService(PlayerService playerService, LeagueRepository leagueRepository) {
        this.playerService = playerService;
        this.leagueRepository = leagueRepository;
    }


    //TODO: hardcoded jornada y alineamiento.
    public void linkUserLeaguePlayer(UUID userID, UUID leagueID, UUID playerID, int jour) {
        userXLeagueXPlayerRepository.save(new UserXLeagueXPlayer(new UserXLeagueXPlayerId(playerID, leagueID, userID,jour), 0));
    }

    public void changeProperty(UUID oldUser, UUID newUser, UUID leagueID, UUID playerID, int jour) {

        UserXLeagueXPlayer oldprop = userXLeagueXPlayerRepository.findById_PlayeruuidAndId_LeagueuuidAndId_UseruuidAndId_Jour(
                playerID, leagueID, oldUser, jour);

        UserXLeagueXPlayer newprop = new UserXLeagueXPlayer(new UserXLeagueXPlayerId(playerID, leagueID, newUser, jour), 0);

        userXLeagueXPlayerRepository.delete(oldprop);
         
        userXLeagueXPlayerRepository.save(newprop);

    }

    public Set<PlayerTeamInfoPOJO> getUserXLeagueTeam(UUID userID, UUID leagueID) {
        List<UserXLeagueXPlayer> teamres = this.userXLeagueXPlayerRepository.findAllById_LeagueuuidAndId_Useruuid(leagueID, userID);

        Set<PlayerTeamInfoPOJO> team = new HashSet<>();

        teamres.forEach(tentry -> {
            UUID playerUUID = tentry.getId().getPlayeruuid();
            PlayerDTO player = playerService.getPlayer(playerUUID);

            String playericonB64 = Base64.getEncoder().encodeToString(playerService.getPlayerIcon(playerUUID.toString()));

            PlayerTeamInfoPOJO ptInfoPOJo = new PlayerTeamInfoPOJO(
                    player.getUuid(),
                    player.getUsername(),
                    player.getFullname(),
                    player.getRole(),
                    player.getValue(),
                    tentry.getId().getJour(),
                    tentry.getAligned(),
                    playericonB64
            );
            team.add(ptInfoPOJo);
        });
        return team;
    }

    public void setAligned(UUID userID, UUID leagueID, UUID playerID, int aligned) {
        int currentJour = leagueRepository.findById(leagueID).get().getRealLeague().getCurrentjour() + 1;

        UserXLeagueXPlayer old = userXLeagueXPlayerRepository.findById_LeagueuuidAndId_UseruuidAndId_JourAndAligned(leagueID, userID, currentJour, aligned);

        if(old != null){
            old.setAligned(0);
            userXLeagueXPlayerRepository.save(old);
        }

        UserXLeagueXPlayerId newId = new UserXLeagueXPlayerId(playerID, leagueID, userID, currentJour);

        UserXLeagueXPlayer userXLeagueXPlayer = userXLeagueXPlayerRepository.findById(newId).get();
        userXLeagueXPlayer.setAligned(aligned);
        userXLeagueXPlayerRepository.save(userXLeagueXPlayer);
    }

    public void playerOwnerJourExtension(RealLeague rLeague){
        System.out.println("Jour player extension");

        Set<League> leagueset = leagueRepository.findAllByRealLeague(rLeague);

        leagueset.forEach(league -> {
            userXLeagueXPlayerRepository.findAllById_Leagueuuid(league.getUuid()).forEach(
                    userXLeagueXPlayer -> {
                        UserXLeagueXPlayer extension  = new UserXLeagueXPlayer(
                                userXLeagueXPlayer.getId().getPlayeruuid(),
                                userXLeagueXPlayer.getId().getLeagueuuid(),
                                userXLeagueXPlayer.getId().getUseruuid(),
                                userXLeagueXPlayer.getId().getJour() + 1,
                                userXLeagueXPlayer.getAligned()
                        );

                        userXLeagueXPlayerRepository.save(extension);
                    }
            );
        });
    }
}
