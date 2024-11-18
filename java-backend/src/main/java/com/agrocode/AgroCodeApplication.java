package com.agrocode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.agrocode.config.DatabaseConfig;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@Import(DatabaseConfig.class)
public class AgroCodeApplication {

    private static final Logger logger = LoggerFactory.getLogger(AgroCodeApplication.class);

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("SMTP_HOST", dotenv.get("SMTP_HOST"));
        System.setProperty("SMTP_PORT", dotenv.get("SMTP_PORT"));
        System.setProperty("EMAIL_ADDRESS", dotenv.get("EMAIL_ADDRESS"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
        System.setProperty("HOST", dotenv.get("HOST"));
        System.setProperty("PORT", dotenv.get("PORT"));
        System.setProperty("POSTGRES_USER", dotenv.get("POSTGRES_USER"));
        System.setProperty("POSTGRES_DB", dotenv.get("POSTGRES_DB"));
        System.setProperty("POSTGRES_PASSWORD", dotenv.get("POSTGRES_PASSWORD"));

        SpringApplication.run(AgroCodeApplication.class, args);
        logger.info("InnoHack Application Started");
    }
}
