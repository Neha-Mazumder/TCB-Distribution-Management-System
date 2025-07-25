package t_c_b;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class cardview implements Initializable {

    @FXML
    private AnchorPane card_form;
    @FXML
    private Button prod_addBtn;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Label prod_name;
    @FXML
    private Label prod_price;
    @FXML
    private Spinner<Integer> prod_spinner;

    private String productId;
    private String productName;
    private double price;
    private int stock;
    private DashBoardController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prod_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
    }

    public void setProductData(String productId, String productName, double price, int stock, DashBoardController parentController) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.parentController = parentController;

        prod_name.setText(productName);
        prod_price.setText(String.format("%.2f", price));

        // Set spinner limit: 10 for P001 (চাল), 3 for others, capped by stock
        int maxQuantity = productId.equals("P001") ? 10 : 3;
        maxQuantity = Math.min(maxQuantity, stock);
        prod_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxQuantity, 0));

        String imagePath = "/t_c_b/image/" + productId + ".png";
        if (getClass().getResource(imagePath) != null) {
            prod_imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } else if (getClass().getResource("/t_c_b/image/images.png") != null) {
            prod_imageView.setImage(new Image(getClass().getResourceAsStream("/t_c_b/image/images.png")));
        } else {
            prod_imageView.setImage(null);
            System.out.println("Warning: Image not found for " + imagePath + " and images.png is missing.");
        }
    }

    @FXML
    private void addToCart() {
        int quantity = prod_spinner.getValue();
        if (quantity > 0) {
            parentController.addProductToCart(productId, productName, price, quantity);
        } else {
            parentController.showAlert("Error", "Please select a quantity greater than 0!");
        }
    }
}