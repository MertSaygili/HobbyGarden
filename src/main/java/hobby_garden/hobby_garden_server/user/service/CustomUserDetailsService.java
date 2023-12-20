package hobby_garden.hobby_garden_server.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService {
    UserDetailsService userDetailsService();
}
