package article.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleSaleStatus {

    ON_SALE("판매 중"),
    RESERVED("예약 중"),
    SOLD_OUT("판매 완료"),
    ;

    private final String description;
}
