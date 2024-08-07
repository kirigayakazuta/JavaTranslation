package com.example.translation.dao;

import com.example.translation.controller.TranslationController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.*;
import com.example.translation.model.TranslationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TranslationDAO {

    @Value("${spring.datasource.url}")
    private static String URL;

    @Value("${spring.datasource.username}")
    private static String USER;

    @Value("${spring.datasource.password}")
    private static String PASSWORD;

    private static final Logger logger = LoggerFactory.getLogger(TranslationController.class);

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Error connection db", e);
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Error connection db", e);
        }
    }

    public void saveUser(TranslationUser user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Users (ip_address, source_text, target_text, timestamp) VALUES(?, ?, ?, ?)");

            preparedStatement.setString(1, user.getIpAddress());
            preparedStatement.setString(2, user.getSourceText());
            preparedStatement.setString(3, user.getTargetText());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error append db", e);
        }
    }
}
