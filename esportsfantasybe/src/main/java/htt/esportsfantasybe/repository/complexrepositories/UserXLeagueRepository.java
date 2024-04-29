package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserXLeagueRepository extends JpaRepository<UserXLeague, UserXLeagueId>{

    @Query("SELECT u FROM UserXLeague u WHERE u.id.useruuid = :useruuid")
    List<UserXLeague> findAllByUserUuid(@Param("useruuid") UUID useruuid);
}
