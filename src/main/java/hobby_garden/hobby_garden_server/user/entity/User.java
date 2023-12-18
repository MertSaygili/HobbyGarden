package hobby_garden.hobby_garden_server.user.entity;

import hobby_garden.hobby_garden_server.common.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@Node("User")
public class User implements UserDetails {

    @GeneratedValue(UUIDStringGenerator.class)
    @Id
    private String userId;

    @Property("username")
    private String username;

    @Property("first_name_last_name")
    private String firstNameLastName;

    @Property("password")
    private String password;

    @Property("email")
    private String email;

    @Property("hobbies")
    private List<String> hobbies;

    @Property("created_at")
    private LocalDateTime createdAt;

    //* JWT token implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Roles.USER.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
