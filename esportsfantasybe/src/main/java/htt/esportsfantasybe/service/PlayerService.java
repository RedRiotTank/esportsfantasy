package htt.esportsfantasybe.service;

import com.google.gson.JsonArray;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    private LolApiCaller lolApiCaller = new LolApiCaller();


    private final TeamXPlayerService teamXPlayerService;

    @Autowired
    public PlayerService(TeamXPlayerService teamXPlayerService) {
        this.teamXPlayerService = teamXPlayerService;
    }

    public void updatePlayers(Team team) {

        if(team.getPlayers() != null && !team.getPlayers().isEmpty())
            team.getPlayers().forEach(player -> {

                try {
                    downloadPlayerImage(player.getUsername());
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Error downloading player image");
                }

                teamXPlayerService.linkTeamToPlayer(team.getUuid(),player.getUuid());
            });


    }



    public void downloadPlayerImage(String overviewpage) throws IOException {
        String op = overviewpage.replace(" ", "_");

        //TODO: manage desambiguations
        String url = LolApiCaller.getTableImgurl(op,"Player");
        if(url != null) {
            op = op.replace("/", "_");
            Utils.downloadImage(url, "src/main/resources/media/LOL/players/" + op + ".png");
        }
    }




}
