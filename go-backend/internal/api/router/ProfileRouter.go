package router

import (
	"app/internal/api/controller"
	"app/internal/api/middleware"
	"github.com/gin-gonic/gin"
)

func (router *Router) ProfileRoutes(group *gin.RouterGroup) {
	group.GET("/user/:id", controller.GetUserDetails)
	group.GET("/user/me", middleware.CookieMiddleware(), controller.GetUserDetails)
}
