package com.agrocode.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AuthModel {

    public static class UserCreateRequest {
        
        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;

        @NotEmpty(message = "Password is required")
        private String password;

        @NotEmpty(message = "Name is required")
        private String name;

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class LoginRequest {

        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;

        @NotEmpty(message = "Password is required")
        private String password;

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class CodeResponse {
        private String token;

        public CodeResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
