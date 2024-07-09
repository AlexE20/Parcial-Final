package com.example.parcial_final; // Define el paquete del archivo 00009423

import javafx.event.ActionEvent; // Importa clases para manejar eventos 00009423
import javafx.fxml.FXML; // Importa anotaciones FXML  00009423
import javafx.fxml.Initializable; // Importa la interfaz Initializable  00009423
import javafx.scene.control.Alert; // Importa la clase Alert para diálogos  00009423
import javafx.scene.control.Button; // Importa la clase Button  00009423
import javafx.scene.control.ComboBox; // Importa la clase ComboBox  00009423
import javafx.scene.control.TextField; // Importa la clase TextField  00009423

import java.net.URL; // Importa la clase URL  00009423
import java.sql.*; // Importa clases para manejo de SQL  00009423
import java.util.ResourceBundle; // Importa la clase ResourceBundle  00009423

public class InsertsController implements Initializable { // Define la clase principal  00009423
    @FXML
    private TextField txtCardNumber; // Define el campo de texto para el número de tarjeta  00009423
    @FXML
    private TextField txtExpiration; // Define el campo de texto para la fecha de expiración 00009423
    @FXML
    private TextField txtCardType; // Define el campo de texto para el tipo de tarjeta  00009423
    @FXML
    private ComboBox cmbFacilitator; // Define el ComboBox para el facilitador de la tarjeta  00009423
    @FXML
    private TextField txtIdClient; // Define el campo de texto para el ID del cliente  00009423
    @FXML
    private Button btnInsert; // Define el botón para insertar tarjeta  00009423
    @FXML
    private Button btnDelete; // Define el botón para eliminar tarjeta  00009423
    @FXML
    private Button btnUpdate; // Define el botón para actualizar tarjeta  00009423
    @FXML
    private TextField txtFirstName; // Define el campo de texto para el nombre del cliente  00009423
    @FXML
    private TextField txtLastname; // Define el campo de texto para el apellido del cliente  00009423
    @FXML
    private TextField txtDirection; // Define el campo de texto para la dirección del cliente  00009423
    @FXML
    private TextField txtPhone; // Define el campo de texto para el teléfono del cliente  00009423
    @FXML
    private Button btnInsertC; // Define el botón para insertar cliente  00009423
    @FXML
    private Button btnDeleteC; // Define el botón para eliminar cliente  00009423
    @FXML
    private Button btnUpdateC; // Define el botón para actualizar cliente  00009423
    @FXML
    private TextField txtMoneyAmount; // Define el campo de texto para la cantidad de dinero de la transacción  00009423
    @FXML
    private TextField txtDesc; // Define el campo de texto para la descripción de la transacción  00009423
    @FXML
    private TextField txtIdCard; // Define el campo de texto para el ID de la tarjeta de la transacción  00009423
    @FXML
    private TextField txtTransc; // Define el campo de texto para una transacción 00009423
    @FXML
    private Button btnInsertT; // Define el botón para insertar una transacción  00009423
    @FXML
    private Button btnDeleteT; // Define el botón para eliminar una transacción  00009423
    @FXML
    private Button btnUpdateT; // Define el botón para actualizar una transacción  00009423
    @FXML
    private TextField txtIdTransaction; // Define el campo de texto para el ID de la transacción  00009423

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // Método de inicialización  00009423

    }

    @FXML
    private void InsertCard(ActionEvent event) { // Método para insertar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 12 || txtExpiration.getText().isEmpty() || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty()) { // Verifica campos vacíos  00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "Egualos123"); // Conecta a la base de datos  00009423
            PreparedStatement st = conn.prepareStatement("INSERT INTO card (card_number, expiration_date, card_type, id_facilitator, id_client) VALUES (?, ?, ?, ?, ?)"); // Prepara la consulta SQL  00009423
            st.setString(1, txtCardNumber.getText()); // Establece el número de tarjeta  00009423
            st.setDate(2, Date.valueOf(txtExpiration.getText())); // Establece la fecha de expiración  00009423
            st.setString(3, txtCardType.getText()); // Establece el tipo de tarjeta  00009423
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedIndex() + 1); // Establece el ID del facilitador  00009423
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); // Establece el ID del cliente  00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta  00009423
                showSuccesAlert("Added card successfully"); // Muestra alerta de éxito  00009423
                clearFields(); // Limpia los campos de texto  00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL  00009423
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); // Muestra alerta de error general  00009423
        }
    }

    @FXML
    private void InsertClient(ActionEvent event) { // Método para insertar cliente  00009423
        if (txtFirstName.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDirection.getText().isEmpty() || txtPhone.getText().length() > 8 || txtPhone.getText().isEmpty()) { // Verifica campos vacíos  00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "Egualos123"); // Conecta a la base de datos  00009423
            PreparedStatement st = conn.prepareStatement("INSERT INTO client (client_first_name, client_last_name, client_direction, client_phone_number) VALUES (?, ?, ?, ?)"); // Prepara la consulta SQL  00009423
            st.setString(1, txtFirstName.getText()); // Establece el nombre del cliente  00009423
            st.setString(2, txtLastname.getText()); // Establece el apellido del cliente  00009423
            st.setString(3, txtDirection.getText()); // Establece la dirección del cliente  00009423
            st.setString(4, txtPhone.getText()); // Establece el teléfono del cliente  00009423
            try {
                int result = st.executeUpdate(); // Ejecuta la consulta  00009423
                showSuccesAlert("Added client successfully"); // Muestra alerta de éxito  00009423
                clearFields(); // Limpia los campos de texto  00009423
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); // Muestra alerta de error SQL  00009423
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); // Muestra alerta de error general 00009423
        }
    }

    @FXML
    private void InsertTransaction(ActionEvent event) { // Método para insertar transacción  00009423
        if (txtMoneyAmount.getText().isEmpty() || txtDesc.getText().isEmpty() || txtIdCard.getText().isEmpty() || txtTransc.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error 00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "Egualos123"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("INSERT INTO transaction (money_amount, description, id_card, transaction_date) VALUES (?, ?, ?, ?)"); // Prepara la consulta SQL  00009423
            st.setDouble(1, Double.parseDouble(txtMoneyAmount.getText())); // Establece la cantidad de dinero 00009423
            st.setString(2, txtDesc.getText()); // Establece la descripción 00009423
            st.setInt(3, Integer.parseInt(txtIdCard.getText())); // Establece el ID de la tarjeta 00009423
            st.setDate(4, Date.valueOf(txtTransc.getText())); // Establece la fecha de la transacción 00009423
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
    private void UpdateClient(ActionEvent event) { // Método para actualizar cliente 00009423
        if (txtFirstName.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDirection.getText().isEmpty() || txtPhone.getText().length() > 8 || txtPhone.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "Egualos123"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("UPDATE client SET client_first_name = ?, client_last_name = ?, client_direction = ?, client_phone_number = ? WHERE id_client = ?;"); // Prepara la consulta SQL 00009423
            st.setString(1, txtFirstName.getText()); // Establece el nombre del cliente 00009423
            st.setString(2, txtLastname.getText()); // Establece el apellido del cliente 00009423
            st.setString(3, txtDirection.getText()); // Establece la dirección del cliente 00009423
            st.setString(4, txtPhone.getText()); // Establece el teléfono del cliente 00009423
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); // Establece el ID del cliente  00009423
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
    private void UpdateCard(ActionEvent event) { // Método para actualizar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 12 || txtExpiration.getText().isEmpty() || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty()) { // Verifica campos vacíos  00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error  00009423
            return; // Sale del método  00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "Egualos123"); // Conecta a la base de datos  00009423
            PreparedStatement st = conn.prepareStatement("UPDATE card SET card_number = ?, expiration_date = ?, card_type = ?, id_facilitator = ?,id_client=? WHERE  id_card = ?;"); // Prepara la consulta SQL  00009423

            st.setInt(1, Integer.parseInt(txtIdCard.getText())); // Establece el ID de la tarjeta 00009423
            st.setString(2, txtCardNumber.getText()); // Establece el número de tarjeta 00009423
            st.setDate(3, Date.valueOf(txtExpiration.getText())); // Establece la fecha de expiración 00009423
            st.setString(4, txtCardType.getText()); // Establece el tipo de tarjeta 00009423
            st.setInt(5, cmbFacilitator.getSelectionModel().getSelectedIndex() + 1); // Establece el ID del facilitador 00009423
            st.setInt(6, Integer.parseInt(txtIdClient.getText())); // Establece el ID del cliente 00009423
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
    private void UpdateTransaction(ActionEvent event) { // Método para actualizar transacción 00009423
        if (txtMoneyAmount.getText().isEmpty() || txtDesc.getText().isEmpty() || txtIdCard.getText().isEmpty() || txtTransc.getText().isEmpty()) { // Verifica campos vacíos 00009423
            showErrorAlert("Make sure you write the fields correctly "); // Muestra alerta de error 00009423
            return; // Sale del método 00009423
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "Egualos123"); // Conecta a la base de datos 00009423
            PreparedStatement st = conn.prepareStatement("UPDATE transaction SET money_amount = ?, description = ?, id_card = ?, transaction_date = ? WHERE  id_transaction = ?;"); // Prepara la consulta SQL 00009423

            st.setInt(1, Integer.parseInt(txtIdTransaction.getText())); // Establece el ID de la transacción 00009423
            st.setDouble(2, Double.parseDouble(txtMoneyAmount.getText())); // Establece la cantidad de dinero 00009423
            st.setString(3, txtDesc.getText()); // Establece la descripción 00009423
            st.setInt(4, Integer.parseInt(txtIdCard.getText())); // Establece el ID de la tarjeta 00009423
            st.setDate(5, Date.valueOf(txtTransc.getText())); // Establece la fecha de la transacción 00009423
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
    private void DeleteClient(ActionEvent event) { // Método para eliminar cliente
        // Aquí va el código para eliminar el cliente
        clearFields(); // Limpia los campos después de eliminar
    }

    @FXML
    private void DeleteCard(ActionEvent event) { // Método para eliminar tarjeta
        // Aquí va el código para eliminar la tarjeta
        clearFields(); // Limpia los campos después de eliminar
    }

    @FXML
    private void DeleteTransaction(ActionEvent event) { // Método para eliminar transacción
        // Aquí va el código para eliminar la transacción
        clearFields(); // Limpia los campos después de eliminar
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

    private void clearFields() { // Método para limpiar campos de texto 00009423
        txtCardNumber.clear(); // Limpia el campo de número de tarjeta 00009423
        txtExpiration.clear(); // Limpia el campo de fecha de expiración 00009423
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
        txtTransc.clear(); // Limpia el campo de fecha de transacción 00009423
        txtIdTransaction.clear(); // Limpia el campo de ID de transacción 00009423
    }
}
