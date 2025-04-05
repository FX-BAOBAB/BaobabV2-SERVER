package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.repository.document.UserChatDocument;
import chat.domain.dto.ChatRoomSearchForm;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static chat.adapter.output.persistence.repository.document.QUserChatDocument.*;

@Repository
public class UserChatQueryRepository extends QuerydslRepositorySupport {

    public UserChatQueryRepository(@Qualifier("mongoTemplate") MongoOperations operations) {
        super(operations);
    }

    public List<UserChatDocument> getUserChats(ChatRoomSearchForm form) {
        return from(userChatDocument)
            .where(
                userChatDocument.userId.eq(form.getUserId()),
                lastChatBefore(form.getLastChatAt())
            )
            .orderBy(getOrderSpecifier(form.getPageable().getSort()).toArray(new OrderSpecifier[0]))
            .limit(form.getPageable().getPageSize())
            .fetch();
    }

    private BooleanExpression lastChatBefore(LocalDateTime sentAt) {
        return sentAt != null ? userChatDocument.lastChatAt.before(sentAt) : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            PathBuilder orderByExpression = new PathBuilder(UserChatDocument.class,
                "userChatDocument");
            orders.add(new OrderSpecifier<>(order.isDescending() ? Order.DESC : Order.ASC,
                orderByExpression.get(order.getProperty())));
        });
        return orders;
    }

}
