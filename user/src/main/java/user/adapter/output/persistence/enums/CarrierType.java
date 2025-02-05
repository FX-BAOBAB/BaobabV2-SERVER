package user.adapter.output.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarrierType {

    SKT("SKT"),
    SKT_MVNO("SKT 알뜰폰"),
    KT("KT"),
    KT_MVNO("KT 알뜰폰"),
    LGU_PLUS("LGU+"),
    LGU_PLUS_MVNO("LGU+ 알뜰폰");

    private final String description;

}