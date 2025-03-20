package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Visit;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainController {
    private static final String DB_URL = "jdbc:sqlite:database/database.db";

    @FXML private TableView<Visit> visitsTable;
    @FXML private TableColumn<Visit, String> dateColumn;
    @FXML private TableColumn<Visit, String> timeColumn;
    @FXML private TableColumn<Visit, String> clientColumn;
    @FXML private TableColumn<Visit, String> notesColumn;
    @FXML private TableColumn<Visit, Double> incomeColumn;

    private ObservableList<Visit> visitsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));

        loadVisits();
    }

    private void loadVisits() {
        visitsList.clear();
        String sql = "SELECT * FROM visits";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                visitsList.add(new Visit(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("client"),
                        rs.getString("notes"),
                        rs.getDouble("income")
                ));
            }
            visitsTable.setItems(visitsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addVisit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/visit_form.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pridėti vizitą");
            stage.showAndWait();

            loadVisits();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
