package article.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleCategory {

    DIGITAL_DEVICES("디지털기기"),
    FURNITURE("가구"),
    BABY_PRODUCTS("유아용품"),
    CLOTHING("의류"),
    ELECTRONICS("가전"),
    HOME_KITCHEN("생활/주방"),
    SPORTS_LEISURE("스포츠/레저"),
    BEAUTY("뷰티"),
    PLANTS("식물"),
    PROCESSED_FOODS("가공식품"),
    HEALTH_SUPPLEMENTS("건강기능식품"),
    BOOKS("도서"),
    OTHERS("기타")
    ;

    private final String description;

}
