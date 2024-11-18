CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,               -- Идентификатор пользователя
    name VARCHAR(255) NOT NULL,          -- Имя пользователя
    email VARCHAR(255) NOT NULL,         -- Email пользователя
    password VARCHAR(255) NOT NULL,      -- Пароль пользователя
    photo VARCHAR(255),                  -- Фото пользователя (URL или путь к фото)
    stats_matches INT DEFAULT 0,         -- Количество матчей
    stats_views INT DEFAULT 0,           -- Количество просмотров
    animals TEXT[],                      -- Список животных
    rating FLOAT DEFAULT 0,              -- Рейтинг пользователя
    reviews_count INT DEFAULT 0,         -- Количество отзывов
    reviews TEXT[]                       -- Список отзывов
);

CREATE TABLE IF NOT EXISTS animals (
    id SERIAL PRIMARY KEY,          -- Идентификатор животного
    name VARCHAR(255) NOT NULL,      -- Имя животного
    age INT,                         -- Возраст животного
    info TEXT                        -- Дополнительная информация
);

CREATE TABLE IF NOT EXISTS family (
    animal_id INT PRIMARY KEY,       -- Идентификатор животного (внешний ключ на animals)
    mother_id INT REFERENCES animals(id), -- Идентификатор матери
    father_id INT REFERENCES animals(id)  -- Идентификатор отца
);
