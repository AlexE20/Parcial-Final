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
import java.sql.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportC_Controller implements FileGenerator{//00080323 Clase responsable de controlar la vista del reporte C.

    private Parent root;//00080323 Ventana padre.
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField idClientTextField;
    @FXML
    private TableView<Card> tableCreditCards;
    @FXML
    private TableColumn<Card, String> colCreditCardNum;

    @FXML
    private TableView<Card> tableDeditCards;
    @FXML
    private TableColumn<Card, String> colDeditCardNum;

    @FXML
    private Button returnBtn;

    @FXML
    private Button generateReportBtn;

    @FXML
    public void initialize() {
        colCreditCardNum.setCellValueFactory(cellData -> cellData.getValue().cardNumberCensoredProperty());
        colDeditCardNum.setCellValueFactory(cellData -> cellData.getValue().cardNumberCensoredProperty());
    }

    @FXML
    public void generateReport() {
        int clientId = Integer.parseInt(idClientTextField.getText());
        ArrayList<Card> creditCards = getCards(clientId, "Credit");
        ArrayList<Card> debitCards = getCards(clientId, "Debit");


        if (creditCards.isEmpty()) {
            creditCards.add(new Card("N/A"));
        }

        if (debitCards.isEmpty()) {
            debitCards.add(new Card("N/A"));
        }

        tableCreditCards.getItems().setAll(creditCards);
        tableDeditCards.getItems().setAll(debitCards);
        generateFile(creditCards,debitCards);
    }

    private ArrayList<Card> getCards(int clientId, String cardType) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbBank", "root", "apolo2004");
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT t.card_number " +
                            "FROM client c " +
                            "JOIN card t ON t.id_client = c.id_client " +
                            "WHERE t.id_client = ? AND t.card_type = ?");
            pst.setInt(1, clientId);
            pst.setString(2, cardType);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String cardNumber = rs.getString("card_number");
                cards.add(new Card(cardNumber));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return cards;
    }

    @FXML
    protected void onReturnbtn_Click(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("initial-view.fxml"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void generateFile(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String path= "Parcial_Final/src/Reports/";
        String fileName = path + "Report-C-" + dtf.format(now) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Credit Cards:\n");
            for (Card card : creditCards) {
                writer.write(card.getCardNumberCensored() + "\n");
            }

            writer.write("\nDebit Cards:\n");
            for (Card card : debitCards) {
                writer.write(card.getCardNumberCensored() + "\n");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while generating the report file: " + e.getMessage());
        }




    }


}
