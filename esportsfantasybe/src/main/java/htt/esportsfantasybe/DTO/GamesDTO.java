package htt.esportsfantasybe.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamesDTO {
    private String game;

    public GamesDTO(String game) {
        this.game = game;
    }
}
