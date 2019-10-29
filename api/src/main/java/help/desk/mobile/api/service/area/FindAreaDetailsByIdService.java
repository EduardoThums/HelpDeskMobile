package help.desk.mobile.api.service.area;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.entity.AreaEntity;
import help.desk.mobile.api.exception.InvalidAreaException;
import help.desk.mobile.api.mapper.AreaMapper;
import help.desk.mobile.api.repository.area.AreaRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class FindAreaDetailsByIdService {

	private AreaRepository areaRepository;

	private AreaMapper areaMapper;

	public FindAreaDetailsByIdService(AreaRepository areaRepository, AreaMapper areaMapper) {
		this.areaRepository = areaRepository;
		this.areaMapper = areaMapper;
	}

	public AreaDetailsResponse findDetailsById(Long id) {
		final AreaEntity areaEntity = areaRepository.findById(id)
				.orElseThrow(InvalidAreaException::new);

		return areaMapper.toAreaDetailsResponse(areaEntity);
	}
}
