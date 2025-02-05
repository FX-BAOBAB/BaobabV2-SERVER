package user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import user.adapter.output.persistence.enums.CarrierType;

@Data
@Builder
@AllArgsConstructor
public class UserPhoneInfo {

    private CarrierType carrierType;

    private String phoneNumber;

}
