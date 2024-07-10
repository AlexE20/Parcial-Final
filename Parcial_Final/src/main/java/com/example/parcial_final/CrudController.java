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

    private Parent root;//00106123 Clase base
    private Stage stage;//00106123 Contenedor(ventana prncipal)
    private Scene scene;//00106123 Escena del progama

    @FXML
    private Button btnClientView;//00106123 Boton para mostrar el menu de clientes

    @FXML
    private Button btnCardView;//00106123 Boton para mostrar el menu de tarjetas

    @FXML
    private Button btnTransactionView;//00106123 Boton para mostrar el menu de transacciones

    @FXML
    private Button btnReturn;//00106123 Boton para retornar

    @FXML
    protected void onClientViewbtn_Click(ActionEvent event)throws IOException {//00106123 Método para cambiar a la escena a la de cliente
        try{
            root = FXMLLoader.load(getClass().getResource("card-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onCardViewbtn_Click(ActionEvent event)throws IOException {//00106123 Método para cambiar a la escena a la de tarjeta
        try{
            root = FXMLLoader.load(getClass().getResource("card-view.fxml"));
        }catch(Exception e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onTransactionViewbtn_Click(ActionEvent event)throws IOException {//00106123 Método para cambiar a la escena a la de transaccion
        try{
            root = FXMLLoader.load(getClass().getResource("transaction-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event)throws IOException {//00106123 Método para regresar a la escena inicial
        try{
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
