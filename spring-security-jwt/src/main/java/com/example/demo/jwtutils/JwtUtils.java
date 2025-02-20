package com.example.demo.jwtutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    /**
     * The getSigningKey method returns a SecretKey that is used to sign JWTs.
     * This method converts a predefined secret key string (SECRET_KEY) into a SecretKey object using the JJWT library.
     *
     * @return The method returns a SecretKey object.
     */
    public SecretKey getSigningKey() {
        /**
         * Keys.hmacShaKeyFor(...):
         *
         * This is a static method provided by the JJWT library (io.jsonwebtoken.security.Keys class).
         *
         * It generates a SecretKey for use with HMAC-SHA algorithms
         *
         * SECRET_KEY.getBytes():
         *
         * Converts the SECRET_KEY string to a byte array.
         *
         * The string should be of sufficient length to ensure the security of the generated key. For example, HMAC-SHA256 requires at least 32 bytes (256 bits).
         */
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * The generateToken method generates a JWT token for a given username. It initializes an empty map for claims and calls the createToken method to create the JWT.
     *
     * @param username: A String representing the username for which the token is generated.
     * @return The method returns a String, which is the generated JWT.
     */
    public String generateToken(String username) {

        /**
         * Creates an empty HashMap to hold any claims that need to be included in the token.
         * In this case, it's initialized as an empty map since no specific claims are provided.
         */
        Map<String, Object> claims = new HashMap<>();
        /**
         * Calls the createToken method, passing the empty claims map and the username. 
         * This method generates the JWT token using the provided claims and username.
         */
        return createToken(claims, username);
    }

    /**
     * The createToken method generates a JSON Web Token (JWT) using the JJWT library.
     * It takes a map of claims and a username as input
     * and creates a signed JWT with specific headers, issued time, and expiration time.
     * Parameters:
     * map: A map containing the claims to be included in the JWT.
     * username: The subject or username to be included in the JWT
     *
     * @return
     */
    public String createToken(Map<String, Object> map, String username) {
        return Jwts.builder()
                .claims(map)   /**
                 * Sets the claims for the JWT using the provided map. These claims are additional information you want to include in the payload of the token
                 */
                .subject(username) //Sets the subject of the JWT, typically used to identify the principal that is the subject of the token (e.g., the username).
                .header().empty()  //header().empty() clears the default headers.
                .add("typ", "JWT")  //add("typ", "JWT") adds the header parameter "typ" with the value "JWT".
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))  //Issued At: Sets the token's issuance time to the current time.
                .expiration(new Date(System.currentTimeMillis() + 1000 * 30)) //Expiration: Sets the token's expiration time to 50 minutes from the current time (1000 * 60 * 50 milliseconds)
                .signWith(getSigningKey()) //Signs the JWT with the key obtained from the getSigningKey() method.
                .compact(); //Compacts the JWT into a URL-safe string representation.
    }

    /**
     * The extractAllClaims method is designed to parse a JWT token and extract all claims (payload) from it. It verifies the token using a signing key.
     * @param token String token, the JWT token from which you want to extract the claims.
     * @return Claims, which represents the claims set (payload) of the JWT.
     */
    private Claims extractAllClaims(String token){
        
        return Jwts.parser()  //Initializes the JWT parser provided by the JJWT library.
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)//Parses the JWT and verifies its signature.
                .getPayload();
    }


    /**
     * The extractUsername method is designed to extract the username (or subject) from a JWT token. 
     * It uses the extractAllClaims method to retrieve the claims from the token and then gets the subject from these claims.
     * @param token the JWT token from which the username (subject) will be extracted.
     * @return String, which represents the username extracted from the JWT token.
     */
    public String extractUsername(String token)
    {
        /**
         * Calls the extractAllClaims method, passing the JWT token as a parameter.
         * Purpose: The extractAllClaims method parses the token, verifies its signature, and returns the claims contained in the token.
         */
        Claims claims = extractAllClaims(token);

        /**
         * Retrieves the subject (username) from the claims using the getSubject method.
         * Purpose: The subject is a standard claim in JWT that typically represents the user or principal associated with the token.
         */
        return claims.getSubject();
    }

    /**
     * The extractExpiration method is designed to extract the expiration date from a JWT token. 
     * It uses the extractAllClaims method to retrieve the claims from the token and then gets the expiration date from these claims
     * @param token the JWT token from which the expiration date will be extracted.
     * @return Date, which represents the expiration date extracted from the JWT token.
     */
   private Date extractExpiration(String token)
    {
        /**
         * Calls the extractAllClaims method, passing the JWT token as a parameter.
         * Purpose: The extractAllClaims method parses the token, verifies its signature, and returns the claims contained in the token.
         */
        Claims claims = extractAllClaims(token);

        /**
         * Retrieves the expiration date from the claims using the getExpiration method.
         * Purpose: The expiration date is a standard claim in JWT that represents the time after which the token should no longer be considered valid.
         */
        Date expirationDate = claims.getExpiration();
        
        return expirationDate;
        
    }

    /**
     * The isTokenExpired method checks whether a given JWT token has expired.
     * @param token String token, the JWT token to be checked for expiration.
     * @return boolean, which indicates whether the token has expired (true) or not (false).
     */
    private boolean isTokenExpired(String token)
    {
        /**
         * Purpose: Calls the extractExpiration method to retrieve the expiration date of the token.
         * Comparison: Compares the expiration date with the current date (new Date()).
         * Result: Returns true if the token's expiration date is before the current date, indicating that the token has expired. Otherwise, it returns false.
         */
        Date expirationDate = extractExpiration(token);
        
        log.info("expiration date of jwt token is "+expirationDate);
        
       boolean result = expirationDate.before(new Date());
       log.info("token expiration result "+result);
        return  result;
    }

    /**
     * The validateToken method checks whether a given JWT token is valid by determining if it has not expired.
     * @param token String token, the JWT token to be validated.
     * @return Boolean, which indicates whether the token is valid (true) or not (false).
     */
    public Boolean validateToken(String token)
    {
        /**
         * Purpose: Calls the isTokenExpired method to check if the token has expired.
         * Negation: Returns true if the token is not expired, indicating that the token is valid. Otherwise, it returns false.
         */
        log.info("validateToken method executed");
        return !isTokenExpired(token);
    }
}
