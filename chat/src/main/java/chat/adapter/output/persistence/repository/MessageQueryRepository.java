package chat.adapter.output.persistence.repository;

import chat.adapter.output.persistence.enums.MessageType;
import chat.adapter.output.persistence.repository.document.MessageDocument;
import chat.domain.dto.ChatMessageSearchForm;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static chat.adapter.output.persistence.repository.document.QMessageDocument.*;

@Slf4j
@Repository
public class MessageQueryRepository extends QuerydslRepositorySupport {

    public MessageQueryRepository(@Qualifier("mongoTemplate") MongoOperations operations) {
        super(operations);
    }

    public List<MessageDocument> getMessages(ChatMessageSearchForm form) {
        return from(messageDocument)
            .where(
                messageDocument.chatRoomId.eq(form.getChatRoomId()),
                containsMessage(form.getMessage()),
                eqMessageType(form.getMessageType()),
                sentBefore(form.getSentAt())
            )
            .orderBy(getOrderSpecifier(form.getPageable().getSort()).toArray(new OrderSpecifier[0]))
            .limit(form.getPageable().getPageSize())
            .fetch();
    }

    private BooleanExpression containsMessage(String message) {
        return message != null ? messageDocument.message.contains(message) : null;
    }

    private BooleanExpression eqMessageType(MessageType messageType) {
        return messageType != null ? messageDocument.messageType.eq(messageType) : null;
    }

    private BooleanExpression sentBefore(LocalDateTime sentAt) {
        return sentAt != null ? messageDocument.sentAt.before(sentAt) : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            PathBuilder orderByExpression = new PathBuilder(MessageDocument.class,
                "messageDocument");
            orders.add(new OrderSpecifier<>(order.isDescending() ? Order.DESC : Order.ASC,
                orderByExpression.get(order.getProperty())));
        });
        return orders;
    }

}