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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReportDController implements Initializable {

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;
    @FXML
    ComboBox<String> comBoxFacilitator; //Declaration of a ComboBox attribute to select the type of Facilitator.
    @FXML
    private TableView<ClientTransaction> tableView; //Declaration of TableView to show the columns selected by the Facilitator.
    @FXML
    private TableColumn<ClientTransaction, String> colFirstName; // Declaration of the column that will show the Client first name.
    @FXML
    private TableColumn<ClientTransaction, String> colLastName; //Declaration of the column that  will show the Client last name.
    @FXML
    private TableColumn<ClientTransaction, String> colPurchaseCount; // Declaration of the column that will show how many purchase has made the Client.
    @FXML
    private TableColumn<ClientTransaction, String> colTotalSpent; // Declaration of the column that will show the total spent by the Client.
    @FXML
    private Button btnSearch; //Declaration of the button to show the Table selected by the Facilitator.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //An abstract method used in JavaFX used to execute process when the program is start.

        comBoxFacilitator.setItems(getFacilitatorNames()); //Sets the items that are in the table thanks to the method 'getFacilitatorNames' in the comboBox

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName")); // References the Column in the TableView that will set and show the first name.
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName")); //References the lastname and sets it.
        colPurchaseCount.setCellValueFactory(new PropertyValueFactory<>("purchaseCount")); //References the count of purchase and sets it.
        colTotalSpent.setCellValueFactory(new PropertyValueFactory<>("totalSpent")); // References the summation of the total spent and sets it.
    }

    public void searchFacilitator(ActionEvent event) { //Method that will use the button to start the generation of the table.

        String selectedFacilitator = comBoxFacilitator.getValue(); //gets the value selected in the comboBox
        if (selectedFacilitator != null) { //Will evaluate if exists a selection of the Facilitator the process to show the hair.
            tableView.setItems(getTransactions(selectedFacilitator)); //The objects generated on the method are saved in the TableView.
        }
    }

     private ObservableList<String> getFacilitatorNames(){
        String query = "SELECT facilitator_name FROM facilitator"; // Is a SELECT that chooses al the names of the Facilitators in the table of it.
         ObservableList<String> facilitatorNames = FXCollections.observableArrayList(); //Is a list that will save Strings in an ObservableList
        try{ //
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank","root","apolo2004"); //starts the connection with the Data Base
            Statement st = conn.createStatement(); //Creates a statement that will be used to generate a result.
            ResultSet rs = st.executeQuery(query); //Execute the result and saves the values that the Query selects.

            while(rs.next()){ //While there's a next result, the ObservableList will continue saving the options.
                facilitatorNames.add(rs.getString("facilitator_name")); //Adds the ObservableList by getting the column
            }
        }catch(Exception e){ //catches if there's a mistake with the connection to the DataBase or looking for the Facilitator names.
            System.out.println(e.getMessage()); //prints the error
        }
        return facilitatorNames; //returns the ObservableList to be used in the comboBox
     }

     private ObservableList<ClientTransaction> getTransactions(String facilitatorName){
        //This the SELECT that will search the client name, lastname, total of purchase and count it, as the same time will get a summation of the amount spent.
         //This SELECT makes use of INNER JOINS too, to combine the four different tables used on this project.
         //The query also makes use of a WHERE in which the name of the facilitator will be evaluated to show the table in base of this. And it also groups and orders the information.
        String query = "SELECT c.client_first_name, c.client_last_name, " +
                 "COUNT(t.id_transaction) AS purchase_count, " +
                 "SUM(t.money_amount) AS total_spent " +
                 "FROM client c " +
                 "INNER JOIN card cr ON c.id_client = cr.id_client " +
                 "INNER JOIN transaction t ON cr.id_card = t.id_card " +
                 "INNER JOIN facilitator f ON cr.id_facilitator = f.id_facilitator " +
                 "WHERE f.facilitator_name = '" + facilitatorName + "' " +
                 "GROUP BY c.client_first_name, c.client_last_name " +
                 "ORDER BY c.client_first_name, c.client_last_name;";

        ObservableList<ClientTransaction> transactions = FXCollections.observableArrayList(); //Is a list that will save objects of the type ClientTransaction similar to the arrayList
        try{ //
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root","apolo2004"); //Starts the connection again.
            Statement st = conn.createStatement(); //generate a statement for this method to be used.
            ResultSet rs = st.executeQuery(query); //executes the query and saves the values.
            while (rs.next()){ //While there's another result keeps
                transactions.add(new ClientTransaction(
                        rs.getString("client_first_name"),
                        rs.getString("client_last_name"),
                        rs.getInt("purchase_count"),
                        rs.getDouble("total_spent")
                )); //Adds the ObservableList by getting the columns
            }
        }catch (Exception e){ //catches if there is a mistake with the connection and looking for the
            System.out.println(e.getMessage()); //prints the error
        }
        return transactions; //returns the ObservableList to be used in the button.
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