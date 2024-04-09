package htt.esportsfantasybe.repository.complexrepositories;


import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.complexentities.TeamXrLeague;
import htt.esportsfantasybe.model.complexkeysmodels.TeamXLeagueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamXrLeagueRepository extends JpaRepository<TeamXrLeague, TeamXLeagueId> {

    @Query("SELECT txl.id.teamuuid FROM TeamXrLeague txl WHERE txl.id.leagueuuid = :leagueId")
    List<UUID> findTeamsByLeagueId(UUID leagueId);
}
