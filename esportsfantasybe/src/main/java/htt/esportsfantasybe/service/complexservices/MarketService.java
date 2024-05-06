package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexkeysmodels.MarketId;
import htt.esportsfantasybe.repository.complexrepositories.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

import java.util.*;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    private UserXLeagueService userXLeagueService;
    private UserXLeagueXPlayerService userXLeagueXPlayerService;

    private static final int MARKET_REGULAR_NUMBER = 8;

    @Autowired
    public MarketService(UserXLeagueService userXLeagueService, UserXLeagueXPlayerService userXLeagueXPlayerService) {
        this.userXLeagueService = userXLeagueService;
        this.userXLeagueXPlayerService = userXLeagueXPlayerService;
    }

    public void initMarket(LeagueDTO league){

        league.getRealLeagueDTO().getTeams().forEach(team -> {
            team.getPlayers().forEach(player -> {
                marketRepository.save(new Market(new MarketId(player.getUuid(), league.getUuid()), null, 0, false, 0, null, player.getValue()));

            });
        });

        updateMarket(league);

    }


    public void updateMarket(LeagueDTO league){

        marketRepository.findAllWithBidUserNotNull().forEach(marketEntry -> {

            userXLeagueService.discountMoney(marketEntry.getBiduseruuid(), league.getUuid(), marketEntry.getMaxbid());

            System.out.println("A");
            userXLeagueXPlayerService.linkUserLeaguePlayer(marketEntry.getBiduseruuid(), league.getUuid(), marketEntry.getId().getPlayeruuid());
            System.out.println("B");
            marketEntry.setOwneruuid(marketEntry.getBiduseruuid());
            marketEntry.setInsell(false);
            marketEntry.setMaxbid(0);
            marketEntry.setBiduseruuid(null);

            marketRepository.save(marketEntry);

        });

        List<Market> leagueMarketEntriesNoSell = marketRepository.findMarketsById_LeagueuuidAndInsellAndOwneruuidIsNull(league.getUuid(), false);   //TODO: añadir que no coja a los jugadores que tengan dueño.

        List<Market> leagueMarketEntriesInSell = marketRepository.findMarketsById_LeagueuuidAndInsell(league.getUuid(), true);

        //TODO: ejecutar compras aquí antes de borrar los registros.




        Collections.shuffle(leagueMarketEntriesNoSell);


        List<Market> selectedMarkets = new ArrayList<>();

        for (Market market : leagueMarketEntriesNoSell) {
            if (!selectedMarkets.contains(market) && selectedMarkets.size() < MARKET_REGULAR_NUMBER){
                market.setInsell(true);
                selectedMarkets.add(market);
            }

            if (selectedMarkets.size() == 8) break;
        }

        leagueMarketEntriesInSell.forEach(market -> {
            market.setInsell(false);
        });

        marketRepository.saveAll(leagueMarketEntriesInSell);
        marketRepository.saveAll(selectedMarkets);





    }

    public List<Market> getLeagueMarketEntriesInSell(UUID leagueUUID, boolean insell){
        return marketRepository.findMarketsById_LeagueuuidAndInsell(leagueUUID, insell);
    }

    public void bidUp(UUID playerUUID, UUID leagueUUID, UUID userUUID, int bidValue){

        Market market = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(leagueUUID, playerUUID);

        if (market == null) throw new RuntimeException("1021");

        if(!userXLeagueService.userHasEnoughMoney(userUUID, leagueUUID, bidValue)) throw new RuntimeException("1022");

        if(bidValue >= market.getMaxbid()){
            market.setMaxbid(bidValue);
            market.setBiduseruuid(userUUID);
            marketRepository.save(market);
        }
    }
}
