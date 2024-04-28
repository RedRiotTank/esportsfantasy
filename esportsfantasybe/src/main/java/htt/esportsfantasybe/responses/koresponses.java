package htt.esportsfantasybe.responses;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** This class contains the responses for the unsuccessful requests.
 * @author Alberto Plaza Montes
 */
public class koresponses {

    /** This method returns a response for an unsuccessful request (creation of a new user).
     * @param codeS This is the code of the error.
     * @return ResponseEntity<?> This returns the response for the unsuccessful request.
     */
    public static ResponseEntity<?> createdNewUser(String codeS){

        int code = Integer.parseInt(codeS);

        JsonObject kojson = new JsonObject();
        kojson.addProperty("result", "ko");
        kojson.addProperty("status", "400");
        kojson.addProperty("appStatus", code);

        String message = switch (code) {
            case 601 -> "Unvalid email";
            case 602 -> "Mail already in use";
            case 603 -> "Unvalid password";
            default -> "unknow error";
        };

        kojson.addProperty("message", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(kojson.toString());
    }

    /** This method returns a response for an unsuccessful request (login of a user).
     * @param codeS This is the code of the error.
     * @return ResponseEntity<?> This returns the response for the unsuccessful request.
     */
    public static ResponseEntity<?> login(String codeS){

        int code = Integer.parseInt(codeS);

        JsonObject kojson = new JsonObject();
        kojson.addProperty("result", "ko");
        kojson.addProperty("status", "400");
        kojson.addProperty("appStatus", code);

        String message = switch (code) {
            case 610 -> "Inexistent email";
            case 611 -> "Wrong password";
            default -> "unknow error";
        };

        kojson.addProperty("message", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(kojson.toString());
    }

    /** This method returns a response for an unsuccessful request (login of a user).
     * @param codeS This is the code of the error.
     * @return ResponseEntity<?> This returns the response for the unsuccessful request.
     */
    public static ResponseEntity<?> googleLogin(String codeS){

        int code = Integer.parseInt(codeS);

        JsonObject kojson = new JsonObject();
        kojson.addProperty("result", "ko");
        kojson.addProperty("status", "400");
        kojson.addProperty("appStatus", code);

        String message = switch (code) {
            case 620 -> "Invalid google token";
            case 621 -> "Locally registered email";
            default -> "unknow error";
        };

        kojson.addProperty("message", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(kojson.toString());

    }

    public static ResponseEntity<?> joinLeague(){
        JsonObject kojson = new JsonObject();
        kojson.addProperty("result", "ko");
        kojson.addProperty("status", "400");
        kojson.addProperty("message", "User join to league failed");
        return ResponseEntity.ok(kojson.toString());
    }

}
