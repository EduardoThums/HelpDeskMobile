package help.desk.mobile.api.controller.area;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.service.area.FindAllAreasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/public/area")
public class AreaPublicController {

	private FindAllAreasService findAllAreasService;

	public AreaPublicController(FindAllAreasService findAllAreasService) {
		this.findAllAreasService = findAllAreasService;
	}

	@GetMapping
	public ResponseEntity<List<AreaDetailsResponse>> findAll() {
		return ResponseEntity.ok(findAllAreasService.findAllAreas());
	}
}
