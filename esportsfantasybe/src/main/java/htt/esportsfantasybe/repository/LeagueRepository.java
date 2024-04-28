package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.RealLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface LeagueRepository extends JpaRepository<League, UUID> {
    Set<League> findAllByRealLeague(RealLeague realLeague);
    Set<League> findAllByRealLeagueAndPublicleague(RealLeague realLeague, boolean publicleague);
}
