package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.List;
import java.util.Optional;

@Service // Указываем Spring, что это компонент сервиса (бизнес-логика)
public class FacultyService {

    private final FacultyRepository facultyRepository; // Финальное поле для неизменяемости

    @Autowired // Внедрение зависимости через конструктор (лучшая практика)
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getById(Long id) {
        Optional<Faculty> optional = facultyRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty update(Long id, Faculty updatedFaculty) {
        if (facultyRepository.existsById(id)) {
            updatedFaculty.setId(id); // Убеждаемся, что обновляем существующую запись по ID
            return facultyRepository.save(updatedFaculty);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
}