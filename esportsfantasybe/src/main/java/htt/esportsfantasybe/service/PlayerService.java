package htt.esportsfantasybe.service;

import com.google.gson.JsonArray;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.pojos.PlayerInfoPOJO;
import htt.esportsfantasybe.model.pojos.PlayerLeaguePOJO;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.repository.complexrepositories.MarketRepository;
import htt.esportsfantasybe.repository.complexrepositories.PlayerPointsRepository;
import htt.esportsfantasybe.repository.complexrepositories.UserXLeagueXPlayerRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.PlayerPointsService;
import htt.esportsfantasybe.service.complexservices.TeamXPlayerService;
import htt.esportsfantasybe.service.complexservices.UserXLeagueXPlayerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PlayerService {

    private static int DEFAULT_VALUE = 300000;

    @Autowired
    private PlayerRepository playerRepository;

    private PlayerPointsRepository playerPointsRepository;
    private UserXLeagueXPlayerRepository userXLeagueXPlayerRepository;
    private MarketRepository marketRepository;

    private LolApiCaller lolApiCaller = new LolApiCaller();


    private final TeamXPlayerService teamXPlayerService;

    private final UserService userService;

    @Autowired
    public PlayerService(
        TeamXPlayerService teamXPlayerService,
        PlayerPointsRepository playerPointsRepository,
        UserXLeagueXPlayerRepository userXLeagueXPlayerRepository,
        MarketRepository marketRepository,
        UserService userService) {
        this.teamXPlayerService = teamXPlayerService;
        this.playerPointsRepository = playerPointsRepository;
        this.userXLeagueXPlayerRepository = userXLeagueXPlayerRepository;
        this.marketRepository = marketRepository;
        this.userService = userService;
    }

    public void updatePlayers(Team team) {

        if(team.getPlayers() != null && !team.getPlayers().isEmpty())
            team.getPlayers().forEach(player -> {

                try {
                    downloadPlayerImage(player);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Error downloading player image");
                }

                teamXPlayerService.linkTeamToPlayer(team.getUuid(),player.getUuid());
            });


    }

    public PlayerInfoPOJO getPlayerInfo(PlayerLeaguePOJO playerLeaguePOJO) throws IOException {

        Player player = playerRepository.findById(UUID.fromString(playerLeaguePOJO.getPlayeruuid()))
                .orElseThrow(() -> new RuntimeException("1008"));

        String playericonB64 = Base64.getEncoder().encodeToString(this.getPlayerIcon(player.getUuid().toString()));

        String ownericonB64 = null;
        List<UserXLeagueXPlayer> ownerList = null;
        UserXLeagueXPlayer owner = null;
        Market market = null;

        if(playerLeaguePOJO.getLeagueuuid() != null) {
            ownerList = userXLeagueXPlayerRepository.findById_LeagueuuidAndId_Playeruuid(
                    UUID.fromString(playerLeaguePOJO.getLeagueuuid()),
                    UUID.fromString(playerLeaguePOJO.getPlayeruuid())
            );

            //obtain the owner with the biggest jour

            for (UserXLeagueXPlayer userXLeagueXPlayer : ownerList) {
                if(owner == null || userXLeagueXPlayer.getId().getJour() > owner.getId().getJour())
                    owner = userXLeagueXPlayer;
            }

            if(owner != null)
                ownericonB64 = Base64.getEncoder().encodeToString(userService.getUserPfp(owner.getId().getUseruuid()));

            market = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(
                    UUID.fromString(playerLeaguePOJO.getLeagueuuid()),
                    UUID.fromString(playerLeaguePOJO.getPlayeruuid())
            );

        }

        return new PlayerInfoPOJO(
                player.getUuid(),
                player.getUsername(),
                player.getFullname(),
                player.getRole(),
                playericonB64,
                player.getTeams().stream().map(Team::getUuid).toList(),
                player.getValue(),
                playerPointsRepository.findAllById_Playeruuid(player.getUuid()),

                owner != null ? owner.getId().getUseruuid() : null,
                owner != null ? userService.getUser(owner.getId().getUseruuid()).getUsername() : "null",
                ownericonB64,
                market != null ? market.getClause() : player.getValue()
        );

    }

    public void downloadPlayerImage(Player player) throws IOException {
        String op = player.getUsername().replace(" ", "_");

        //TODO: manage desambiguations
        String url = LolApiCaller.getTableImgurl(op,"Player");
        if(url != null) {
            op = op.replace("/", "_");
            Utils.downloadImage(url, "src/main/resources/media/players/" + player.getUuid().toString() + ".png");
        }
    }


    public PlayerDTO getPlayer(UUID uuid){
        return new PlayerDTO(playerRepository.findById(uuid).orElseThrow(RuntimeException::new));
    }

    public Player getPlayer(String username){
        Set<Player> player = playerRepository.findPlayerByUsername(username);

        if(player == null || player.isEmpty())
            return null;

        return player.stream().findFirst().get();
    }

    public Player getPlayer(String username, String role){
        Set<Player> player = playerRepository.findPlayerByUsernameAndRole(username, role);

        if(player == null || player.isEmpty())
            return null;

        return player.stream().findFirst().get();
    }

    public byte[] getPlayerIcon(String uuid){
        Optional<Player> playerOptional = playerRepository.findById(UUID.fromString(uuid));

        Player player = playerOptional.orElseThrow(() -> new RuntimeException("1018"));

        Path imagePath;
        //TODO: LOL hardcoded. Change to player.getGame()
        //TODO 2: Change to player.getUuid().toString()
        imagePath = Paths.get("src/main/resources/media/players/" + player.getUuid() + ".png");

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

    public static int getDefaultValue() {
        return DEFAULT_VALUE;
    }

}
