package main;
//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainApp extends Application {
    private static final String DB_URL = "jdbc:sqlite:database/database.db";
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createDatabase();
        checkFirstRun();
    }

    private void checkFirstRun() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) > 0) {
                showLoginScene();
            } else {
                showRegisterScene();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoginScene() {
        loadScene("/views/login.fxml", "Prisijungimas");
    }

    public void showRegisterScene() {
        loadScene("/views/register.fxml", "Registracija");
    }

    public void showMainScene() {
        loadScene("/views/main.fxml", "Pagrindinis langas");
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL);";

            String visitsTable = "CREATE TABLE IF NOT EXISTS visits (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date TEXT NOT NULL, " +
                    "time TEXT NOT NULL, " +
                    "client TEXT NOT NULL, " +
                    "notes TEXT, " +
                    "income REAL);";

            stmt.execute(usersTable);
            stmt.execute(visitsTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
