package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.complexentities.BidUp;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexkeysmodels.MarketId;
import htt.esportsfantasybe.repository.complexrepositories.MarketRepository;
import htt.esportsfantasybe.service.LeagueService;
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

    private BidUpService bidUpService;


    private static final int MARKET_REGULAR_NUMBER = 8;

    @Autowired
    public MarketService(UserXLeagueService userXLeagueService, UserXLeagueXPlayerService userXLeagueXPlayerService, BidUpService bidUpService) {
        this.userXLeagueService = userXLeagueService;
        this.userXLeagueXPlayerService = userXLeagueXPlayerService;
        this.bidUpService = bidUpService;
    }

    public void initMarket(LeagueDTO league){
        league.getRealLeagueDTO().getTeams().forEach(team -> {
            team.getPlayers().forEach(player -> {
                marketRepository.save(new Market(new MarketId(player.getUuid(), league.getUuid()), null, 0, false, player.getValue()));

            });
        });

        updateMarket(league);
    }


    public void updateMarket(LeagueDTO league){
        List<Market> leagueMarketEntriesNoSell = marketRepository.findMarketsById_LeagueuuidAndInsellAndOwneruuidIsNull(league.getUuid(), false);   //TODO: añadir que no coja a los jugadores que tengan dueño.

        List<Market> leagueMarketEntriesInSell = marketRepository.findMarketsById_LeagueuuidAndInsell(league.getUuid(), true);

        //--- actualizacion de pujas

        leagueMarketEntriesInSell.forEach(market ->{
            List<BidUp> bidUpList = bidUpService.getBidUpByLeagueAndPlayer(league.getUuid(), market.getId().getPlayeruuid(),true);

            if(!bidUpList.isEmpty()){
                BidUp maxBidUp = Collections.max(bidUpList, Comparator.comparingInt(BidUp::getBid));

                userXLeagueXPlayerService.linkUserLeaguePlayer(maxBidUp.getId().getBiduseruuid(), league.getUuid(), market.getId().getPlayeruuid(),league.getRealLeagueDTO().getCurrentjour());
                bidUpService.closeBidUp(maxBidUp);

                bidUpList.remove(maxBidUp);

                bidUpList.forEach(bidUp -> {
                    userXLeagueService.addMoney(bidUp.getId().getBiduseruuid(), league.getUuid(), bidUp.getBid());
                    bidUpService.closeBidUp(bidUp);
                });

            }


        });

        //--- fin actualizacion de pujas

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

        if (!market.isInsell()) throw new RuntimeException("1023");

        //if(!userXLeagueService.userHasEnoughMoney(userUUID, leagueUUID, bidValue)) throw new RuntimeException("1022");

        userXLeagueService.discountMoney(userUUID, leagueUUID, bidValue);
        bidUpService.bidUp(playerUUID, leagueUUID, userUUID, bidValue);





    }


}
