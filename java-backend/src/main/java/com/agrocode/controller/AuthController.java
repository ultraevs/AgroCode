package com.agrocode.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrocode.model.AuthModel;
import com.agrocode.model.UserCreateRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1")
public class AuthController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Operation(summary = "Создать пользователя", description = "Создаёт нового пользователя и отправляет ему email с приветствием.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
        @ApiResponse(responseCode = "400", description = "Ошибка при создании пользователя"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/user_create")
    public ResponseEntity<?> userCreate(@RequestBody UserCreateRequest request) {
        try (Connection connection = dataSource.getConnection()) {
            String hashPass = passwordEncoder.encode(request.getPassword());

            String maxIdQuery = "SELECT COALESCE(MAX(id), 0) FROM users";
            int maxId;
            try (PreparedStatement statement = connection.prepareStatement(maxIdQuery)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    maxId = resultSet.getInt(1);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to retrieve max ID");
                }
            }

            int newId = maxId + 1;
            String uniqueName = String.format("%s#%04d", request.getName(), newId);

            String insertUserQuery = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertUserQuery)) {
                statement.setString(1, request.getEmail());
                statement.setString(2, hashPass);
                statement.setString(3, uniqueName);
                statement.executeUpdate();
            }

            String token = Jwts.builder()
                    .setSubject(request.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)))
                    .signWith(SignatureAlgorithm.HS256, "SECRET") 
                    .compact();


            return ResponseEntity.ok(new AuthModel.CodeResponse(token));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create user: " + e.getMessage());
        }
    }
    @Operation(summary = "Авторизация пользователя", description = "Проверяет учетные данные пользователя и возвращает JWT токен.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
        @ApiResponse(responseCode = "401", description = "Неправильный логин или пароль"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthModel.LoginRequest request) {
        try (Connection connection = dataSource.getConnection()) {
            String findUserQuery = "SELECT email, password FROM users WHERE email = ?";
            AuthModel.LoginRequest form = new AuthModel.LoginRequest();
            try (PreparedStatement statement = connection.prepareStatement(findUserQuery)) {
                statement.setString(1, request.getEmail());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    form.setEmail(resultSet.getString("email"));
                    form.setPassword(resultSet.getString("password"));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No User");
                }
            }

            if (!passwordEncoder.matches(request.getPassword(), form.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
            }

            String token = Jwts.builder()
                    .setSubject(form.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)))
                    .signWith(SignatureAlgorithm.HS256, "SECRET") 
                    .compact();

            return ResponseEntity.ok(new AuthModel.CodeResponse(token));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while logging in: " + e.getMessage());
        }
    }
}
