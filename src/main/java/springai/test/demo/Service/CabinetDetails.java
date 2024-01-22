package springai.test.demo.Service;

import java.util.List;

public record CabinetDetails(String name, String owner, String opening_hour, String closing_hour, List<String> specialites, String description) { }
