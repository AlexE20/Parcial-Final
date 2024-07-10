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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
public class ReportB_Controller implements Initializable {

    private Parent root;//00106123 Clase base
    private Stage stage;//00106123 Contenedor(ventana prncipal)
    private Scene scene;//00106123 Escena del progama

    @FXML
    private Button returnBtn;//00080323 Botón para retornar a la ventana principal.

    @FXML
    private TextField txtIdClient;//00009423 Text field para ingresar el id del cliente

    @FXML
    private ComboBox<String> cmbMonth;//00009423 Combo box para seleccionar el mes

    @FXML
    private TextField txtYear;//00009423 Texto para ingresar el año

    @FXML
    private TableView<Report> tvReport;//00009423Tabla donde se mostraran los datos

    @FXML
    private TableColumn<Report, String> cClientName; //00009423 Columna donde se mostrara el nombre del cliente

    @FXML
    private TableColumn<Report, Double> cAmountOfMoney;//00009423 Columna donde se mostrara el total de dinero que gasto

    @FXML
    private Button btnBuscar;//00009423 Boton dentro de la interfaz para buscar al cliente

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbMonth.getItems().addAll("01-January", "02-February", "03-March", "04-April", "05-May", "06-June", "07-July", "08-August", "09-September", "10-October", "11-November", "12-December"); //00009423 Asignacion de los meses que apareceran en el combo box
        cClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));//00009423 Configura la columna cClientName para que utilice la propiedad clientName de los objetos Report que se mostrarán en la TableView para que cada celda muestre el valor
        cAmountOfMoney.setCellValueFactory(new PropertyValueFactory<>("amountOfMoney"));//00009423 Configura la columna cAmountOfMoney para que utilice la propiedad 'amountOfMoney' de los objetos Report que se mostrarán en la TableView para que cada celda muestre el valor
    }

    //00009423 Funcion que se realiza al apretar el boton buscar dentro de la interfaz
    @FXML
    private void reportB() {
        if (txtIdClient.getText().isEmpty() || txtYear.getText().isEmpty() || cmbMonth.getSelectionModel().getSelectedItem() == null) { //00009423 Comprueba si algun campo de la interfaz esta vacio o sin seleccionar
            showErrorAlert("Campo obligatorio no seleccionado"); //00009423 Muestra un mensaje de alerta si entra en el if
            return; //00009423 No se realiza nada
        }
        try {//00009423 Intenta la conexion con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBanco", "root", "Elchocochele04!");//00009423 Realizando la conexion a la base de datos
            PreparedStatement st = conn.prepareStatement("SELECT c.client_first_name, c.client_last_name, SUM(t.money_amount) AS total_gastado FROM client c INNER JOIN card ca ON c.id_client = ca.id_client INNER JOIN transaction t ON ca.id_card = t.id_card WHERE c.id_client = ? AND YEAR(t.transaction_date) = ? AND MONTH(t.transaction_date) = ? GROUP BY c.client_first_name, c.client_last_name;");//00009423 Selecciona al cliente , al mes y al año para devolver el total que el cliente gasto en ese tiempo
            st.setInt(1, Integer.parseInt(txtIdClient.getText()));//00009423 Asigna el primer parametro que seria el id
            st.setInt(2, Integer.parseInt(txtYear.getText()));//00009423 Asigna el segundo parametro que seria el año
            st.setInt(3, cmbMonth.getSelectionModel().getSelectedIndex() + 1);//00009423Asigna el tercer parametro que seria el mes, y se le suma 1 porque el selected index empieza desde 0
            ResultSet rs = st.executeQuery(); //00009423 Se ejecuta la consulta
            if (!rs.isBeforeFirst()) {//00009423 Un if quE dictamina si la consulta es vacia (No hay resultados encontrados)
                showErrorAlert("No se encontraron resultados ");//00009423 Si entra al if, muestra la alerta que no se encontraron resultados
                return;//00009423 No realiza lo demas
            }
            ObservableList<Report> reportList = FXCollections.observableArrayList();//00009423 Crea una lista observable vacía para almacenar objetos de tipo Report.

            while (rs.next()) {
                String clientName = rs.getString("client_first_name") + " " + rs.getString("client_last_name");//00009423 Muestra el nombre y apellido del cliente
                double totalSpent = rs.getDouble("total_gastado"); //00009423 Muestra el total gastado del cliente
                reportList.add(new Report(clientName, totalSpent)); //00009423 Añade a la lista de reportes al cliente y su total gastado.
            }
            tvReport.setItems(reportList);//00009423 Se muestran los resultados en la interfaz
        } catch (Exception e) { //00009423 Si no logra conectarse a la base , atrapa al error
            showErrorAlert(e.getMessage()); //00009423 Muestra un mensaje de error si no se conecta a la base
        }
        generateFile(); //00009423 Se genera el archivo de texto del reporte

    }

    private void showErrorAlert(String message) { //00009423Metodo para crear la alerta
        Alert alert = new Alert(Alert.AlertType.ERROR);//00009423Crea una nueva alerta de tipo error
        alert.setTitle("Error!"); //00009423 Le asigna un titulo
        alert.setHeaderText("Se ha producido un error!"); //00009423 Le asigna el mensaje
        alert.setContentText(message);//00009423Si hay algun mensaje contexto por ejemplo uno de SQL, lo manda a llamar de esta manera
        alert.showAndWait();//00009423 Cuando la alerta aparece espera a que el usuario precione el boton:
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

        String fileName = path + "Report-B-" + dtf.format(now) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            ObservableList<Report> reportList = tvReport.getItems();

            if (reportList.isEmpty()) {
                writer.write("No data available\n");
            } else {
                for (Report report : reportList) {
                    writer.write("Client: " + report.getClientName() + "\nAmount: " + report.getAmountOfMoney() + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException {//00009423 Método para retornar a la ventana principal.
        try {
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml"));//00080323 Cargamos la pantalla inicial.
        } catch (NullPointerException e) {//00080323 Manejamos la exepción.
            e.printStackTrace();// 00080323 Imprimimos exepción.
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//00080323 Método para  obtener la ventana actual.
        scene = new Scene(root);// 00080323 Instanciamos una nueva escena .
        stage.setScene(scene);//00080323 Cambiamos la escena de la ventana.
        stage.show();//00080323 Mostramos nueva escena en la ventana.
    }
}
