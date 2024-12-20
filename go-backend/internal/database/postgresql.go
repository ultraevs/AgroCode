package database

import (
	"database/sql"
	"fmt"
	"github.com/joho/godotenv"
	_ "github.com/lib/pq"
	"os"
	"strconv"
)

var Db *sql.DB

func ConnectDatabase() {

	err := godotenv.Load()
	if err != nil {
		fmt.Println("Error occurred while loading .env file, please check")
	}
	// Read environment variables from .env file
	host := os.Getenv("HOST")
	port, _ := strconv.Atoi(os.Getenv("PORT"))
	user := os.Getenv("POSTGRES_USER")
	dbname := os.Getenv("POSTGRES_DB")
	pass := os.Getenv("POSTGRES_PASSWORD")
	psqlSetup := fmt.Sprintf("postgres://%v:%v@%v:%v/%v?sslmode=disable",
		user, pass, host, port, dbname)
	db, errSql := sql.Open("postgres", psqlSetup)
	if errSql != nil {
		fmt.Println("There is an error while connecting to the database: ", errSql)
		panic(errSql)
	} else {
		Db = db
		fmt.Println("Successfully connected to the database!")
	}

	createTablesQuery := `
CREATE TABLE IF NOT EXISTS cows_users (
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

	`

	_, err = Db.Exec(createTablesQuery)
	if err != nil {
		fmt.Println("An error occurred while creating the tables:", err)
		panic(err)
	} else {
		fmt.Println("Tables have been created successfully or already exist")
	}
}
