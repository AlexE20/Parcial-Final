package com.example.parcial_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankApplication extends Application {//00009423 Clase para ejecutar
    @Override
    public void start(Stage stage) throws IOException {//00009423 Inicia

        FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("initial-view.fxml")); //00009423 Carga la pantalla que se usara
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);// 00009423 dimensiones
        stage.setTitle("Bank Aplication");//00009423 titulo de la app

        stage.setScene(scene);//00009423 coloca la escena inicial
        stage.show();//00009423 muestra la escena inicial
    }

    public static void main(String[] args) {
        launch();
    }//00009423 inicia el progrsma
}
