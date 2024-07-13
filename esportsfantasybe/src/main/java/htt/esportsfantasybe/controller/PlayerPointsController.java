package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.service.complexservices.PlayerPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class PlayerPointsController {

    private final PlayerPointsService playerPointsService;


    @Autowired
    public PlayerPointsController(PlayerPointsService playerPointsService) {
        this.playerPointsService = playerPointsService;
    }
}
