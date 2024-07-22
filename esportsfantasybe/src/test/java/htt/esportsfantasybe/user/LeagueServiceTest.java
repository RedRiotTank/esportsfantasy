package htt.esportsfantasybe.user;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.model.pojos.JoinLeaguePOJO;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.service.LeagueService;
import htt.esportsfantasybe.service.RealLeagueService;
import htt.esportsfantasybe.service.UserService;
import htt.esportsfantasybe.service.complexservices.MarketService;
import htt.esportsfantasybe.service.complexservices.UserXLeagueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class LeagueServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private RealLeagueService realLeagueService;

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private UserXLeagueService userXLeagueService;

    @Mock
    private MarketService marketService;

    @InjectMocks
    private LeagueService leagueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testJoinLeague_Case1() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(1);
        joinLeaguePOJO.setCompetition("La Liga");
        joinLeaguePOJO.setLeagueName("Test League");
        joinLeaguePOJO.setClauseActive(true);
        joinLeaguePOJO.setStartType(1);
        joinLeaguePOJO.setPublicLeague(true);

        User user = new User();
        user.setUuid(UUID.randomUUID());
        UserDTO userDTO = new UserDTO(user);

        RealLeague realLeague = new RealLeague();
        Set<Team> teams = new HashSet<>();
        realLeague.setTeams(teams);
        League league = new League("Test League", true, 1, realLeague, true);

        when(userService.getUser("test@example.com")).thenReturn(userDTO);
        when(realLeagueService.getRLeague("La Liga")).thenReturn(realLeague);
        when(leagueRepository.save(any(League.class))).thenReturn(league);

        leagueService.joinLeague(joinLeaguePOJO);

        verify(userService).getUser("test@example.com");
        verify(realLeagueService).getRLeague("La Liga");
        verify(leagueRepository).save(any(League.class));
        verify(userXLeagueService).linkUserToLeague(eq(userDTO.getUuid()), eq(league.getUuid()), eq(true), eq(8000000));
        verify(marketService).initMarket(any(LeagueDTO.class));
    }

    @Test
    public void testJoinLeague_Case2() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(2);
        joinLeaguePOJO.setCompetition("La Liga");

        UUID userUuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(userUuid);

        Set<League> leagues = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            League league = new League();
            league.setUuid(UUID.randomUUID());
            league.setName("League " + i);
            leagues.add(league);
        }

        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));
        when(realLeagueService.getRLeague(joinLeaguePOJO.getCompetition())).thenReturn(new RealLeague());
        when(leagueRepository.findAllByRealLeagueAndPublicleague(any(RealLeague.class), eq(true))).thenReturn(leagues);
        when(userXLeagueService.isUserInLeague(any(UUID.class), any(UUID.class))).thenReturn(false);

        leagueService.joinLeague(joinLeaguePOJO);

        verify(userXLeagueService, times(1)).linkUserToLeague(eq(userUuid), any(UUID.class), eq(false), eq(8000000));
    }


    @Test
    public void testJoinLeague_Case3() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test3@example.com");
        joinLeaguePOJO.setLeagueType(3);
        joinLeaguePOJO.setCode("invitationCode123");

        UUID leagueUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        // Configuración del mapa de códigos de invitación
        HashMap<String, UUID> invitationCodes = new HashMap<>();
        invitationCodes.put("invitationCode123", leagueUuid);

        User user = new User();
        user.setUuid(userUuid);

        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));

        League league = new League();
        league.setUuid(leagueUuid);

        when(leagueRepository.findById(leagueUuid)).thenReturn(Optional.of(league));

        leagueService.setInvCodes(invitationCodes);

        leagueService.joinLeague(joinLeaguePOJO);

        verify(userXLeagueService, times(1)).linkUserToLeague(eq(userUuid), eq(leagueUuid), eq(false), eq(8000000));
    }

    @Test
    public void testJoinLeague_ErrorLeagueWithSpecifiedTournament() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(2);
        joinLeaguePOJO.setCompetition("ExistingTournament");

        UUID userUuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(userUuid);

        RealLeague realLeague = new RealLeague();
        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));
        when(realLeagueService.getRLeague(joinLeaguePOJO.getCompetition())).thenReturn(realLeague);
        when(leagueRepository.findAllByRealLeagueAndPublicleague(eq(realLeague), eq(true)))
                .thenReturn(Collections.emptySet()); // No hay ligas públicas

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> leagueService.joinLeague(joinLeaguePOJO));
        assertEquals("1010", thrown.getMessage());
    }

    @Test
    public void testJoinLeague_ErrorNoPublicLeague() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(2);
        joinLeaguePOJO.setCompetition("ExistingTournament");

        UUID userUuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(userUuid);

        // Configuración del escenario donde el torneo tiene ligas públicas, pero no hay ligas disponibles para el usuario
        RealLeague realLeague = new RealLeague();
        Set<League> leagues = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            League league = new League();
            league.setUuid(UUID.randomUUID());
            league.setName("League " + i);
            leagues.add(league);
        }

        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));
        when(realLeagueService.getRLeague(joinLeaguePOJO.getCompetition())).thenReturn(realLeague);
        when(leagueRepository.findAllByRealLeagueAndPublicleague(eq(realLeague), eq(true))).thenReturn(leagues);

        // Simular que el usuario ya está en todas las ligas disponibles
        when(userXLeagueService.isUserInLeague(any(UUID.class), any(UUID.class))).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> leagueService.joinLeague(joinLeaguePOJO));
        assertEquals("1011", thrown.getMessage());
    }

    @Test
    public void testJoinLeague_ErrorInvitationCode() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(3);
        joinLeaguePOJO.setCode("invalidInvitationCode");

        UUID userUuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(userUuid);

        HashMap<String, UUID> invitationCodes = new HashMap<>();
        invitationCodes.put("validInvitationCode", UUID.randomUUID());

        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));
        leagueService.setInvCodes(invitationCodes);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> leagueService.joinLeague(joinLeaguePOJO));
        assertEquals("1012", thrown.getMessage());
    }

    @Test
    public void testJoinLeague_ErrorLeagueExists() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(3);
        joinLeaguePOJO.setCode("validInvitationCode");

        UUID leagueUuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(UUID.randomUUID());

        HashMap<String, UUID> invitationCodes = new HashMap<>();
        invitationCodes.put("validInvitationCode", leagueUuid);

        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));
        when(leagueRepository.findById(leagueUuid)).thenReturn(Optional.empty());

        leagueService.setInvCodes(invitationCodes);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> leagueService.joinLeague(joinLeaguePOJO));
        assertEquals("1013", thrown.getMessage());
    }
    @Test
    public void testJoinLeague_ErrorUserInLeague() {
        JoinLeaguePOJO joinLeaguePOJO = new JoinLeaguePOJO();
        joinLeaguePOJO.setUserMail("test@example.com");
        joinLeaguePOJO.setLeagueType(3);
        joinLeaguePOJO.setCode("validInvitationCode");

        UUID leagueUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        HashMap<String, UUID> invitationCodes = new HashMap<>();
        invitationCodes.put("validInvitationCode", leagueUuid);

        User user = new User();
        user.setUuid(userUuid);
        League league = new League();
        league.setUuid(leagueUuid);

        when(userService.getUser(joinLeaguePOJO.getUserMail())).thenReturn(new UserDTO(user));
        when(leagueRepository.findById(leagueUuid)).thenReturn(Optional.of(league));
        when(userXLeagueService.isUserInLeague(userUuid, leagueUuid)).thenReturn(true);

        leagueService.setInvCodes(invitationCodes);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> leagueService.joinLeague(joinLeaguePOJO));
        assertEquals("1014", thrown.getMessage());
    }

    @Test
    public void testChangeLeagueName(){
        //TODO
    }

    @Test
    public void testRemovePlayerFromLeague(){
        //TODO
    }
}
