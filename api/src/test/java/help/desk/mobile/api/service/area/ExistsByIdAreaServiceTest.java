package help.desk.mobile.api.service.area;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.repository.area.AreaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author eduardo.thums
 */
public class ExistsByIdAreaServiceTest extends AbstractUnitTest {

	@InjectMocks
	private ExistsByIdAreaService existsByIdAreaService;

	@Mock
	private AreaRepository areaRepository;

	@Test
	public void existsById() {
		// Arrange
		final Long id = 1L;

		BDDMockito
				.given(areaRepository.existsById(id))
				.willReturn(true);

		// Act
		final boolean response = existsByIdAreaService.existsById(id);

		// Assert
		Assert.assertTrue(response);
	}
}
