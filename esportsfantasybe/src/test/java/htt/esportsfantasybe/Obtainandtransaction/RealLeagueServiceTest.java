package htt.esportsfantasybe.Obtainandtransaction;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.RealLeagueService;
import htt.esportsfantasybe.service.apicaller.CounterApiCaller;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.EventService;
import htt.esportsfantasybe.service.complexservices.UserXLeagueXPlayerService;
import htt.esportsfantasybe.service.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RealLeagueServiceTest {

    @InjectMocks
    private RealLeagueService realLeagueService;

    @Mock
    private RealLeagueRepository realLeagueRepository;

    @Mock
    private LolApiCaller lolApiCaller;

    @Mock
    private CounterApiCaller counterApiCaller;

    @Mock
    private TeamService teamService;

    @Mock
    private EventService eventService;

    @Mock
    private UserXLeagueXPlayerService userXLeagueXPlayerService;

    @Test
    public void testGeneralCascadeUpdate() throws IOException {
        Set<PlayerDTO> mockedPlayers = new HashSet<>();
        PlayerDTO playerDTO = new PlayerDTO(
                "name",
                "image",
                "fullaname",
                "role",
                1,
                null
        );
        mockedPlayers.add(playerDTO);

        TeamDTO teamDTO = new TeamDTO(
                "name",
                "image",
                "shortname",
                "overviewpage",
                "game",
                mockedPlayers
        );
        Set<TeamDTO> teams = new HashSet<>();
        teams.add(teamDTO);

        playerDTO.setTeams(teams);
        teams.add(teamDTO);

        Set<Team> mockedTeams = new HashSet<>();
        mockedTeams.add(new Team(teamDTO));

        mockedTeams.forEach(team -> {
            team.getPlayers().forEach(player -> {
                player.setTeams(mockedTeams);
            });
        });

        RealLeague rl1 = new RealLeague(
                "event",
                "overviewpage",
                "shortname",
                "game",
                "apiID",
                mockedTeams
        );

        RealLeagueDTO rl1DTO = new RealLeagueDTO(rl1);

        Set<RealLeague> mockedRealLeagues = new HashSet<>();
        mockedRealLeagues.add(new RealLeague(rl1DTO));

        when(realLeagueRepository.findAll()).thenReturn(mockedRealLeagues.stream().toList());
        realLeagueService.GeneralCascadeUpdate();
    }


}
