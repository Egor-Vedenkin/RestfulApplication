-- scripts421.sql

-- 1. Ограничение для таблицы student: возраст не менее 16 лет
ALTER TABLE student
ADD CONSTRAINT check_student_age CHECK (age >= 16);

-- 2. Ограничение для таблицы student: имя не может быть NULL и должно быть уникальным
ALTER TABLE student
ADD CONSTRAINT uq_student_name UNIQUE (name),
ALTER COLUMN name SET NOT NULL;

-- 3. Ограничение для таблицы faculty: составной уникальный ключ (название, цвет)
ALTER TABLE faculty
ADD CONSTRAINT uq_faculty_name_color UNIQUE (name, color);

-- 4. Ограничение для таблицы student: если возраст не указан, присваивать 20 лет
ALTER TABLE student
ALTER COLUMN age SET DEFAULT 20;