package org.example.laba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LabaApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LabaApplication.class);
    }
}