package user.adapter.output.persistence;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private LocalDate birth;

    private String department;

    private String address;

    private String detailAddress;

    private String basicAddress;

    private String post;

    private Long imageId;

}
