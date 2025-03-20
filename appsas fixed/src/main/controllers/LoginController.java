package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.sql.*;

public class LoginController {
    private static final String DB_URL = "jdbc:sqlite:database/database.db";

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML
    private void goToRegister(ActionEvent event) {
        MainApp mainApp = new MainApp();
        mainApp.showRegisterScene();
    }
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            MainApp mainApp = new MainApp();
            mainApp.showMainScene();
        } else {
            errorLabel.setText("Neteisingas vartotojo vardas arba slaptažodis");
        }
    }

    private boolean authenticate(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            MainApp mainApp = new MainApp();
            mainApp.showMainScene();
        } else {
            errorLabel.setText("Neteisingas vartotojo vardas arba slaptažodis");
        }
    }

}
