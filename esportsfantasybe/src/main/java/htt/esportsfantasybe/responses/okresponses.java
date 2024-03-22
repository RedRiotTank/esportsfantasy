package htt.esportsfantasybe.responses;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;

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
}
