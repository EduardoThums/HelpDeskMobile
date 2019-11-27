package help.desk.mobile.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileType implements Serializable {

    ADMIN("ROLE_ADMIN"),
    DEFAULT("ROLE_DEFAULT");

    private final String role;

}
