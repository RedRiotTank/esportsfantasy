package htt.esportsfantasybe.Obtainandtransaction;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexkeysmodels.MarketId;
import htt.esportsfantasybe.repository.complexrepositories.MarketRepository;
import htt.esportsfantasybe.service.complexservices.BidUpService;
import htt.esportsfantasybe.service.complexservices.MarketService;
import htt.esportsfantasybe.service.complexservices.UserXLeagueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MarketServiceTest {

    @InjectMocks
    private MarketService marketService;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private UserXLeagueService userXLeagueService;

    @Mock
    private BidUpService bidUpService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateMarket() {
        UUID leagueUUID = UUID.randomUUID();

        List<Market> leagueMarketEntriesNoSellMock = new ArrayList<>();
        List<Market> leagueMarketEntriesInSell = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            leagueMarketEntriesNoSellMock.add(
                    new Market(
                            new MarketId(
                                    UUID.randomUUID(),
                                    leagueUUID
                            ),
                            null,
                            0,
                            false,
                            0
                    )
            );
        }

        for (int i = 0; i < 10; i++) {
            leagueMarketEntriesInSell.add(
                    new Market(
                            new MarketId(
                                    UUID.randomUUID(),
                                    leagueUUID
                            ),
                            null,
                            0,
                            true,
                            0
                    )
            );
        }

        when(marketRepository.findMarketsById_LeagueuuidAndInsellAndOwneruuidIsNull(leagueUUID, false))
                .thenReturn(leagueMarketEntriesNoSellMock);

        when(marketRepository.findMarketsById_LeagueuuidAndInsell(leagueUUID, true))
                .thenReturn(leagueMarketEntriesInSell);

        when(bidUpService.getBidUpByLeagueAndPlayer(
                any(UUID.class),
                any(UUID.class),
                anyBoolean()
        )).thenReturn(new ArrayList<>());

        marketService.updateMarket(new LeagueDTO(
                leagueUUID,
                "leaguename",
                false,
                0,
                null
        ));

        verify(marketRepository, times(2)).saveAll(any());

        ArgumentCaptor<List<Market>> captor = ArgumentCaptor.forClass(List.class);
        verify(marketRepository, times(2)).saveAll(captor.capture());

        List<List<Market>> allValues = captor.getAllValues();
        List<Market> updatedMarkets = allValues.get(1);

        long updatedToSell = updatedMarkets.stream().filter(Market::isInsell).count();
        assertEquals(8, updatedToSell);

        List<Market> removedFromSell = allValues.get(0);
        removedFromSell.forEach(market -> assertFalse(market.isInsell()));
    }

    @Test
    public void testBidUpNoMarketEntry(){
        when(marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(
                any(),
                any()
        )).thenReturn(null);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> marketService.bidUp(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 100)
        );

        assertEquals("1021", thrown.getMessage(), "Should throw 1021");
    }

    @Test
    public void testBidUpNoMarketInSell(){
        when(marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(
                any(),
                any()
        )).thenReturn(
                new Market(
                        new MarketId(
                                UUID.randomUUID(),
                                UUID.randomUUID()
                        ),
                        UUID.randomUUID(),
                        0,
                        false,
                        0)
        );

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> marketService.bidUp(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 100)
        );

        assertEquals("1023", thrown.getMessage(), "Should throw 1023");
    }

    @Test
    public void testBidUp(){
        UUID playerUUID = UUID.randomUUID();
        UUID leagueUUID = UUID.randomUUID();
        UUID userUUID = UUID.randomUUID();
        int bidValue = 100;

        Market mockMarket = new Market(
                new MarketId(
                        playerUUID,
                        leagueUUID
                ),
                userUUID,
                0,
                true,
                bidValue
        );

        when(marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(
                any(UUID.class),
                any(UUID.class)
        )).thenReturn(mockMarket);

        doNothing().when(userXLeagueService).discountMoney(userUUID, leagueUUID, bidValue);

        marketService.bidUp(playerUUID, leagueUUID, userUUID, bidValue);

        verify(bidUpService, times(1)).bidUp(playerUUID, leagueUUID, userUUID, bidValue);
    }

}