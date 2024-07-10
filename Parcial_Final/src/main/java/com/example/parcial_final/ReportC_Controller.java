package com.example.parcial_final;
import com.example.parcial_final.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportC_Controller {//00080323 Clase responsable de controlar la vista del reporte C.

    private Parent root;//00080323 Es el nodo ruta del archivo FXML.
    private Stage stage;//00080323 Es la ventana de la aplicación
    private Scene scene;//00080323 Es el contenido adentro de la ventana, es decir lo que cambia dinámicamente.

    @FXML
    private TextField idClientTextField;//00080323  Campo de texto para ingresar el id.
    @FXML
    private TableView<Card> tableCreditCards;//00080323  Tabla para tarjetas de crédito.
    @FXML
    private TableColumn<Card, String> colCreditCardNum;//00080323  Columna para los números de las tarjetas de crédito.

    @FXML
    private TableView<Card> tableDeditCards;//00080323  Tabla para tarjetas de dédito.
    @FXML
    private TableColumn<Card, String> colDeditCardNum;//00080323 Columna para los números de las tarjetas de dédito.

    @FXML
    private Button returnBtn;//00080323 Botón para retornar a la ventana principal.

    @FXML
    private Button generateReportBtn;//00080323 Botón para generar reporte.

    @FXML
    public void initialize() {// 00080323 Método para inicializar el controlador, y inicializar los valores de las columnas de las tablas.
        colCreditCardNum.setCellValueFactory(cellData -> cellData.getValue().cardNumberCensoredProperty()); //00080323 Llena los valores de las columnas con la informacion de la base
        colDeditCardNum.setCellValueFactory(cellData -> cellData.getValue().cardNumberCensoredProperty()); //00080323 Llena los valores de las columnas con la informacion de la base
    }

    @FXML
    public void generateReport() {// 00080323 Método que s eactiva al presionar el botón Generar Reporte.
        int clientId = Integer.parseInt(idClientTextField.getText());// 00080323 Leer texto ingresado en el campo para el id del cliente.
        ArrayList<Card> creditCards = getCards(clientId, "Credit");//00080323 Obtener tarjetas de crédito.
        ArrayList<Card> debitCards = getCards(clientId, "Debit");// 00080323 Obtener tarjetas de débito.


        if (creditCards.isEmpty()) {// 00080323 Condición para verificar si el cliente tiene tarjetas de crédito.
            creditCards.add(new Card("N/A"));// 00080323 Desplegar como número N/A si no tiene tarjetas el cliente, este se ingresa al objeto Card para que sea desplegado en la tabla.
        }

        if (debitCards.isEmpty()) {// 00080323 Condición para verificar si el cliente tiene tarjetas de débito.
            debitCards.add(new Card("N/A"));// 00080323 Desplegar N/A si no tiene tarjetas de crédito.
        }

        tableCreditCards.getItems().setAll(creditCards);//00080323 Llenar valores de la tabla con las tarjetas de crédito encontradas.
        tableDeditCards.getItems().setAll(debitCards);//00080323 Llenar valores de la tabla con las tarjetas de dédito encontradas.
        generateFile(creditCards,debitCards);//00080323 Generar archivo txt.
    }

    private ArrayList<Card> getCards(int clientId, String cardType) {//00080323 Método para obtener las tarjetas dado un id de cliente y un tipo de tarjeta.
        ArrayList<Card> cards = new ArrayList<>();//00080323 Se crea un ArrayList de tarjetas vacío.
        try {//00080323 Try-catch para establecer conexión con bD.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");//00080323 Establecer conexión con bD.
            PreparedStatement pst = conn.prepareStatement(//00080323 Preparamos el statement de sql dado un id y tipo de tarjeta.
                    "SELECT t.card_number " +
                            "FROM client c " +
                            "JOIN card t ON t.id_client = c.id_client " +
                            "WHERE t.id_client = ? AND t.card_type = ?");
            pst.setInt(1, clientId);//00080323 Llenamos el primer parámetro del query. con el id dado.
            pst.setString(2, cardType);//00080323 Llenamos el segundo parámetro con el tipo de tarjeta.
            ResultSet rs = pst.executeQuery();//00080323 Ejecutamos el query.

            while (rs.next()) {//00080323 Iteramos sobre el conjunto de resultados.
                String cardNumber = rs.getString("card_number");//00080323 Obtenemos el número de tarjeta obtenido de la consulta.
                cards.add(new Card(cardNumber));//00080323 Agregamos la tarjeta encontrada a la lista.
            }

        } catch (Exception e) {//00080323 Manejamos la exepción.
            System.out.println(e);//00080323 Se imprime la exepción.
        }

        return cards;//00080323 Retornamos el ArrayList de tarjetas solicitado.
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException {//00080323 Método para retornar a la ventana principal.
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

    public void generateFile(ArrayList<Card> creditCards, ArrayList<Card> debitCards){//00080323 Método para generar archivo txt.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");//00080323 Crea un formateador de fecha y hora con el patrón "dd-MM-yyyy_HH-mm-ss-
        LocalDateTime now = LocalDateTime.now();//00080323 Obtiene la fecha y hora actual.
        String path= "Reports/";//00080323 Ruta donde se quiere agregar el archivo.
        try { // 00106123 se intenta ejecutar el siguiente bloque de codigo
            Files.createDirectories(Paths.get(path)); //00106123 Revisa si la carpeta que se le paso existe, y si no, la crea
        } catch (IOException e) { //00106123 manejo de la excepcion
            System.out.println(e); //00106123 se imprime la excepcion
        }
        String fileName = path + "Report-C-" + dtf.format(now) + ".txt";//00080323 Crea el nombre del archivo utilizando la ruta, un prefijo y la fecha y hora formateadas.

        try (FileWriter writer = new FileWriter(fileName)) {//00080323 Try-Catch para asegurar que el FileWriter se cierre automáticamente
            writer.write("Credit Cards:\n");//00080323 Encabezado para las tarjetas crédito.
            for (Card card : creditCards) {//00080323 Iteramos sobre las tarjetas de crédito encontradas.
                writer.write(card.getCardNumberCensored() + "\n");//00080323 Censuramos y imprimos en el archivo el # de tarjeta.
            }

            writer.write("\nDebit Cards:\n");//00080323 Encabezado para las tarjetas dédito.
            for (Card card : debitCards) {//00080323 Iteramos sobre las tarjetas de dédito encontradas.
                writer.write(card.getCardNumberCensored() + "\n");//00080323 Escribimso el # de tarjeta en el .txt.
            }

        } catch (IOException e) {//00080323 Capturamos cualquier exepción que suceda durante el proceso de escritura en el archivo.
            System.out.println(e);//00080323  Imprmir mensaje de exepción si ocurre .
        }

    }


}
