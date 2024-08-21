package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.model.complexkeysmodels.PlayerPointsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PlayerPointsRepository extends JpaRepository<PlayerPoints, PlayerPointsId> {

    boolean existsById_Matchid(String matchid);

    List<PlayerPoints> findAllById_Playeruuid(UUID playeruuid);

    PlayerPoints findById_MatchidAndId_Playeruuid(String matchid, UUID playeruuid);

    List<PlayerPoints> findAllById_PlayeruuidIn(List<UUID> playerUuids);
}
