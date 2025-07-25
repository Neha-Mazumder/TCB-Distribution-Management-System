package t_c_b;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController {

    @FXML
    private TextField nid_number;
    @FXML
    private TextField tcb_id;
    @FXML
    private PasswordField password;
    @FXML
    private Button login_button;

    @FXML
    private void loginBtn(ActionEvent event) {
        String nidNumber = nid_number.getText().trim();
        String tcbId = tcb_id.getText().trim();
        String pass = password.getText().trim();

        if (tcbId.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter TCB ID and Password");
            return;
        }

        try (Connection conn = database.connectDb()) {
            if (conn == null) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
                return;
            }

            String query;
            PreparedStatement stmt;
            boolean isAdminLogin = nidNumber.isEmpty();

            if (isAdminLogin) {
                query = "SELECT id, is_admin FROM users WHERE TCB_ID = ? AND Password = ? AND is_admin = 1";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, tcbId);
                stmt.setString(2, pass);
            } else {
                query = "SELECT id, is_admin FROM users WHERE NID_Number = ? AND TCB_ID = ? AND Password = ? AND is_admin = 0";
                stmt = conn.prepareStatement(query);
                try {
                    stmt.setInt(1, Integer.parseInt(nidNumber));
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Form Error", "NID Number must be a valid number");
                    return;
                }
                stmt.setString(2, tcbId);
                stmt.setString(3, pass);
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                boolean isAdmin = rs.getBoolean("is_admin");
                Parent root;
                String title;

                if (isAdmin) {
                    root = FXMLLoader.load(getClass().getResource("/t_c_b/adminPanal.fxml"));
                    title = "Admin Panel";
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/t_c_b/DashBoard.fxml"));
                    root = loader.load();
                    DashBoardController controller = loader.getController();
                    controller.setUserId(userId);
                    title = "User Dashboard";
                }

                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();

                Stage loginStage = (Stage) login_button.getScene().getWindow();
                loginStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed",
                    isAdminLogin ? "Invalid TCB ID or Password for admin" : "Invalid NID Number, TCB ID, or Password for user");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "FXML Error", "Failed to load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}