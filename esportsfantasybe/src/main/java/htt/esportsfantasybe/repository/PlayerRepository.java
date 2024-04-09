package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
