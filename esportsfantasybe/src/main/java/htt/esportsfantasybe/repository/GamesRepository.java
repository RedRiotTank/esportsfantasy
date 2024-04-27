package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.model.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Games, String>{
}
