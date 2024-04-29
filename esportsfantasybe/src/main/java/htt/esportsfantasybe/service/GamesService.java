package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.GamesDTO;
import htt.esportsfantasybe.model.Games;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.repository.GamesRepository;
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
public class GamesService {

    private final GamesRepository gamesRepository;

    @Autowired
    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public List<GamesDTO> getGames() {
        List<Games> games = gamesRepository.findAll();

        if(games.isEmpty()) throw new RuntimeException("1015");

        List<GamesDTO> gamesDTO = new ArrayList<>();

        games.forEach(game -> {
            gamesDTO.add(new GamesDTO(game.getGame()));
        });

        return gamesDTO;
    }

    public byte[] getGameIcon(String game) {

        Path imagePath;
        imagePath = Paths.get("src/main/resources/media/gamesicons/" + game + ".png");

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
