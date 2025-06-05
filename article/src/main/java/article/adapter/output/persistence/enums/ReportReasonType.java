package article.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportReasonType {

    BAD_MANNER("비매너 행위"),
    SPAM_ADVERTISING("스팸성 홍보"),
    ILLEGAL_ACTIVITY("불법 행위"),
    FRAUD("사기 피해"),
    INACCURATE_INFORMATION("정보 부정확"),
    OTHER("기타");

    private final String description;

}
