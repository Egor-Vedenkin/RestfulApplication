package ru.hogwarts.school.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long createdStudentId;
    private static final String BASE_URL = "/student";

    @Test
    @Order(1)
    @DisplayName("POST /student - Создать студента")
    void testCreateStudent() {
        Student student = new Student("Иван Иванов", 18);
        ResponseEntity<Student> response = restTemplate.postForEntity(BASE_URL, student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Иван Иванов");

        createdStudentId = response.getBody().getId();
    }

    @Test
    @Order(2)
    @DisplayName("GET /student/{id} - Получить студента по ID")
    void testGetStudentById() {
        ResponseEntity<Student> response = restTemplate.getForEntity(BASE_URL + "/{id}", Student.class, createdStudentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(createdStudentId);
    }

    @Test
    @Order(3)
    @DisplayName("GET /student - Получить список всех студентов")
    void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(BASE_URL, Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> students = Arrays.asList(response.getBody());
        assertThat(students).isNotEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("PUT /student/{id} - Обновить студента")
    void testUpdateStudent() {
        Student updated = new Student("Петр Петров", 20);
        restTemplate.put(BASE_URL + "/{id}", updated, createdStudentId);

        ResponseEntity<Student> response = restTemplate.getForEntity(BASE_URL + "/{id}", Student.class, createdStudentId);
        assertThat(response.getBody().getName()).isEqualTo("Петр Петров");
        assertThat(response.getBody().getAge()).isEqualTo(20);
    }

    @Test
    @Order(5)
    @DisplayName("DELETE /student/{id} - Удалить студента")
    void testDeleteStudent() {
        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE, null, Void.class, createdStudentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Проверка, что студента больше нет
        ResponseEntity<Student> getResponse = restTemplate.getForEntity(BASE_URL + "/{id}", Student.class, createdStudentId);
        assertThat(getResponse.getBody()).isNull();
    }
}