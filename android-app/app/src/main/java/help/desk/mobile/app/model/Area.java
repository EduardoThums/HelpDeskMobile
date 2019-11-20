package help.desk.mobile.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
