package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student getById(Long id) {
        Optional<Student> optional = studentRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(Long id, Student updatedStudent) {
        if (studentRepository.existsById(id)) {
            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Student> filterByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> filterByAgeRange(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
}