package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.model.RealLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RealLeagueRepository extends JpaRepository<RealLeague, UUID> {

    Optional<RealLeague> findByEvent(String event);

    Set<RealLeague> findByGame(String game);

    RealLeague findByEventAndOverviewpageAndGame(String event, String overviewpage, String game);


}
