/**
 * Created by mitchell on 7/15/17.
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Deck d = new Deck();
        d.makeDeck();
        d.shuffleDeck();
        Card[] cards = d.getCardDeck();


        JFrame frame = new JFrame("Grid Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920,1080);
        JPanel panel = new JPanel();
        // rows, col, hgap, vgap
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = 6;
        gridConstraints.gridy = 10;
        gridConstraints.gridwidth = 1;
        gridConstraints.gridheight = 1;
        gridConstraints.weightx = 0;
        gridConstraints.weighty = 0;
        gridConstraints.insets = new Insets(5,5,-100,5);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.fill = GridBagConstraints.BOTH;



        /*int ci = 0;
        Box[][] card_locations = new Box[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                //Box newBox = Box.createHorizontalBox();
                JPanel inBox = new JPanel();
                //inBox.setSize(161, 161);
                inBox.setLayout(new BorderLayout());
                //inBox.add(card1, BorderLayout.CENTER);
                //inBox.setBorder(boxBorder);
                if (ci < 52) {
                    inBox.add(cards[ci].getCardImg(), BorderLayout.CENTER);
                    inBox.add(cards[ci].getCardImg(), BorderLayout.CENTER);
                    ci++;
                }
                //newBox.add(inBox);
                //card_locations[row][col] = newBox;
                //gridConstraints.gridx++;
                if (gridConstraints.gridx > 7) {
                    gridConstraints.gridx = 1;
                    gridConstraints.gridy--;
                }
                else {
                    gridConstraints.gridx++;
                }
                panel.add(inBox, gridConstraints);
            }

        } */

        // place cards 7 col last 11 row
        int top = 10;
        for (Card card : cards) {
            JPanel spot = new JPanel();
            spot.add(card.getCardImg());
            panel.add(spot, gridConstraints);
            if (gridConstraints.gridy == 0) {
                if (gridConstraints.gridx == 1) {
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 0;
                }
                else {
                    top--;
                    gridConstraints.gridy = top;
                    gridConstraints.gridx--;
                }
            }
            else {
                gridConstraints.gridy--;
            }
        }


        frame.add(panel);

        frame.setVisible(true);

    }

}
