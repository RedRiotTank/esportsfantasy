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

    public ArrayList<BidUp> getBidUpByLeagueAndPlayer(UUID leagueUUID, UUID playerUUID, boolean state) {
        return bidUpRepository.findAllById_LeagueuuidAndId_PlayeruuidAndState(leagueUUID, playerUUID,state);
    }

    public void closeBidUp(BidUp bidUp) {
        bidUp.setState(false);
        bidUpRepository.save(bidUp);
    }
}
