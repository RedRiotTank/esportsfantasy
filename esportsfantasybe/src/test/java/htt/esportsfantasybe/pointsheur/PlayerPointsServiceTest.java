package htt.esportsfantasybe.pointsheur;

import htt.esportsfantasybe.model.pojos.PlayerEventStatPOJO;
import htt.esportsfantasybe.repository.complexrepositories.PlayerPointsRepository;
import htt.esportsfantasybe.service.complexservices.PlayerPointsService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerPointsServiceTest {

    @InjectMocks
    private PlayerPointsService playerPointsService;

    @Mock
    private PlayerPointsRepository playerPointsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateLOLPoints_EmptyGames() {
        Set<PlayerEventStatPOJO> games = new HashSet<>();
        int result = playerPointsService.calculateLOLPoints(games);
        assertEquals(0, result);
    }

    @Test
    public void testCalculateLOLPoints_TopRole() {
        Set<PlayerEventStatPOJO> games = new HashSet<>();

        PlayerEventStatPOJO game = mock(PlayerEventStatPOJO.class);
        when(game.getRole()).thenReturn("top");
        when(game.getKills()).thenReturn(10);
        when(game.getAssists()).thenReturn(5);
        when(game.getCs()).thenReturn(300);
        when(game.getVisionScore()).thenReturn(60);
        when(game.getGold()).thenReturn(15000);
        when(game.getDamageToChampions()).thenReturn(20000);
        when(game.getTeamKills()).thenReturn(50);
        when(game.getTeamGold()).thenReturn(60000);
        when(game.getDeaths()).thenReturn(2);
        when(game.isWin()).thenReturn(true);

        games.add(game);

        int result = playerPointsService.calculateLOLPoints(games);
        int expectedPoints = (10 * 3) + (5 * 2) + (300 / 100) + (60 / 30) + (15000 / 1000) + (20000 / 1000) +
                ((10 / 50) * 3) + (((15000 / 60000) / 10000) * 2) - (2 * 2) + 10;
        assertEquals(expectedPoints, result);
    }

    @Test
    public void testCalculateLOLPoints_JungleRole() {
        Set<PlayerEventStatPOJO> games = new HashSet<>();

        PlayerEventStatPOJO game = mock(PlayerEventStatPOJO.class);
        when(game.getRole()).thenReturn("jungle");
        when(game.getKills()).thenReturn(10);
        when(game.getAssists()).thenReturn(5);
        when(game.getCs()).thenReturn(170);
        when(game.getVisionScore()).thenReturn(50);
        when(game.getGold()).thenReturn(14000);
        when(game.getDamageToChampions()).thenReturn(15000);
        when(game.getTeamKills()).thenReturn(60);
        when(game.getTeamGold()).thenReturn(50000);
        when(game.getDeaths()).thenReturn(3);
        when(game.isWin()).thenReturn(false);

        games.add(game);

        int result = playerPointsService.calculateLOLPoints(games);
        int expectedPoints = (10 * 4) + (5 * 2) + (170 / 170) + (50 / 25) + (14000 / 1000) + (15000 / 1000) +
                ((10 / 60) * 3) + (((14000 / 50000) / 10000) * 2) - (3 * 3);
        assertEquals(expectedPoints, result);
    }

    @Test
    public void testCalculateLOLPoints_MidRole() {
        Set<PlayerEventStatPOJO> games = new HashSet<>();

        PlayerEventStatPOJO game = mock(PlayerEventStatPOJO.class);
        when(game.getRole()).thenReturn("mid");
        when(game.getKills()).thenReturn(8);
        when(game.getAssists()).thenReturn(7);
        when(game.getCs()).thenReturn(200);
        when(game.getVisionScore()).thenReturn(40);
        when(game.getGold()).thenReturn(13000);
        when(game.getDamageToChampions()).thenReturn(18000);
        when(game.getTeamKills()).thenReturn(55);
        when(game.getTeamGold()).thenReturn(70000);
        when(game.getDeaths()).thenReturn(4);
        when(game.isWin()).thenReturn(true);

        games.add(game);

        int result = playerPointsService.calculateLOLPoints(games);
        int expectedPoints = (8 * 4) + (7 * 2) + (200 / 150) + (40 / 30) + (13000 / 1000) + (18000 / 1000) +
                ((8 / 55) * 4) + (((13000 / 70000) / 10000) * 2) - (4 * 2) + 10;
        assertEquals(expectedPoints, result);
    }

    @Test
    public void testCalculateLOLPoints_BotRole() {
        Set<PlayerEventStatPOJO> games = new HashSet<>();

        PlayerEventStatPOJO game = mock(PlayerEventStatPOJO.class);
        when(game.getRole()).thenReturn("bot");
        when(game.getKills()).thenReturn(12);
        when(game.getAssists()).thenReturn(3);
        when(game.getCs()).thenReturn(250);
        when(game.getVisionScore()).thenReturn(30);
        when(game.getGold()).thenReturn(16000);
        when(game.getDamageToChampions()).thenReturn(22000);
        when(game.getTeamKills()).thenReturn(70);
        when(game.getTeamGold()).thenReturn(80000);
        when(game.getDeaths()).thenReturn(5);
        when(game.isWin()).thenReturn(false);

        games.add(game);

        int result = playerPointsService.calculateLOLPoints(games);
        int expectedPoints = (12 * 4) + (3) + (250 / 200) + (30 / 30) + (16000 / 1400) + (22000 / 1000) +
                ((12 / 70) * 4) + (((16000 / 80000) / 10000) * 2) - (5 * 3);
        assertEquals(expectedPoints, result);
    }

    @Test
    public void testCalculateLOLPoints_SupportRole() {
        Set<PlayerEventStatPOJO> games = new HashSet<>();

        PlayerEventStatPOJO game = mock(PlayerEventStatPOJO.class);
        when(game.getRole()).thenReturn("support");
        when(game.getKills()).thenReturn(1);
        when(game.getAssists()).thenReturn(20);
        when(game.getCs()).thenReturn(10);
        when(game.getVisionScore()).thenReturn(80);
        when(game.getGold()).thenReturn(5000);
        when(game.getDamageToChampions()).thenReturn(5000);
        when(game.getTeamKills()).thenReturn(40);
        when(game.getTeamGold()).thenReturn(60000);
        when(game.getDeaths()).thenReturn(2);
        when(game.isWin()).thenReturn(true);

        games.add(game);

        int result = playerPointsService.calculateLOLPoints(games);
        int expectedPoints = (1) + (20 * 4) + (10 / 50) + (80 / 20) + (5000 / 700) + (5000 / 1000) +
                ((1 / 40)) + (((5000 / 60000) / 10000) * 3) - (2) + 10;
        assertEquals(expectedPoints, result);
    }

}
