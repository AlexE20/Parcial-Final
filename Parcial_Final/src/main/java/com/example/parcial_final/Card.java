package  com.example.parcial_final;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card{
    private final StringProperty cardNumber;

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
