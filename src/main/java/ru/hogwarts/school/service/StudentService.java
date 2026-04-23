package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 0L;

    public Student create(Student student) {
        student.setId(++idCounter);
        students.put(student.getId(), student);
        return student;
    }

    public Student getById(Long id) {
        return students.get(id);
    }

    public List<Student> getAll() {
        return students.values().stream().collect(Collectors.toList());
    }

    public Student update(Long id, Student updatedStudent) {
        if (students.containsKey(id)) {
            updatedStudent.setId(id);
            students.put(id, updatedStudent);
            return updatedStudent;
        }
        return null;
    }

    public boolean delete(Long id) {
        return students.remove(id) != null;
    }

    public List<Student> filterByAge(int age) {
        return students.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
