package help.desk.mobile.api.controller.user.response;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.type.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author eduardo.thums
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

    private Long id;

    private String name;

    private String email;

    private String cpf;

    private String phone;

    private ProfileType role;

    private AreaDetailsResponse area;
}
