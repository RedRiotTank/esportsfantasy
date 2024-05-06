package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.complexentities.Market;
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

    private static final int MARKET_REGULAR_NUMBER = 8;

    public void initMarket(LeagueDTO league){

        league.getRealLeagueDTO().getTeams().forEach(team -> {
            team.getPlayers().forEach(player -> {
                marketRepository.save(new Market(new MarketId(player.getUuid(), league.getUuid()), null, 0, false, 0, null, player.getValue()));

            });
        });

        updateMarket(league);

    }


    public void updateMarket(LeagueDTO league){

        List<Market> leagueMarketEntriesNoSell = marketRepository.findMarketsById_LeagueuuidAndInsell(league.getUuid(), false);   //TODO: añadir que no coja a los jugadores que tengan dueño.

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


}
