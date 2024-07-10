package  com.example.parcial_final;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Card{

    private int idCard;
    private final StringProperty cardNumber;
    private LocalDate expirationDate;
    private String cardType;
    private int idFacilitator;
    private int idClient;

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber.set(cardNumber);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getIdFacilitator() {
        return idFacilitator;
    }

    public void setIdFacilitator(int idFacilitator) {
        this.idFacilitator = idFacilitator;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Card(int idCard, StringProperty cardNumber, LocalDate expirationDate, String cardType, int idFacilitator, int idClient) {
        this.idCard = idCard;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardType = cardType;
        this.idFacilitator = idFacilitator;
        this.idClient = idClient;
    }

    public Card(String cardNumber) {
        this.cardNumber = new SimpleStringProperty(cardNumber);
    }

    public String getCardNumber() {
        return cardNumber.get();
    }

    public StringProperty cardNumberProperty() {
        return cardNumber;
    }

    public String getCardNumberCensored() {
        String cardNum = cardNumber.get();
        if (cardNum.equals("N/A")) {
            return cardNum;
        }
        return "XXXX XXXX XXXX " + cardNum.substring(cardNum.length() - 4);
    }

    public StringProperty cardNumberCensoredProperty() {
        return new SimpleStringProperty(getCardNumberCensored());
    }
}
