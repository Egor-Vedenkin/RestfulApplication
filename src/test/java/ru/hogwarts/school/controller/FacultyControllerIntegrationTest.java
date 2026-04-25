package ru.hogwarts.school.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FacultyControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long createdFacultyId;
    private static final String BASE_URL = "/faculty";

    @Test
    @Order(1)
    @DisplayName("POST /faculty - Создать факультет")
    void testCreateFaculty() {
        Faculty faculty = new Faculty("Гриффиндор", "Красный");
        ResponseEntity<Faculty> response = restTemplate.postForEntity(BASE_URL, faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        createdFacultyId = response.getBody().getId();
    }

    @Test
    @Order(2)
    @DisplayName("GET /faculty/{id} - Получить факультет по ID")
    void testGetFacultyById() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity(BASE_URL + "/{id}", Faculty.class, createdFacultyId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(createdFacultyId);
    }

    @Test
    @Order(3)
    @DisplayName("GET /faculty - Получить список всех факультетов")
    void testGetAllFaculties() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(BASE_URL, Faculty[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Faculty> faculties = Arrays.asList(response.getBody());
        assertThat(faculties).isNotEmpty();
    }
}