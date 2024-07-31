package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserXLeagueXPlayerRepository extends JpaRepository<UserXLeagueXPlayer, UserXLeagueXPlayerId> {
    List<UserXLeagueXPlayer> findAllById_LeagueuuidAndId_Useruuid(UUID leagueuuid, UUID useruuid);

    UserXLeagueXPlayer findByIdAndAligned(UserXLeagueXPlayerId id, int aligned);

    UserXLeagueXPlayer findById_PlayeruuidAndId_LeagueuuidAndId_UseruuidAndId_Jour(UUID playeruuid, UUID leagueuuid, UUID useruuid, int jour);

    UserXLeagueXPlayer findById_LeagueuuidAndId_UseruuidAndId_JourAndAligned(UUID leagueuuid, UUID useruuid, int jour, int aligned);

    List<UserXLeagueXPlayer> findAllById_Leagueuuid(UUID uuid);
}
