package htt.esportsfantasybe.responses;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** This class contains the responses for the unsuccessful requests.
 * @author Alberto Plaza Montes
 */
public class koresponses {

    private static final Map<Integer, String> koMap = new HashMap<>();

    static {
        koMap.put(1000, "Unknown error.");
        koMap.put(1001, "Non existent email.");
        koMap.put(1002, "Wrong password.");
        koMap.put(1003, "Invalid google token.");
        koMap.put(1004, "Already locally registered email.");
        koMap.put(1005, "Invalid email.");
        koMap.put(1006, "Mail already in use.");
        koMap.put(1007, "Unvalid password.");
        koMap.put(1008, "Couldn’t find a player.");
        koMap.put(1009, "Tournament not found.");
        koMap.put(1010, "Couldn’ find public leagues with specified tournament.");
        koMap.put(1011, "Error finding public league.");
        koMap.put(1012, "Expirated or invalid invitation code.");
        koMap.put(1013, "No league linked to this code.");
        koMap.put(1014, "This user is already in this league.");
        koMap.put(1015, "No games found.");
        koMap.put(1016, "No competitions found.");
        koMap.put(1017, "Competition not found.");
        koMap.put(1018, "Player not found.");
        koMap.put(1019, "Team not found.");
        koMap.put(1020, "Player in league not found.");
        koMap.put(1021, "Market entry not found.");
        koMap.put(1022, "Not enough money.");
    }

    public static ResponseEntity<?> generateKO(){
        return generateKO("1000");
    }

    public static ResponseEntity<?> generateKO(String koCode){
        JsonObject kojson = new JsonObject();
        kojson.addProperty("result", "ko");

        if(koCode == null || koCode.isEmpty()){
            kojson.addProperty("appStatus", "1000");
            kojson.addProperty("description", 1000);
        } else {
            kojson.addProperty("appStatus", koCode);
            kojson.addProperty("description", koMap.get(Integer.parseInt(koCode)));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(kojson.toString());
    }



}
