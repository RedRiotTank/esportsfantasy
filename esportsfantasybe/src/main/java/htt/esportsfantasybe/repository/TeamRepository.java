package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    Set<Team> findTeamsByNameAndGame(String name, String game);

}
