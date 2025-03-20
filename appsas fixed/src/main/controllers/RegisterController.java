package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.sql.*;
import utils.HashUtil;

public class RegisterController {
    private static final String DB_URL = "jdbc:sqlite:database/database.db";

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Visi laukai privalomi.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Slaptažodžiai nesutampa.");
            return;
        }

        if (registerUser(username, password)) {
            errorLabel.setText("Registracija sėkminga! Galite prisijungti.");
        } else {
            errorLabel.setText("Vartotojo vardas jau egzistuoja.");
        }
    }

    private boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, HashUtil.hashPassword(password));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
