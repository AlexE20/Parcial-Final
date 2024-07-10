package com.example.parcial_final;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReportD_Controller implements Initializable {

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;
    @FXML
    ComboBox<String> comBoxFacilitator; //00005923 Declaration of a ComboBox attribute to select the type of Facilitator.
    @FXML
    private TableView<ClientTransaction> tvClientTransactions; //00005923 Declaration of TableView to show the columns selected by the Facilitator.
    @FXML
    private TableColumn<ClientTransaction, String> colFirstName; // 00005923Declaration of the column that will show the Client first name.
    @FXML
    private TableColumn<ClientTransaction, String> colLastName; //00005923 Declaration of the column that  will show the Client last name.
    @FXML
    private TableColumn<ClientTransaction, String> colPurchaseCount; //00005923 Declaration of the column that will show how many purchase has made the Client.
    @FXML
    private TableColumn<ClientTransaction, String> colTotalSpent; //00005923 Declaration of the column that will show the total spent by the Client.
    @FXML
    private Button btnSearch; //00005923 Declaration of the button to show the Table selected by the Facilitator.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //00005923 An abstract method used in JavaFX used to execute process when the program is start.

        comBoxFacilitator.setItems(getFacilitatorNames()); //00005923 Sets the items that are in the table thanks to the method 'getFacilitatorNames' in the comboBox

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName")); //00005923 References the Column in the TableView that will set and show the first name.
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName")); //00005923References the lastname and sets it.
        colPurchaseCount.setCellValueFactory(new PropertyValueFactory<>("purchaseCount")); //00005923 References the count of purchase and sets it.
        colTotalSpent.setCellValueFactory(new PropertyValueFactory<>("totalSpent")); //00005923 References the summation of the total spent and sets it.
    }

    @FXML
    public void searchFacilitator() { //00005923 Method that will use the button to start the generation of the table.

        String selectedFacilitator = comBoxFacilitator.getValue(); //00005923 gets the value selected in the comboBox
        if (selectedFacilitator != null) { //00005923 Will evaluate if exists a selection of the Facilitator the process to show the hair.
            tvClientTransactions.setItems(getTransactions(selectedFacilitator)); //00005923 The objects generated on the method are saved in the TableView.
        }
        generateFile();
    }

     private ObservableList<String> getFacilitatorNames(){
        String query = "SELECT facilitator_name FROM facilitator"; //00005923 Is a SELECT that chooses al the names of the Facilitators in the table of it.
         ObservableList<String> facilitatorNames = FXCollections.observableArrayList(); //00005923Is a list that will save Strings in an ObservableList
        try{ //
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco","root","Elchocochele04!"); //00005923 starts the connection with the Data Base
            Statement st = conn.createStatement(); //00005923 Creates a statement that will be used to generate a result.
            ResultSet rs = st.executeQuery(query); //00005923 Execute the result and saves the values that the Query selects.

            while(rs.next()){ //00005923 While there's a next result, the ObservableList will continue saving the options.
                facilitatorNames.add(rs.getString("facilitator_name")); //00005923 Adds the ObservableList by getting the column
            }
        }catch(Exception e){ //00005923 catches if there's a mistake with the connection to the DataBase or looking for the Facilitator names.
            System.out.println(e); //00005923 prints the error
        }
        return facilitatorNames; //00005923 returns the ObservableList to be used in the comboBox
     }

     private ObservableList<ClientTransaction> getTransactions(String facilitatorName){
        String query = "SELECT c.client_first_name, c.client_last_name, " +//00005923 This the SELECT that will search the client name, lastname, total of purchase and count it, as the same time will get a summation of the amount spent.
                 "COUNT(t.id_transaction) AS purchase_count, " +
                 "SUM(t.money_amount) AS total_spent " +
                 "FROM client c " +
                 "INNER JOIN card cr ON c.id_client = cr.id_client " + //00005923 This SELECT makes use of INNER JOINS too, to combine the four different tables used on this project.
                 "INNER JOIN transaction t ON cr.id_card = t.id_card " +
                 "INNER JOIN facilitator f ON cr.id_facilitator = f.id_facilitator " +
                 "WHERE f.facilitator_name = '" + facilitatorName + "' " + //00005923 The query also makes use of a WHERE in which the name of the facilitator will be evaluated to show the table in base of this. And it also groups and orders the information.
                 "GROUP BY c.client_first_name, c.client_last_name " +
                 "ORDER BY c.client_first_name, c.client_last_name;";

        ObservableList<ClientTransaction> transactions = FXCollections.observableArrayList(); //00005923 Is a list that will save objects of the type ClientTransaction similar to the arrayList
        try{ //00005923 tries to excecute the block of code
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root","Elchocochele04!"); //00005923 Starts the connection again.
            Statement st = conn.createStatement(); //00005923 generate a statement for this method to be used.
            ResultSet rs = st.executeQuery(query); //00005923 executes the query and saves the values.
            while (rs.next()){ //00005923 While there's another result keeps
                transactions.add(new ClientTransaction(
                        rs.getString("client_first_name"),
                        rs.getString("client_last_name"),
                        rs.getInt("purchase_count"),
                        rs.getDouble("total_spent")
                )); //00005923 Adds the ObservableList by getting the columns
            }
        }catch (Exception e){ //00005923 catches if there is a mistake with the connection and looking for the
            System.out.println(e); //00005923 prints the error
        }
        return transactions; //00005923 returns the ObservableList to be used in the button.
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

        String fileName = path + "Report-D-" + dtf.format(now) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            ObservableList<ClientTransaction> reportList = tvClientTransactions.getItems();

            if (reportList.isEmpty()) {
                writer.write("No data available\n");
            } else {
                for (ClientTransaction clientTransaction : reportList) {
                    writer.write("Client: " + clientTransaction.getFirstName() + " " + clientTransaction.getLastName() + "\nPurchase count: " + clientTransaction.getPurchaseCount() + "\nTotal spent: " + clientTransaction.getTotalSpent() + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
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