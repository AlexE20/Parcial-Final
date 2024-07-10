package com.example.parcial_final; // Define el paquete del archivo 00009423

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionController implements Initializable { // Define la clase principal  00009423

    private Parent root;//00080323 Es el nodo ruta del archivo FXML.
    private Stage stage;//00080323 Es la ventana de la aplicación
    private Scene scene;//00080323 Es el contenido adentro de la ventana, es decir lo que cambia dinámicamente.


    @FXML
    private TextField txtMoneyAmount; // Define el campo de texto para la cantidad de dinero de la transacción  00009423
    @FXML
    private TextField txtDesc; // Define el campo de texto para la descripción de la transacción  00009423
    @FXML
    private TextField txtIdCard; // Define el campo de texto para el ID de la tarjeta de la transacción  00009423

    @FXML
    private DatePicker dpTransaction; //Define datePicker 00005923

    @FXML
    private TextField txtIdTransactionDel; //Define el textField que se ingresara el ID de Transaction para borrarlo 00005923



    @FXML
    private Button btnReturn;


    @FXML
    private TableView<Transaction> tableView;
    @FXML
    private TableColumn<Transaction, Integer> colId;
    @FXML
    private TableColumn<Transaction, Double> colMoneyAmount;
    @FXML
    private TableColumn<Transaction, String> colDescription;
    @FXML
    private TableColumn<Transaction, Integer> colCardId;
    @FXML
    private TableColumn<Transaction, LocalDate> colTransactionDate;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // Método de inicialización  00009423

        initializeTableColumns();
        loadTransactionsFromDatabase();
        showInfoAlert("In case you want to make an update or a deletion, you must write the id of \n the registre, if you want to \n insert, the id is not necessary."); //Muestra el mensaje al iniciar esta ventana 00009423
    }

    private void initializeTableColumns() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colMoneyAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
        colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        colCardId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCardId()).asObject());
        colTransactionDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPurchaseDate()));
    }

    private void loadTransactionsFromDatabase() {
        ObservableList<Transaction>transactions = FXCollections.observableArrayList();
        String query = "SELECT id_transaction, money_amount, description, id_card, transaction_date FROM transaction";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id_transaction");
                double moneyAmount = rs.getDouble("money_amount");
                String description = rs.getString("description");
                int cardId = rs.getInt("id_card");
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();

                transactions.add(new Transaction(id, moneyAmount, description, cardId, transactionDate));
            }

            tableView.setItems(transactions);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }





    @FXML
    private void InsertTransaction() { // Método para insertar transacción  00009423
        if (txtMoneyAmount.getText().isEmpty() || txtDesc.getText().isEmpty() || txtIdCard.getText().isEmpty() || dpTransaction.getValue()==null) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error 00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("INSERT INTO transaction (money_amount, description, id_card, transaction_date) VALUES (?, ?, ?, ?)"); // Prepara la consulta SQL  00009423
            st.setDouble(1, Double.parseDouble(txtMoneyAmount.getText())); // Establece la cantidad de dinero 00009423
            st.setString(2, txtDesc.getText()); // Establece la descripción 00009423
            st.setInt(3, Integer.parseInt(txtIdCard.getText())); // Establece el ID de la tarjeta 00009423
            st.setDate(4, Date.valueOf(dpTransaction.getValue())); // Establece la fecha de la transacción por el datePicker 00005923
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00009423
                loadTransactionsFromDatabase();
                showSuccesAlert("Added client successfully"); // Muestra alerta de éxito 00009423
                clearFields(); // Limpia los campos de texto 00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00009423
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); // Muestra alerta de error general 00009423
        }
    }



    @FXML
    private void UpdateTransaction() { // Método para actualizar transacción 00009423
        if (txtMoneyAmount.getText().isEmpty() || txtDesc.getText().isEmpty() || txtIdCard.getText().isEmpty() || dpTransaction.getValue()==null || txtIdTransactionDel.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error 00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("UPDATE transaction SET money_amount = ?, description = ?, id_card = ?, transaction_date = ? WHERE  id_transaction = ?;"); // Prepara la consulta SQL 00009423


            st.setDouble(1, Double.parseDouble(txtMoneyAmount.getText())); //Establece la cantidad de dinero 00009423
            st.setString(2, txtDesc.getText()); // Establece la descripción 00009423
            st.setInt(3, Integer.parseInt(txtIdCard.getText())); //Establece el ID de la transacción 00009423
            st.setDate(4, Date.valueOf(dpTransaction.getValue())); // Establece la fecha de la transacción por el datePicker 00005923
            st.setInt(5, Integer.parseInt(txtIdTransactionDel.getText())); //Establece el ID de la tarjeta 00009423
            try {
                int result = st.executeUpdate(); //00009423 Ejecuta la consulta
                loadTransactionsFromDatabase();
                showSuccesAlert("Updated transaction successfully"); //Muestra alerta de éxito 00009423
                clearFields(); // Limpia los campos de texto 00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); //Muestra alerta de error SQL 00009423
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage()); //Muestra alerta de error general 00009423
        }
    }



    @FXML
    private void DeleteTransaction() { // Método para eliminar transacción
        if (txtIdTransactionDel.getText().isEmpty()) { //00005923 Evalua si es posible escribir en el textfield
            showErrorAlert("Please enter the Transaction ID to delete"); //muestra un mensaje de error
            return;// 00005923no retorna nada
        }
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");// Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("DELETE FROM transaction WHERE id_transaction = ?");//Prepara la consulta 00009423
            st.setInt(1, Integer.parseInt(txtIdTransactionDel.getText())); //Pasa los parametros necesarios a la consulta 00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00005923
                loadTransactionsFromDatabase();//00005923 Carga todos los registros de la base de datos
                showSuccesAlert("Deleted transaction successfully"); // Muestra alerta de éxito 00005923
                clearFields(); // Limpia los campos de texto 00005923
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00005923
            }
        }catch (Exception e){
            showErrorAlert(e.getMessage());//00005923 muestra el mensaje de error
        }

    }

    private void showErrorAlert(String message) { // Método para mostrar alerta de error  00009423
        Alert alert = new Alert(Alert.AlertType.ERROR); // Crea una alerta de tipo error 00009423
        alert.setTitle("Error!"); // Establece el título de la alerta 00009423
        alert.setHeaderText("An error has occurred!"); // Establece el encabezado de la alerta 00009423
        alert.setContentText(message); // Establece el mensaje de contenido de la alerta 00009423
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre 00009423
    }

    private void showSuccesAlert(String message) { // Método para mostrar alerta de éxito 00009423
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Crea una alerta de tipo información 00009423
        alert.setTitle("Success"); // Establece el título de la alerta 00009423
        alert.setHeaderText("Successful Operation!"); // Establece el encabezado de la alerta 00009423
        alert.setContentText(message); // Establece el mensaje de contenido de la alerta 00009423
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre 00009423
    }
    private void showInfoAlert(String message){ //Metodo para mostrar informacion 00009423
        Alert alert=new Alert(Alert.AlertType.INFORMATION); // Crea una alerta de informacion 00009423
        alert.setTitle("Information"); //Establece el titulo de la alerta 00009423
        alert.setHeaderText("Suggestion:"); //Establece el encabezado de la alerta 00009423
        alert.setContentText(message); //Establece el mensaje y el contenido 00009423
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre 00009423
    }
    private void clearFields() { // Método para limpiar campos de texto 00009423
        txtMoneyAmount.clear(); // Limpia el campo de cantidad de dinero 00009423
        txtDesc.clear(); // Limpia el campo de descripción 00009423
        txtIdCard.clear(); // Limpia el campo de ID de tarjeta 00009423
        dpTransaction.setValue(null); // Limpia el campo de fecha de transacción 00009423
        txtIdTransactionDel.clear(); //Lipia el campo de ID para borrar la transaccion 00005923
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException { //00080323 Método para retornar a la ventana principal.
        try {
            root = FXMLLoader.load(getClass().getResource("crud-view.fxml")); //00080323 Cargamos la pantalla inicial
        } catch (NullPointerException e) { //00080323 Manejamos la exepción.
            e.printStackTrace(); // 00080323 Imprimimos exepción.
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //00080323 Método para  obtener la ventana actual.
        scene = new Scene(root); // 00080323 Instanciamos una nueva escena .
        stage.setScene(scene); //00080323 Cambiamos la escena de la ventana.
        stage.show(); //00080323 Mostramos nueva escena en la ventana.
    }
}
