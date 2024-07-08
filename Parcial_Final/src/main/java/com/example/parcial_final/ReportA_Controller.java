package com.example.parcial_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportA_Controller implements Initializable { //Implementing the interface Initializable

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;
    @FXML
    private Button btnReturn;//00080323 Button to return to the home window.

    @FXML
    private TextField txtClientId; //Declares a text field linked to the fxml to input the client's id

    @FXML
    private DatePicker dpInitialDate; //Declares a date picker linked to the fxml to input the initial date

    @FXML
    private DatePicker dpFinalDate; //Declares a date picker linked to the fxml to input the final date

    @FXML
    private Button btnReport; //Declares a date picker linked to the fxml that represents the button to generate the report

    @FXML
    private TableView<Transaction> tableTransactions; //Declares a table view linked to the fxml to display the information of transactions

    @FXML
    private TableColumn<Transaction, LocalDate> colPurchaseDate; //Declares a column linked to the fxml inside the table view for the date of purchase

    @FXML
    private TableColumn<Transaction, Double> colAmount; //Declares a column linked to the fxml inside the table view for the amount

    @FXML
    private TableColumn<Transaction, String> colDescription; //Declares a column linked to the fxml inside the table view for the description

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //Method called automatically upon the initialization of the program
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate")); //Sets up the purchase date column by associating it with the "purchaseDate" attribute from the Transaction class
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount")); //Sets up the amount column by associating it with the "amount" attribute from the Transaction class
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description")); //Sets up the description column by associating it with the "description" attribute from the Transaction class
    }

    @FXML
    public void generateReport(){
        int clientId = Integer.parseInt(txtClientId.getText()); //Retreives the value inputed in txtClientId and stores them in a variable
        LocalDate initialDate = dpInitialDate.getValue(); //Retreives the value inputed in initialDate and stores them in a variable
        LocalDate finalDate = dpFinalDate.getValue(); //Retreives the value inputed in finalDate and stores them in a variable

        ArrayList<Transaction> transactions = getTransactions(clientId, initialDate, finalDate); // Retrieves transactions for a client within a date range and stores them in an ArrayList
        tableTransactions.getItems().setAll(transactions); //Sets the items in the table view to display the transactions
    }

    private ArrayList<Transaction> getTransactions(int clientId, LocalDate initialDate, LocalDate finalDate) {
        ArrayList<Transaction> transactions = new ArrayList<>(); //A new arraylist of Transactions is declared
        try { //Tries to excecute the following block of code
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); //Establishes the connection to the database
            PreparedStatement pst = conn.prepareStatement( //Prepares the SQL statement to retrieve transactions through a query
                            "SELECT t.transaction_date, t.money_amount, t.description " + //Selects date, amount, and description fields
                            "FROM transaction t " + //From the transaction table with alias 't'
                            "INNER JOIN card c ON t.id_card = c.id_card " + //Makes an inner join with card table on card ID
                                    "WHERE c.id_client = ? AND t.transaction_date BETWEEN ? AND ?"); //Filters the query depending on the client ID and date range
            pst.setInt(1, clientId); //Sets the first parameters for the query which is the id
            pst.setDate(2, Date.valueOf(initialDate)); //Sets the second parameters for the query which is the initial date
            pst.setDate(3, Date.valueOf(finalDate)); //Sets the third parameters for the query which is the final date

            ResultSet rs = pst.executeQuery(); //Executes the query and stores it in a variable

            while (rs.next()) { //Iterates through the result set until there is none left
                LocalDate purchaseDate = rs.getDate("transaction_date").toLocalDate(); //Gets the transaction date, converts it to LocalDate and stores it in a variable
                double amount = rs.getDouble("money_amount"); //Gets the transaction amount and stores it in a variable
                String description = rs.getString("description"); //Gets the transaction description and stores it in a variable

                transactions.add(new Transaction(purchaseDate, amount, description)); //Adds a new Transaction to the list using the previous variables as parameters
            }

        }catch (Exception e){ //Manages the exception in case there is an error
            System.out.println(e); //Prints the error
        }

        return transactions; //Returns the Arraylist of transactions
    }


    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
