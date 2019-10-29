package help.desk.mobile.api.service.area;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.entity.AreaEntity;
import help.desk.mobile.api.mapper.AreaMapper;
import help.desk.mobile.api.repository.area.AreaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

/**
 * @author eduardo.thums
 */
public class FindAllAreasServiceTest extends AbstractUnitTest {

	@InjectMocks
	private FindAllAreasService findAllAreasService;

	@Mock
	private AreaRepository areaRepository;

	@Mock
	private AreaMapper areaMapper;

	@Test
	public void findAllAreas() {
		// Arrange
		final AreaEntity mockedAreaEntity = new AreaEntity();

		final AreaDetailsResponse mockedResponse = new AreaDetailsResponse();

		BDDMockito.given(areaRepository.findAll())
				.willReturn(Collections.singletonList(mockedAreaEntity));


		BDDMockito.given(areaMapper.toAreaDetailsResponseList(Collections.singletonList(mockedAreaEntity)))
				.willReturn(Collections.singletonList(mockedResponse));

		// Act
		final List<AreaDetailsResponse> responseList = findAllAreasService.findAllAreas();

		// Assert
		Assert.assertFalse(responseList.isEmpty());

		final AreaDetailsResponse response = responseList.get(0);
		Assert.assertEquals(mockedResponse, response);
	}
}
