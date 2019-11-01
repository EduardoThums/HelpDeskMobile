package help.desk.mobile.app.mock;

import java.util.Arrays;
import java.util.List;

import help.desk.mobile.app.model.Area;

public final class AreasMock {

    private static final Area[] MOCKED_AREAS = {
            new Area(1L, "Infraestrutura"),
            new Area(2L, "RH")
    };

    public static List<Area> getMockedAreas() {
        return Arrays.asList(MOCKED_AREAS);
    }
}
