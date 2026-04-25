-- Получить всех студентов, возраст которых находится между 10 и 20.
SELECT * FROM student WHERE age BETWEEN 10 AND 20;

-- Получить всех студентов, но отобразить только список их имен.
SELECT name FROM student;

-- Получить всех студентов, у которых в имени присутствует буква О.
SELECT * FROM student WHERE name LIKE '%О%';

-- Получить всех студентов, у которых возраст меньше идентификатора.
SELECT * FROM student WHERE age < id;

-- Получить всех студентов упорядоченных по возрасту.
SELECT * FROM student ORDER BY age ASC; -- или DESC для убывания

-- Получить факультет студента (пример для студента с id=1)
SELECT f.* FROM faculty f JOIN student s ON f.id = s.faculty_id WHERE s.id = 1;