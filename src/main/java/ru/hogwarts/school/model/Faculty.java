package ru.hogwarts.school.model;

import jakarta.persistence.*;

@Entity // Указываем, что это сущность JPA
public class Faculty {
    @Id // Первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID
    private Long id;
    private String name;
    private String color;

    // Конструктор по умолчанию нужен JPA/Hibernate
    public Faculty() {}

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

    // Геттеры и сеттеры (ID не имеет сеттер, если используется GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
