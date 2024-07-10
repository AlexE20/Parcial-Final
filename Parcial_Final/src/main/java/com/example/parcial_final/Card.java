package  com.example.parcial_final;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Card {

    private int idCard; // 00009423 ID de la tarjeta
    private final StringProperty cardNumber; // 00009423 Número de la tarjeta como propiedad
    private LocalDate expirationDate; // 00009423 Fecha de vencimiento de la tarjeta
    private String cardType; // 00009423 Tipo de tarjeta
    private int idFacilitator; // 00009423 ID del facilitador
    private int idClient; // 00009423 ID del cliente

    public int getIdCard() {
        return idCard; // 00009423 Obtiene el ID de la tarjeta
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard; // 00009423 Establece el ID de la tarjeta
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber.set(cardNumber); // 00009423 Establece el número de la tarjeta
    }

    public LocalDate getExpirationDate() {
        return expirationDate; // 00009423 Obtiene la fecha de vencimiento
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate; // 00009423 Establece la fecha de vencimiento
    }

    public String getCardType() {
        return cardType; // 00009423 Obtiene el tipo de tarjeta
    }

    public void setCardType(String cardType) {
        this.cardType = cardType; // 00009423 Establece el tipo de tarjeta
    }

    public int getIdFacilitator() {
        return idFacilitator; // 00009423 Obtiene el ID del facilitador
    }

    public void setIdFacilitator(int idFacilitator) {
        this.idFacilitator = idFacilitator; // 00009423 Establece el ID del facilitador
    }

    public int getIdClient() {
        return idClient; // 00009423 Obtiene el ID del cliente
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient; // 00009423 Establece el ID del cliente
    }

    public Card(int idCard, StringProperty cardNumber, LocalDate expirationDate, String cardType, int idFacilitator, int idClient) {
        this.idCard = idCard; // 00009423 Constructor que inicializa el ID de la tarjeta
        this.cardNumber = cardNumber; // 00009423 Inicializa el número de la tarjeta
        this.expirationDate = expirationDate; // 00009423 Inicializa la fecha de vencimiento
        this.cardType = cardType; // 00009423 Inicializa el tipo de tarjeta
        this.idFacilitator = idFacilitator; // 00009423 Inicializa el ID del facilitador
        this.idClient = idClient; // 00009423 Inicializa el ID del cliente
    }

    public Card(String cardNumber) {
        this.cardNumber = new SimpleStringProperty(cardNumber); // 00009423 Constructor que inicializa solo el número de la tarjeta
    }

    public String getCardNumber() {
        return cardNumber.get(); // 00009423 Obtiene el número de la tarjeta
    }

    public StringProperty cardNumberProperty() {
        return cardNumber; // 00009423 Obtiene la propiedad del número de la tarjeta
    }

    public String getCardNumberCensored() {
        String cardNum = cardNumber.get(); // 00009423 Obtiene el número de la tarjeta
        if (cardNum.equals("N/A")) {
            return cardNum; // 00009423 Retorna "N/A" si el número es "N/A"
        }
        return "XXXX XXXX XXXX " + cardNum.substring(cardNum.length() - 4); // 00009423 Censura el número de la tarjeta, mostrando solo los últimos 4 dígitos
    }

    public StringProperty cardNumberCensoredProperty() {
        return new SimpleStringProperty(getCardNumberCensored()); // 00009423 Retorna la propiedad del número de tarjeta censurado
    }
}
