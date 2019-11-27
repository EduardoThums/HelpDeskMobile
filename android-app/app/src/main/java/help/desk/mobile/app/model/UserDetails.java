package help.desk.mobile.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String cpf;

    private String phone;

    private ProfileType role;

    private Area area;
}