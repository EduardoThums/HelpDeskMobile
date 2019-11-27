package help.desk.mobile.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"pageable", "pageable", "totalElements", "totalPages", "last", "first", "sort", "numberOfElements", "empty"})
public class Page<T> {

    private List<T> content;

    private Integer size;

    private Integer number;
}
