package springai.test.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class Cabinet {
    private String name;
    private String owner;
    private String opening_hour;
    private String closing_hour;
    private List<String> specialites;
    private String description;
}
