package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController // Указывает, что класс — REST контроллер (@ResponseBody на все методы)
@RequestMapping("faculty") // Базовый путь: /faculty/*
public class FacultyController {

    private final FacultyService facultyService; // Внедрение сервиса

    @Autowired // Внедрение зависимости через конструктор (вместо new)
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping // POST /faculty - Создание нового факультета из тела запроса (JSON)
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("{id}") // GET /faculty/1 - Получить факультет по ID из пути URL
    public Faculty getById(@PathVariable Long id) {
        return facultyService.getById(id);
    }

    @GetMapping // GET /faculty - Получить список всех факультетов (можно добавить пагинацию)
    public List<Faculty> getAll() {
        return facultyService.getAll();
    }

    @PutMapping("{id}") // PUT /faculty/1 - Обновить факультет с ID=1 данными из тела запроса
    public Faculty update(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("{id}") // DELETE /faculty/1 - Удалить факультет по ID из пути URL
    public boolean delete(@PathVariable Long id) {
        return facultyService.delete(id);
    }

    @GetMapping("filter/color") // GET /faculty/filter/color?color=green - Фильтрация по цвету (Query Param)
    public List<Faculty> filterByColor(@RequestParam String color) {
        return facultyService.filterByColor(color);
    }

    @GetMapping("search")
    public List<Faculty> search(@RequestParam String query) {
        return facultyService.searchByNameOrColor(query);
    }

    @GetMapping("{id}/students")
    public List<Student> getStudentsOfFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getById(id);
        return faculty != null ? faculty.getStudents() : null;
    }

    @GetMapping("longest-name")
    public String getLongestFacultyName() {
        List<Faculty> faculties = facultyService.getAll();
        return faculties.stream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

}