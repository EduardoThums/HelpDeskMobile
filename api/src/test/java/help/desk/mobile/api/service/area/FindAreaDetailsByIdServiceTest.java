package help.desk.mobile.api.service.area;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.entity.AreaEntity;
import help.desk.mobile.api.exception.InvalidAreaException;
import help.desk.mobile.api.mapper.AreaMapper;
import help.desk.mobile.api.repository.area.AreaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public class FindAreaDetailsByIdServiceTest extends AbstractUnitTest {

	@InjectMocks
	private FindAreaDetailsByIdService findAreaDetailsByIdService;

	@Mock
	private AreaRepository areaRepository;

	@Mock
	private AreaMapper areaMapper;

	@Test
	public void findDetailsById() {
		// Arrange
		final Long id = 1L;

		final AreaEntity mockedAreaEntity = new AreaEntity();

		final AreaDetailsResponse mockedResponse = new AreaDetailsResponse();

		BDDMockito.given(areaRepository.findById(id))
				.willReturn(Optional.of(mockedAreaEntity));

		BDDMockito.given(areaMapper.toAreaDetailsResponse(mockedAreaEntity))
				.willReturn(mockedResponse);

		// Act
		final AreaDetailsResponse response = findAreaDetailsByIdService.findDetailsById(id);

		// Assert
		Assert.assertEquals(mockedResponse, response);
	}

	@Test(expected = InvalidAreaException.class)
	public void findDetailsById_throwsInvalidAreaException() {
		// Arrange
		final Long id = 1L;

		BDDMockito.given(areaRepository.findById(id))
				.willReturn(Optional.empty());

		// Act
		findAreaDetailsByIdService.findDetailsById(id);
	}
}
