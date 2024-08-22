package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import htt.esportsfantasybe.service.complexservices.UserXLeagueXPlayerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserXLeagueXPlayerRepository extends JpaRepository<UserXLeagueXPlayer, UserXLeagueXPlayerId> {
    List<UserXLeagueXPlayer> findAllById_LeagueuuidAndId_Useruuid(UUID leagueuuid, UUID useruuid);

    UserXLeagueXPlayer findByIdAndAligned(UserXLeagueXPlayerId id, int aligned);

    UserXLeagueXPlayer findById_PlayeruuidAndId_LeagueuuidAndId_UseruuidAndId_Jour(UUID playeruuid, UUID leagueuuid, UUID useruuid, int jour);

    UserXLeagueXPlayer findById_LeagueuuidAndId_UseruuidAndId_JourAndAligned(UUID leagueuuid, UUID useruuid, int jour, int aligned);

    List<UserXLeagueXPlayer> findAllById_Leagueuuid(UUID uuid);

    List<UserXLeagueXPlayer> findById_LeagueuuidAndId_Playeruuid(UUID leagueuuid, UUID playeruuid);

    Set<UserXLeagueXPlayer> findAllById_UseruuidAndId_Leagueuuid(UUID useruuid, UUID leagueuuid);
}
