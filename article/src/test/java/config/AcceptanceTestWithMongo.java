package config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootTest
@EnableMongoTestServer
@EnableMongoRepositories(
    basePackages = {
        "article.adapter.output.persistence.repository",
        "file.adapter.output.repository"
    }
)public abstract class AcceptanceTestWithMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    public void setup() {
        mongoTemplate.getDb().drop();
    }

}
