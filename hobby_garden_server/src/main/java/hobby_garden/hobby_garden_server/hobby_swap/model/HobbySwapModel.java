package hobby_garden.hobby_garden_server.hobby_swap.model;

import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;


@Data
@Node("TalentSwap")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HobbySwapModel {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Property("talent_swap_id")
    String talentSwapId;


    @Property("main_user")
    @Relationship(type = "REQUESTED_BY", direction = Relationship.Direction.INCOMING)
    User mainUser;


    @Property("requested_user")
    @Relationship(type = "REQUESTED_FROM", direction = Relationship.Direction.OUTGOING)
    User requestedUser;

    @Property("is_approved")
    boolean isApproved;

    @Property("request_date")
    LocalDateTime requestedDate;

    Hobby requestedHobby;
}
