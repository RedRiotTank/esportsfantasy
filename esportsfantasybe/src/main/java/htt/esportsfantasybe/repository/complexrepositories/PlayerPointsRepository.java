package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.model.complexkeysmodels.PlayerPointsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerPointsRepository extends JpaRepository<PlayerPoints, PlayerPointsId> {

    boolean existsById_Matchid(String matchid);


}
