package com.example.parcial_final;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CardController implements Initializable {//00080323 Clase para aparatado de tarjetas.
    private Parent root; // 00080323 Ventana padre
    private Stage stage; // 00080323 Escenario
    private Scene scene; // 00080323 Escena

    @FXML
    private TextField txtIdClient; // 00009423 Campo de texto para el ID del cliente

    @FXML
    private TextField txtCardNumber; // 00009423 Campo de texto para el número de tarjeta

    @FXML
    private DatePicker dpCard;  // 00009423 Selector de fecha para la fecha de expiración de la tarjeta

    @FXML
    private TextField txtCardType; // 00009423 Campo de texto para el tipo de tarjeta

    @FXML
    private ComboBox<Integer> cmbFacilitator; // 00009423 ComboBox para el facilitador de la tarjeta

    @FXML
    private TextField txtIdCard; // 00009423 Campo de texto para el ID de la tarjeta

    @FXML
    private TableColumn<Card, Integer> colId; // 00009423 Columna de tabla para el ID de la tarjeta

    @FXML
    private TableColumn<Card, Integer> colClientId; // 00009423 Columna de tabla para el ID del cliente

    @FXML
    private TableColumn<Card, String> colCardNumber; // 00009423 Columna de tabla para el número de la tarjeta

    @FXML
    private TableColumn<Card, LocalDate> colExpirationDate; // 00009423 Columna de tabla para la fecha de expiración

    @FXML
    private TableColumn<Card, String> colCardType; // 00009423 Columna de tabla para el tipo de tarjeta

    @FXML
    private TableColumn<Card, Integer> colIdFacilitator; // 00009423 Columna de tabla para el ID del facilitador

    @FXML
    private TableView<Card> tvCard; // 00009423 Tabla para mostrar tarjetas

    @Override
    public void initialize(URL location, ResourceBundle resources) { // 00106123 Método de inicialización al iniciar el programa
        cmbFacilitator.setItems(getFacilitatorIds()); // 00005923 Inicializa los IDs en el ComboBox
        showInfoAlert("In case you want to make an update or a deletion, you must write the id of \n the registre, if you want to \n insert, the id is not necessary."); // 00009423 Muestra mensaje de información al iniciar la ventana
        initializeTableColumns(); // 00009423 Inicializa las columnas de la tabla
        loadCardsFromDatabase(); // 00009423 Carga las tarjetas desde la base de datos
    }

    private void initializeTableColumns() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCard()).asObject()); // 00009423 Inicializa la columna ID de la tarjeta
        colClientId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdClient()).asObject()); // 00009423 Inicializa la columna ID del cliente
        colCardNumber.setCellValueFactory(cellData -> cellData.getValue().cardNumberProperty()); // 00009423 Inicializa la columna número de tarjeta
        colExpirationDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpirationDate())); // 00009423 Inicializa la columna fecha de expiración
        colCardType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardType())); // 00009423 Inicializa la columna tipo de tarjeta
        colIdFacilitator.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdFacilitator()).asObject()); // 00009423 Inicializa la columna ID del facilitador
    }

    private void loadCardsFromDatabase() {
        ObservableList<Card> cards = FXCollections.observableArrayList(); // 00009423 Crea una lista observable de tarjetas

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // 00009423 Conecta a la base de datos
            Statement stmt = conn.createStatement(); // 00009423 Crea una declaración SQL
            ResultSet rs = stmt.executeQuery("SELECT id_card, card_number, expiration_date, card_type, id_facilitator, id_client FROM card"); // 00009423 Ejecuta consulta SQL para obtener tarjetas

            while (rs.next()) { // 00009423 Recorre los resultados de la consulta
                int id = rs.getInt("id_card"); // 00009423 Obtiene el ID de la tarjeta
                StringProperty cardNumber = new SimpleStringProperty(rs.getString("card_number")); // 00009423 Obtiene el número de la tarjeta
                LocalDate expirationDate = LocalDate.parse(rs.getString("expiration_date")); // 00009423 Obtiene la fecha de expiración
                String cardType = rs.getString("card_type"); // 00009423 Obtiene el tipo de tarjeta
                int Idfacilitator = rs.getInt("id_facilitator"); // 00009423 Obtiene el ID del facilitador
                int IdClient = rs.getInt("id_client"); // 00009423 Obtiene el ID del cliente

                cards.add(new Card(id, cardNumber, expirationDate, cardType, Idfacilitator, IdClient)); // 00009423 Añade la tarjeta a la lista
            }

            tvCard.setItems(cards); // 00009423 Establece los ítems de la tabla

        } catch (SQLException e) { // 00009423 Maneja excepciones SQL
            showErrorAlert("Error loading cards from database: " + e.getMessage()); // 00009423 Muestra alerta de error
        }
    }

    private ObservableList<Integer> getFacilitatorIds() {
        String query = "SELECT id_facilitator FROM facilitator"; // 00005923 Consulta SQL para obtener IDs de facilitadores
        ObservableList<Integer> facilitatorIds = FXCollections.observableArrayList(); // 00005923 Crea una lista observable para los IDs

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // 00009423 Conecta a la base de datos
            Statement st = conn.createStatement(); // 00005923 Crea una declaración SQL
            ResultSet rs = st.executeQuery(query); // 00005923 Ejecuta la consulta SQL

            while (rs.next()) { // 00005923 Recorre los resultados de la consulta
                facilitatorIds.add(rs.getInt("id_facilitator")); // 00005923 Añade el ID a la lista observable
            }
        } catch (Exception e) { // 00005923 Maneja excepciones generales
            System.out.println(e.getMessage()); // 00005923 Muestra el mensaje de error
        }
        return facilitatorIds; // 00005923 Retorna la lista observable para usar en el ComboBox
    }

    @FXML
    private void InsertCard() { // 00009423 Método para insertar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 16 || dpCard.getValue() == null || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty()) { // 00009423 Verifica campos vacíos
            showErrorAlert("Make sure you write the fields correctly "); // 00009423 Muestra alerta de error
            return; // 00009423 Sale del método
        }
        try { // 00106123 Intenta ejecutar el bloque de código
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // 00009423 Conecta a la base de datos
            PreparedStatement st = conn.prepareStatement("INSERT INTO card (card_number, expiration_date, card_type, id_facilitator, id_client) VALUES (?, ?, ?, ?, ?)"); // 00009423 Prepara la consulta SQL para insertar tarjeta
            st.setString(1, txtCardNumber.getText()); // 00009423 Establece el número de tarjeta
            st.setDate(2, Date.valueOf(dpCard.getValue())); // 00009423 Establece la fecha de expiración
            st.setString(3, txtCardType.getText()); // 00009423 Establece el tipo de tarjeta
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedItem()); // 00009423 Establece el ID del facilitador
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); // 00009423 Establece el ID del cliente
            try { // 00106123 Intenta ejecutar el bloque de código
                int result = st.executeUpdate(); // 00009423 Ejecuta la consulta
                showSuccesAlert("Added card successfully"); // 00009423 Muestra alerta de éxito
                loadCardsFromDatabase(); // 00106123 Actualiza la tabla de tarjetas
                clearFields(); // 00009423 Limpia los campos de texto
            } catch (SQLException e) { // 00106123 Maneja excepciones SQL
                showErrorAlert(e.getMessage()); // 00009423 Muestra alerta de error SQL
            }
        } catch (Exception E) { // 00106123 Maneja excepciones generales
            showErrorAlert(E.getMessage()); // 00009423 Muestra alerta de error general
        }
    }

    @FXML
    private void UpdateCard() { // 00009423 Método para actualizar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 16 || dpCard.getValue() == null || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty() || txtIdCard.getText().isEmpty()) { // 00009423 Verifica campos vacíos
            showErrorAlert("Make sure you write the fields correctly "); // 00009423 Muestra alerta de error
            return; // 00009423 Sale del método
        }
        try { // 00106123 Intenta ejecutar el bloque de código
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // 00009423 Conecta a la base de datos
            PreparedStatement st = conn.prepareStatement("UPDATE card SET card_number = ?, expiration_date = ?, card_type = ?, id_facilitator = ?, id_client = ? WHERE id_card = ?"); // 00009423 Prepara la consulta SQL para actualizar tarjeta

            st.setString(1, txtCardNumber.getText()); // 00009423 Establece el número de tarjeta
            st.setDate(2, Date.valueOf(dpCard.getValue())); // 00009423 Establece la fecha de expiración
            st.setString(3, txtCardType.getText()); // 00009423 Establece el tipo de tarjeta
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedItem()); // 00009423 Establece el ID del facilitador
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); // 00009423 Establece el ID del cliente
            st.setInt(6, Integer.parseInt(txtIdCard.getText())); // 00009423 Establece el ID de la tarjeta

            try { // 00106123 Intenta ejecutar el bloque de código
                int result = st.executeUpdate(); // 00009423 Ejecuta la consulta
                showSuccesAlert("Updated card successfully"); // 00009423 Muestra alerta de éxito
                loadCardsFromDatabase(); // 00106123 Actualiza la tabla de tarjetas
                clearFields(); // 00009423 Limpia los campos de texto
            } catch (SQLException e) { // 00106123 Maneja excepciones SQL
                showErrorAlert(e.getMessage()); // 00009423 Muestra alerta de error SQL
            }
        } catch (Exception e) { // 00106123 Maneja excepciones generales
            showErrorAlert(e.getMessage()); // 00009423 Muestra alerta de error general
        }
    }

    @FXML
    private void DeleteCard() { // 00009423 Método para eliminar tarjeta
        if (txtIdCard.getText().isEmpty()) { // 00005923 Verifica si el campo ID de la tarjeta está vacío
            showErrorAlert("Please enter the Card ID to delete"); // 00005923 Muestra alerta de error
            return; // 00005923 Sale del método
        }
        try { // 00005923 Intenta ejecutar el bloque de código
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); // 00005923 Conecta a la base de datos
            PreparedStatement st = conn.prepareStatement("DELETE FROM card WHERE id_card = ?"); // 00005923 Prepara la consulta SQL para eliminar tarjeta
            st.setInt(1, Integer.parseInt(txtIdCard.getText())); // 00005923 Establece el ID de la tarjeta

            try { // 00005923 Intenta ejecutar el bloque de código
                st.executeUpdate(); // 00005923 Ejecuta la consulta
                loadCardsFromDatabase(); // 00005923 Actualiza la tabla de tarjetas
                showSuccesAlert("Deleted Card successfully"); // 00005923 Muestra alerta de éxito
                clearFields(); // 00005923 Limpia los campos de texto
            } catch (SQLException e) { // 00005923 Maneja excepciones SQL
                showErrorAlert(e.getMessage()); // 00005923 Muestra alerta de error SQL
            }
        } catch (Exception e) { // 00005923 Maneja excepciones generales
            showErrorAlert(e.getMessage()); // 00005923 Muestra alerta de error general
        }
    }

    private void showErrorAlert(String message) { // 00009423 Método para mostrar alerta de error
        Alert alert = new Alert(Alert.AlertType.ERROR); // 00009423 Crea una alerta de tipo error
        alert.setTitle("Error!"); // 00009423 Establece el título de la alerta
        alert.setHeaderText("An error has occurred!"); // 00009423 Establece el encabezado de la alerta
        alert.setContentText(message); // 00009423 Establece el mensaje de contenido de la alerta
        alert.showAndWait(); // 00009423 Muestra la alerta y espera a que el usuario la cierre
    }

    private void showSuccesAlert(String message) { // 00009423 Método para mostrar alerta de éxito
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00009423 Crea una alerta de tipo información
        alert.setTitle("Success"); // 00009423 Establece el título de la alerta
        alert.setHeaderText("Successful Operation!"); // 00009423 Establece el encabezado de la alerta
        alert.setContentText(message); // 00009423 Establece el mensaje de contenido de la alerta
        alert.showAndWait(); // 00009423 Muestra la alerta y espera a que el usuario la cierre
    }

    private void showInfoAlert(String message) { // 00009423 Método para mostrar información
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00009423 Crea una alerta de tipo información
        alert.setTitle("Information"); // 00009423 Establece el título de la alerta
        alert.setHeaderText("Suggestion:"); // 00009423 Establece el encabezado de la alerta
        alert.setContentText(message); // 00009423 Establece el mensaje de contenido de la alerta
        alert.showAndWait(); // 00009423 Muestra la alerta y espera a que el usuario la cierre
    }

    private void clearFields() { // 00009423 Método para limpiar campos de texto
        txtCardNumber.clear(); // 00009423 Limpia el campo de número de tarjeta
        dpCard.setValue(null); // 00009423 Limpia el campo de fecha de expiración
        txtCardType.clear(); // 00009423 Limpia el campo de tipo de tarjeta
        cmbFacilitator.getSelectionModel().clearSelection(); // 00009423 Limpia la selección del ComboBox
        txtIdClient.clear(); // 00009423 Limpia el campo de ID del cliente
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException { // 00009423 Método para manejar el clic del botón de retorno
        try { // 00009423 Intenta cargar la vista "crud-view.fxml"
            root = FXMLLoader.load(getClass().getResource("crud-view.fxml")); // 00009423 Carga el archivo FXML
        } catch (NullPointerException e) { // 00009423 Maneja excepción de puntero nulo
            e.printStackTrace(); // 00009423 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00009423 Obtiene el escenario actual
        scene = new Scene(root); // 00009423 Crea una nueva escena con la ventana cargada
        stage.setScene(scene); // 00009423 Establece la nueva escena en el escenario
        stage.show(); // 00009423 Muestra el escenario
    }
}
