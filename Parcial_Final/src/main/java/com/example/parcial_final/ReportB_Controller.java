package com.example.parcial_final;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class ReportB_Controller implements Initializable {
    //Texto para ingresar el id del cliente
    @FXML
    private TextField txtIdClient;
    @FXML
    //Combo box para seleccionar el mes
    private ComboBox<String> cmbMonth;
    // Texto para ingresar el año
    @FXML
    private TextField txtYear;
    //Tabla donde se mostraran los datos

    @FXML
    private TableView<ReportBDto> tvReport;
    // Columna donde se mostrara el nombre del cliente
    @FXML
    private TableColumn<ReportBDto, String> cClientName;
    //Columna donde se mostrara el total de dinero que gasto
    @FXML
    private TableColumn<ReportBDto, Double> cAmountOfMoney;
    //Boton dentro de la interfaz para buscar al cliente
    @FXML
    private Button btnBuscar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbMonth.getItems().addAll("01-January", "02-February", "03-March", "04-April", "05-May", "06-June", "07-July", "08-August", "09-September", "10-October", "11-November", "12-December"); //Asignacion de los meses que apareceran en el combo box
        cClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));// Configura la columna cClientName para que utilice la propiedad clientName de los objetos Report que se mostrarán en la TableView para que cada celda muestre el valor.
        cAmountOfMoney.setCellValueFactory(new PropertyValueFactory<>("amountOfMoney"));// Configura la columna cAmountOfMoney para que utilice la propiedad 'amountOfMoney' de los objetos Report que se mostrarán en la TableView para que cada celda muestre el valor
    }

    //Funcion que se realiza al apretar el boton buscar dentro de la interfaz
    @FXML
    private void reportB(ActionEvent actionEvent) {
        if (txtIdClient.getText().isEmpty() || txtYear.getText().isEmpty() || cmbMonth.getSelectionModel().getSelectedItem() == null) { //Comprueba si algun campo de la interfaz esta vacio o sin seleccionar
            showErrorAlert("Campo obligatorio no seleccionado"); //Muestra un mensaje de alerta si entra en el if
            return; //No se realiza nada
        }
        try {//Intenta la conexion con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");//Realizando la conexion a la base de datos
            PreparedStatement st = conn.prepareStatement("SELECT c.client_first_name, c.client_last_name, SUM(t.money_amount) AS total_gastado FROM client c INNER JOIN card ca ON c.id_client = ca.id_client INNER JOIN transaction t ON ca.id_card = t.id_card WHERE c.id_client = ? AND YEAR(t.transaction_date) = ? AND MONTH(t.transaction_date) = ? GROUP BY c.client_first_name, c.client_last_name;");//Selecciona al cliente , al mes y al año para devolver el total que el cliente gasto en ese tiempo
            st.setInt(1, Integer.parseInt(txtIdClient.getText()));//Asigna el primer parametro que seria el id
            st.setInt(2, Integer.parseInt(txtYear.getText()));//Asigna el segundo parametro que seria el año
            st.setInt(3, cmbMonth.getSelectionModel().getSelectedIndex() + 1);//Asigna el tercer parametro que seria el mes, y se le suma 1 porque el selected index empieza desde 0
            ResultSet rs = st.executeQuery(); // Se ejecuta la consulta
            if (!rs.isBeforeFirst()) {//Un if quE dictamina si la consulta es vacia (No hay resultados encontrados)
                showErrorAlert("No se encontraron resultados ");// Si entra al if, muestra la alerta que no se encontraron resultados
                return;// No realiza lo demas
            }
            ObservableList<ReportBDto> reportBDtoList = FXCollections.observableArrayList();// Crea una lista observable vacía para almacenar objetos de tipo Report.

            while (rs.next()) {
                String clientName = rs.getString("client_first_name") + " " + rs.getString("client_last_name");//Muestra el nombre y apellido del cliente
                double totalGastado = rs.getDouble("total_gastado"); //Muestra el total gastado del cliente
                reportBDtoList.add(new ReportBDto(clientName, totalGastado)); // Añade a la lista de reportes al cliente y su total gastado.
            }
            tvReport.setItems(reportBDtoList);//Se muestran los resultados en la interfaz
        } catch (Exception e) { // Si no logra conectarse a la base , atrapa al error
            showErrorAlert("Error al conectarse a la base: " + e.getMessage()); //Muestra un mensaje de error si no se conecta a la base
        }

    }

    private void showErrorAlert(String message) { //Metodo para crear la alerta
        Alert alert = new Alert(Alert.AlertType.ERROR);//Crea una nueva alerta de tipo error
        alert.setTitle("Error!"); // Le asigna un titulo
        alert.setHeaderText("Se ha producido un error!"); //Le asigna el mensaje
        alert.setContentText(message);//Si hay algun mensaje contexto por ejemplo uno de SQL, lo manda a llamar de esta manera
        alert.showAndWait();// Cuando la alerta aparece espera a que el usuario precione el boton:
    }
}
