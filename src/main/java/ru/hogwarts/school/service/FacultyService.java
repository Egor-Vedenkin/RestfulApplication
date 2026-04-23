package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();
    private Long idCounter = 0L;

    public Faculty create(Faculty faculty) {
        faculty.setId(++idCounter);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getById(Long id) {
        return faculties.get(id);
    }

    public List<Faculty> getAll() {
        return faculties.values().stream().collect(Collectors.toList());
    }

    public Faculty update(Long id, Faculty updatedFaculty) {
        if (faculties.containsKey(id)) {
            updatedFaculty.setId(id);
            faculties.put(id, updatedFaculty);
            return updatedFaculty;
        }
        return null;
    }

    public boolean delete(Long id) {
        return faculties.remove(id) != null;
    }

    public List<Faculty> filterByColor(String color) {
        return faculties.values().stream()
                .filter(f -> f.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }
}
