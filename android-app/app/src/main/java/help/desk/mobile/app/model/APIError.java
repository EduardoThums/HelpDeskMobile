package help.desk.mobile.app.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIError {

    private Date timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

}
