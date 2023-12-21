package hobby_garden.hobby_garden_server.hobby.controller;

import hobby_garden.hobby_garden_server.hobby.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/hobby")
@RequiredArgsConstructor
public class HobbyController {
    private final HobbyService hobbyService;
}
