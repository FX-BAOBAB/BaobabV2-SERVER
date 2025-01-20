package article.adapter.output.persistence.repository;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.domain.command.ArticleSearchCommand;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.util.List;
import static article.adapter.output.persistence.repository.QArticle.*;

@Repository
public class ArticleQueryRepository extends QuerydslRepositorySupport {

    public ArticleQueryRepository(@Qualifier("mongoTemplate") MongoOperations operations) {
        super(operations);
    }

    public List<Article> getArticlesBy(ArticleSearchCommand articleSearchCommand) {
        // 모든 조건이 비어있는 경우 모든 아티클 반환
        if (isSearchCommandEmpty(articleSearchCommand)) {
            return from(article).fetch();
        }

        BooleanBuilder builder = buildQueryConditions(articleSearchCommand);

        return from(article)
            .where(builder)
//            .orderBy(article.registeredAt.desc()) // 응답에서 정렬되어 있지 않아 주석 처리
            .fetch();
    }

    private boolean isSearchCommandEmpty(ArticleSearchCommand command) {
        return command.getTitle() == null &&
            command.getContent() == null &&
            command.getCategory() == null;
    }

    private BooleanBuilder buildQueryConditions(ArticleSearchCommand command) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!isTitleEmpty(command.getTitle())) {
            builder.and(article.title.like("%" + command.getTitle() + "%"));
        }

        if (!isContentEmpty(command.getContent())) {
            builder.and(article.content.like("%" + command.getContent() + "%"));
        }

        if (!isCategoryEmpty(command.getCategory())) {
            builder.and(article.category.eq(command.getCategory()));
        }

        return builder;
    }

    private boolean isTitleEmpty(String title) { return title == null; }

    private boolean isContentEmpty(String content) { return content == null; }

    private boolean isCategoryEmpty(ArticleCategory category) { return category == null; }

}