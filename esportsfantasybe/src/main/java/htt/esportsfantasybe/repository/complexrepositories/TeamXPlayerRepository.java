package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.TeamXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.TeamXPlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamXPlayerRepository extends JpaRepository<TeamXPlayer, TeamXPlayerId> {
}
