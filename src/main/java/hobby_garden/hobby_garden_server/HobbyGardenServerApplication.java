package hobby_garden.hobby_garden_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

// swagger path = http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
public class HobbyGardenServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HobbyGardenServerApplication.class, args);
	}
}