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



    List<Market> findMarketsById_LeagueuuidAndInsellAndOwneruuidIsNull(UUID leagueId, boolean insell);

    List<Market> findMarketsById_LeagueuuidAndInsell(UUID leagueId, boolean insell);

    List<Market> findMarketsById_LeagueuuidAndInsellAndOwneruuidNotNull(UUID leagueId, boolean insell);

    List<Market> findMarketsById_LeagueuuidAndOwneruuidIsNotNullAndOwneruuidEquals(UUID leagueId, UUID owneruuid);

    Market findMarketById_LeagueuuidAndId_Playeruuid(UUID leagueId, UUID playerUuid);

    List<Market> findMarketsById_Playeruuid(UUID playerUuid);

    List<Market> findMarketsById_LeagueuuidAndOwneruuid(UUID leagueId, UUID owneruuid);

    List<Market> findMarketsById_Leagueuuid(UUID leagueId);

    List<Market> findMarketsById_LeagueuuidAndOwneruuidIsNull(UUID leagueId);

    //@Query("SELECT m FROM Market m WHERE m.biduseruuid IS NOT NULL")
    //List<Market> findAllWithBidUserNotNull();




}
