package article.adapter.output.persistence.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleMongoRepository extends MongoRepository<Article, String> {

    List<Article> findFirstByUserId(String userId);

}
