package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserXLeagueRepository extends JpaRepository<UserXLeague, UserXLeagueId>{
}
