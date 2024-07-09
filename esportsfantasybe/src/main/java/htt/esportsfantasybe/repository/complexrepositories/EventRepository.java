package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, String>{
    List<Event> findAllById_Realleagueuuid(UUID realleagueuuid);
    @Query("SELECT MAX(e.id.jour) FROM Event e WHERE e.id.realleagueuuid = :realLeagueUuid")
    int findMaxJour(@Param("realLeagueUuid") UUID realleagueuuid);
}
