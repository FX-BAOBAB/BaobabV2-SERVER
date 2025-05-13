package user.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootTest
@EnableMongoTestServer
@EnableMongoRepositories(
    basePackages = {
        "user.adapter.output.persistence.repository",
        "file.adapter.output.repository"
    }
)
public abstract class AcceptanceTestWithMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        mongoTemplate.getDb().drop();
    }

}