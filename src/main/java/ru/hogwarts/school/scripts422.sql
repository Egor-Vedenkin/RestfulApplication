-- scripts422.sql

-- Создаем таблицу для людей (человек)
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT,
    has_license BOOLEAN NOT NULL DEFAULT FALSE -- Признак наличия прав (по умолчанию false)
);

-- Создаем таблицу для машин
CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL, -- Марка
    model VARCHAR(255) NOT NULL, -- Модель
    cost DECIMAL(15, 2) -- Стоимость (числовой тип с точностью)
);

-- Создаем связующую таблицу для связи "многие-ко-многим"
-- Один человек может владеть несколькими машинами, и одна машина может принадлежать нескольким людям.
CREATE TABLE person_car (
    person_id INT NOT NULL,
    car_id INT NOT NULL,
    PRIMARY KEY (person_id, car_id),
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE
);