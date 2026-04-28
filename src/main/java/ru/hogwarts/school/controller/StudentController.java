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

    @GetMapping("count")
    public Long getTotalCount() {
        return studentService.getTotalStudentsCount();
    }

    @GetMapping("average-age")
    public Double getAverageAge() {
        return studentService.getAverageStudentsAge();
    }

    @GetMapping("last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("names/starts-with-a")
    public List<String> getStudentNamesStartsWithA() {
        List<Student> students = studentService.getAll();
        return students.stream()
                .filter(s -> s.getName() != null && s.getName().toUpperCase().startsWith("A"))
                .map(s -> s.getName().toUpperCase())
                .sorted()
                .toList();
    }

    @GetMapping("/students/print-parallel")
    public String printStudentsParallel() {
        List<Student> students = studentService.getAll();
        if (students.size() < 6) {
            return "Недостаточно студентов для демонстрации (нужно минимум 6).";
        }

        System.out.println("Main Thread: " + students.get(0).getName());
        System.out.println("Main Thread: " + students.get(1).getName());

        new Thread(() -> {
            System.out.println("Thread 1: " + students.get(2).getName());
            System.out.println("Thread 1: " + students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println("Thread 2: " + students.get(4).getName());
            System.out.println("Thread 2: " + students.get(5).getName());
        }).start();

        return "Имена студентов выведены в консоль параллельно. Проверьте логи сервера.";
    }

    @GetMapping("/students/print-synchronized")
    public String printStudentsSynchronized() {
        List<Student> students = studentService.getAll();
        if (students.size() < 6) {
            return "Недостаточно студентов для демонстрации (нужно минимум 6).";
        }

        // Основной поток: первые два имени через синхронизированный метод
        printNameSync("Main Thread: " + students.get(0).getName());
        printNameSync("Main Thread: " + students.get(1).getName());

        new Thread(() -> {
            printNameSync("Thread 1: " + students.get(2).getName());
            printNameSync("Thread 1: " + students.get(3).getName());
        }).start();

        new Thread(() -> {
            printNameSync("Thread 2: " + students.get(4).getName());
            printNameSync("Thread 2: " + students.get(5).getName());
        }).start();

        return "Имена студентов выведены в консоль с синхронизацией. Проверьте логи сервера.";
    }

    private synchronized void printNameSync(String name) {
        System.out.println(name);
    }
}