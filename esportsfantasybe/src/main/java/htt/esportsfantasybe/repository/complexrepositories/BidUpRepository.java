package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.BidUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface BidUpRepository extends JpaRepository<BidUp, UUID> {
    ArrayList<BidUp> findAllById_LeagueuuidAndId_PlayeruuidAndState(UUID leagueUUID, UUID playerUUID, boolean state);
}
