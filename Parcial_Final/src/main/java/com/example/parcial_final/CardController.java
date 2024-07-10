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

public class CardController implements Initializable {
    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField txtIdClient; //00009423 Define el campo para el ID del cliente

    @FXML
    private TextField txtCardNumber; //00009423Define el campo de texto para el número de tarjeta

    @FXML
    private DatePicker dpCard;  //00009423 Define datePicker para la fecha de expiracion de la tarjeta

    @FXML
    private TextField txtCardType; //00009423 Define el campo de texto para el tipo de tarjeta

    @FXML
    private ComboBox<Integer> cmbFacilitator; //00009423 Define el ComboBox para el facilitador de la tarjeta

    @FXML
    private TextField txtIdCard; //00009423 Define el campo de texto para el ID de la tarjeta

    @FXML
    private TableColumn<Card, Integer> colId;

    @FXML
    private TableColumn<Card, Integer> colClientId;

    @FXML
    private TableColumn<Card, String> colCardNumber;

    @FXML
    private TableColumn<Card, LocalDate> colExpirationDate;

    @FXML
    private TableColumn<Card, String> colCardType;

    @FXML
    private TableColumn<Card, Integer> colIdFacilitator;

    @FXML
    private TableView<Card> tvCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {//00106123 Metodo de inicializacion que se activa al iniciar el programa
        cmbFacilitator.setItems(getFacilitatorIds()); //00005923 Inicializa los ids que estaran en el comboBox
        showInfoAlert("In case you want to make an update or a deletion, you must write the id of \n the registre, if you want to \n insert, the id is not necessary."); //Muestra el mensaje al iniciar esta ventana 00009423
        initializeTableColumns();
        loadCardsFromDatabase();
    }

