package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.model.pojos.PlayerEventStatPOJO;
import htt.esportsfantasybe.repository.complexrepositories.PlayerPointsRepository;
import htt.esportsfantasybe.service.PlayerService;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlayerPointsService {

    private LolApiCaller lolApiCaller = new LolApiCaller();

    @Autowired
    private PlayerPointsRepository playerPointsRepository;

    private final PlayerService playerService;

    @Autowired
    public PlayerPointsService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<PlayerPoints> getpointsByPlayer(UUID playeruuid){
        return playerPointsRepository.findAllById_Playeruuid(playeruuid);
    }

    public int getPointsByPlayerAndMatch(UUID playeruuid, String matchid){
        return playerPointsRepository.findById_MatchidAndId_Playeruuid(matchid, playeruuid).getPoints();
    }

    public void obtainLOLPoints(Event event){

        if(playerPointsRepository.existsById_Matchid(event.getMatchid()))
            return;




        Utils.esfPrint("Obtaining points for event: " + event.getMatchid());

        if(event.getMatchid().contains("PCL"))
            System.out.println("PCL");

        Set<PlayerEventStatPOJO> set = lolApiCaller.getPlayerEventStats(event.getMatchid());

        Map<String, Set<PlayerEventStatPOJO>> groupedByPlayer = set.stream()
                .collect(Collectors.groupingBy(PlayerEventStatPOJO::getName, Collectors.toSet()));


        if(event.getMatchid() == "LVP SuperLiga/2024 Season/Summer Season_Week 9_3")
            System.out.println("LVP SuperLiga/2024 Season/Summer Season_Week 9_3");

        groupedByPlayer.forEach((name, playerStats) -> {
            int points = calculateLOLPoints(playerStats);

            //System.out.println(playerStats.stream().findFirst().get().getRole() + " " + name + " " + points);

            if(event.getMvp().equals(name))
                points += 20;

            Player player;

            player = playerService.getPlayerDTO(name, playerStats.stream().findFirst().get().getRole());

            if(player == null)
                player = playerService.getPlayerDTO(name);

            if(player == null)
                return;



            playerPointsRepository.save(new PlayerPoints(event.getMatchid(), player.getUuid(), points));
        });

    }

    public static int calculateLOLPoints(Set<PlayerEventStatPOJO> games) {
        if (games.isEmpty()) {
            return 0;
        }

        int totalPoints = 0;

        for (PlayerEventStatPOJO game : games) {
            int points = 0;

            switch (game.getRole().toLowerCase()) {
                case "top" -> {
                    points += game.getKills() * 3;
                    points += game.getAssists() * 2;
                    points += game.getCs()  / 100;
                    points += game.getVisionScore() / 30;
                    points += game.getGold() / 1000;
                    points += game.getDamageToChampions() / 1000;

                    if(game.getTeamKills() != 0)
                        points += ( game.getKills() / game.getTeamKills() ) * 3;

                    if(game.getTeamGold() != 0)
                        points += ((game.getGold() / game.getTeamGold()) / 10000) * 2;

                    points -= game.getDeaths() * 2;
                }

                case "jungle" -> {
                    points += game.getKills() * 4;
                    points += game.getAssists() * 2;
                    points += game.getCs() / 170;
                    points += game.getVisionScore() / 25;
                    points += game.getGold() / 1000;
                    points += game.getDamageToChampions() / 1000;

                    if(game.getTeamKills() != 0)
                        points += ( game.getKills() / game.getTeamKills() ) * 3;

                    if(game.getTeamGold() != 0)
                     points += ((game.getGold() / game.getTeamGold()) / 10000) * 2;

                    points -= game.getDeaths() * 3;
                }

                case "mid" -> {
                    points += game.getKills() * 4;
                    points += game.getAssists() * 2;
                    points += game.getCs() / 150;
                    points += game.getVisionScore() / 30;
                    points += game.getGold() / 1000;
                    points += game.getDamageToChampions() / 1000;

                    if(game.getTeamKills() != 0)
                        points += ( game.getKills() / game.getTeamKills() ) * 4;

                    if(game.getTeamGold() != 0)
                        points += ((game.getGold() / game.getTeamGold()) / 10000) * 2;

                    points -= game.getDeaths() * 2;
                }

                case "bot" -> {
                    points += game.getKills() * 4;
                    points += game.getAssists();
                    points += game.getCs() / 200;
                    points += game.getVisionScore() / 30;
                    points += game.getGold() / 1400;
                    points += game.getDamageToChampions() / 1000;

                    if(game.getTeamKills() != 0)
                        points += ( game.getKills() / game.getTeamKills() ) * 4;

                    if(game.getTeamGold() != 0)
                        points += ((game.getGold() / game.getTeamGold()) / 10000) * 2;

                    points -= game.getDeaths() * 3;
                }
                case "support" -> {
                    points += game.getKills() * 1;
                    points += game.getAssists() * 4;
                    points += game.getCs() / 50;
                    points += game.getVisionScore() / 20;
                    points += game.getGold() / 700;
                    points += game.getDamageToChampions() / 1000;

                    if(game.getTeamKills() != 0)
                        points += ( game.getKills() / game.getTeamKills() );

                    if(game.getTeamGold() != 0)
                        points += ((game.getGold() / game.getTeamGold()) / 10000) * 3;

                    points -= game.getDeaths() * 1;
                }
            }

            if (game.isWin()) {
                points += 10;
            }

            totalPoints += points;
        }

        return totalPoints / games.size();
    }

}
