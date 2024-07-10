package com.example.parcial_final; // Define el paquete del archivo 00009423

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent; // Importa clases para manejar eventos 00009423
import javafx.fxml.FXML; // Importa anotaciones FXML  00009423
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable; // Importa la interfaz Initializable  00009423
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL; // Importa la clase URL  00009423
import java.sql.*; // Importa clases para manejo de SQL  00009423
import java.util.ResourceBundle; // Importa la clase ResourceBundle  00009423

public class InsertsController implements Initializable { // Define la clase principal  00009423

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField txtCardNumber; //00009423 Define el campo de texto para el número de tarjeta  
    @FXML
    private TextField txtExpiration; //00009423 Define el campo de texto para la fecha de expiración 
    @FXML
    private TextField txtCardType; //00009423 Define el campo de texto para el tipo de tarjeta  
    @FXML
    private ComboBox<Integer> cmbFacilitator; //00009423 Define el ComboBox para el facilitador de la tarjeta  
    @FXML
    private TextField txtIdClient; //00009423 Define el campo de texto para el ID del cliente  
    @FXML
    private Button btnInsert; //00009423 Define el botón para insertar tarjeta  
    @FXML
    private Button btnDelete; //00009423 Define el botón para eliminar tarjeta  
    @FXML
    private Button btnUpdate; //00009423 Define el botón para actualizar tarjeta  
    @FXML
    private TextField txtFirstName; //00009423 Define el campo de texto para el nombre del cliente  
    @FXML
    private TextField txtLastname; //00009423 Define el campo de texto para el apellido del cliente  
    @FXML
    private TextField txtDirection; //00009423 Define el campo de texto para la dirección del cliente  
    @FXML
    private TextField txtPhone; //00009423 Define el campo de texto para el teléfono del cliente  
    @FXML
    private Button btnInsertC; //00009423 Define el botón para insertar cliente  
    @FXML
    private Button btnDeleteC; //00009423 Define el botón para eliminar cliente  
    @FXML
    private Button btnUpdateC; //00009423 Define el botón para actualizar cliente  
    @FXML
    private TextField txtMoneyAmount; //00009423 Define el campo de texto para la cantidad de dinero de la transacción  
    @FXML
    private TextField txtDesc; //00009423 Define el campo de texto para la descripción de la transacción  
    @FXML
    private TextField txtIdCard; //00009423 Define el campo de texto para el ID de la tarjeta de la transacción  
    @FXML
    private TextField txtTransc; //00009423 Define el campo de texto para una transacción 
    @FXML
    private Button btnInsertT; //00009423 Define el botón para insertar una transacción  
    @FXML
    private Button btnDeleteT; //00009423 Define el botón para eliminar una transacción  
    @FXML
    private Button btnUpdateT; //00009423 Define el botón para actualizar una transacción  
    @FXML
    private TextField txtIdTransaction; //00009423 Define el campo de texto para el ID de la transacción  
    @FXML
    private DatePicker dpTransaction; //00005923 Define datePicker 
    @FXML
    private DatePicker dpCard;  //00005923 Define datePicker for card expiration 
    @FXML
    private TextField txtIdClientDel; //00005923 Define el textField que se ingresara el ID de client para borrarlo 
    @FXML
    private TextField txtIdCardDel; //00005923 Define el textField que se ingresara el ID de Card para borrarlo 
    @FXML
    private TextField txtIdTransactionDel; //00005923 Define el textField que se ingresara el ID de Transaction para borrarlo 
    @FXML
    private TextField txtIdCardUpdate; //00005923 Define el textField que se ingresara el ID para el update en tarjeta 
    @FXML
    private TextField txtIdClientUpdate; //00005923 Define el textField que se ingresara el ID para el update en client 
    @FXML
    private TextField txtTransactionUpdate; //00005923 Define el textField que se ingresara el ID para el update en transaction 


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //00009423 Método de inicialización  
        cmbFacilitator.setItems(getFacilitatorIds()); //00005923 Inicializa los ids que estaran en el comboBox 
        showInfoAlert("In case you want to make an update, you must write the id of \n the process by the other hand, if you want to \n insert, the id is not necessary"); //00009423 Muestra el mensaje al iniciar esta ventana 
    }

    private ObservableList<Integer> getFacilitatorIds() {
        String query = "SELECT id_facilitator FROM facilitator"; //00005923 Es un SELECT que escoge los ids de la tabla Facilitador //00005923 Inicia la conexion con la Base de datos
        ObservableList<Integer> facilitatorIds = FXCollections.observableArrayList(); //00005923 Es una lista que guardara dentro de una ObservableList
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!");
            Statement st = conn.createStatement(); //00005923 Crea un declaracion que sera usada para generar un resultado
            ResultSet rs = st.executeQuery(query);  //00005923 Executa el resultado y guarda los valores que la Query selecciono

            while (rs.next()) { //00005923 Mientras haya un siguiente la ObservalbeList seguira guardando atributos
                facilitatorIds.add(rs.getInt("id_facilitator")); //00005923 Agrega la ObservableList por medio de la columna
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); //00005923 Muestra alerta de error general  
        }
        return facilitatorIds; //00005923 devuelve la ObservableListe para ser usada en el comboBox
    }

    @FXML
    private void InsertCard() { // Método para insertar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 16|| dpCard.getValue()==null || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty()) { //00009423 Verifica campos vacíos  
            showErrorAlert("Make sure you write the fields correctly "); //00009423 Muestra alerta de error  
            return; //00009423 Sale del método 
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!"); //00009423 Conecta a la base de datos  
            PreparedStatement st = conn.prepareStatement("INSERT INTO card (card_number, expiration_date, card_type, id_facilitator, id_client) VALUES (?, ?, ?, ?, ?)"); //00009423 Prepara la consulta SQL  
            st.setString(1, txtCardNumber.getText()); //00009423 Establece el número de tarjeta  
            st.setDate(2, Date.valueOf(dpCard.getValue())); //00005923 Establece la fecha de expiración por medio del date picker 
            st.setString(3, txtCardType.getText()); // Establece el tipo de tarjeta  00009423
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedItem()); //00005923 Establece el ID del facilitador  
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); //00009423 Establece el ID del cliente  
            try {
                int result = st.executeUpdate(); //00009423 Ejecuta la consulta  
                showSuccesAlert("Added card successfully"); //00009423 Muestra alerta de éxito  
                clearFields(); //00009423 Limpia los campos de texto  
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); //00009423 Muestra alerta de error SQL  
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); //00009423 Muestra alerta de error general  
        }
    }

    @FXML
    private void InsertClient() { // Método para insertar cliente  00009423
        if (txtFirstName.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDirection.getText().isEmpty() || txtPhone.getText().length() > 8 || txtPhone.getText().isEmpty()) { //00009423 Verifica campos vacíos  
            showErrorAlert("Make sure you write the fields correctly "); //00009423 Muestra alerta de error  
            return; // Sale del método
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!"); //00009423 Conecta a la base de datos  
            PreparedStatement st = conn.prepareStatement("INSERT INTO client (client_first_name, client_last_name, client_direction, client_phone_number) VALUES (?, ?, ?, ?)"); //00009423 Prepara la consulta SQL  
            st.setString(1, txtFirstName.getText()); //00009423 Establece el nombre del cliente  
            st.setString(2, txtLastname.getText()); //00009423 Establece el apellido del cliente  
            st.setString(3, txtDirection.getText()); //00009423 Establece la dirección del cliente  
            st.setString(4, txtPhone.getText()); //00009423 Establece el teléfono del cliente  
            try {
                int result = st.executeUpdate(); //00009423 Ejecuta la consulta  
                showSuccesAlert("Added client successfully"); //00009423 Muestra alerta de éxito  
                clearFields(); //00009423 Limpia los campos de texto  
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); //00009423 Muestra alerta de error SQL  
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); //00009423 Muestra alerta de error general 
        }
    }

    @FXML
    private void InsertTransaction() { // Método para insertar transacción  00009423
        if (txtMoneyAmount.getText().isEmpty() || txtDesc.getText().isEmpty() || txtIdCard.getText().isEmpty() || dpTransaction.getValue()==null) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error 00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("INSERT INTO transaction (money_amount, description, id_card, transaction_date) VALUES (?, ?, ?, ?)"); // Prepara la consulta SQL  00009423
            st.setDouble(1, Double.parseDouble(txtMoneyAmount.getText())); // Establece la cantidad de dinero 00009423
            st.setString(2, txtDesc.getText()); // Establece la descripción 00009423
            st.setInt(3, Integer.parseInt(txtIdCard.getText())); // Establece el ID de la tarjeta 00009423
            st.setDate(4, Date.valueOf(dpTransaction.getValue())); // Establece la fecha de la transacción por el datePicker 00005923
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00009423
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
    private void UpdateClient() { // Método para actualizar cliente 00009423
        if (txtFirstName.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDirection.getText().isEmpty() || txtPhone.getText().length() > 8 || txtPhone.getText().isEmpty() || txtIdClientUpdate.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("UPDATE client SET client_first_name = ?, client_last_name = ?, client_direction = ?, client_phone_number = ? WHERE id_client = ?;"); // Prepara la consulta SQL 00009423
            st.setString(1, txtFirstName.getText()); // Establece el nombre del cliente 00009423
            st.setString(2, txtLastname.getText()); // Establece el apellido del cliente 00009423
            st.setString(3, txtDirection.getText()); // Establece la dirección del cliente 00009423
            st.setString(4, txtPhone.getText()); // Establece el teléfono del cliente 00009423
            st.setInt(5, Integer.parseInt(txtIdClientUpdate.getText())); // Establece el ID del cliente  00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta  00009423
                showSuccesAlert("Updated client successfully"); // Muestra alerta de éxito  00009423
                clearFields(); // Limpia los campos de texto  00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL  00009423
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage()); // Muestra alerta de error general  00009423
        }
    }

    @FXML
    private void UpdateCard() { // Método para actualizar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 16 || dpCard.getValue()==null || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty() || txtIdCardUpdate.getText().isEmpty()) { // Verifica campos vacíos  00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método  00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!"); // Conecta a la base de datos  00009423
            PreparedStatement st = conn.prepareStatement("UPDATE card SET card_number = ?, expiration_date = ?, card_type = ?, id_facilitator = ?,id_client=? WHERE  id_card = ?;"); // Prepara la consulta SQL  00009423


            st.setString(1, txtCardNumber.getText()); // Establece el número de tarjeta 00009423
            st.setDate(2, Date.valueOf(dpCard.getValue())); // Establece la fecha de expiración por el datePicker 00005923
            st.setString(3, txtCardType.getText()); // Establece el tipo de tarjeta 00009423
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedItem()); // Establece el ID del facilitador 00009423
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); // Establece el ID del cliente 00009423
            st.setInt(6, Integer.parseInt(txtIdCardUpdate.getText())); // Establece el ID de la tarjeta 00009423

            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00009423
                showSuccesAlert("Updated card successfully"); // Muestra alerta de éxito 00009423
                clearFields(); // Limpia los campos de texto 00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00009423
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage()); // Muestra alerta de error general 00009423
        }
    }

    @FXML
    private void UpdateTransaction() { // Método para actualizar transacción 00009423
        if (txtMoneyAmount.getText().isEmpty() || txtDesc.getText().isEmpty() || txtIdCard.getText().isEmpty() || dpTransaction.getValue()==null || txtTransactionUpdate.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error 00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("UPDATE transaction SET money_amount = ?, description = ?, id_card = ?, transaction_date = ? WHERE  id_transaction = ?;"); // Prepara la consulta SQL 00009423


            st.setDouble(1, Double.parseDouble(txtMoneyAmount.getText())); // Establece la cantidad de dinero 00009423
            st.setString(2, txtDesc.getText()); // Establece la descripción 00009423
            st.setInt(3, Integer.parseInt(txtIdCard.getText())); // Establece el ID de la transacción 00009423
            st.setDate(4, Date.valueOf(dpTransaction.getValue())); // Establece la fecha de la transacción por el datePicker 00005923
            st.setInt(5, Integer.parseInt(txtTransactionUpdate.getText())); // Establece el ID de la tarjeta 00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta
                showSuccesAlert("Updated transaction successfully"); // Muestra alerta de éxito 00009423
                clearFields(); // Limpia los campos de texto 00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00009423
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage()); // Muestra alerta de error general 00009423
        }
    }

    @FXML
    private void DeleteClient() { // Método para eliminar cliente
        if (txtIdClientDel.getText().isEmpty()) { //00005923 Evalua si es posible escribir en el textfield
            showErrorAlert("Please enter the Client ID to delete"); //muestra un mensaje de error 00005923
            return;
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!");
            PreparedStatement st = conn.prepareStatement("DELETE FROM client WHERE id_client = ?");
            st.setInt(1, Integer.parseInt(txtIdClientDel.getText()));
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00005923
                showSuccesAlert("Deleted Client successfully"); // Muestra alerta de éxito 00005923
                clearFields(); // Limpia los campos de texto 00005923
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00005923
            }
        }catch (Exception e){
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void DeleteCard() { // Método para eliminar tarjeta
        if (txtIdCardDel.getText().isEmpty()) { //00005923 Evalua si es posible escribir en el textfield
            showErrorAlert("Please enter the Card ID to delete"); //muestra un mensaje de error 00005923
            return;
        }
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!");
            PreparedStatement st = conn.prepareStatement("DELETE FROM card WHERE id_card = ?");
            st.setInt(1, Integer.parseInt(txtIdCardDel.getText()));
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00005923
                showSuccesAlert("Deleted Card successfully"); // Muestra alerta de éxito 00005923
                clearFields(); // Limpia los campos de texto 00005923
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00005923
            }
        }catch (Exception e){//00080323 Maneja la exepcion.
            showErrorAlert(e.getMessage());
        }

    }

    @FXML
    private void DeleteTransaction() { // Método para eliminar transacción
        if (txtIdTransactionDel.getText().isEmpty()) { //00005923 Evalua si es posible escribir en el textfield
            showErrorAlert("Please enter the Transaction ID to delete"); //muestra un mensaje de error
            return;
        }
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!");
            PreparedStatement st = conn.prepareStatement("DELETE FROM transaction WHERE id_transaction = ?");
            st.setInt(1, Integer.parseInt(txtIdTransactionDel.getText()));
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00005923
                showSuccesAlert("Deleted transaction successfully"); // Muestra alerta de éxito 00005923
                clearFields(); // Limpia los campos de texto 00005923
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL 00005923
            }
        }catch (Exception e){
            showErrorAlert(e.getMessage());
        }

    }

    private void showErrorAlert(String message) { // Método para mostrar alerta de error  00009423
        Alert alert = new Alert(Alert.AlertType.ERROR); // Crea una alerta de tipo error 00009423
        alert.setTitle("Error!"); // Establece el título de la alerta 00009423
        alert.setHeaderText("Se ha producido un error!"); // Establece el encabezado de la alerta 00009423
        alert.setContentText(message); // Establece el mensaje de contenido de la alerta 00009423
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre 00009423
    }

    private void showSuccesAlert(String message) { // Método para mostrar alerta de éxito 00009423
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Crea una alerta de tipo información 00009423
        alert.setTitle("Exito"); // Establece el título de la alerta 00009423
        alert.setHeaderText("Se ha realizado con exito"); // Establece el encabezado de la alerta 00009423
        alert.setContentText(message); // Establece el mensaje de contenido de la alerta 00009423
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre 00009423
    }
    private void showInfoAlert(String message){ //Metodo para mostrar informacion 00009423
        Alert alert=new Alert(Alert.AlertType.INFORMATION); // Crea una alerta de informacion 00009423
        alert.setTitle("Information"); //Establece el titulo de la alerta 00009423
        alert.setHeaderText("Advise:"); //Establece el encabezado de la alerta 00009423
        alert.setContentText(message); //Establece el mensaje y el contenido 00009423
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre 00009423
    }

    private void clearFields() { // Método para limpiar campos de texto 00009423
        txtCardNumber.clear(); // Limpia el campo de número de tarjeta 00009423
        dpCard.setValue(null); // Limpia el campo de fecha de expiración 00009423
        txtCardType.clear(); // Limpia el campo de tipo de tarjeta 00009423
        cmbFacilitator.getSelectionModel().clearSelection(); // Limpia la selección del ComboBox 00009423
        txtIdClient.clear(); // Limpia el campo de ID de cliente 00009423
        txtFirstName.clear(); // Limpia el campo de nombre de cliente 00009423
        txtLastname.clear(); // Limpia el campo de apellido de cliente 00009423
        txtDirection.clear(); // Limpia el campo de dirección de cliente 00009423
        txtPhone.clear(); // Limpia el campo de teléfono de cliente 00009423
        txtMoneyAmount.clear(); // Limpia el campo de cantidad de dinero 00009423
        txtDesc.clear(); // Limpia el campo de descripción 00009423
        txtIdCard.clear(); // Limpia el campo de ID de tarjeta 00009423
        dpTransaction.setValue(null); // Limpia el campo de fecha de transacción 00009423
        txtIdClientDel.clear(); //Lipia el campo de ID para borrar la Cliente 00005923
        txtIdCardDel.clear(); //Lipia el campo de ID para borrar la Tarjeta 00005923
        txtIdTransactionDel.clear(); //Lipia el campo de ID para borrar la transaccion 00005923
        txtTransactionUpdate.clear(); //Lipia el campo de ID de la transaccion 00005923
        txtIdCardUpdate.clear(); //Lipia el campo de ID de Tarjeta 00005923
        txtIdClientUpdate.clear(); //Lipia el campo de ID de Cliente 00005923
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException { //00080323 Function to return to the pricipal view 
        try {
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml")); //00080323 sets the root of the principal view 
        } catch (NullPointerException e) { //00080323 Manejamos la exepción.
            e.printStackTrace(); // 00080323 Imprimimos exepción.
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //00080323 Método para  obtener la ventana actual.
        scene = new Scene(root); // 00080323 Instanciamos una nueva escena .
        stage.setScene(scene); //00080323 Cambiamos la escena de la ventana.
        stage.show(); //00080323 Mostramos nueva escena en la ventana.
    }
}
