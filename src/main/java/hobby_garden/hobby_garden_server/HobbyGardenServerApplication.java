package hobby_garden.hobby_garden_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// swagger path = http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
public class HobbyGardenServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HobbyGardenServerApplication.class, args);
	}
}