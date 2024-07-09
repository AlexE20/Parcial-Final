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

    private Parent root;//Clase base
    private Stage stage;//Contenedor(ventana prncipal)
    private Scene scene;//Escena del progama

    @FXML
    private Button btnReportA;//Boton para mostrar reporte A.

    @FXML
    private Button btnReportB;//Boton para mostrar reporte B.

    @FXML
    private Button btnReportC;//Boton para mostrar reporte C.

    @FXML
    private Button btnReportD;//Boton para mostrar reporte D.
    @FXML
    private Button btnCRUD; //Boton para mostrar el CRUD.



    @FXML
    protected void onReportA_Click(ActionEvent event)throws IOException {//Método para cambiar a la escena del reporte A
        try{
            root = FXMLLoader.load(getClass().getResource("reportA-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onReportB_Click(ActionEvent event) throws IOException{
        try{
            root = FXMLLoader.load(getClass().getResource("reportB-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onReportC_Click(ActionEvent event)throws IOException {
        try{
            root = FXMLLoader.load(getClass().getResource("reportC-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    protected void onReportD_Click(ActionEvent event) throws IOException{
        try{
            root = FXMLLoader.load(getClass().getResource("reportD-view.fxml"));
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onCRUD_Click(ActionEvent event)throws IOException {
        try{
            root = FXMLLoader.load(getClass().getResource("crud-view.fxml"));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}