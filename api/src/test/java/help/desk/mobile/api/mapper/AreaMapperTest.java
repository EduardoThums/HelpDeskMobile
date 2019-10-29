package help.desk.mobile.api.mapper;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.domain.entity.AreaEntity;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Collections;
import java.util.List;

/**
 * @author eduardo.thums
 */
public class AreaMapperTest extends AbstractUnitTest {

	@InjectMocks
	private AreaMapper areaMapper;

	@Test
	public void toAreaDetailsResponseList() {
		// Arrange
		final Long id = 1L;
		final String name = "name";

		final AreaEntity areaEntity = new AreaEntity();
		areaEntity.setId(id);
		areaEntity.setName(name);

		// Act
		final List<AreaDetailsResponse> responseList = areaMapper.toAreaDetailsResponseList(Collections.singletonList(areaEntity));

		// Assert
		Assert.assertFalse(responseList.isEmpty());

		final AreaDetailsResponse response = responseList.get(0);
		Assert.assertEquals(name, response.getName());
		Assert.assertEquals(id, response.getId());
	}

	@Test
	public void toAreaDetailsResponse() {
		// Arrange
		final Long id = 1L;
		final String name = "name";

		final AreaEntity areaEntity = new AreaEntity();
		areaEntity.setId(id);
		areaEntity.setName(name);

		// Act
		final AreaDetailsResponse response = areaMapper.toAreaDetailsResponse(areaEntity);

		// Assert
		Assert.assertEquals(id, response.getId());
		Assert.assertEquals(name, response.getName());
	}
}
