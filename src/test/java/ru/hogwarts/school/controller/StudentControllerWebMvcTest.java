package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Используем @WebMvcTest для загрузки только контроллера StudentController
@WebMvcTest(StudentController.class)
// Используем @ExtendWith для совместимости JUnit 5 и Mockito
@ExtendWith({SpringExtension.class, MockitoExtension.class})
// Настраиваем Mockito, чтобы не было предупреждений о незадействованных мока (если это не ошибка)
// @MockitoSettings(strictness = Strictness.LENIENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc; // Основной инструмент для отправки запросов

    @Autowired
    private ObjectMapper objectMapper; // Для преобразования объектов в JSON

    // Используем @Mock вместо устаревшего @MockBean
    @Mock
    private StudentService studentService;

    // Используем @InjectMocks, чтобы Spring внедрил моки в контроллер
    // (в данном случае Spring делает это автоматически через @Autowired, но это хорошая практика)
    // @Autowired
    // private StudentController studentController;

    private static final Long STUDENT_ID = 1L;
    private static final String BASE_URL = "/student";

    // Тест 1: Создание студента (POST)
    @Test
    @Order(1)
    void testCreateStudent() throws Exception {
        // Подготовка данных
        Student newStudent = new Student("Иван Иванов", 20);
        Student savedStudent = new Student("Иван Иванов", 20);
        savedStudent.setId(100L); // Имитируем ID, который сгенерирует БД

        // Настройка мока: когда сервис вызывается с любым студентом, вернуть savedStudent
        when(studentService.create(any(Student.class))).thenReturn(savedStudent);

        // Выполнение запроса и проверка результата
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.name").value("Иван Иванов"))
                .andExpect(jsonPath("$.age").value(20));

        // Проверка, что метод сервиса был вызван ровно 1 раз
        verify(studentService, times(1)).create(any(Student.class));
    }

    // Тест 2: Получение студента по ID (GET /{id})
    @Test
    @Order(2)
    void testGetStudentById() throws Exception {
        // Настройка мока: вернуть студента при запросе по конкретному ID
        when(studentService.getById(STUDENT_ID)).thenReturn(new Student("Петр", 22));

        mockMvc.perform(get(BASE_URL + "/{id}", STUDENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Петр"))
                .andExpect(jsonPath("$.age").value(22));

        verify(studentService, times(1)).getById(STUDENT_ID);
    }

    // Тест 3: Получение списка всех студентов (GET)
    @Test
    @Order(3)
    void testGetAllStudents() throws Exception {
        when(studentService.getAll()).thenReturn(List.of(
                new Student("Анна", 19),
                new Student("Борис", 21)
        ));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Анна"))
                .andExpect(jsonPath("$[1].name").value("Борис"));

        verify(studentService, times(1)).getAll();
    }

    // Тест 4: Обновление студента (PUT /{id})
    @Test
    @Order(4)
    void testUpdateStudent() throws Exception {
        Student updatedData = new Student("Новое Имя", 99);
        when(studentService.update(eq(STUDENT_ID), any(Student.class))).thenReturn(new Student("Новое Имя", 99));

        mockMvc.perform(put(BASE_URL + "/{id}", STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Новое Имя"))
                .andExpect(jsonPath("$.age").value(99));

        verify(studentService, times(1)).update(eq(STUDENT_ID), any(Student.class));
    }

    // Тест 5: Удаление студента (DELETE /{id})
    @Test
    @Order(5)
    void testDeleteStudent() throws Exception {
        doReturn(true).when(studentService).delete(STUDENT_ID); // Мокаем void-метод

        mockMvc.perform(delete(BASE_URL + "/{id}", STUDENT_ID))
                .andExpect(status().isOk());

        verify(studentService, times(1)).delete(STUDENT_ID);
    }
}