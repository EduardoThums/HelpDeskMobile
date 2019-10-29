package help.desk.mobile.api.mapper;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.entity.AreaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Component
public class AreaMapper {

	public List<AreaDetailsResponse> toAreaDetailsResponse(List<AreaEntity> areaEntities) {
		return areaEntities
				.stream()
				.map(areaEntity -> AreaDetailsResponse
						.builder()
						.id(areaEntity.getId())
						.name(areaEntity.getName())
						.build())
				.collect(Collectors.toList());
	}
}
