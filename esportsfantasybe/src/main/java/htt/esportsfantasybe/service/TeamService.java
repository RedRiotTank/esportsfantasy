package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.repository.TeamRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXPlayerService;
import htt.esportsfantasybe.service.complexservices.TeamXrLeagueService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    @Getter
    private final TeamXrLeagueService teamxrleagueService;

    private final PlayerService playerService;


    LolApiCaller lolApiCaller = new LolApiCaller();

    @Autowired
    public TeamService( TeamXrLeagueService teamxrleagueService, PlayerService playerService) {
        this.teamxrleagueService = teamxrleagueService;
        this.playerService = playerService;
    }

    public TeamDTO getTeamDataDB(){
        Set<Team> team = teamRepository.findTeamsByNameAndGame("Invictus Gaming", "LOL");

        return new TeamDTO(team.stream().findFirst().get());
    }


    public void updateTeams(RealLeague rl){
        rl.getTeams().forEach(team -> {

            try {
                downloadTeamImage(team.getOverviewpage());
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Error downloading team image");
            }

            teamxrleagueService.linkTeamToLeague(team.getUuid(),rl.getUuid());

            playerService.updatePlayers(team);
        });

    }

    public void downloadTeamImage(String overviewpage) throws IOException {
        String op = overviewpage.replace(" ", "_");
        op = op.replace("'", "%27");


        String url = LolApiCaller.getTableImgurl(op, "Team");
        op = op.replace("/","_");
        Utils.downloadImage(url,"src/main/resources/media/lolmedia/teams/" + op + ".png");
    }
}
