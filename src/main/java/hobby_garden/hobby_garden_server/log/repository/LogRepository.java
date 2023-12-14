package hobby_garden.hobby_garden_server.log.repository;

import hobby_garden.hobby_garden_server.log.model.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogModel, String> {
}
