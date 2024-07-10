package com.example.parcial_final; // Define el paquete del archivo 00009423

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class ClientController implements Initializable { // Define la clase principal  00009423

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;


    @FXML
    private TextField txtIdClient; // Define el campo de texto para el ID del cliente  00009423

    @FXML
    private TextField txtFirstName; // Define el campo de texto para el nombre del cliente  00009423
    @FXML
    private TextField txtLastname; // Define el campo de texto para el apellido del cliente  00009423
    @FXML
    private TextField txtAddress; // Define el campo de texto para la dirección del cliente  00009423
    @FXML
    private TextField txtPhone; // Define el campo de texto para el teléfono del cliente  00009423
    @FXML
    private Button btnInsertC; // Define el botón para insertar cliente  00009423
    @FXML
    private Button btnDeleteC; // Define el botón para eliminar cliente  00009423
    @FXML
    private Button btnUpdateC; // Define el botón para actualizar cliente  00009423

    @FXML
    private Button btnReturn;

    @FXML
    private TableView<Client> tvClients; // Define la tabla para mostrar clientes
    @FXML
    private TableColumn<Client, Integer> colId;
    @FXML
    private TableColumn<Client, String> colFirstName;
    @FXML
    private TableColumn<Client, String> colLastName;
    @FXML
    private TableColumn<Client, String> colAddress;
    @FXML
    private TableColumn<Client, String> colPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // Método de inicialización  00009423
        initializeTableColumns();
        loadClientsFromDatabase();
        showInfoAlert("In case you want to make an update or a deletion, you must write the id of \n the registre, if you want to \n insert, the id is not necessary."); //Muestra el mensaje al iniciar esta ventana 00009423
    }


    private void initializeTableColumns() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        colLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        colPhone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
    }

    private void loadClientsFromDatabase() {
        ObservableList<Client> clients = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_client, client_first_name, client_last_name, client_direction, client_phone_number FROM client");

            while (rs.next()) {
                int id = rs.getInt("id_client");
                String firstName = rs.getString("client_first_name");
                String lastName = rs.getString("client_last_name");
                String direction = rs.getString("client_direction");
                String phone = rs.getString("client_phone_number");

                clients.add(new Client(id, firstName, lastName, direction, phone));
            }

            tvClients.setItems(clients);

        } catch (SQLException e) {
            showErrorAlert("Error loading clients from database: " + e.getMessage());
        }
    }

    @FXML
    private void InsertClient() { // Método para insertar cliente  00009423
        if (txtFirstName.getText().isEmpty() || txtLastname.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhone.getText().length() > 8 || txtPhone.getText().isEmpty()) { // Verifica campos vacíos  00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // Conecta a la base de datos  00009423
            PreparedStatement st = conn.prepareStatement("INSERT INTO client (client_first_name, client_last_name, client_direction, client_phone_number) VALUES (?, ?, ?, ?)"); // Prepara la consulta SQL  00009423
            st.setString(1, txtFirstName.getText()); // Establece el nombre del cliente  00009423
            st.setString(2, txtLastname.getText()); // Establece el apellido del cliente  00009423
            st.setString(3, txtAddress.getText()); // Establece la dirección del cliente  00009423
            st.setString(4, txtPhone.getText()); // Establece el teléfono del cliente  00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta  00009423
                showSuccesAlert("Added client successfully"); // Muestra alerta de éxito  00009423
                loadClientsFromDatabase();
                clearFields(); // Limpia los campos de texto  00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL  00009423
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); // Muestra alerta de error general 00009423
        }
    }


    @FXML
    private void UpdateClient() { // Método para actualizar cliente 00009423
        if (txtFirstName.getText().isEmpty() || txtLastname.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhone.getText().length() > 8 || txtPhone.getText().isEmpty() || txtIdClient.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("UPDATE client SET client_first_name = ?, client_last_name = ?, client_direction = ?, client_phone_number = ? WHERE id_client = ?;"); // Prepara la consulta SQL 00009423
            st.setString(1, txtFirstName.getText()); // Establece el nombre del cliente 00009423
            st.setString(2, txtLastname.getText()); // Establece el apellido del cliente 00009423
            st.setString(3, txtAddress.getText()); // Establece la dirección del cliente 00009423
            st.setString(4, txtPhone.getText()); // Establece el teléfono del cliente 00009423
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); // Establece el ID del cliente  00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta  00009423
                showSuccesAlert("Updated client successfully"); // Muestra alerta de éxito  00009423
                loadClientsFromDatabase();
                clearFields(); // Limpia los campos de texto  00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL  00009423
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage()); // Muestra alerta de error general  00009423
        }
    }


    @FXML
    private void DeleteClient() { // Método para eliminar cliente
        if (txtIdClient.getText().isEmpty()) { //00005923 Evalua si es posible escribir en el textfield
            showErrorAlert("Please enter the Client ID to delete"); //muestra un mensaje de error 00005923
            return;
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            PreparedStatement st = conn.prepareStatement("DELETE FROM client WHERE id_client = ?");
            st.setInt(1, Integer.parseInt(txtIdClient.getText()));
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta 00005923
                showSuccesAlert("Deleted Client successfully"); // Muestra alerta de éxito 00005923
                loadClientsFromDatabase();
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

        txtIdClient.clear(); // Limpia el campo de ID de cliente 00009423
        txtFirstName.clear(); // Limpia el campo de nombre de cliente 00009423
        txtLastname.clear(); // Limpia el campo de apellido de cliente 00009423
        txtAddress.clear(); // Limpia el campo de dirección de cliente 00009423
        txtPhone.clear(); // Limpia el campo de teléfono de cliente 00009423
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("crud-view.fxml"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
