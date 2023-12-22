package hobby_garden.hobby_garden_server.user.model;

import hobby_garden.hobby_garden_server.common.enums.Roles;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.post.model.Post;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Node("User")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Property("user_id")
    private String userId;

    @Property("username")
    private String username;

    @Property("first_name_last_name")
    private String firstNameLastName;

    @Property("password")
    private String password;

    @Property("email")
    private String email;

    @Relationship(type = "HAS_HOBBY", direction = Relationship.Direction.OUTGOING)
    private List<Hobby> hobbies;

    @Relationship(type = "CREATED_POST", direction = Relationship.Direction.OUTGOING)
    private List<Post> posts;

    @Property("created_at")
    private LocalDateTime createdAt;

    @Property("role")
    private Roles role;

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
