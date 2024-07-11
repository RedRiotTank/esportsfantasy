package htt.esportsfantasybe.responses;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonElement;
import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.DTO.GamesDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.model.pojos.EventInfoPOJO;
import htt.esportsfantasybe.model.pojos.PlayerInfoPOJO;
import htt.esportsfantasybe.model.pojos.PlayerTeamInfoPOJO;
import htt.esportsfantasybe.model.pojos.UserLeagueInfoPOJO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            playerJson.addProperty("points", player.getPoints());
            playerJson.addProperty("clause", player.getClause());
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

    public static ResponseEntity<?> teamInfo(Set<PlayerTeamInfoPOJO> teamInfo) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got team info correctly");

        JsonArray playersArray = new JsonArray();

        teamInfo.forEach(player -> {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("uuid", player.getPlayeruuid().toString());
            playerJson.addProperty("username", player.getUsername());
            playerJson.addProperty("fullname", player.getFullname());
            playerJson.addProperty("role", player.getRole());
            playerJson.addProperty("jour", player.getJour());
            playerJson.addProperty("aligned", player.getAligned());
            playerJson.addProperty("icon", player.getIcon());
            playersArray.add(playerJson);
        });

        okjson.add("players", playersArray);


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

    public static ResponseEntity<?> getRLeagueCurrentJour(int currentJour) {
        JsonObject okjson = new JsonObject();
        okjson.addProperty("result", "ok");
        okjson.addProperty("status", "200");
        okjson.addProperty("message", "got current jour correctly");
        okjson.addProperty("currentJour", currentJour);

        return ResponseEntity.ok(okjson.toString());
    }
}
