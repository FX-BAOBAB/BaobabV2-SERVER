package user.adapter.output.persistence.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.domain.command.AddressCommand;
import user.domain.command.UserUpdateCommand;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    public Address updateAddressInfo(AddressCommand addressCommand) {
        addressCommand.setAddress(addressCommand.getAddress());
        addressCommand.setDetailAddress(addressCommand.getDetailAddress());
        addressCommand.setBasicAddress(addressCommand.getBasicAddress());
        addressCommand.setPost(addressCommand.getPost());
        return this;
    }

}
