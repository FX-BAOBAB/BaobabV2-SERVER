package article.adapter.output.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleReportRepository extends MongoRepository<ArticleReport, String> {

    boolean existsByArticleIdAndUserId(String articleId, String userId);

}
