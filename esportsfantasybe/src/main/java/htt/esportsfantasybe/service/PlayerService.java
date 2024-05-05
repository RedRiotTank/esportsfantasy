package htt.esportsfantasybe.service;

import com.google.gson.JsonArray;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                    downloadPlayerImage(player);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Error downloading player image");
                }

                teamXPlayerService.linkTeamToPlayer(team.getUuid(),player.getUuid());
            });


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

}
