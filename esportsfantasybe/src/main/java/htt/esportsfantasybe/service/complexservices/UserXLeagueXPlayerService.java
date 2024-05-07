package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import htt.esportsfantasybe.model.pojos.PlayerTeamInfoPOJO;
import htt.esportsfantasybe.repository.complexrepositories.UserXLeagueXPlayerRepository;
import htt.esportsfantasybe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserXLeagueXPlayerService {

    @Autowired
    private UserXLeagueXPlayerRepository userXLeagueXPlayerRepository;

    private PlayerService playerService;

    @Autowired
    public UserXLeagueXPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }


    //TODO: hardcoded jornada y alineamiento.
    public void linkUserLeaguePlayer(UUID userID, UUID leagueID, UUID playerID) {
        System.out.println(userID);
        System.out.println(leagueID);
        System.out.println(playerID);
        userXLeagueXPlayerRepository.save(new UserXLeagueXPlayer(new UserXLeagueXPlayerId(playerID, leagueID, userID,1), false));
    }

    public Set<PlayerTeamInfoPOJO> getUserXLeagueTeam(UUID userID, UUID leagueID) {
        List<UserXLeagueXPlayer> teamres = this.userXLeagueXPlayerRepository.findAllById_LeagueuuidAndId_Useruuid(leagueID, userID);

        Set<PlayerTeamInfoPOJO> team = new HashSet<>();

        teamres.forEach(tentry -> {
            UUID playerUUID = tentry.getId().getPlayeruuid();
            PlayerDTO player = playerService.getPlayer(playerUUID);

            PlayerTeamInfoPOJO ptInfoPOJo = new PlayerTeamInfoPOJO(player.getUuid(), player.getUsername(), player.getFullname(), player.getRole(), tentry.isAligned());
            team.add(ptInfoPOJo);
        });
        return team;
    }
}
