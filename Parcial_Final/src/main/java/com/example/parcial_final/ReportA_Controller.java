package com.example.parcial_final;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportA_Controller implements Initializable {
    @FXML
    private TextField txtClientId;

    @FXML
    private DatePicker dpInitialDate;

    @FXML
    private DatePicker dpFinalDate;

    @FXML
    private Button btnReport;

    @FXML
    private TableView<Transaction> tableTransactions;

    @FXML
    private TableColumn<Transaction, LocalDate> colPurchaseDate;

    @FXML
    private TableColumn<Transaction, Double> colAmount;

    @FXML
    private TableColumn<Transaction, String> colDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    public void generateReport(){
        int clientId = Integer.parseInt(txtClientId.getText());
        LocalDate initialDate = dpInitialDate.getValue();
        LocalDate finalDate = dpFinalDate.getValue();

        ArrayList<Transaction> transactions = getTransactions(clientId, initialDate, finalDate);
        tableTransactions.getItems().setAll(transactions);
    }

    private ArrayList<Transaction> getTransactions(int clientId, LocalDate initialDate, LocalDate finalDate) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!");
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT t.transaction_date, t.money_amount, t.description " +
                            "FROM transaction t " +
                            "INNER JOIN card c ON t.id_card = c.id_card " +
                            "WHERE c.id_client = ? AND t.transaction_date BETWEEN ? AND ?");

            pst.setInt(1, clientId);
            pst.setDate(2, Date.valueOf(initialDate));
            pst.setDate(3, Date.valueOf(finalDate));

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                LocalDate purchaseDate = rs.getDate("transaction_date").toLocalDate();
                double amount = rs.getDouble("money_amount");
                String description = rs.getString("description");

                transactions.add(new Transaction(purchaseDate, amount, description));
            }

        }catch (Exception e){
            System.out.println(e);
        }

        return transactions;
    }
}
