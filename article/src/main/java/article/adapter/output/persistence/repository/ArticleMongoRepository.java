package article.adapter.output.persistence.repository;

import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleMongoRepository extends MongoRepository<Article, String> {

    List<Article> findAllByUserId(String userId, Pageable pageable);

    List<Article> findByIdInAndVisibilityStatus(List<String> articleIds,
        ArticleVisibilityStatus visibilityStatus);

    Optional<Article> findByIdAndVisibilityStatus(String articleId,
        ArticleVisibilityStatus visibilityStatus);

}