    private void initializeTableColumns() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCard()).asObject());
        colClientId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdClient()).asObject());
        colCardNumber.setCellValueFactory(cellData -> cellData.getValue().cardNumberProperty());
        colExpirationDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpirationDate()));
        colCardType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardType()));
        colIdFacilitator.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdFacilitator()).asObject());
    }

    private void loadCardsFromDatabase() {
        ObservableList<Card> cards = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_card, card_number, expiration_date, card_type, id_facilitator, id_client FROM card");

            while (rs.next()) {
                int id = rs.getInt("id_card");
                StringProperty cardNumber = new SimpleStringProperty(rs.getString("card_number"));
                LocalDate expirationDate = LocalDate.parse(rs.getString("expiration_date"));
                String cardType = rs.getString("card_type");
                int Idfacilitator = rs.getInt("id_facilitator");
                int IdClient = rs.getInt("id_client");

                cards.add(new Card(id, cardNumber, expirationDate, cardType, Idfacilitator, IdClient));
            }

            tvCard.setItems(cards);

        } catch (SQLException e) {
            showErrorAlert("Error loading cards from database: " + e.getMessage());
        }
    }



    private ObservableList<Integer> getFacilitatorIds() {
        String query = "SELECT id_facilitator FROM facilitator"; //00005923 Es un SELECT que escoge los ids de la tabla Facilitador //00005923 Inicia la conexion con la Base de datos
        ObservableList<Integer> facilitatorIds = FXCollections.observableArrayList(); //00005923 Es una lista que guardara dentro de una ObservableList
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            Statement st = conn.createStatement(); //00005923 Crea un declaracion que sera usada para generar un resultado
            ResultSet rs = st.executeQuery(query);  //00005923 Executa el resultado y guarda los valores que la Query selecciono

            while (rs.next()) { //00005923 Mientras haya un siguiente la ObservalbeList seguira guardando atributos
                facilitatorIds.add(rs.getInt("id_facilitator")); //00005923 Agrega la ObservableList por medio de la columna
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Muestra alerta de error general  00005923
        }
        return facilitatorIds; //00005923 devuelve la ObservableListe para ser usada en el comboBox
    }

    @FXML
    private void InsertCard() { //00009423 Método para insertar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 16|| dpCard.getValue()==null || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty()) { //00009423 Verifica campos vacíos
            showErrorAlert("Make sure you write the fields correctly "); //00009423 Muestra alerta de error
            return; //00009423 Sale del método
        }
        try {//00106123 intenta ejecutar el bloque de codigo
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); //00009423 Conecta a la base de datos
            PreparedStatement st = conn.prepareStatement("INSERT INTO card (card_number, expiration_date, card_type, id_facilitator, id_client) VALUES (?, ?, ?, ?, ?)"); //00009423 Prepara la consulta SQL insertando en card los valores necesarios
            st.setString(1, txtCardNumber.getText()); //00009423 Establece el número de tarjeta
            st.setDate(2, Date.valueOf(dpCard.getValue())); //00009423 Establece la fecha de expiración por medio del date picker
            st.setString(3, txtCardType.getText()); //00009423 Establece el tipo de tarjeta
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedItem()); //00009423 Establece el ID del facilitador
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); //00009423 Establece el ID del cliente
            try {//00106123 intenta ejecutar el bloque de codigo
                int result = st.executeUpdate(); //00009423 Ejecuta la consulta
                showSuccesAlert("Added card successfully"); // 00009423Muestra alerta de éxito
                loadCardsFromDatabase();//00106123 Actualiza la tableview
                clearFields(); //00009423 Limpia los campos de texto
            } catch (SQLException e) {
                showErrorAlert(e.getMessage()); //00009423 Muestra alerta de error SQL
            }
        } catch (Exception E) {
            showErrorAlert(E.getMessage()); //00009423 Muestra alerta de error general
        }
    }

    @FXML
    private void UpdateCard() { //00009423 Método para actualizar tarjeta
        if (txtCardNumber.getText().isEmpty() || txtCardNumber.getText().length() != 16 || dpCard.getValue()==null || txtCardType.getText().isEmpty() || cmbFacilitator.getSelectionModel().getSelectedItem() == null || txtIdClient.getText().isEmpty() || txtIdCard.getText().isEmpty()) { //00009423 Verifica campos vacíos
            showErrorAlert("Make sure you write the fields correctly "); //00009423 Muestra alerta de error
            return; //00009423 Sale del método
        }
        try {//00106123 intenta ejecutar el bloque de codigo
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004"); //00009423 Conecta a la base de datos
            PreparedStatement st = conn.prepareStatement("UPDATE card SET card_number = ?, expiration_date = ?, card_type = ?, id_facilitator = ?,id_client=? WHERE  id_card = ?;"); //00009423 Prepara la consulta SQL haciendo pdate y actualizando los campos deseados


            st.setString(1, txtCardNumber.getText()); // 00009423 Establece el número de tarjeta
            st.setDate(2, Date.valueOf(dpCard.getValue())); //00009423 Establece la fecha de expiración por el datePicker
            st.setString(3, txtCardType.getText()); //00009423 Establece el tipo de tarjeta
            st.setInt(4, cmbFacilitator.getSelectionModel().getSelectedItem()); //00009423 Establece el ID del facilitador
            st.setInt(5, Integer.parseInt(txtIdClient.getText())); //00009423 Establece el ID del cliente
            st.setInt(6, Integer.parseInt(txtIdCard.getText())); //00009423 Establece el ID de la tarjeta

            try {//00106123 intenta ejecutar el bloque de codigo
                int result = st.executeUpdate(); //00009423 Ejecuta la consulta
                showSuccesAlert("Updated card successfully"); //00009423 Muestra alerta de éxito
                loadCardsFromDatabase();//00106123 Actualiza la tableview
                clearFields(); //00009423 Limpia los campos de texto
            } catch (SQLException e) { //00106123 atrapa la excepcion
                showErrorAlert(e.getMessage()); //00009423 Muestra alerta de error SQL
            }
        } catch (Exception e) {//00106123 atrapa la excepcion
            showErrorAlert(e.getMessage()); //00009423 Muestra alerta de error general
        }
    }

    @FXML
    private void DeleteCard() { // Método para eliminar tarjeta
        if (txtIdCard.getText().isEmpty()) { //00005923 Evalua si es posible escribir en el textfield
            showErrorAlert("Please enter the Card ID to delete"); //muestra un mensaje de error 00005923
            return;
        }
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            PreparedStatement st = conn.prepareStatement("DELETE FROM card WHERE id_card = ?");
            st.setInt(1, Integer.parseInt(txtIdCard.getText()));
            try {
                st.executeUpdate(); // Ejecuta la consulta 00005923
                loadCardsFromDatabase();
                showSuccesAlert("Deleted Card successfully"); // Muestra alerta de éxito 00005923
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

    private void clearFields() { //00009423 Método para limpiar campos de texto
        txtCardNumber.clear(); //00009423 Limpia el campo de número de tarjeta
        dpCard.setValue(null); //00009423 Limpia el campo de fecha de expiración
        txtCardType.clear(); //00009423 Limpia el campo de tipo de tarjeta
        cmbFacilitator.getSelectionModel().clearSelection(); //00009423 Limpia la selección del ComboBox
        txtIdClient.clear(); //00009423 Limpia el campo de ID de cliente
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
