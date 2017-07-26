import javax.swing.*;

/**
 * Created by mitchell on 7/16/17.
 */
public class Card {

    private int value;
    private String type, fullTitle;
    private int xpos, ypos;
    private JPanel cardImg, backSide;

    public Card(String s) {
        String temp_s = s.substring(0, s.length() - 4); // remove .png extension
        this.setFullTitle(temp_s);
        String[] split = temp_s.split(("_"));

        this.setValue(strToVal(split[0]));
        this.setType(split[2]);
        this.setXpos(-1);
        this.setYpos(-1);

        ShowPNG img = new ShowPNG();
        //System.out.println(s);
        this.setCardImg(img.getCard(s));
        this.setBackSideImg(img.getCard("back.png"));
    }


    /* turn string into integer representation
     * 0 -> 2
     * 1 -> 3
     * ...
     * 8 -> 10
     * 9 -> Jack
     * 10 -> Queen
     * 11 -> King
     * 12 -> Ace
     */
    public int strToVal(String s) {
        int val;
        switch(s) {
            case "ace":
                val = 0;
                break;
            case "king":
                val = 12;
                break;
            case "queen":
                val = 11;
                break;
            case "jack":
                val = 10;
                break;
            case "10":
                val = 9;
                break;
            case "9":
                val = 8;
                break;
            case "8":
                val = 7;
                break;
            case "7":
                val = 6;
                break;
            case "6":
                val = 5;
                break;
            case "5":
                val = 4;
                break;
            case "4":
                val = 3;
                break;
            case "3":
                val = 2;
                break;
            case "2":
                val = 1;
                break;
            default:
                val = -1; // error
                break;
        }
            return val;
    }

    public JPanel getCardImg() {
        return cardImg;
    }

    public JPanel getBackSideImg() {
        return backSide;
    }

    public void setBackSideImg(JPanel back) {
        this.backSide = back;
    }

    public void setCardImg(JPanel cardImg) {
        this.cardImg = cardImg;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public void setFullTitle(String s) {
        this.fullTitle = s;
    }

    public String getFullTitle() {
        return fullTitle;
    }

}
