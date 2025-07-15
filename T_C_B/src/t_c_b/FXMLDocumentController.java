/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package t_c_b;

import java.sql.Connection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert.AlertType;

public class FXMLDocumentController implements Initializable {

    @FXML
    private PasswordField pass;

    @FXML
    private Button login1;

    @FXML
    private TextField Enter_TCB_ID;

    @FXML
    private TextField Enter_NID_Number;

    // Database components
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private Button admin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Optional initialization
    }

    @FXML
    private void loginBtn(ActionEvent event) {
        String tcbId = Enter_TCB_ID.getText().trim();
        String nid = Enter_NID_Number.getText().trim();
        String password = pass.getText().trim();

        // --- Validation Section ---
        if (tcbId.isEmpty() || nid.isEmpty() || password.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.", AlertType.WARNING);
            return;
        }

        // ✅ NID must be exactly 10 digits
        if (!nid.matches("\\d{9}")) {
            showAlert("Invalid NID", "NID Number must be exactly 9 digits.", AlertType.WARNING);
            return;
        }

        // ✅ TCB ID must be at least 6 characters and contain both letters and digits
        if (tcbId.length() < 6 || !Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", tcbId)) {
            showAlert("Invalid TCB ID", "TCB ID must be at least 6 characters long and contain both letters and numbers.", AlertType.WARNING);
            return;
        }

        // ✅ Password must be exactly 4 digits
        if (!password.matches("\\d{4}")) {
            showAlert("Invalid Password", "Password must be exactly 4 digits.", AlertType.WARNING);
            return;
        }

        // --- Database Connection ---
        connect = database.connectDb();
        if (connect == null) {
            showAlert("Database Error", "Failed to connect to the database.", AlertType.ERROR);
            return;
        }

        String sql = "SELECT * FROM admin WHERE tcb_id = ? AND nid_number = ? AND password = ?";

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, tcbId);
            prepare.setString(2, nid);
            prepare.setString(3, password);

            result = prepare.executeQuery();

            if (result.next()) {
                showAlert("Login Success", "Welcome, " + tcbId + "!", AlertType.INFORMATION);

                Parent root = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root));
                stage.show();

                // Close login window
                ((Stage) login1.getScene().getWindow()).close();
                
                
            } else {
                showAlert("Login Failed", "Incorrect TCB ID, NID Number, or Password!", AlertType.ERROR);
            }

        } catch (SQLException | IOException e) {
            showAlert("Error", "Login failed: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void admin_login(ActionEvent event) throws IOException {
        
        
        
         Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root));
                stage.show();
    }
}