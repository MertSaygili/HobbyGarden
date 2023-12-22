package hobby_garden.hobby_garden_server.hobby.dto.response;

import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import lombok.Data;

import java.util.List;

@Data
public class AllHobbiesResponse {
    List<Hobby> hobbies;
}
