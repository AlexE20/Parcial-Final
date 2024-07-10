package com.example.parcial_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CrudController {

    private Parent root; // 00106123 Clase base
    private Stage stage; // 00106123 Contenedor (ventana principal)
    private Scene scene; // 00106123 Escena del programa

    @FXML
    private Button btnClientView; // 00106123 Botón para mostrar el menú de clientes

    @FXML
    private Button btnCardView; // 00106123 Botón para mostrar el menú de tarjetas

    @FXML
    private Button btnTransactionView; // 00106123 Botón para mostrar el menú de transacciones

    @FXML
    private Button btnReturn; // 00106123 Botón para retornar

    @FXML
    protected void onClientViewbtn_Click(ActionEvent event) throws IOException { // 00106123 Método para cambiar a la escena de cliente
        try {
            root = FXMLLoader.load(getClass().getResource("client-view.fxml")); // 00106123 Carga la vista de cliente
        } catch (NullPointerException e) { // 00106123 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00106123 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00106123 Obtiene la ventana actual
        scene = new Scene(root); // 00106123 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00106123 Establece la nueva escena en la ventana
        stage.show(); // 00106123 Muestra la ventana
    }

    @FXML
    protected void onCardViewbtn_Click(ActionEvent event) throws IOException { // 00106123 Método para cambiar a la escena de tarjeta
        try {
            root = FXMLLoader.load(getClass().getResource("card-view.fxml")); // 00106123 Carga la vista de tarjeta
        } catch (Exception e) { // 00106123 Captura cualquier excepción
            e.printStackTrace(); // 00106123 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00106123 Obtiene la ventana actual
        scene = new Scene(root); // 00106123 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00106123 Establece la nueva escena en la ventana
        stage.show(); // 00106123 Muestra la ventana
    }

    @FXML
    protected void onTransactionViewbtn_Click(ActionEvent event) throws IOException { // 00106123 Método para cambiar a la escena de transacción
        try {
            root = FXMLLoader.load(getClass().getResource("transaction-view.fxml")); // 00106123 Carga la vista de transacción
        } catch (NullPointerException e) { // 00106123 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00106123 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00106123 Obtiene la ventana actual
        scene = new Scene(root); // 00106123 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00106123 Establece la nueva escena en la ventana
        stage.show(); // 00106123 Muestra la ventana
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException { // 00106123 Método para regresar a la escena inicial
        try {
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml")); // 00106123 Carga la vista inicial
        } catch (NullPointerException e) { // 00106123 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00106123 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00106123 Obtiene la ventana actual
        scene = new Scene(root); // 00106123 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00106123 Establece la nueva escena en la ventana
        stage.show(); // 00106123 Muestra la ventana
    }
}
