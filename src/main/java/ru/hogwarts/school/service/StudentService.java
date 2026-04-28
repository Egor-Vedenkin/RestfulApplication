package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getById(Long id) {
        logger.debug("Was invoked method for get student by id: {}", id);
        Optional<Student> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            logger.warn("No student with id={}", id);
        }
        return optional.orElse(null);
    }

    public List<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Student update(Long id, Student updatedStudent) {
        logger.info("Was invoked method for update student with id={}", id);
        if (studentRepository.existsById(id)) {
            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        }
        logger.error("No student with id={} for update", id);
        return null;
    }

    public boolean delete(Long id) {
        logger.info("Was invoked method for delete student with id={}", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        logger.error("No student with id={} for delete", id);
        return false;
    }

    public List<Student> filterByAge(int age) {
        logger.info("Was invoked method for filter students by age: {}", age);
        return studentRepository.findByAge(age);
    }

    public List<Student> filterByAgeRange(int minAge, int maxAge) {
        logger.info("Was invoked method for filter students by age range: {}-{}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Long getTotalStudentsCount() {
        logger.info("Was invoked method for get total students count");
        return studentRepository.getTotalCount();
    }

    public Double getAverageStudentsAge() {
        logger.info("Was invoked method for get average students age");
        return studentRepository.getAverageAge();
    }
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.findLastFiveStudentsNative();
    }
}