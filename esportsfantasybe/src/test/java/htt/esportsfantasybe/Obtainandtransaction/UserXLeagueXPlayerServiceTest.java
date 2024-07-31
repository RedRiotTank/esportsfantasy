package htt.esportsfantasybe.Obtainandtransaction;

import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.complexrepositories.UserXLeagueXPlayerRepository;
import htt.esportsfantasybe.service.complexservices.UserXLeagueXPlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserXLeagueXPlayerServiceTest {

    @InjectMocks
    private UserXLeagueXPlayerService userXLeagueXPlayerService;

    @Mock
    private UserXLeagueXPlayerRepository userXLeagueXPlayerRepository;

    @Mock
    private LeagueRepository leagueRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetAligned() {
        UUID userID = UUID.randomUUID();
        UUID leagueID = UUID.randomUUID();
        UUID playerID = UUID.randomUUID();
        UUID realLeagueID = UUID.randomUUID();
        int aligned = 1;

        RealLeague realLeague = new RealLeague(
                "event",
                "overviewpage",
                "shortname",
                "game",
                "apiID",
                null
        );

        realLeague.setCurrentjour(1);
        realLeague.setUuid(realLeagueID);

        League league = new League(
                "name",
                true,
                1,
                realLeague,
                true
        );

        Optional<League> opLeague = Optional.of(league);

        when(leagueRepository.findById(leagueID)).thenReturn(opLeague);

        when(userXLeagueXPlayerRepository.findById_LeagueuuidAndId_UseruuidAndId_JourAndAligned(leagueID, userID, 1, aligned))
                .thenReturn(null);

        Optional<UserXLeagueXPlayer> opUserXLeagueXPlayer = Optional.of(new UserXLeagueXPlayer());
        when(userXLeagueXPlayerRepository.findById(any())).thenReturn(opUserXLeagueXPlayer);

        userXLeagueXPlayerService.setAligned(userID, leagueID, playerID, aligned);

        verify(leagueRepository).findById(leagueID);

        verify(userXLeagueXPlayerRepository).findById_LeagueuuidAndId_UseruuidAndId_JourAndAligned(leagueID, userID, 1, aligned);

        ArgumentCaptor<UserXLeagueXPlayerId> captor = ArgumentCaptor.forClass(UserXLeagueXPlayerId.class);
        verify(userXLeagueXPlayerRepository).findById(captor.capture());
        UserXLeagueXPlayerId capturedId = captor.getValue();
        assertEquals(playerID, capturedId.getPlayeruuid());
        assertEquals(leagueID, capturedId.getLeagueuuid());
        assertEquals(userID, capturedId.getUseruuid());
        assertEquals(1, capturedId.getJour());

        UserXLeagueXPlayer capturedUserXLeagueXPlayer = opUserXLeagueXPlayer.get();
        verify(userXLeagueXPlayerRepository).save(capturedUserXLeagueXPlayer);
        assertEquals(aligned, capturedUserXLeagueXPlayer.getAligned());
    }
}
