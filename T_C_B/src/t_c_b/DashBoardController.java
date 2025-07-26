package t_c_b;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DashBoardController {
    @FXML
    private AnchorPane main_form;
    @FXML
    private BorderPane root;
    @FXML
    private AnchorPane productPane;
    @FXML
    private GridPane productGrid;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> col_productName;
    @FXML
    private TableColumn<Product, Double> col_price;
    @FXML
    private TableColumn<Product, Integer> col_quantity;
    @FXML
    private TextField paymentField;
    @FXML
    private Label totalLabel;
    @FXML
    private Label paymentLabel;
    @FXML
    private Label changeLabel;
    @FXML
    private Button paymentButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button receiptButton;
    @FXML
    private Button logoutButton;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private double totalAmount = 0.0;
    private int userId;

    // Shared data for admin panel
    public static ObservableList<ProductData> inventoryData = FXCollections.observableArrayList();
    public static ObservableList<SaleData> salesData = FXCollections.observableArrayList();

    public static class Product {
        private final SimpleStringProperty productId;
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty quantity;

        public Product(String productId, String name, double price, int quantity) {
            this.productId = new SimpleStringProperty(productId);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        public String getProductId() { return productId.get(); }
        public String getName() { return name.get(); }
        public double getPrice() { return price.get(); }
        public int getQuantity() { return quantity.get(); }
        public SimpleStringProperty productIdProperty() { return productId; }
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleDoubleProperty priceProperty() { return price; }
        public SimpleIntegerProperty quantityProperty() { return quantity; }
    }

    public static class ProductData {
        private final SimpleStringProperty productId;
        private final SimpleStringProperty productName;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty stock;

        public ProductData(String productId, String productName, double price, int stock) {
            this.productId = new SimpleStringProperty(productId);
            this.productName = new SimpleStringProperty(productName);
            this.price = new SimpleDoubleProperty(price);
            this.stock = new SimpleIntegerProperty(stock);
        }

        public String getProductId() { return productId.get(); }
        public String getProductName() { return productName.get(); }
        public double getPrice() { return price.get(); }
        public int getStock() { return stock.get(); }
        public SimpleStringProperty productIdProperty() { return productId; }
        public SimpleStringProperty productNameProperty() { return productName; }
        public SimpleDoubleProperty priceProperty() { return price; }
        public SimpleIntegerProperty stockProperty() { return stock; }
    }

    public static class SaleData {
        private final SimpleIntegerProperty saleId;
        private final SimpleStringProperty productId;
        private final SimpleIntegerProperty quantity;
        private final SimpleDoubleProperty totalPrice;
        private final SimpleStringProperty saleDate;
        private final SimpleIntegerProperty userId;

        public SaleData(int saleId, String productId, int quantity, double totalPrice, String saleDate, int userId) {
            this.saleId = new SimpleIntegerProperty(saleId);
            this.productId = new SimpleStringProperty(productId);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.totalPrice = new SimpleDoubleProperty(totalPrice);
            this.saleDate = new SimpleStringProperty(saleDate);
            this.userId = new SimpleIntegerProperty(userId);
        }

        public int getSaleId() { return saleId.get(); }
        public String getProductId() { return productId.get(); }
        public int getQuantity() { return quantity.get(); }
        public double getTotalPrice() { return totalPrice.get(); }
        public String getSaleDate() { return saleDate.get(); }
        public int getUserId() { return userId.get(); }
        public SimpleIntegerProperty saleIdProperty() { return saleId; }
        public SimpleStringProperty productIdProperty() { return productId; }
        public SimpleIntegerProperty quantityProperty() { return quantity; }
        public SimpleDoubleProperty totalPriceProperty() { return totalPrice; }
        public SimpleStringProperty saleDateProperty() { return saleDate; }
        public SimpleIntegerProperty userIdProperty() { return userId; }
    }

    @FXML
    private void initialize() {
        if (root != null) {
            root.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        }
        if (productPane != null) {
            productPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        }
        if (paymentButton != null) {
            paymentButton.setStyle("-fx-background-color: #29aa5b; -fx-text-fill: #ffffff; -fx-background-radius: 5px;");
        }
        if (removeButton != null) {
            removeButton.setStyle("-fx-background-color: #29aa5b; -fx-text-fill: #ffffff; -fx-background-radius: 5px;");
        }
        if (receiptButton != null) {
            receiptButton.setStyle("-fx-background-color: #29aa5b; -fx-text-fill: #ffffff; -fx-background-radius: 5px;");
        }
        if (col_productName != null) {
            col_productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        }
        if (col_price != null) {
            col_price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        }
        if (col_quantity != null) {
            col_quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        }
        if (tableView != null) {
            tableView.setItems(productList);
        }
        if (totalLabel != null) {
            totalLabel.setText("0.00/-");
        }
        if (paymentLabel != null) {
            paymentLabel.setText("0.00/-");
        }
        if (changeLabel != null) {
            changeLabel.setText("0.00/-");
        }
        loadProducts();
        loadSharedData();
        checkWeeklyPurchaseStatus();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Check if user has made a purchase in the last 7 days
     * @return true if user can purchase, false if restricted
     */
    private boolean canUserPurchase() {
        try (Connection conn = database.connectDb()) {
            String query = "SELECT MAX(sale_date) as last_purchase FROM sales WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Date lastPurchaseDate = rs.getDate("last_purchase");
                if (lastPurchaseDate != null) {
                    Date today = new Date();
                    long diffInMillies = today.getTime() - lastPurchaseDate.getTime();
                    long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    return daysBetween >= 7; // Allow purchase if 7 or more days have passed
                }
            }
            return true; // No previous purchase found, allow purchase
        } catch (SQLException e) {
            showAlert("Error", "Failed to check purchase history: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the next allowed purchase date for the user
     * @return Date when user can next purchase, or null if can purchase now
     */
    private Date getNextAllowedPurchaseDate() {
        try (Connection conn = database.connectDb()) {
            String query = "SELECT MAX(sale_date) as last_purchase FROM sales WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Date lastPurchaseDate = rs.getDate("last_purchase");
                if (lastPurchaseDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(lastPurchaseDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    return calendar.getTime();
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to get next purchase date: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get days remaining until next allowed purchase
     * @return number of days remaining, or 0 if can purchase now
     */
    private long getDaysUntilNextPurchase() {
        Date nextAllowedDate = getNextAllowedPurchaseDate();
        if (nextAllowedDate != null) {
            Date today = new Date();
            if (nextAllowedDate.after(today)) {
                long diffInMillies = nextAllowedDate.getTime() - today.getTime();
                return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            }
        }
        return 0;
    }

    /**
     * Check and display weekly purchase status to user
     */
    private void checkWeeklyPurchaseStatus() {
        if (!canUserPurchase()) {
            Date nextAllowedDate = getNextAllowedPurchaseDate();
            if (nextAllowedDate != null) {
                long daysUntilNext = getDaysUntilNextPurchase();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                showAlert("Purchase Restriction", 
                    "আপনি সপ্তাহে একবার কেনাকাটা করতে পারবেন।\n" +
                    "আপনার পরবর্তী কেনাকাটার তারিখ: " + dateFormat.format(nextAllowedDate) + "\n" +
                    "আরও " + daysUntilNext + " দিন অপেক্ষা করুন।");
                
                // Disable purchase-related buttons
                paymentButton.setDisable(true);
                paymentButton.setText("সীমাবদ্ধ");
            }
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) {
        try {
            // Load the login page (FXMLDocument.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/t_c_b/FXMLDocument.fxml"));
            Parent root = loader.load();
            
            // Get the current stage
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            
            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            showAlert("Error", "Failed to load login page: " + e.getMessage());
        }
    }

    private void loadSharedData() {
        inventoryData.clear();
        salesData.clear();
        try (Connection conn = database.connectDb();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT product_id, product_name, price, stock FROM inventory");
            while (rs.next()) {
                inventoryData.add(new ProductData(
                    rs.getString("product_id"),
                    rs.getString("product_name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
            rs = stmt.executeQuery("SELECT sale_id, product_id, quantity, total_price, sale_date, user_id FROM sales");
            while (rs.next()) {
                salesData.add(new SaleData(
                    rs.getInt("sale_id"),
                    rs.getString("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price"),
                    rs.getString("sale_date"),
                    rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to load shared data: " + e.getMessage());
        }
    }

    private void loadProducts() {
        productGrid.getChildren().clear(); // Clear existing cards
        try (Connection conn = database.connectDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT product_id, product_name, price, stock FROM inventory")) {
            List<ProductData> products = new ArrayList<>();
            while (rs.next()) {
                products.add(new ProductData(
                    rs.getString("product_id"),
                    rs.getString("product_name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
            int column = 0;
            int row = 0;
            for (ProductData product : products) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/t_c_b/cardview.fxml"));
                AnchorPane card = loader.load();
                cardview controller = loader.getController();
                controller.setProductData(
                    product.getProductId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getStock(),
                    this
                );
                productGrid.add(card, column, row);
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            showAlert("Error", "Failed to load products: " + e.getMessage());
        }
    }

    public void addProductToCart(String productId, String productName, double price, int quantity) {
        // Check weekly purchase restriction first
        if (!canUserPurchase()) {
            Date nextAllowedDate = getNextAllowedPurchaseDate();
            if (nextAllowedDate != null) {
                long daysUntilNext = getDaysUntilNextPurchase();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                showAlert("Purchase Restricted", 
                    "আপনি সপ্তাহে একবার কেনাকাটা করতে পারবেন।\n" +
                    "আপনার পরবর্তী কেনাকাটার তারিখ: " + dateFormat.format(nextAllowedDate) + "\n" +
                    "আরও " + daysUntilNext + " দিন অপেক্ষা করুন।");
                return;
            }
        }

        // Enforce limits: 10 for P001, 3 for others
        int maxQuantity = productId.equals("P001") ? 10 : 3;
        for (Product existing : productList) {
            if (existing.getProductId().equals(productId)) {
                int newQuantity = existing.getQuantity() + quantity;
                if (newQuantity > maxQuantity) {
                    showAlert("Error", "Cannot add more than " + maxQuantity + " units of " + productName + "!");
                    return;
                }
                existing.quantityProperty().set(newQuantity);
                updateTotal();
                showAlert("Success", "Quantity updated in cart!");
                return;
            }
        }
        if (quantity > maxQuantity) {
            showAlert("Error", "Cannot add more than " + maxQuantity + " units of " + productName + "!");
            return;
        }
        productList.add(new Product(productId, productName, price, quantity));
        updateTotal();
        showAlert("Success", "Product added to cart!");
    }

    @FXML
    private void handlePaymentButton(ActionEvent event) {
        // Check weekly purchase restriction before processing payment
        if (!canUserPurchase()) {
            Date nextAllowedDate = getNextAllowedPurchaseDate();
            if (nextAllowedDate != null) {
                long daysUntilNext = getDaysUntilNextPurchase();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                showAlert("Purchase Restricted", 
                    "আপনি সপ্তাহে একবার কেনাকাটা করতে পারবেন।\n" +
                    "আপনার পরবর্তী কেনাকাটার তারিখ: " + dateFormat.format(nextAllowedDate) + "\n" +
                    "আরও " + daysUntilNext + " দিন অপেক্ষা করুন।");
                return;
            }
        }

        if (productList.isEmpty()) {
            showAlert("Error", "No products in cart to purchase!");
            return;
        }

        try {
            double payment = Double.parseDouble(paymentField.getText());
            if (payment < totalAmount) {
                showAlert("Error", "Payment amount is less than total amount!");
                return;
            }
            paymentLabel.setText(String.format("%.2f/-", payment));
            double change = payment - totalAmount;
            changeLabel.setText(String.format("%.2f/-", change));
            
            try (Connection conn = database.connectDb()) {
                conn.setAutoCommit(false);
                String insertSale = "INSERT INTO sales (product_id, quantity, total_price, sale_date, user_id) VALUES (?, ?, ?, ?, ?)";
                String updateStock = "UPDATE inventory SET stock = stock - ? WHERE product_id = ?";
                PreparedStatement saleStmt = conn.prepareStatement(insertSale);
                PreparedStatement stockStmt = conn.prepareStatement(updateStock);
                
                for (Product product : productList) {
                    saleStmt.setString(1, product.getProductId());
                    saleStmt.setInt(2, product.getQuantity());
                    saleStmt.setDouble(3, product.getPrice() * product.getQuantity());
                    saleStmt.setDate(4, new java.sql.Date(new Date().getTime()));
                    saleStmt.setInt(5, userId);
                    saleStmt.executeUpdate();
                    
                    stockStmt.setInt(1, product.getQuantity());
                    stockStmt.setString(2, product.getProductId());
                    stockStmt.executeUpdate();
                }
                
                conn.commit();
                
                // Update shared data
                loadSharedData();
                
                // Prompt for receipt
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Payment Success");
                alert.setHeaderText("Payment processed successfully!");
                alert.setContentText("Would you like to generate a receipt?");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);
                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        generateAndSaveReceipt();
                    }
                });
                
                // Clear cart and refresh UI
                productList.clear();
                updateTotal();
                loadProducts();
                
                // Disable payment button after successful purchase
                paymentButton.setDisable(true);
                paymentButton.setText("সীমাবদ্ধ");
                
                showAlert("Purchase Complete", 
                    "আপনার কেনাকাটা সম্পন্ন হয়েছে।\n" +
                    "পরবর্তী কেনাকাটা ৭ দিন পর করতে পারবেন।");
                
            } catch (SQLException e) {
                showAlert("Error", "Failed to save payment: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid payment amount!");
        }
    }

    private void generateAndSaveReceipt() {
        String receiptContent = generateTextReceipt();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Receipt Text");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        if (file != null) {
            try {
                Files.write(Paths.get(file.getAbsolutePath()), receiptContent.getBytes());
                showAlert("Success", "Receipt saved successfully!");
            } catch (IOException e) {
                showAlert("Error", "Failed to save receipt: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleRemoveButton(ActionEvent event) {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            updateTotal();
            showAlert("Success", "Selected product removed!");
        } else {
            showAlert("Error", "Please select a product to remove!");
        }
    }

    @FXML
    private void handleReceiptButton(ActionEvent event) {
        if (productList.isEmpty()) {
            showAlert("Error", "No products to include in the receipt!");
            return;
        }
        generateAndSaveReceipt();
    }

    private String generateTextReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("ট্রেডিং কর্পোরেশন অব বাংলাদেশ\n");
        receipt.append("গণপ্রজাতন্ত্রী বাংলাদেশ সরকার\n\n");
        receipt.append("রসিদ\n");
        receipt.append("তারিখ: ").append(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).append("\n\n");
        receipt.append(String.format("%-20s %-10s %-10s\n", "পণ্যের নাম", "মূল্য", "পরিমাণ"));
        receipt.append("-".repeat(40)).append("\n");
        for (Product product : productList) {
            receipt.append(String.format("%-20s %-10.2f %-10d\n", product.getName(), product.getPrice(), product.getQuantity()));
        }
        receipt.append("-".repeat(40)).append("\n");
        receipt.append(String.format("মোট: %.2f/-\n", totalAmount));
        receipt.append(String.format("প্রদান: %s\n", paymentLabel.getText()));
        receipt.append(String.format("ফেরৎ: %s\n", changeLabel.getText()));
        return receipt.toString();
    }

    private void updateTotal() {
        totalAmount = productList.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
        totalLabel.setText(String.format("%.2f/-", totalAmount));
        paymentLabel.setText("0.00/-");
        changeLabel.setText("0.00/-");
        paymentField.setText("");
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}