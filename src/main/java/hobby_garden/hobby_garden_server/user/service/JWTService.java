package hobby_garden.hobby_garden_server.user.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
