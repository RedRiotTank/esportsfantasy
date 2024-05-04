package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexkeysmodels.MarketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.UUID;

@Repository
public interface MarketRepository extends JpaRepository<Market, MarketId> {

    List<Market> findMarketsById_LeagueuuidAndInsell(UUID leagueId, boolean insell);




}
