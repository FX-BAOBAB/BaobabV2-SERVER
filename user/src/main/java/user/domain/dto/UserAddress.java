package user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserAddress {

    private String address;

    private String detailAddress;

    private Boolean basicAddress;

    private String post;

}
