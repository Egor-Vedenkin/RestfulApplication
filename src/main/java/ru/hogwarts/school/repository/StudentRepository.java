package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int minAge, int maxAge);

    // 1. Получить количество всех студентов
    @Query("SELECT COUNT(s) FROM Student s")
    Long getTotalCount();

    // 2. Получить средний возраст студентов
    @Query("SELECT AVG(s.age) FROM Student s")
    Double getAverageAge();

    // 3. Получить 5 последних студентов (по убыванию ID)
    @Query("SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findTop5ByOrderByIdDesc();
}

