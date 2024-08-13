package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.complexentities.BidUp;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexkeysmodels.MarketId;
import htt.esportsfantasybe.model.pojos.ClausePOJO;
import htt.esportsfantasybe.model.pojos.IncreaseClausePOJO;
import htt.esportsfantasybe.model.pojos.OfferResponsePOJO;
import htt.esportsfantasybe.model.pojos.UserOffer;
import htt.esportsfantasybe.repository.complexrepositories.MarketRepository;
import htt.esportsfantasybe.service.PlayerService;
import htt.esportsfantasybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    private UserXLeagueService userXLeagueService;
    private UserXLeagueXPlayerService userXLeagueXPlayerService;

    private BidUpService bidUpService;
    private UserService userService;

    private PlayerService playerService;
    private EventService eventService;

    private TransferPostService transferPostService;


    private static final int MARKET_REGULAR_NUMBER = 8;

    @Autowired
    public MarketService(
        UserXLeagueService userXLeagueService,
        UserXLeagueXPlayerService userXLeagueXPlayerService,
        BidUpService bidUpService,
        UserService userService,
        PlayerService playerService,
        EventService eventService,
        TransferPostService transferPostService
    ) {
        this.userXLeagueService = userXLeagueService;
        this.userXLeagueXPlayerService = userXLeagueXPlayerService;
        this.bidUpService = bidUpService;
        this.userService = userService;
        this.playerService = playerService;
        this.eventService = eventService;
        this.transferPostService = transferPostService;
    }

    public void initMarket(LeagueDTO league){
        league.getRealLeagueDTO().getTeams().forEach(team -> {
            team.getPlayers().forEach(player -> {
                marketRepository.save(new Market(new MarketId(player.getUuid(), league.getUuid()), null, player.getValue() + ((int) (    player.getValue() + (float) player.getValue() * (2/3)  ) ), false, player.getValue()));

            });
        });

        updateMarket(league);
    }


    public void updateMarket(LeagueDTO league){
        List<Market> leagueMarketEntriesNoSell = marketRepository.findMarketsById_LeagueuuidAndInsellAndOwneruuidIsNull(league.getUuid(), false);

        List<Market> leagueMarketEntriesInSellWithOwner = marketRepository.findMarketsById_LeagueuuidAndInsellAndOwneruuidNotNull(league.getUuid(),true);

        List<Market> leagueMarketEntriesInSellNoOwner = marketRepository.findMarketsById_LeagueuuidAndInsellAndOwneruuidIsNull(league.getUuid(), true);

        //--- actualizacion de pujas

        leagueMarketEntriesInSellNoOwner.forEach(market ->{
            List<BidUp> bidUpList = bidUpService.getBidUpByLeagueAndPlayer(league.getUuid(), market.getId().getPlayeruuid(),true);
            List<BidUp> bidUpListClone = new ArrayList<>(bidUpList);

            if(!bidUpList.isEmpty()){
                BidUp maxBidUp = Collections.max(bidUpList, Comparator.comparingInt(BidUp::getBid));

                userXLeagueXPlayerService.linkUserLeaguePlayer(maxBidUp.getId().getBiduseruuid(), league.getUuid(), market.getId().getPlayeruuid(),league.getRealLeagueDTO().getCurrentjour() + 1);
                bidUpService.closeBidUp(maxBidUp);

                market.setOwneruuid(maxBidUp.getId().getBiduseruuid());

                bidUpList.remove(maxBidUp);

                bidUpList.forEach(bidUp -> {
                    userXLeagueService.addMoney(bidUp.getId().getBiduseruuid(), league.getUuid(), bidUp.getBid());
                    bidUpService.closeBidUp(bidUp);
                });

                transferPostService.generateTransferPost(
                        market.getId().getPlayeruuid(),
                        league.getUuid(),
                        null,
                        bidUpListClone

                );
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

        selectedMarkets.addAll(leagueMarketEntriesInSellWithOwner);

        leagueMarketEntriesInSellNoOwner.forEach(market -> {
            market.setInsell(false);
        });

        marketRepository.saveAll(leagueMarketEntriesInSellNoOwner);
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

    public void sell(UUID playerUUID, UUID leagueUUID, UUID userUUID, int value){
        Market market = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(leagueUUID, playerUUID);
        market.setInsell(true);
        marketRepository.save(market);




    }

    public void cancelSell(UUID playerUUID, UUID leagueUUID, UUID userUUID, int value){
        Market market = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(leagueUUID, playerUUID);
        market.setInsell(false);
        marketRepository.save(market);
    }

    public List<UserOffer> getOffers(UUID userUUID, UUID leagueUUID){

        List<UserOffer> userOffers = new ArrayList<>();

        List<Market> leagueMarketEntriesInSellWithOwner = marketRepository.findMarketsById_LeagueuuidAndOwneruuidIsNotNullAndOwneruuidEquals(leagueUUID, userUUID);

        leagueMarketEntriesInSellWithOwner.forEach(market -> {
            bidUpService.getBidUpByLeagueAndPlayer(leagueUUID, market.getId().getPlayeruuid(), true).forEach(bidUp -> {

                UserDTO user = userService.getUser(bidUp.getId().getBiduseruuid());

                byte[] usericon = new byte[0];
                try {
                    usericon = userService.getUserPfp(user.getMail());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String usericonb64 = Base64.getEncoder().encodeToString(usericon);

                PlayerDTO player = playerService.getPlayerDTO(bidUp.getId().getPlayeruuid());

                byte[] playericon = new byte[0];
                playericon = playerService.getPlayerIcon(player.getUuid().toString());
                String playericonb64 = Base64.getEncoder().encodeToString(playericon);

                userOffers.add(
                        new UserOffer(
                            bidUp.getId().getBiduseruuid(),
                            user.getUsername(),
                            usericonb64,
                            market.getId().getPlayeruuid(),
                            player.getUsername(),
                            playericonb64,
                            player.getRole(),
                            player.getValue(),
                            eventService.getPlayerPointsHistory(player.getUuid()),
                            bidUp.getBid()
                        )
                );
            });

        });



        return userOffers;
    }

    private void changeProperty(UUID oldUser, UUID newUser, UUID leagueID, UUID playerID, int jour) {
        userXLeagueXPlayerService.changeProperty(oldUser, newUser, leagueID, playerID, jour);

        Market oldmarket = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(leagueID, playerID);

        oldmarket.setOwneruuid(newUser);
        oldmarket.setInsell(false);
        marketRepository.save(oldmarket);
    }

    public void acceptOffer(OfferResponsePOJO offerResponsePOJO){
        List<BidUp> bidups = bidUpService.getActiveBidUpByLeagueAndPlayer(offerResponsePOJO.getLeagueuuid(), offerResponsePOJO.getPlayeruuid());

        AtomicReference<BidUp> acceptedBidUp = new AtomicReference<>();

        bidups.forEach(bidUp -> {
            if(bidUp.getId().getBiduseruuid().equals(offerResponsePOJO.getBiduseruuid())){

                acceptedBidUp.set(bidUp);

                changeProperty(offerResponsePOJO.getUseruuid(), bidUp.getId().getBiduseruuid(), offerResponsePOJO.getLeagueuuid(), offerResponsePOJO.getPlayeruuid(), offerResponsePOJO.getJour());

                userXLeagueXPlayerService.linkUserLeaguePlayer(offerResponsePOJO.getBiduseruuid(), offerResponsePOJO.getLeagueuuid(), offerResponsePOJO.getPlayeruuid(), offerResponsePOJO.getJour());
                userXLeagueService.addMoney(offerResponsePOJO.getUseruuid(),offerResponsePOJO.getLeagueuuid() ,bidUp.getBid());
                bidUpService.closeBidUp(bidUp);
            }else{
                userXLeagueService.addMoney(bidUp.getId().getBiduseruuid(), offerResponsePOJO.getLeagueuuid(), bidUp.getBid());
                bidUpService.closeBidUp(bidUp);
            }
        });

        ArrayList<BidUp> res = new ArrayList<>();
        res.add(acceptedBidUp.get());

        transferPostService.generateTransferPost(
                offerResponsePOJO.getPlayeruuid(),
                offerResponsePOJO.getLeagueuuid(),
                offerResponsePOJO.getUseruuid(),
                res
        );


    }

    public void rejectOffer(OfferResponsePOJO offerResponsePOJO){

        BidUp bidUp = bidUpService.activeBidUp(offerResponsePOJO.getLeagueuuid(), offerResponsePOJO.getPlayeruuid(), offerResponsePOJO.getBiduseruuid());
        userXLeagueService.addMoney(bidUp.getId().getBiduseruuid(), offerResponsePOJO.getLeagueuuid(), bidUp.getBid());
        bidUpService.closeBidUp(bidUp);


    }

    public void clausePlayer(ClausePOJO orPOJO){
        userXLeagueService.discountMoney(orPOJO.getBiduseruuid(), orPOJO.getLeagueuuid(), orPOJO.getValue());
        changeProperty(orPOJO.getUseruuid(), orPOJO.getBiduseruuid(), orPOJO.getLeagueuuid(), orPOJO.getPlayeruuid(), orPOJO.getJour());
        userXLeagueService.addMoney(orPOJO.getUseruuid(), orPOJO.getLeagueuuid(), orPOJO.getValue());

        Market playermarket = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(orPOJO.getLeagueuuid(), orPOJO.getPlayeruuid());

        playermarket.setClause((int) (playermarket.getClause() + playermarket.getClause() * 0.3));

        marketRepository.save(playermarket);
    }

    public void increaseClause(IncreaseClausePOJO increaseClausePOJO){

        Market playermarket = marketRepository.findMarketById_LeagueuuidAndId_Playeruuid(increaseClausePOJO.getLeagueUuid(), increaseClausePOJO.getPlayerUuid());

        int cost = 0;

        switch (increaseClausePOJO.getIncreaseType()) {
            case 1:
                playermarket.setClause((int) (playermarket.getClause() + ( (float) playermarket.getClause() * (1.0f / 4.0f))));
                cost = (int) ( (float) playermarket.getClause() * (1.0f / 4.0f) * (5.0f / 6.0f));
                break;
            case 2:
                playermarket.setClause((int) (playermarket.getClause() + ((float) playermarket.getClause() * (1.0f / 3.0f))));
                cost = (int) ( (float) playermarket.getClause() * (1.0f / 3.0f) * (4.0f / 6.0f));
                break;
            case 3:
                playermarket.setClause((int) (playermarket.getClause() + ((float) playermarket.getClause() * (1.0f / 2.0f))));
                cost = (int) ( (float) playermarket.getClause() * (1.0f / 2.0f) * (3.0f / 6.0f));
                break;
            case 4:
                playermarket.setClause(playermarket.getClause() * 2);
                cost = (int) ( (float) playermarket.getClause() * (1.0f / 2.0f) * (3.0f / 6.0f));
                break;
        }


        userXLeagueService.discountMoney(increaseClausePOJO.getUserUuid(), increaseClausePOJO.getLeagueUuid(), cost);


        marketRepository.save(playermarket);


    }
}
