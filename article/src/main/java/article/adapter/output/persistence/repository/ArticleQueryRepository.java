package article.adapter.output.persistence.repository;

import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.domain.command.ArticleSearchCommand;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Article> getArticlesBy(ArticleSearchCommand articleSearchCommand,
        ArticleVisibilityStatus visibilityStatus) {

        BooleanBuilder builder = buildQueryConditions(articleSearchCommand, visibilityStatus);

        Pageable pageable = articleSearchCommand.getPageable();
        if (pageable.isUnpaged()) {
            return from(article)
                .where(builder)
                .orderBy(article.registeredAt.desc())
                .fetch();
        }

        return from(article)
            .where(builder)
            .orderBy(orderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public List<Article> getBookmarkedArticlesByUserId(String userId) {
        BooleanBuilder builder = buildQueryConditions(userId);

        return from(article)
            .where(builder)
            .orderBy(article.registeredAt.desc())
            .fetch();
    }

    private BooleanBuilder buildQueryConditions(ArticleSearchCommand command,
        ArticleVisibilityStatus visibilityStatus) {

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
            builder.and(article.saleStatus.eq(command.getStatus()));
        }

        if(!isArticleIdEmpty(command.getArticleId())) {
            builder.and(article.id.eq(command.getArticleId()));
        }

        builder.and(article.visibilityStatus.eq(visibilityStatus));

        return builder;
    }

    private BooleanBuilder buildQueryConditions(String userId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!isUserIdEmpty(userId)) {
            builder.and(article.bookmarkUserIdList.contains(userId));
        }

        builder.and(article.visibilityStatus.eq(ArticleVisibilityStatus.VISIBILITY));

        return builder;
    }

    private List<OrderSpecifier<?>> orderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        PathBuilder<Article> orderByExpression = new PathBuilder<>(Article.class, "article");

        NumberPath<Integer> pricePath = orderByExpression.getNumber("price", Integer.class);
        DateTimePath<Date> registeredAtPath = orderByExpression.getDateTime("registeredAt",
            java.util.Date.class);

        // 가격, 등록일자만 정렬
        sort.stream().forEach(order -> {
            if ("price".equals(order.getProperty())) {
                orders.add(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    pricePath
                ));
            }

            if ("registeredAt".equals(order.getProperty())) {
                orders.add(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    registeredAtPath
                ));
            }
        });

        return orders;
    }

    private boolean isTitleEmpty(String title) { return StringUtils.isEmpty(title); }

    private boolean isContentEmpty(String content) { return StringUtils.isEmpty(content); }

    private boolean isCategoryEmpty(ArticleCategory category) { return category == null; }

    private boolean isUserIdEmpty(String userId) { return StringUtils.isEmpty(userId); }

    private boolean isStatusEmpty(ArticleSaleStatus status) { return status == null; }

    private boolean isArticleIdEmpty(String articleId) { return StringUtils.isEmpty(articleId); }


}