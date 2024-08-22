package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.model.complexentities.BidUp;
import htt.esportsfantasybe.repository.complexrepositories.BidUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class BidUpService {
    @Autowired
    private BidUpRepository bidUpRepository;

    public void bidUp(UUID playerUUID, UUID leagueUUID, UUID userUUID, int bidValue) {
        BidUp bidUp = new BidUp(playerUUID, leagueUUID, userUUID, new Date(), bidValue, true);
        bidUpRepository.save(bidUp);
    }

    public ArrayList<BidUp> getBidUpByPlayer(UUID playerUUID) {
        return bidUpRepository.findAllById_Playeruuid(playerUUID);
    }

    public ArrayList<BidUp> getBidUpByLeague(UUID leagueUUID) {
        return bidUpRepository.findAllById_Leagueuuid(leagueUUID);
    }

    public ArrayList<BidUp> getBidUpByLeagueAndPlayer(UUID leagueUUID, UUID playerUUID, boolean state) {
        return bidUpRepository.findAllById_LeagueuuidAndId_PlayeruuidAndState(leagueUUID, playerUUID,state);
    }

    public ArrayList<BidUp> getActiveBidUpByLeagueAndPlayer(UUID leagueUUID, UUID playerUUID) {
        return bidUpRepository.findAllById_LeagueuuidAndId_PlayeruuidAndState(leagueUUID, playerUUID,true);
    }

    public BidUp activeBidUp(UUID leagueUUID, UUID playerUUID, UUID userUUID) {
        return bidUpRepository.findById_LeagueuuidAndId_PlayeruuidAndId_BiduseruuidAndState(leagueUUID, playerUUID, userUUID, true);
    }

    public void closeBidUp(BidUp bidUp) {
        bidUp.setState(false);
        bidUpRepository.save(bidUp);
    }

    public BidUp getBidUp(UUID leagueUUID, UUID playerUUID, UUID userUUID) {
        return bidUpRepository.findById_LeagueuuidAndId_PlayeruuidAndId_BiduseruuidAndState(leagueUUID, playerUUID, userUUID, true);
    }

    public int cancelBid(UUID playerUUID, UUID leagueUUID, UUID userUUID) {
        BidUp bidUp = bidUpRepository.findById_LeagueuuidAndId_PlayeruuidAndId_BiduseruuidAndState(leagueUUID, playerUUID, userUUID, true);

        if(bidUp == null) {
            throw new RuntimeException("1024");
        }

        int bidValue = bidUp.getBid();
        bidUpRepository.delete(bidUp);
        return bidValue;
    }

    public void deleteLeagueBidups(UUID leagueUUID) {
        ArrayList<BidUp> bidUps = bidUpRepository.findAllById_Leagueuuid(leagueUUID);
        bidUpRepository.deleteAll(bidUps);
    }
}
