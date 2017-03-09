package android.trizleo.mlawmina.models;

/**
 * Created by hebi525 on 25-Feb-17.
 */

public class Card {
    private String cardName;
    private int cardNum;

    public Card(String cardName, int cardNum) {
        this.cardName = cardName;
        this.cardNum = cardNum;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardNum() {
        return cardNum;
    }
}
