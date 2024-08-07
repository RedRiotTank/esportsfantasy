package htt.esportsfantasybe.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import htt.esportsfantasybe.DTO.SocialUserDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * JwtService class.
 * @author Alberto Plaza Montes.
 */
@Service
public class JwtService {

    private final static String SECRET_KEY = "c272ddf8bc884ed30e27e06dac182b2c80a49c36a1b146c569c43005a9af8e02";

    private final static int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 24;

    /**
     * Method that extracts the user email from the token.
     * @param token String.
     * @return String.
     */
    public String extractUserEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Method that generates a token.
     * @param userDTO UserDTO.
     * @return String.
     */
    public String generateToken(UserDTO userDTO){
       return generateToken(new HashMap<>(),userDTO);
    }

    /**
     * Method that generates a token.
     * @param socialUserDTO SocialUserDTO.
     * @return String.
     */
    public String generateToken(SocialUserDTO socialUserDTO){

        UserDTO userDTO = new UserDTO(socialUserDTO);

        return generateToken(new HashMap<>(),userDTO);
    }
    /**
     * Method that generates a token.
     * @param extraClaims Map<String, Object>.
     * @param userDTO UserDTO.
     * @return String.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDTO userDTO){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDTO.getMail())
                .setId(userDTO.getUuid().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * Method that extracts a claim from the token.
     * @param token String.
     * @param claimsResolver Function<Claims, T>.
     * @param <T> T.
     * @return T.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims =  extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method that extracts all the claims from the token.
     * @param token String.
     * @return Claims.
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Method that gets the sign in key.
     * @return Key.
     */

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Method that checks if the token is valid.
     * @param token String.
     * @param user UserDetails.
     * @return boolean.
     */
    public boolean isTokenValid(String token, UserDetails user){
        final String usermail = extractUserEmail(token);

        return (!isTokenExpired(token));

    }

    /**
     * Method that checks if the token is expired.
     * @param token String.
     * @return boolean.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Method that extracts the expiration date from the token.
     * @param token String.
     * @return Date.
     */
    private Date extractExpiration(String token) {
       return extractClaim(token, Claims::getExpiration);
    }


    // Social Login

    /**
     * Method that verifies a Google token.
     * @param idTokenString String.
     * @return boolean.
     */
    public boolean verifyGoogleToken(String idTokenString) {
        try {
            String GOOGLE_CLIENT_ID = "860986754360-fqq5h0sfu5abhccvp7u1djv0h23rr80c.apps.googleusercontent.com";
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                return true;
            } else {
                return false;
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
