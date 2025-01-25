package article.adapter.output.persistence.repository;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleStatus;
import article.domain.command.ArticleSearchCommand;
import com.querydsl.core.BooleanBuilder;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.util.List;
import static article.adapter.output.persistence.repository.QArticle.*;

@Slf4j
@Repository
public class ArticleQueryRepository extends QuerydslRepositorySupport {

    public ArticleQueryRepository(@Qualifier("mongoTemplate") MongoOperations operations) {
        super(operations);
    }

    public List<Article> getArticlesBy(ArticleSearchCommand articleSearchCommand) {
        /*// 모든 조건이 비어있는 경우 모든 아티클 반환
        if (isSearchCommandEmpty(articleSearchCommand)) {
            return from(article).fetch();
        }*/

        BooleanBuilder builder = buildQueryConditions(articleSearchCommand);

        Pageable pageable = articleSearchCommand.getPageable();

        return from(article)
            .where(builder)
//            .orderBy(article.registeredAt.desc()) // 응답에서 정렬되어 있지 않아 주석 처리
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
            .fetch();
    }

    // 왜 필요?
    private boolean isSearchCommandEmpty(ArticleSearchCommand command) {
        return command.getTitle() == null
                && command.getContent() == null
                && command.getCategory() == null
                && command.getStatus() == null
                && command.getUserId() == null;
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

        if(!isUserIdEmpty(command.getUserId())) {
            builder.and(article.userId.eq(command.getUserId()));
        }

        if(!isStatusEmpty(command.getStatus())) {
            builder.and(article.status.eq(command.getStatus()));
        }

        if(!isArticleIdEmpty(command.getArticleId())) {
            log.info("Article id Check : {} ",article.id.eq(command.getArticleId()));
            builder.and(article.id.eq(command.getArticleId()));
        }

        return builder;
    }

    private boolean isTitleEmpty(String title) { return title == null; }

    private boolean isContentEmpty(String content) { return content == null; }

    private boolean isCategoryEmpty(ArticleCategory category) { return category == null; }

    private boolean isUserIdEmpty(String userId) { return StringUtils.isEmpty(userId); }

    private boolean isStatusEmpty(ArticleStatus status) { return status == null; }

    private boolean isArticleIdEmpty(String articleId) { return StringUtils.isEmpty(articleId); }


}