package help.desk.mobile.api.mapper;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.user.response.UserDetailsResponse;
import help.desk.mobile.api.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * @author eduardo.thums
 */
@Component
public class UserMapper {

    public UserDetailsResponse toUserDetailsResponse(UserEntity userEntity, AreaDetailsResponse areaDetailsResponse) {
        return UserDetailsResponse
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .cpf(userEntity.getCpf())
                .phone(userEntity.getPhone())
                .role(userEntity.getProfile())
                .area(areaDetailsResponse)
                .build();
    }
}
