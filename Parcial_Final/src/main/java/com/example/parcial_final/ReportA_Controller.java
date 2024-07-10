package com.example.parcial_final;

import javafx.collections.ObservableList;
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

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportA_Controller implements Initializable { //00106123 Implementing the interface Initializable

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;

    @FXML
    private Button btnReturn;//00080323 Button to return to the home window.

    @FXML
    private TextField txtClientId; //00106123 Declares a text field linked to the fxml to input the client's id

    @FXML
    private DatePicker dpInitialDate; //00106123 Declares a date picker linked to the fxml to input the initial date

    @FXML
    private DatePicker dpFinalDate; //00106123 Declares a date picker linked to the fxml to input the final date

    @FXML
    private Button btnReport; //00106123 Declares a date picker linked to the fxml that represents the button to generate the report

    @FXML
    private TableView<Transaction> tvTransactions; //00106123 Declares a table view linked to the fxml to display the information of transactions

    @FXML
    private TableColumn<Transaction, LocalDate> colPurchaseDate; //00106123 Declares a column linked to the fxml inside the table view for the date of purchase

    @FXML
    private TableColumn<Transaction, Double> colAmount; //00106123 Declares a column linked to the fxml inside the table view for the amount

    @FXML
    private TableColumn<Transaction, String> colDescription; //00106123 Declares a column linked to the fxml inside the table view for the description

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //00106123 Method called automatically upon the initialization of the program
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate")); //00106123 Sets up the purchase date column by associating it with the "purchaseDate" attribute from the Transaction class
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount")); //00106123 Sets up the amount column by associating it with the "amount" attribute from the Transaction class
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description")); //00106123 Sets up the description column by associating it with the "description" attribute from the Transaction class
    }

    @FXML
    public void generateReport(){
        int clientId = Integer.parseInt(txtClientId.getText()); //00106123 Retreives the value inputed in txtClientId and stores them in a variable
        LocalDate initialDate = dpInitialDate.getValue(); //00106123 Retreives the value inputed in initialDate and stores them in a variable
        LocalDate finalDate = dpFinalDate.getValue(); //00106123 Retreives the value inputed in finalDate and stores them in a variable

        ArrayList<Transaction> transactions = getTransactions(clientId, initialDate, finalDate); //00106123 Retrieves transactions for a client within a date range and stores them in an ArrayList
        tvTransactions.getItems().setAll(transactions); //00106123 Sets the items in the table view to display the transactions
        generateFile(); //Genera el archivo de texto con el reporte individual
    }

    private ArrayList<Transaction> getTransactions(int clientId, LocalDate initialDate, LocalDate finalDate) {
        ArrayList<Transaction> transactions = new ArrayList<>(); //00106123 A new arraylist of Transactions is declared
        try { //00106123 Tries to excecute the following block of code
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); //00106123 Establishes the connection to the database
            PreparedStatement pst = conn.prepareStatement( //00106123 Prepares the SQL statement to retrieve transactions through a query
                            "SELECT t.transaction_date, t.money_amount, t.description " + //00106123 Selects date, amount, and description fields
                            "FROM transaction t " + //00106123 From the transaction table with alias 't'
                            "INNER JOIN card c ON t.id_card = c.id_card " + //00106123 Makes an inner join with card table on card ID
                                    "WHERE c.id_client = ? AND t.transaction_date BETWEEN ? AND ?"); //00106123 Filters the query depending on the client ID and date range
            pst.setInt(1, clientId); //00106123 Sets the first parameters for the query which is the id
            pst.setDate(2, Date.valueOf(initialDate)); //00106123 Sets the second parameters for the query which is the initial date
            pst.setDate(3, Date.valueOf(finalDate)); //00106123 Sets the third parameters for the query which is the final date

            ResultSet rs = pst.executeQuery(); //00106123 Executes the query and stores it in a variable

            while (rs.next()) { //00106123 Iterates through the result set until there is none left
                LocalDate purchaseDate = rs.getDate("transaction_date").toLocalDate(); // 00106123Gets the transaction date, converts it to LocalDate and stores it in a variable
                double amount = rs.getDouble("money_amount"); //00106123 Gets the transaction amount and stores it in a variable
                String description = rs.getString("description"); //00106123 Gets the transaction description and stores it in a variable

                transactions.add(new Transaction(purchaseDate, amount, description)); //00106123 Adds a new Transaction to the list using the previous variables as parameters
            }

        }catch (Exception e){ //00106123 Manages the exception in case there is an error
            System.out.println(e); //00106123Prints the error
        }

        return transactions; //00106123 Returns the Arraylist of transactions
    }

    public void generateFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String path = "Reports/";

        try { // 00106123 se intenta ejecutar el siguiente bloque de codigo
            Files.createDirectories(Paths.get(path)); //00106123 Revisa si la carpeta que se le paso existe, y si no, la crea
        } catch (IOException e) { //00106123 manejo de la excepcion
            System.out.println(e); //00106123 se imprime la excepcion
        }

        String fileName = path + "Report-A-" + dtf.format(now) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            ObservableList<Transaction> reportList = tvTransactions.getItems();

            if (reportList.isEmpty()) {
                writer.write("No data available\n");
            } else {
                for (Transaction transaction : reportList) {
                    writer.write("Purchase date: " + transaction.getPurchaseDate() + "\nAmount: " + transaction.getAmount() + "\nDescription: " + transaction.getDescription() + "\n");
                }
            }

        } catch (IOException e) { //00106123 Captura la Exception
            System.out.println(e); //00106123 Imprime un mensaje del error
        }
    }


    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException { //00080323 Método para retornar a la ventana principal.
        try {
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml")); //00080323 Cargamos la pantalla inicial.
        } catch (NullPointerException e) { //00080323 Manejamos la exepción.
            e.printStackTrace(); // 00080323 Imprimimos exepción.
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //00080323 Método para  obtener la ventana actual.
        scene = new Scene(root); // 00080323 Instanciamos una nueva escena.
        stage.setScene(scene); //00080323 Cambiamos la escena de la ventana.
        stage.show(); //00080323 Mostramos nueva escena en la ventana.
    }
}
