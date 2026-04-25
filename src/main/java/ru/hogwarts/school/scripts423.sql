-- scripts423.sql

-- Запрос 1: Получить информацию обо всех студентах с названиями их факультетов
SELECT
    s.name AS student_name,
    s.age AS student_age,
    f.name AS faculty_name
FROM
    student s
LEFT JOIN
    faculty f ON s.faculty_id = f.id;

-- Запрос 2: Получить только тех студентов, у которых есть аватарки
SELECT
    s.name AS student_name,
    s.age AS student_age
FROM
    student s
INNER JOIN
    avatar a ON s.id = a.student_id;