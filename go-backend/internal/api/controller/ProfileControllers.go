package controller

import (
	"app/internal/database"
	"app/internal/model"
	"database/sql"
	"errors"
	"github.com/gin-gonic/gin"
	"net/http"
)

// GetUserDetails возвращает полные данные о пользователе.
// @Summary Получить данные пользователя
// @Description Возвращает все данные о пользователе по идентификатору или email из cookie.
// @Accept json
// @Produce json
// @Param id path string false "ID пользователя, если доступ по ID"
// @Success 200 {object} model.UserResponse "Данные пользователя успешно получены"
// @Failure 400 {object} model.ErrorResponse "Ошибка при получении данных пользователя"
// @Tags User
// @Router /v1/user/{id} [get]
// @Router /v1/user/me [get]
func GetUserDetails(context *gin.Context) {
	var user model.UserResponse
	var query string
	var queryParam interface{}

	// Check if the request is for the user's own account using "/me"
	if context.Request.URL.Path == "/v1/user/me" {
		// Get email from cookie
		email := context.MustGet("Email").(string)

		query = `
            SELECT id, name, email, COALESCE(photo, '') AS photo, stats_matches, stats_views, animals, rating, reviews_count, reviews
            FROM cows_users
            WHERE email = $1
        `
		queryParam = email
	} else {
		// Otherwise, use the provided ID
		userID := context.Param("id")
		query = `
            SELECT id, name, email, COALESCE(photo, '') AS photo, stats_matches, stats_views, animals, rating, reviews_count, reviews
            FROM cows_users
            WHERE id = $1
        `
		queryParam = userID
	}

	// Execute the query based on either email or user ID
	err := database.Db.QueryRow(query, queryParam).Scan(
		&user.ID,
		&user.Name,
		&user.Email,
		&user.Photo,
		&user.StatsMatches,
		&user.StatsViews,
		&user.Animals,
		&user.Rating,
		&user.ReviewsCount,
		&user.Reviews,
	)

	// Check if the user was found
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			context.JSON(http.StatusNotFound, gin.H{"error": "User not found"})
			return
		}
		context.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to retrieve user data"})
		return
	}

	// Respond with the user data in JSON format
	context.JSON(http.StatusOK, gin.H{"user": user})
}
