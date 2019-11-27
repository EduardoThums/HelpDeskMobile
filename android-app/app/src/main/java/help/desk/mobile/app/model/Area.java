package help.desk.mobile.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Area implements Serializable {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
