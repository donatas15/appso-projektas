package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Visit;
import java.sql.*;

public class VisitController {
    private static final String DB_URL = "jdbc:sqlite:database/database.db";

    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextField clientField;
    @FXML private TextArea notesField;
    @FXML private TextField incomeField;
    @FXML private Label errorLabel;

    private boolean isEditMode = false;
    private int visitId;

    public void setEditMode(Visit visit) {
        isEditMode = true;
        visitId = visit.getId();
        datePicker.setValue(java.time.LocalDate.parse(visit.getDate()));
        timeField.setText(visit.getTime());
        clientField.setText(visit.getClient());
        notesField.setText(visit.getNotes());
        incomeField.setText(String.valueOf(visit.getIncome()));
    }

    @FXML
    private void saveVisit() {
        String date = datePicker.getValue().toString();
        String time = timeField.getText();
        String client = clientField.getText();
        String notes = notesField.getText();
        String incomeText = incomeField.getText();

        if (date.isEmpty() || time.isEmpty() || client.isEmpty()) {
            errorLabel.setText("Prašome užpildyti visus laukus.");
            return;
        }

        double income;
        try {
            income = Double.parseDouble(incomeText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Neteisingas pajamų formatas.");
            return;
        }

        if (isEditMode) {
            updateVisit(visitId, date, time, client, notes, income);
        } else {
            insertVisit(date, time, client, notes, income);
        }

        closeWindow();
    }

    private void insertVisit(String date, String time, String client, String notes, double income) {
        String sql = "INSERT INTO visits (date, time, client, notes, income) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, client);
            pstmt.setString(4, notes);
            pstmt.setDouble(5, income);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateVisit(int id, String date, String time, String client, String notes, double income) {
        String sql = "UPDATE visits SET date = ?, time = ?, client = ?, notes = ?, income = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, client);
            pstmt.setString(4, notes);
            pstmt.setDouble(5, income);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) datePicker.getScene().getWindow();
        stage.close();
    }
}
