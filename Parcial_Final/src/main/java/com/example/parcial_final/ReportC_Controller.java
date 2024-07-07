package com.example.parcial_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportC_Controller {

    private Parent root;//Clase base
    private Stage stage;//Contenedor(ventana prncipal)
    private Scene scene;//Escena del progama

    @FXML
    protected void onReturnbtn_Click(ActionEvent event)throws IOException {//Método para cambiar a la escena del reporte A
        try{
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);//Se cambia la escena.
        stage.show();
    }
}

