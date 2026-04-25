package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FacultyController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock // Используем современную аннотацию Mockito вместо @MockBean
    private FacultyService facultyService;

    private static final Long FACULTY_ID = 1L;
    private static final String BASE_URL = "/faculty";

    // --- CRUD Операции ---

    @Test
    @Order(1)
    void testCreateFaculty() throws Exception {
        Faculty newFaculty = new Faculty("Гриффиндор", "Красный");
        Faculty savedFaculty = new Faculty("Гриффиндор", "Красный");
        savedFaculty.setId(50L);

        when(facultyService.create(any(Faculty.class))).thenReturn(savedFaculty);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFaculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(50))
                .andExpect(jsonPath("$.name").value("Гриффиндор"));

        verify(facultyService, times(1)).create(any(Faculty.class));
    }

    @Test
    @Order(2)
    void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty("Когтевран", "Синий");
        faculty.setId(FACULTY_ID);

        when(facultyService.getById(FACULTY_ID)).thenReturn(faculty);

        mockMvc.perform(get(BASE_URL + "/{id}", FACULTY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Когтевран"));

        verify(facultyService, times(1)).getById(FACULTY_ID);
    }

    // Тест на фильтрацию по цвету (GET /filter/color?color=...)
    @Test
    void testFilterByColor() throws Exception {
        String colorParam = "green";
        List<Faculty> resultList = List.of(new Faculty("Зеленый Факультет", colorParam));

        when(facultyService.filterByColor(colorParam)).thenReturn(resultList);

        mockMvc.perform(get(BASE_URL + "/filter/color")
                        .param("color", colorParam))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(colorParam));

        verify(facultyService).filterByColor(colorParam);
    }

    // Тест на поиск по имени или цвету (GET /search?query=...)
    @Test
    void testSearch() throws Exception {
        String query = "red";
        List<Faculty> resultList = List.of(new Faculty("Красный", query));

        when(facultyService.searchByNameOrColor(query)).thenReturn(resultList);

        mockMvc.perform(get(BASE_URL + "/search")
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(query));

        verify(facultyService).searchByNameOrColor(query);
    }

    @Test
    void testGetStudentsOfFaculty() throws Exception {
        Long facultyId = 10L;
        Student testStudent = new Student("Студент Факультета", 18);

        // 1. Создаем факультет
        Faculty faculty = new Faculty("Факультет Имени", "Синий");

        // 2. Получаем список студентов (геттер getStudents() у вас есть)
        // и добавляем в него нашего студента.
        faculty.getStudents().add(testStudent);

        // 3. Настраиваем мок сервиса так, чтобы он возвращал этот подготовленный факультет.
        when(facultyService.getById(facultyId)).thenReturn(faculty);

        // Выполняем запрос к контроллеру
        mockMvc.perform(get(BASE_URL + "/{id}/students", facultyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Студент Факультета"))
                .andExpect(jsonPath("$[0].age").value(18));

        verify(facultyService, times(1)).getById(facultyId);
    }
}