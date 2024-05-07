package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserXLeagueXPlayerRepository extends JpaRepository<UserXLeagueXPlayer, UserXLeagueXPlayerId> {
    List<UserXLeagueXPlayer> findAllById_LeagueuuidAndId_Useruuid(UUID leagueuuid, UUID useruuid);
}
