package hobby_garden.hobby_garden_server.config;

import hobby_garden.hobby_garden_server.common.enums.Roles;
import hobby_garden.hobby_garden_server.common.security.ApplicationFilter;
import hobby_garden.hobby_garden_server.common.security.JwtTokenFilter;
import hobby_garden.hobby_garden_server.user.service.CustomUserDetailsService;
import hobby_garden.hobby_garden_server.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
<<<<<<< HEAD

    private final ApplicationFilter applicationFilter;
    private final UserService userService;
    public static String[] whiteList = new String[] { "/swagger-ui/index.html", "/swagger-resources/**",
            "/v2/api-docs**", "/webjars/**", "/swaggerfox.js", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/**"};
=======
    private final JwtTokenFilter jwtTokenFilter;
    private final CustomUserDetailsService userService;

>>>>>>> add553c72abf9e54a352794a784fb2b79067e52f
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
<<<<<<< HEAD
                        .requestMatchers(whiteList).permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/**")
                        .hasAnyAuthority(Roles.ADMIN.name()).requestMatchers("/api/**")
                        .hasAnyAuthority(Roles.USER.name())
                        .anyRequest().authenticated()

                )
                // white list

                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
=======
                        .requestMatchers("/api/user/**")
                        .permitAll()
                        .requestMatchers("/api/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.USER.name())
                        .anyRequest().authenticated()

                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
>>>>>>> add553c72abf9e54a352794a784fb2b79067e52f
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
