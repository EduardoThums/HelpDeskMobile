package help.desk.mobile.app.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequest {

    private String phone;

    private Long areaId;

    private String password;

    private String confirmPassword;
}
