package hobby_garden.hobby_garden_server.common.mapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordMapper {

    //* PasswordEncoder is an interface, BCryptPasswordEncoder is a class that implements PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    public PasswordMapper() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //* encode() and matches() are methods of BCryptPasswordEncoder
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}

