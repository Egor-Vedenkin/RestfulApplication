package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @PutMapping("{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        return studentService.delete(id);
    }

    @GetMapping("filter/age")
    public List<Student> filterByAge(@RequestParam int age) {
        return studentService.filterByAge(age);
    }

    @GetMapping("filter/age/range")
    public List<Student> filterByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.filterByAgeRange(minAge, maxAge);
    }

    @GetMapping("{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return student != null ? student.getFaculty() : null;
    }
}