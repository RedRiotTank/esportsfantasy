package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserXLeagueXPlayerRepository extends JpaRepository<UserXLeagueXPlayer, UserXLeagueXPlayerId> {
}
