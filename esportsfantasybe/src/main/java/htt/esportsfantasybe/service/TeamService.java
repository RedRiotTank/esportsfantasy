package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.pojos.PlayerInfoPOJO;
import htt.esportsfantasybe.model.pojos.PlayerLeaguePOJO;
import htt.esportsfantasybe.model.pojos.TeamInfoPOJO;
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

    public TeamDTO getTeamDTO(UUID teamuuid){
        Optional<Team> team = teamRepository.findById(teamuuid);

        return team.map(TeamDTO::new).orElse(null);

    }

    public Team getTeam(UUID teamUuid){
        Optional<Team> team = teamRepository.findById(teamUuid);
        return team.orElseThrow(() -> new RuntimeException("1019"));
    }

    public TeamDTO getTeamDataDB(String teamname){
        Set<Team> team = teamRepository.findTeamsByNameAndGame(teamname, "LOL");

        if(team == null || team.isEmpty()) return null;

        return new TeamDTO(team.stream().findFirst().get());
    }



    public void updateTeams(RealLeague rl){
        rl.getTeams().forEach(team -> {

            try {
                downloadTeamImage(team);
            } catch (IOException e) {
                e.printStackTrace();
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
        Utils.downloadImage(url,"teams/" + team.getUuid() + ".png");
    }

    public byte[] getPlayerTeamIcon(String playeruuid, String leagueuuid){
        PlayerDTO playerDTO = playerService.getPlayerDTO(UUID.fromString(playeruuid));

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

        imagePath = Paths.get(Utils.getStoragePath() + "teams/" + teamuuid + ".png");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);

        } catch (IOException e) {
            try {
                imageBytes = Files.readAllBytes(Paths.get(Utils.getStoragePath() + "not_found.png"));
            } catch (IOException ioException) {
                throw new RuntimeException();
            }
        }
        return imageBytes;
    }

    public byte[] getTeamIcon(UUID teamuuid){
        Path imagePath;

        imagePath = Paths.get(Utils.getStoragePath() + "teams/" + teamuuid.toString() + ".png");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);

        } catch (IOException e) {
            try {
                imageBytes = Files.readAllBytes(Paths.get(Utils.getStoragePath() + "not_found.png"));
            } catch (IOException ioException) {
                throw new RuntimeException();
            }
        }
        return imageBytes;
    }

    public TeamInfoPOJO getTeamInfo(String teamuuid){
        Team t = getTeam(UUID.fromString(teamuuid));

        byte[] image = getTeamIcon(t.getUuid());

        String teamIcon = Base64.getEncoder().encodeToString(image);

        return new TeamInfoPOJO(
            t.getUuid(),
            t.getName(),
            t.getShortname(),
            t.getGame(),
            teamIcon,
            t.getPlayers(),
            t.getLeagues()
        );

    }

    public Set<PlayerInfoPOJO> getTeamPlayersInfo(String teamuuid, String leagueuuid){
        Team t = getTeam(UUID.fromString(teamuuid));

        Set<PlayerInfoPOJO> playerInfoPOJOS = new HashSet<>();

        t.getPlayers().forEach(player -> {
            try {
                playerInfoPOJOS.add(playerService.getPlayerInfo(new PlayerLeaguePOJO(player.getUuid().toString(), leagueuuid)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return playerInfoPOJOS;
    }
}
