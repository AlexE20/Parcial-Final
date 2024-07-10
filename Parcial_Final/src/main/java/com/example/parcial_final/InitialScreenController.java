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

public class InitialScreenController {

    private Parent root; // 00009423 Clase base
    private Stage stage; // 00009423 Contenedor (ventana principal)
    private Scene scene; // 00009423 Escena del programa

    @FXML
    private Button btnReportA; // 00009423 Botón para mostrar reporte A

    @FXML
    private Button btnReportB; // 00009423 Botón para mostrar reporte B

    @FXML
    private Button btnReportC; // 00009423 Botón para mostrar reporte C

    @FXML
    private Button btnReportD; // 00009423 Botón para mostrar reporte D

    @FXML
    private Button btnCRUD; // 00009423 Botón para mostrar el CRUD

    @FXML
    protected void onReportA_Click(ActionEvent event) throws IOException { // 00009423 Método para cambiar a la escena del reporte A
        try {
            root = FXMLLoader.load(getClass().getResource("reportA-view.fxml")); // 00009423 Carga la vista del reporte A
        } catch (NullPointerException e) { // 00009423 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00009423 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00009423 Obtiene la ventana actual
        scene = new Scene(root); // 00009423 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00009423 Establece la nueva escena en la ventana
        stage.show(); // 00009423 Muestra la ventana
    }

    @FXML
    protected void onReportB_Click(ActionEvent event) throws IOException { // 00009423 Método para cambiar a la escena del reporte B
        try {
            root = FXMLLoader.load(getClass().getResource("reportB-view.fxml")); // 00009423 Carga la vista del reporte B
        } catch (NullPointerException e) { // 00009423 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00009423 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00009423 Obtiene la ventana actual
        scene = new Scene(root); // 00009423 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00009423 Establece la nueva escena en la ventana
        stage.show(); // 00009423 Muestra la ventana
    }

    @FXML
    protected void onReportC_Click(ActionEvent event) throws IOException { // 00009423 Método para cambiar a la escena del reporte C
        try {
            root = FXMLLoader.load(getClass().getResource("reportC-view.fxml")); // 00009423 Carga la vista del reporte C
        } catch (NullPointerException e) { // 00009423 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00009423 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00009423 Obtiene la ventana actual
        scene = new Scene(root); // 00009423 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00009423 Establece la nueva escena en la ventana
        stage.show(); // 00009423 Muestra la ventana
    }

    @FXML
    protected void onReportD_Click(ActionEvent event) throws IOException { // 00009423 Método para cambiar a la escena del reporte D
        try {
            root = FXMLLoader.load(getClass().getResource("reportD-view.fxml")); // 00009423 Carga la vista del reporte D
        } catch (NullPointerException e) { // 00009423 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00009423 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00009423 Obtiene la ventana actual
        scene = new Scene(root); // 00009423 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00009423 Establece la nueva escena en la ventana
        stage.show(); // 00009423 Muestra la ventana
    }

    @FXML
    protected void onCRUD_Click(ActionEvent event) throws IOException { // 00009423 Método para cambiar a la escena del CRUD
        try {
            root = FXMLLoader.load(getClass().getResource("crud-view.fxml")); // 00009423 Carga la vista del CRUD
        } catch (NullPointerException e) { // 00009423 Captura la excepción de puntero nulo
            e.printStackTrace(); // 00009423 Imprime el stack trace
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00009423 Obtiene la ventana actual
        scene = new Scene(root); // 00009423 Crea una nueva escena con la vista cargada
        stage.setScene(scene); // 00009423 Establece la nueva escena en la ventana
        stage.show(); // 00009423 Muestra la ventana
    }
}
