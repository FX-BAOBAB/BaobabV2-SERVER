package user.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCommand {

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

}
