package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.TeamRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXrLeagueService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    @Getter
    private final TeamXrLeagueService teamxrleagueService;

    private final PlayerService playerService;

    private RealLeagueService realLeagueService;

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
                downloadTeamImage(team);
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Error downloading team image");
            }

            teamxrleagueService.linkTeamToLeague(team.getUuid(),rl.getUuid());

            playerService.updatePlayers(team);
        });

    }

    public void downloadTeamImage(Team team) throws IOException {
        String op = team.getOverviewpage().replace(" ", "_");
        op = op.replace("'", "%27");


        String url = LolApiCaller.getTableImgurl(op, "Team");
        op = op.replace("/","_");
        Utils.downloadImage(url,"src/main/resources/media/teams/" + team.getUuid() + ".png");
    }

    public byte[] getPlayerTeamIcon(String playeruuid, String leagueuuid){
        PlayerDTO playerDTO = playerService.getPlayer(UUID.fromString(playeruuid));

        if(playerDTO == null) throw new RuntimeException("1018");



        AtomicReference<String> teamuuid = new AtomicReference<>("");

        //TODO: ASIGNAR CON UUIDS
        playerDTO.getTeams().forEach(team -> {
            team.getLeagues().forEach( league ->{
                if(league.getUuid().equals(UUID.fromString(leagueuuid))){
                    teamuuid.set(team.getUuid().toString());
                }
            });


        });


        Path imagePath;

        imagePath = Paths.get("src/main/resources/media/teams/" + teamuuid + ".png");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);

        } catch (IOException e) {
            try {
                imageBytes = Files.readAllBytes(Paths.get("src/main/resources/media/not_found.png"));
            } catch (IOException ioException) {
                throw new RuntimeException();
            }
        }
        return imageBytes;
    }
}
