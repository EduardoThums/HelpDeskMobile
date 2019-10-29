package help.desk.mobile.api.service.area;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.entity.AreaEntity;
import help.desk.mobile.api.mapper.AreaMapper;
import help.desk.mobile.api.repository.area.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eduardo.thums
 */
@Service
public class FindAllAreasService {

	private AreaRepository areaRepository;

	private AreaMapper areaMapper;

	public FindAllAreasService(AreaRepository areaRepository, AreaMapper areaMapper) {
		this.areaRepository = areaRepository;
		this.areaMapper = areaMapper;
	}

	public List<AreaDetailsResponse> findAllAreas() {
		final List<AreaEntity> areaEntities = areaRepository.findAll();

		return areaMapper.toAreaDetailsResponse(areaEntities);
	}
}
