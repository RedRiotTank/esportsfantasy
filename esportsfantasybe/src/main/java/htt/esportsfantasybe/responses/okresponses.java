package htt.esportsfantasybe.responses;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonElement;
import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.DTO.GamesDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.model.complexentities.TransferPost;
import htt.esportsfantasybe.model.pojos.*;
import htt.esportsfantasybe.service.PlayerService;
import org.springframework.http.ResponseEntity;

import java.util.*;

/** This class contains the responses for the successful requests.
 * @author Alberto Plaza Montes
 */
public class okresponses {
    /** This method returns a response for a successful request (creation of a new user).
     * @return ResponseEntity<?> This returns the response for the successful request.
     */
    public static ResponseEntity<?> createdNewUser(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "User created correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> bidup(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "bidup created correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> cancelBidup(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "bidup canceled correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    /** This method returns a response for a successful request (login of a user).
     * @param token This is the token of the user.
     * @return ResponseEntity<?> This returns the response for the successful request.
     */
    public static ResponseEntity<?> loginUser(String token) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "User login correctly");
        okjson.addProperty("token", token);
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> joinLeague(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "User joined league correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getEvents(Set<EventInfoPOJO> events) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("result", "ok");
        responseJson.addProperty("status", "200");
        responseJson.addProperty("message", "got events correctly");

        Map<Integer, JsonArray> eventsGroupedByJour = new HashMap<>();

        for (EventInfoPOJO event : events) {
            JsonObject eventJson = new JsonObject();
            eventJson.addProperty("team1uuid", event.getTeam1uuid().toString());
            eventJson.addProperty("team2uuid", event.getTeam2uuid().toString());
            eventJson.addProperty("team1name", event.getTeam1name());
            eventJson.addProperty("team2name", event.getTeam2name());
            eventJson.addProperty("date", event.getDate().toString());
            eventJson.addProperty("team1icon", event.getTeam1icon());
            eventJson.addProperty("team2icon", event.getTeam2icon());
            eventJson.addProperty("team1Score", event.getTeam1Score());
            eventJson.addProperty("team2Score", event.getTeam2Score());

            eventsGroupedByJour
                    .computeIfAbsent(event.getJour(), k -> new JsonArray())
                    .add(eventJson);
        }

        for (Map.Entry<Integer, JsonArray> entry : eventsGroupedByJour.entrySet()) {
            responseJson.add(entry.getKey().toString(), entry.getValue());
        }
        return ResponseEntity.ok(responseJson.toString());
    }

    public static ResponseEntity<?> getGames(List<GamesDTO> games) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got games correctly");

        JsonArray gamesArray = new JsonArray();
        games.forEach(game -> {
            gamesArray.add(game.getGame());
        });

        okjson.add("games", gamesArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getUserLeagues(List<UserLeagueInfoPOJO> leagues) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got leagues correctly");

        JsonArray leaguesArray = new JsonArray();

        leagues.forEach(league -> {
            JsonObject leagueJson = new JsonObject();
            leagueJson.addProperty("leagueUUID", league.getLeagueUUID());
            leagueJson.addProperty("leagueName", league.getLeagueName());
            leagueJson.addProperty("isAdmin", league.getIsAdmin());
            leagueJson.addProperty("money", league.getMoney());
            leagueJson.addProperty("rLeagueUUID", league.getRLeagueUUID());
            leagueJson.addProperty("leagueGame", league.getLeagueGame());
            leagueJson.addProperty("activeClause", league.getActiveClause());
            leagueJson.addProperty("code", league.getCode());
            leaguesArray.add(leagueJson);
        });

        okjson.add("leagues", leaguesArray);


        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getGameLeagues(Set<RealLeagueDTO> rlList) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got games correctly");

        JsonArray leaguesArray = new JsonArray();

        rlList.forEach(rl ->{
            JsonObject league = new JsonObject();
            league.addProperty("Event", rl.getEvent());
            league.addProperty("ShortName", rl.getShortname());
            league.addProperty("Uuid", rl.getUuid().toString());
            leaguesArray.add(league);
        });

        okjson.add("leagues", leaguesArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getMarketPlayers(Set<PlayerInfoPOJO> players) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got market players correctly");

        JsonArray playersArray = new JsonArray();

        players.forEach(player -> {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("uuid", player.getUuid().toString());
            playerJson.addProperty("name", player.getName());
            playerJson.addProperty("role", player.getRole());
            playerJson.addProperty("teamsList", player.getTeamsList().toString());

            if(player.getOwnerUUID() != null)
                playerJson.addProperty("ownerUUID", player.getOwnerUUID().toString());
            else
                playerJson.addProperty("ownerUUID", "null");

            playerJson.addProperty("ownerUsername", player.getOwnerUsername());
            playerJson.addProperty("price", player.getPrice());

            if(player.getPlayerBidUpValue() != -999)
                playerJson.addProperty("playerBidUpValue", player.getPlayerBidUpValue());

            playerJson.addProperty("playerBidUpValue", player.getPlayerBidUpValue());
            playerJson.addProperty("points", player.getPoints());
            playerJson.addProperty("clause", player.getClause());

            JsonArray pointsArray = new JsonArray();
            player.getPointsHistory().forEach(pointsArray::add);
            playerJson.add("pointsHistory", pointsArray);

            playersArray.add(playerJson);
        });

        okjson.add("players", playersArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getUserXLeagueMoney(int money) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got money correctly");
        okjson.addProperty("money", money);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> teamInfo(TeamAllComponentsPOJO teamAllComponentsPOJO) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got team info correctly");

        JsonArray playersArray = new JsonArray();

        teamAllComponentsPOJO.getTeamInfo().forEach(player -> {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("uuid", player.getPlayeruuid().toString());
            playerJson.addProperty("username", player.getUsername());
            playerJson.addProperty("fullname", player.getFullname());
            playerJson.addProperty("role", player.getRole());
            playerJson.addProperty("value", player.getValue());
            playerJson.addProperty("jour", player.getJour());
            playerJson.addProperty("aligned", player.getAligned());
            playerJson.addProperty("teamUuid", player.getTeamUuid());

            if(player.getPoints() != -999)
                playerJson.addProperty("points", player.getPoints());

            playersArray.add(playerJson);
        });

        okjson.add("players", playersArray);

        JsonArray playerIconsArray = new JsonArray();
        teamAllComponentsPOJO.getResourcesPlayerIcons().forEach((uuid, icon) -> {
            JsonObject iconJson = new JsonObject();
            iconJson.addProperty("uuid", uuid);
            iconJson.addProperty("icon", icon);
            playerIconsArray.add(iconJson);
        });

        okjson.add("playerIcons", playerIconsArray);

        JsonArray teamIconsArray = new JsonArray();
        teamAllComponentsPOJO.getResourcesTeamIcons().forEach((uuid, icon) -> {
            JsonObject iconJson = new JsonObject();
            iconJson.addProperty("uuid", uuid);
            iconJson.addProperty("icon", icon);
            teamIconsArray.add(iconJson);
        });

        okjson.add("teamIcons", teamIconsArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> setAligned() {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "Player aligned correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getRLeagueTotalJours(int totalJours) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got total jours correctly");
        okjson.addProperty("totalJours", totalJours);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getRLeagueCurrentJour(int currentJour, boolean editable, List<Integer> joursList) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got current jour correctly");
        okjson.addProperty("currentJour", currentJour);
        okjson.addProperty("isEditable", editable);

        JsonArray joursArray = new JsonArray();
        joursList.forEach(joursArray::add);
        okjson.add("jourList", joursArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> sell(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "sell created correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> cancelSell(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "cancel sell created correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getOffers(List<UserOffer> offers){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "get offers correctly");

        JsonArray offersArray = new JsonArray();
        offers.forEach(offer -> {
            JsonObject offerJson = new JsonObject();
            offerJson.addProperty("userUuid", offer.getUserUuid().toString());
            offerJson.addProperty("userName", offer.getUserName());
            offerJson.addProperty("userIcon", offer.getUserIcon());
            offerJson.addProperty("playerUuid", offer.getPlayerUuid().toString());
            offerJson.addProperty("playerUsername", offer.getPlayerUsername());
            offerJson.addProperty("playerIcon", offer.getPlayerIcon());
            offerJson.addProperty("playerRole", offer.getPlayerRole());
            offerJson.addProperty("value", offer.getValue());
            offerJson.addProperty("offerValue", offer.getOfferValue());

            JsonArray pointsArray = new JsonArray();
            offer.getPoints().forEach(pointsArray::add);
            offerJson.add("points", pointsArray);

            offersArray.add(offerJson);
        });

        okjson.add("offers", offersArray);

        return ResponseEntity.ok(okjson.toString());

    }

    public static ResponseEntity<?> acceptOffer(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "offer accepted correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> rejectOffer(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "offer rejected correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getPlayerInfo(PlayerInfoPOJO pInfo){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "got player info correctly");


        okjson.addProperty("uuid", pInfo.getUuid().toString());
        okjson.addProperty("name", pInfo.getName());
        okjson.addProperty("fullName", pInfo.getFullName());
        okjson.addProperty("role", pInfo.getRole());
        okjson.addProperty("icon", pInfo.getIcon());
        //okjson.addProperty("teamsList", pInfo.getTeamsList().toString());
        JsonArray teamsArray = new JsonArray();
        pInfo.getTeamsList().forEach( t ->{
            teamsArray.add(t.toString());
        });
        okjson.add("teamsList", teamsArray);


        okjson.addProperty("price", pInfo.getPrice());
        okjson.addProperty("clause", pInfo.getClause());

        JsonArray pointsArray = new JsonArray();
        pInfo.getPlayerPoints().forEach( p ->{
            JsonObject point = new JsonObject();
            point.addProperty("event", p.getId().getMatchid().toString());
            point.addProperty("points", p.getPoints());
            pointsArray.add(point);
        });

        okjson.add("playerPoints", pointsArray);

        if(pInfo.getOwnerUUID() != null) {
            okjson.addProperty("ownerUUID", pInfo.getOwnerUUID().toString());
            okjson.addProperty("ownerUsername", pInfo.getOwnerUsername());
            okjson.addProperty("ownerIcon", pInfo.getOwnerIcon());

        }
        else {
            okjson.addProperty("ownerUUID", "null");
            okjson.addProperty("ownerUsername", "null");
            okjson.addProperty("ownerIcon", "null");
        }





        return ResponseEntity.ok(okjson.toString());

    }

    public static ResponseEntity<?> getAllPlayersInfo(Set<PlayerInfoPOJO> players) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got all players info correctly");

        JsonArray playersArray = new JsonArray();
        players.forEach(player -> {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("uuid", player.getUuid().toString());
            playerJson.addProperty("name", player.getName());
            playerJson.addProperty("role", player.getRole());
            playerJson.addProperty("icon", player.getIcon());


            JsonArray teamsArray = new JsonArray();
            player.getTeamsList().forEach( t ->{
                teamsArray.add(t.toString());
            });
            playerJson.add("teamList", teamsArray);


            playerJson.addProperty("price", player.getPrice());

            playerJson.addProperty("clause", player.getClause());

            if(player.getOwnerUUID() != null){
                playerJson.addProperty("ownerUUID", player.getOwnerUUID().toString());
                playerJson.addProperty("ownerUsername", player.getOwnerUsername());
                playerJson.addProperty("ownerIcon", player.getOwnerIcon());
            }
            else{
                playerJson.addProperty("ownerUUID", "null");
                playerJson.addProperty("ownerUsername", "null");
                playerJson.addProperty("ownerIcon", "null");
            }

            JsonArray pointsArray = new JsonArray();

            player.getPlayerPoints().forEach( p ->{
                JsonObject point = new JsonObject();
                point.addProperty("event", p.getId().getMatchid().toString());
                point.addProperty("points", p.getPoints());
                pointsArray.add(point);
            });

            playerJson.add("playerPoints", pointsArray);

            playersArray.add(playerJson);
        });

        okjson.add("players", playersArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getTeamInfo(TeamInfoPOJO pInfo){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "got team info correctly");

        okjson.addProperty("uuid", pInfo.getUuid().toString());
        okjson.addProperty("name", pInfo.getName());
        okjson.addProperty("shortName", pInfo.getShortName());
        okjson.addProperty("game", pInfo.getGame());
        okjson.addProperty("icon", pInfo.getIcon());

        JsonArray playersArray = new JsonArray();
        pInfo.getPlayers().forEach( p ->{
            JsonObject player = new JsonObject();
            player.addProperty("uuid", p.getUuid().toString());
            player.addProperty("name", p.getUsername());
            player.addProperty("role", p.getRole());

            byte[] img = PlayerService.getPlayerIconStatic(p.getUuid().toString());

            String pIcon = Base64.getEncoder().encodeToString(img);

            player.addProperty("playerIcon", pIcon);


            playersArray.add(player);
        });

        okjson.add("players", playersArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> clausePlayer(){
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "player clausulated correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getRanking(RankingPOJO ranking) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got ranking correctly");

        JsonObject jourRanking = new JsonObject();
        JsonArray jourRankingArray = new JsonArray();
        ranking.getJourRank().forEach(userRank -> {
            JsonObject userRankJson = new JsonObject();
            userRankJson.addProperty("uuid", userRank.getUserUuid().toString());
            userRankJson.addProperty("username", userRank.getUsername());
            userRankJson.addProperty("icon", userRank.getIcon());
            userRankJson.addProperty("points", userRank.getPoints());
            jourRankingArray.add(userRankJson);
        });
        jourRanking.add("jourRank", jourRankingArray);

        JsonObject totalRanking = new JsonObject();
        JsonArray totalRankingArray = new JsonArray();
        ranking.getTotalRank().forEach(userRank -> {
            JsonObject userRankJson = new JsonObject();
            userRankJson.addProperty("uuid", userRank.getUserUuid().toString());
            userRankJson.addProperty("username", userRank.getUsername());
            userRankJson.addProperty("icon", userRank.getIcon());
            userRankJson.addProperty("points", userRank.getPoints());
            totalRankingArray.add(userRankJson);
        });
        totalRanking.add("totalRank", totalRankingArray);

        okjson.add("jourRanking", jourRanking);
        okjson.add("totalRanking", totalRanking);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getLeagueTransferPosts(List<TransferPostPOJO> transferPosts) {
       JsonObject okjson = new JsonObject();
         okjson.addProperty("result", "ok");
            okjson.addProperty("status", "200");
            okjson.addProperty("message", "got transfer posts correctly");

            JsonArray transferPostsArray = new JsonArray();
            transferPosts.forEach(transferPost -> {
                JsonObject transferPostJson = new JsonObject();
                transferPostJson.addProperty("transferPostId", transferPost.getTransferPostId());
                transferPostJson.addProperty("date", transferPost.getDate());
                transferPostJson.addProperty("playeruuid", transferPost.getPlayeruuid());
                transferPostJson.addProperty("playername", transferPost.getPlayername());
                transferPostJson.addProperty("playericon", transferPost.getPlayericon());
                transferPostJson.addProperty("leagueuuid", transferPost.getLeagueuuid());
                transferPostJson.addProperty("prevowneruuid", transferPost.getPrevowneruuid());
                transferPostJson.addProperty("prevownername", transferPost.getPrevownername());
                transferPostJson.addProperty("prevownericon", transferPost.getPrevownericon());

                if(transferPost.getClause() != 0){
                    transferPostJson.addProperty("clause", transferPost.getClause());
                    transferPostJson.addProperty("newowneruuid", transferPost.getNewowneruuid());
                    transferPostJson.addProperty("newownername", transferPost.getNewownername());
                    transferPostJson.addProperty("newownericon", transferPost.getNewownericon());
                } else {
                    JsonArray bidListArray = new JsonArray();
                    transferPost.getBidList().forEach(bid -> {
                        JsonObject bidJson = new JsonObject();
                        bidJson.addProperty("useruuid", bid.getUseruuid());
                        bidJson.addProperty("username", bid.getUsername());
                        bidJson.addProperty("usericon", bid.getUsericon());
                        bidJson.addProperty("bid", bid.getBid());
                        bidListArray.add(bidJson);
                    });

                    transferPostJson.add("bidList", bidListArray);

                }



                transferPostsArray.add(transferPostJson);
            });

            okjson.add("transferPosts", transferPostsArray);

            return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getCloseEvent(EventInfoPOJO event) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "Got event correctly");

        if(event == null) return ResponseEntity.ok(okjson.toString());

        okjson.addProperty("team1uuid", event.getTeam1uuid().toString());
        okjson.addProperty("team2uuid", event.getTeam2uuid().toString());
        okjson.addProperty("team1name", event.getTeam1name());
        okjson.addProperty("team2name", event.getTeam2name());
        okjson.addProperty("team1icon", event.getTeam1icon());
        okjson.addProperty("team2icon", event.getTeam2icon());
        okjson.addProperty("team1Score", event.getTeam1Score());
        okjson.addProperty("team2Score", event.getTeam2Score());
        okjson.addProperty("jour", event.getJour());
        okjson.addProperty("date", event.getDate().toString());

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getUserInfo(UserInfoPOJO user) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got user info correctly");

        okjson.addProperty("uuid", user.getUuid().toString());
        okjson.addProperty("mail", user.getMail());
        okjson.addProperty("username", user.getUsername());
        okjson.addProperty("admin", user.isAdmin());
        okjson.addProperty("icon", user.getIcon());

        return ResponseEntity.ok(okjson.toString());

    }

    public static ResponseEntity<?> updateUserInfo() {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "user info updated correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> leaveLeague() {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "201");
        okjson.addProperty("message", "user left league correctly");
        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getRLeagueTeams(Set<Team> teams) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got teams correctly");

        JsonArray teamsArray = new JsonArray();
        teams.forEach(team -> {
            JsonObject teamJson = new JsonObject();
            teamJson.addProperty("uuid", team.getUuid().toString());
            teamJson.addProperty("name", team.getName());
            teamJson.addProperty("shortName", team.getShortname());
            teamJson.addProperty("icon", team.getImage());
            teamsArray.add(teamJson);
        });

        okjson.add("teams", teamsArray);

        return ResponseEntity.ok(okjson.toString());
    }

    public static ResponseEntity<?> getUserLeaguesInvCodes(List<LeagueInvCodePOJO> invCodes) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got leagues inv codes correctly");

        JsonArray invCodesArray = new JsonArray();
        invCodes.forEach(invCode -> {
            JsonObject invCodeJson = new JsonObject();
            invCodeJson.addProperty("leagueUuid", invCode.getLeagueUuid().toString());
            invCodeJson.addProperty("code", invCode.getCode());
            invCodesArray.add(invCodeJson);
        });

        okjson.add("invCodes", invCodesArray);

        return ResponseEntity.ok(okjson.toString());
    }
}
