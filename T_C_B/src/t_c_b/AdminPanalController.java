package t_c_b;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminPanalController implements Initializable {

    @FXML
    private Button customer_btn;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button inventory_addBtn;
    @FXML
    private Button inventory_btn;
    @FXML
    private Button inventory_clearBtn;
    @FXML
    private TableColumn<Inventory, String> inventory_col_date;
    @FXML
    private TableColumn<Inventory, Double> inventory_col_price;
    @FXML
    private TableColumn<Inventory, String> inventory_col_productID;
    @FXML
    private TableColumn<Inventory, String> inventory_col_productName;
    @FXML
    private TableColumn<Inventory, Integer> inventory_col_stock;
    @FXML
    private TableColumn<Inventory, String> inventory_col_type;
    @FXML
    private Button inventory_deleteBtn;
    @FXML
    private AnchorPane inventory_form;
    @FXML
    private TableView<Inventory> inventory_tableView;
    @FXML
    private Button inventory_updateBtn;
    @FXML
    private Button logout_btn;
    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField inventory_productID;
    @FXML
    private TextField inventory_productname;
    @FXML
    private TextField inventory_price;
    @FXML
    private TextField inventory_stock;
    @FXML
    private Label user_count_label;
    @FXML
    private Label daily_income_label;
    @FXML
    private Label total_income_label;
    @FXML
    private Label total_products_sold_label;
    @FXML
    private PieChart stock_pie_chart; // Changed from AreaChart to PieChart
    @FXML
    private BarChart<String, Number> customer_chart;
    @FXML
    private AnchorPane customer_form;
    @FXML
    private TableView<User> customer_tableView;
    @FXML
    private TableColumn<User, Integer> customer_col_id;
    @FXML
    private TableColumn<User, Integer> customer_col_nid;
    @FXML
    private TableColumn<User, String> customer_col_tcbId;
    @FXML
    private TextField customer_nid;
    @FXML
    private TextField customer_tcbId;
    @FXML
    private TextField customer_password;
    @FXML
    private Button customer_addBtn;
    @FXML
    private Button customer_updateBtn;
    @FXML
    private Button customer_deleteBtn;
    @FXML
    private Button customer_clearBtn;
    @FXML
    private AnchorPane sales_form;
    @FXML
    private TableView<Sale> sales_tableView;
    @FXML
    private TableColumn<Sale, Integer> sales_col_saleId;
    @FXML
    private TableColumn<Sale, String> sales_col_productId;
    @FXML
    private TableColumn<Sale, Integer> sales_col_quantity;
    @FXML
    private TableColumn<Sale, Double> sales_col_totalPrice;
    @FXML
    private TableColumn<Sale, String> sales_col_saleDate;
    @FXML
    private TableColumn<Sale, Integer> sales_col_userId;
    @FXML
    private TextField sales_productId;
    @FXML
    private TextField sales_quantity;
    @FXML
    private TextField sales_totalPrice;
    @FXML
    private TextField sales_userId;
    @FXML
    private Button sales_addBtn;
    @FXML
    private Button sales_updateBtn;
    @FXML
    private Button sales_deleteBtn;
    @FXML
    private Button sales_clearBtn;
    @FXML
    private Button sales_btn;

    private ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
    private ObservableList<User> customerList = FXCollections.observableArrayList();
    private ObservableList<Sale> salesList = FXCollections.observableArrayList();
    private Alert alert;

    public static class Inventory {

        private String productId;
        private String productName;
        private String productType;
        private Integer stock;
        private Double price;
        private String dateAdded;

        public Inventory(String productId, String productName, String productType, Integer stock, Double price, String dateAdded) {
            this.productId = productId;
            this.productName = productName;
            this.productType = productType;
            this.stock = stock;
            this.price = price;
            this.dateAdded = dateAdded;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductType() {
            return productType;
        }

        public Integer getStock() {
            return stock;
        }

        public Double getPrice() {
            return price;
        }

        public String getDateAdded() {
            return dateAdded;
        }
    }

    public static class User {

        private int id;
        private int nidNumber;
        private String tcbId;

        public User(int id, int nidNumber, String tcbId) {
            this.id = id;
            this.nidNumber = nidNumber;
            this.tcbId = tcbId;
        }

        public int getId() {
            return id;
        }

        public int getNidNumber() {
            return nidNumber;
        }

        public String getTcbId() {
            return tcbId;
        }
    }

    public static class Sale {

        private int saleId;
        private String productId;
        private int quantity;
        private double totalPrice;
        private String saleDate;
        private int userId;

        public Sale(int saleId, String productId, int quantity, double totalPrice, String saleDate, int userId) {
            this.saleId = saleId;
            this.productId = productId;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.saleDate = saleDate;
            this.userId = userId;
        }

        public int getSaleId() {
            return saleId;
        }

        public String getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public String getSaleDate() {
            return saleDate;
        }

        public int getUserId() {
            return userId;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupInventoryTableColumns();
        setupCustomerTableColumns();
        setupSalesTableColumns();
        loadDashboardData();
        loadInventoryData();
        loadCustomerData();
        loadSalesData();
        inventory_form.setVisible(false);
        customer_form.setVisible(false);
        sales_form.setVisible(false);
        main_form.setVisible(true);
        inventory_tableView.setOnMouseClicked(this::inventoryTableClicked);
        customer_tableView.setOnMouseClicked(this::customerTableClicked);
        sales_tableView.setOnMouseClicked(this::salesTableClicked);
    }

    private void setupInventoryTableColumns() {
        inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("productType"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        inventory_tableView.setItems(inventoryList);
    }

    private void setupCustomerTableColumns() {
        customer_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        customer_col_nid.setCellValueFactory(new PropertyValueFactory<>("nidNumber"));
        customer_col_tcbId.setCellValueFactory(new PropertyValueFactory<>("tcbId"));
        customer_tableView.setItems(customerList);
    }

    private void setupSalesTableColumns() {
        sales_col_saleId.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        sales_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        sales_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sales_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        sales_col_saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        sales_col_userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        sales_tableView.setItems(salesList);
    }

    private void loadInventoryData() {
        inventoryList.clear();
        try (Connection conn = database.connectDb()) {
            if (conn != null) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM inventory"); ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        inventoryList.add(new Inventory(
                                rs.getString("product_id"),
                                rs.getString("product_name"),
                                rs.getString("product_type"),
                                rs.getInt("stock"),
                                rs.getDouble("price"),
                                rs.getString("date_added")
                        ));
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "ইনভেন্টরি ডেটা লোড করার সময় ত্রুটি৷: " + e.getMessage());
        }
    }

    private void loadCustomerData() {
        customerList.clear();
        try (Connection conn = database.connectDb()) {
            if (conn != null) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT id, NID_Number, TCB_ID FROM users WHERE is_admin = 0"); ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        customerList.add(new User(
                                rs.getInt("id"),
                                rs.getInt("NID_Number"),
                                rs.getString("TCB_ID")
                        ));
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "গ্রাহক ডেটা লোড করার সময় ত্রুটি৷: " + e.getMessage());
        }
    }

    private void loadSalesData() {
        salesList.clear();
        try (Connection conn = database.connectDb()) {
            if (conn != null) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sales"); ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        salesList.add(new Sale(
                                rs.getInt("sale_id"),
                                rs.getString("product_id"),
                                rs.getInt("quantity"),
                                rs.getDouble("total_price"),
                                rs.getString("sale_date"),
                                rs.getInt("user_id")
                        ));
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "বিক্রয় ডেটা লোড করার সময় ত্রুটি৷: " + e.getMessage());
        }
    }

    private void loadDashboardData() {
        try (Connection conn = database.connectDb()) {
            if (conn != null) {
                // Load user count
                try (PreparedStatement userStmt = conn.prepareStatement("SELECT COUNT(*) AS user_count FROM users WHERE is_admin = 0"); ResultSet userRs = userStmt.executeQuery()) {
                    if (userRs.next()) {
                        user_count_label.setText(String.valueOf(userRs.getInt("user_count")));
                    }
                }

                // Load daily income
                try (PreparedStatement dailyIncomeStmt = conn.prepareStatement("SELECT SUM(total_price) AS daily_income FROM sales WHERE sale_date = ?")) {
                    dailyIncomeStmt.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    try (ResultSet dailyIncomeRs = dailyIncomeStmt.executeQuery()) {
                        if (dailyIncomeRs.next()) {
                            double dailyIncome = dailyIncomeRs.getDouble("daily_income");
                            daily_income_label.setText(String.format("%.2f", dailyIncome));
                        }
                    }
                }

                // Load total income
                try (PreparedStatement totalIncomeStmt = conn.prepareStatement("SELECT SUM(total_price) AS total_income FROM sales"); ResultSet totalIncomeRs = totalIncomeStmt.executeQuery()) {
                    if (totalIncomeRs.next()) {
                        double totalIncome = totalIncomeRs.getDouble("total_income");
                        total_income_label.setText(String.format("%.2f", totalIncome));
                    }
                }

                // Load total products sold
                try (PreparedStatement productsSoldStmt = conn.prepareStatement("SELECT SUM(quantity) AS total_products FROM sales"); ResultSet productsSoldRs = productsSoldStmt.executeQuery()) {
                    if (productsSoldRs.next()) {
                        int totalProducts = productsSoldRs.getInt("total_products");
                        total_products_sold_label.setText(String.valueOf(totalProducts));
                    }
                }

                // Load stock data for PieChart
                loadStockPieChart(conn);

                // Load customer chart data
                XYChart.Series<String, Number> customerSeries = new XYChart.Series<>();
                customerSeries.setName("Customer Sales");
                try (PreparedStatement customerChartStmt = conn.prepareStatement("SELECT u.TCB_ID, COUNT(s.sale_id) AS sale_count FROM users u LEFT JOIN sales s ON u.id = s.user_id WHERE u.is_admin = 0 GROUP BY u.id, u.TCB_ID"); ResultSet customerChartRs = customerChartStmt.executeQuery()) {
                    while (customerChartRs.next()) {
                        customerSeries.getData().add(new XYChart.Data<>(customerChartRs.getString("TCB_ID"), customerChartRs.getInt("sale_count")));
                    }
                    customer_chart.getData().clear();
                    customer_chart.getData().add(customerSeries);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "ড্যাশবোর্ড ডেটা লোড করার সময় ত্রুটি৷: " + e.getMessage());
        }
    }

    /**
     * Load real-time stock data into PieChart
     */
    private void loadStockPieChart(Connection conn) {
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            
            // Query to get current stock levels for each product
            String query = "SELECT product_name, stock FROM inventory WHERE stock > 0 ORDER BY stock DESC";
            try (PreparedStatement stmt = conn.prepareStatement(query); 
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    int stock = rs.getInt("stock");
                    
                    // Add data to pie chart
                    pieChartData.add(new PieChart.Data(productName + " (" + stock + ")", stock));
                }
            }
            
            // Update the pie chart
            stock_pie_chart.setData(pieChartData);
            stock_pie_chart.setTitle("Current Stock Distribution");
            
            // Optional: Add some styling to the pie chart
            stock_pie_chart.setLegendVisible(true);
            stock_pie_chart.setLabelsVisible(true);
            
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Chart Error", "স্টক চার্ট ডেটা লোড করার সময় ত্রুটি হয়েছে: " + e.getMessage());
        }
    }

    @FXML
    private void dashboard_btn(ActionEvent event) {
        main_form.setVisible(true);
        inventory_form.setVisible(false);
        customer_form.setVisible(false);
        sales_form.setVisible(false);
        loadDashboardData(); // Refresh data when dashboard is opened
    }

    @FXML
    private void inventory_btn(ActionEvent event) {
        main_form.setVisible(false);
        inventory_form.setVisible(true);
        customer_form.setVisible(false);
        sales_form.setVisible(false);
        loadInventoryData();
    }

    @FXML
    private void customer_btn(ActionEvent event) {
        main_form.setVisible(false);
        inventory_form.setVisible(false);
        customer_form.setVisible(true);
        sales_form.setVisible(false);
        loadCustomerData();
    }

    @FXML
    private void sales_btn(ActionEvent event) {
        main_form.setVisible(false);
        inventory_form.setVisible(false);
        customer_form.setVisible(false);
        sales_form.setVisible(true);
        loadSalesData();
    }

    @FXML
    private void inventory_addBtn(ActionEvent event) {
        String productId = inventory_productID.getText().trim();
        String productName = inventory_productname.getText().trim();
        String productType = "Food";
        String stockText = inventory_stock.getText().trim();
        String priceText = inventory_price.getText().trim();

        if (productId.isEmpty() || productName.isEmpty() || stockText.isEmpty() || priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "অনুগ্রহ করে সমস্ত ফর্মের ঘর পূরণ করুন।");
            return;
        }

        try {
            int stock = Integer.parseInt(stockText);
            double price = Double.parseDouble(priceText);
            String dateAdded = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO inventory (product_id, product_name, product_type, stock, price, date_added) VALUES (?, ?, ?, ?, ?, ?)")) {
                        stmt.setString(1, productId);
                        stmt.setString(2, productName);
                        stmt.setString(3, productType);
                        stmt.setInt(4, stock);
                        stmt.setDouble(5, price);
                        stmt.setString(6, dateAdded);
                        stmt.executeUpdate();
                        loadInventoryData();
                        loadDashboardData(); // Refresh dashboard data including pie chart
                        clearInventoryFields();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "পণ্য সফলভাবে যোগ করা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "স্টক এবং মূল্য অবশ্যই সংখ্যা হতে হবে");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "পণ্য যোগ করার সময় ত্রুটি: " + e.getMessage());
        }
    }

    @FXML
    private void inventory_updateBtn(ActionEvent event) {
        Inventory selected = inventory_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "আপডেট করার জন্য একটি পণ্য নির্বাচন করুন");
            return;
        }

        String productId = inventory_productID.getText().trim();
        String productName = inventory_productname.getText().trim();
        String productType = "Food";
        String stockText = inventory_stock.getText().trim();
        String priceText = inventory_price.getText().trim();

        if (productId.isEmpty() || productName.isEmpty() || stockText.isEmpty() || priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "অনুগ্রহ করে সমস্ত ফর্মের ঘর পূরণ করুন।");
            return;
        }

        try {
            int stock = Integer.parseInt(stockText);
            double price = Double.parseDouble(priceText);
            String dateAdded = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("UPDATE inventory SET product_name = ?, product_type = ?, stock = ?, price = ?, date_added = ? WHERE product_id = ?")) {
                        stmt.setString(1, productName);
                        stmt.setString(2, productType);
                        stmt.setInt(3, stock);
                        stmt.setDouble(4, price);
                        stmt.setString(5, dateAdded);
                        stmt.setString(6, productId);
                        stmt.executeUpdate();
                        loadInventoryData();
                        loadDashboardData(); // Refresh dashboard data including pie chart
                        clearInventoryFields();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "পণ্য সফলভাবে আপডেট করা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "স্টক এবং মূল্য অবশ্যই সংখ্যা হতে হবে");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "পণ্য আপডেট করার সময় ত্রুটি: " + e.getMessage());
        }
    }

    @FXML
    private void inventory_deleteBtn(ActionEvent event) {
        Inventory selected = inventory_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "মুছে ফেলার জন্য একটি পণ্য নির্বাচন করুন।");
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("আপনি কি নিশ্চিত যে আপনি পণ্যটি মুছে ফেলতে চান? " + selected.getProductId() + "?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM inventory WHERE product_id = ?")) {
                        stmt.setString(1, selected.getProductId());
                        stmt.executeUpdate();
                        loadInventoryData();
                        loadDashboardData(); // Refresh dashboard data including pie chart
                        clearInventoryFields();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "পণ্য সফলভাবে মুছে ফেলা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "পণ্য মুছে ফেলার সময় ত্রুটি: " + e.getMessage());
            }
        }
    }

    @FXML
    private void inventory_clearBtn(ActionEvent event) {
        clearInventoryFields();
    }

    private void inventoryTableClicked(MouseEvent event) {
        Inventory selected = inventory_tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            inventory_productID.setText(selected.getProductId());
            inventory_productname.setText(selected.getProductName());
            inventory_stock.setText(String.valueOf(selected.getStock()));
            inventory_price.setText(String.format("%.2f", selected.getPrice()));
        }
    }

    private void clearInventoryFields() {
        inventory_productID.clear();
        inventory_productname.clear();
        inventory_stock.clear();
        inventory_price.clear();
    }

    // Rest of the methods remain the same...
    @FXML
    private void customer_addBtn(ActionEvent event) {
        String nid = customer_nid.getText().trim();
        String tcbId = customer_tcbId.getText().trim();
        String password = customer_password.getText().trim();

        if (nid.isEmpty() || tcbId.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "অনুগ্রহ করে সমস্ত ফর্মের ঘর পূরণ করুন।");
            return;
        }

        try {
            int nidNumber = Integer.parseInt(nid);
            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (NID_Number, TCB_ID, Password, is_admin) VALUES (?, ?, ?, 0)")) {
                        stmt.setInt(1, nidNumber);
                        stmt.setString(2, tcbId);
                        stmt.setString(3, password);
                        stmt.executeUpdate();
                        loadCustomerData();
                        clearCustomerFields();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "গ্রাহক সফলভাবে যোগ করা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "ডাটাবেসের সাথে সংযোগ করতে ব্যর্থ হয়েছে");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "এনআইডি নম্বর অবশ্যই একটি নম্বর হতে হবে");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "গ্রাহক যোগ করার সময় ত্রুটি: " + e.getMessage());
        }
    }

    @FXML
    private void customer_updateBtn(ActionEvent event) {
        User selected = customer_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "আপডেট করার জন্য অনুগ্রহ করে একজন গ্রাহক নির্বাচন করুন।");
            return;
        }

        String nid = customer_nid.getText().trim();
        String tcbId = customer_tcbId.getText().trim();
        String password = customer_password.getText().trim();

        if (nid.isEmpty() || tcbId.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "অনুগ্রহ করে সমস্ত ফর্মের ঘর পূরণ করুন।");
            return;
        }

        try {
            int nidNumber = Integer.parseInt(nid);
            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("UPDATE users SET NID_Number = ?, TCB_ID = ?, Password = ? WHERE id = ? AND is_admin = 0")) {
                        stmt.setInt(1, nidNumber);
                        stmt.setString(2, tcbId);
                        stmt.setString(3, password);
                        stmt.setInt(4, selected.getId());
                        stmt.executeUpdate();
                        loadCustomerData();
                        clearCustomerFields();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "গ্রাহক সফলভাবে আপডেট করা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "এনআইডি নম্বর অবশ্যই একটি বৈধ নম্বর হতে হবে");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error updating customer: " + e.getMessage());
        }
    }

    @FXML
    private void customer_deleteBtn(ActionEvent event) {
        User selected = customer_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "মুছে ফেলার জন্য অনুগ্রহ করে একজন গ্রাহক নির্বাচন করুন");
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete customer " + selected.getTcbId() + "?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ? AND is_admin = 0")) {
                        stmt.setInt(1, selected.getId());
                        stmt.executeUpdate();
                        loadCustomerData();
                        clearCustomerFields();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "গ্রাহক সফলভাবে মুছে ফেলা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting customer: " + e.getMessage());
            }
        }
    }

    @FXML
    private void customer_clearBtn(ActionEvent event) {
        clearCustomerFields();
    }

    private void customerTableClicked(MouseEvent event) {
        User selected = customer_tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            customer_nid.setText(String.valueOf(selected.getNidNumber()));
            customer_tcbId.setText(selected.getTcbId());
            customer_password.clear();
        }
    }

    private void clearCustomerFields() {
        customer_nid.clear();
        customer_tcbId.clear();
        customer_password.clear();
    }

    @FXML
    private void sales_addBtn(ActionEvent event) {
        String productId = sales_productId.getText().trim();
        String quantityText = sales_quantity.getText().trim();
        String totalPriceText = sales_totalPrice.getText().trim();
        String userIdText = sales_userId.getText().trim();

        if (productId.isEmpty() || quantityText.isEmpty() || totalPriceText.isEmpty() || userIdText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "অনুগ্রহ করে সমস্ত ফর্মের ঘর পূরণ করুন।");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            double totalPrice = Double.parseDouble(totalPriceText);
            int userId = Integer.parseInt(userIdText);
            String saleDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement checkProductStmt = conn.prepareStatement("SELECT COUNT(*) FROM inventory WHERE product_id = ?")) {
                        checkProductStmt.setString(1, productId);
                        try (ResultSet productRs = checkProductStmt.executeQuery()) {
                            productRs.next();
                            if (productRs.getInt(1) == 0) {
                                showAlert(Alert.AlertType.ERROR, "Form Error", "অবৈধ পণ্য ID");
                                return;
                            }
                        }
                    }

                    try (PreparedStatement checkUserStmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE id = ? AND is_admin = 0")) {
                        checkUserStmt.setInt(1, userId);
                        try (ResultSet userRs = checkUserStmt.executeQuery()) {
                            userRs.next();
                            if (userRs.getInt(1) == 0) {
                                showAlert(Alert.AlertType.ERROR, "Form Error", "অবৈধ ব্যবহারকারী আইডি");
                                return;
                            }
                        }
                    }

                    try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO sales (product_id, quantity, total_price, sale_date, user_id) VALUES (?, ?, ?, ?, ?)")) {
                        stmt.setString(1, productId);
                        stmt.setInt(2, quantity);
                        stmt.setDouble(3, totalPrice);
                        stmt.setString(4, saleDate);
                        stmt.setInt(5, userId);
                        stmt.executeUpdate();
                        loadSalesData();
                        clearSalesFields();
                        loadDashboardData(); // Refresh dashboard data
                        showAlert(Alert.AlertType.INFORMATION, "Success", "বিক্রয় সফলভাবে যোগ করা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "পরিমাণ, মোট মূল্য এবং ব্যবহারকারীর আইডি অবশ্যই বৈধ সংখ্যা হতে হবে");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error adding sale: " + e.getMessage());
        }
    }

    @FXML
    private void sales_updateBtn(ActionEvent event) {
        Sale selected = sales_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "আপডেট করার জন্য একটি বিক্রয় নির্বাচন করুন।");
            return;
        }

        String productId = sales_productId.getText().trim();
        String quantityText = sales_quantity.getText().trim();
        String totalPriceText = sales_totalPrice.getText().trim();
        String userIdText = sales_userId.getText().trim();

        if (productId.isEmpty() || quantityText.isEmpty() || totalPriceText.isEmpty() || userIdText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "অনুগ্রহ করে সমস্ত ফর্মের ঘর পূরণ করুন।");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            double totalPrice = Double.parseDouble(totalPriceText);
            int userId = Integer.parseInt(userIdText);
            String saleDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement checkProductStmt = conn.prepareStatement("SELECT COUNT(*) FROM inventory WHERE product_id = ?")) {
                        checkProductStmt.setString(1, productId);
                        try (ResultSet productRs = checkProductStmt.executeQuery()) {
                            productRs.next();
                            if (productRs.getInt(1) == 0) {
                                showAlert(Alert.AlertType.ERROR, "Form Error", "অবৈধ পণ্য ID");
                                return;
                            }
                        }
                    }

                    try (PreparedStatement checkUserStmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE id = ? AND is_admin = 0")) {
                        checkUserStmt.setInt(1, userId);
                        try (ResultSet userRs = checkUserStmt.executeQuery()) {
                            userRs.next();
                            if (userRs.getInt(1) == 0) {
                                showAlert(Alert.AlertType.ERROR, "Form Error", "Invalid User ID");
                                return;
                            }
                        }
                    }

                    try (PreparedStatement stmt = conn.prepareStatement("UPDATE sales SET product_id = ?, quantity = ?, total_price = ?, sale_date = ?, user_id = ? WHERE sale_id = ?")) {
                        stmt.setString(1, productId);
                        stmt.setInt(2, quantity);
                        stmt.setDouble(3, totalPrice);
                        stmt.setString(4, saleDate);
                        stmt.setInt(5, userId);
                        stmt.setInt(6, selected.getSaleId());
                        stmt.executeUpdate();
                        loadSalesData();
                        clearSalesFields();
                        loadDashboardData(); // Refresh dashboard data
                        showAlert(Alert.AlertType.INFORMATION, "Success", "বিক্রয় সফলভাবে আপডেট করা হয়েছে");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Quantity, Total Price, and User ID must be valid numbers");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error updating sale: " + e.getMessage());
        }
    }

    @FXML
    private void sales_deleteBtn(ActionEvent event) {
        Sale selected = sales_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "মুছে ফেলার জন্য একটি বিক্রয় নির্বাচন করুন।");
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete sale ID " + selected.getSaleId() + "?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            try (Connection conn = database.connectDb()) {
                if (conn != null) {
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM sales WHERE sale_id = ?")) {
                        stmt.setInt(1, selected.getSaleId());
                        stmt.executeUpdate();
                        loadSalesData();
                        clearSalesFields();
                        loadDashboardData(); // Refresh dashboard data
                        showAlert(Alert.AlertType.INFORMATION, "Success", "বিক্রয় সফলভাবে মুছে ফেলা হয়েছে৷");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting sale: " + e.getMessage());
            }
        }
    }

    @FXML
    private void sales_clearBtn(ActionEvent event) {
        clearSalesFields();
    }

    private void salesTableClicked(MouseEvent event) {
        Sale selected = sales_tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            sales_productId.setText(selected.getProductId());
            sales_quantity.setText(String.valueOf(selected.getQuantity()));
            sales_totalPrice.setText(String.format("%.2f", selected.getTotalPrice()));
            sales_userId.setText(String.valueOf(selected.getUserId()));
        }
    }

    private void clearSalesFields() {
        sales_productId.clear();
        sales_quantity.clear();
        sales_totalPrice.clear();
        sales_userId.clear();
    }

    @FXML
    public void logout() throws IOException {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("তুমি কি নিশ্চিত যে তুমি লগ আউট করতে চাও??");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            Stage currentStage = (Stage) logout_btn.getScene().getWindow();
            currentStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/t_c_b/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("T_C_B");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}