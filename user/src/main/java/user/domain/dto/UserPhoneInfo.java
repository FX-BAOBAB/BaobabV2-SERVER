package user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.adapter.output.persistence.enums.CarrierType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneInfo {

    private CarrierType carrierType;

    private String phoneNumber;

}
