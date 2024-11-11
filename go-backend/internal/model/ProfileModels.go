package model

type UserResponse struct {
	ID           int       `json:"id"`
	Name         string    `json:"name"`
	Email        string    `json:"email"`
	Photo        *string   `json:"photo"`
	StatsMatches *int      `json:"stats_matches"`
	StatsViews   *int      `json:"stats_views"`
	Animals      *[]string `json:"animals"`
	Rating       *float64  `json:"rating"`
	ReviewsCount *int      `json:"reviews_count"`
	Reviews      *[]string `json:"reviews"`
}
