package com.agrocode.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {

    @Value("${HOST}")
    private String host;

    @Value("${PORT}")
    private int port;

    @Value("${POSTGRES_USER}")
    private String user;

    @Value("${POSTGRES_PASSWORD}")
    private String password;

    @Value("${POSTGRES_DB}")
    private String dbname;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbname);
        System.out.println(url);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void initializeDatabase() {
        try {
            String sql = new String(Files.readAllBytes(Paths.get(new ClassPathResource("schema.sql").getURI())));
            JdbcTemplate jdbcTemplate = jdbcTemplate(dataSource());
            List<String> queries = List.of(sql.split(";"));

            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    jdbcTemplate.execute(query);
                }
            }
            System.out.println("Tables have been created successfully or already exist");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the schema.sql file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while creating the tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
