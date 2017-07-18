/**
 * Created by mitchell on 7/15/17.
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {

    private Deck d;
    private Card[] cards;
    private GridBagConstraints gridConstraints;
    private JFrame frame;
    private JPanel panel;
    private JPanel[][] cardPanelLocations;
    private ListenForFirstMouse lForMouse1;
    private ListenForSecondMouse lForMouse2;
    private int[] firstCardLocations;
    private Card firstCard, secondCard;

    public static void main(String[] args) {
        Main m = new Main();
        m.initFrame();
    }

    public void initFrame() {
        frame = new JFrame("Grid Layout");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920,1080);
        panel = new JPanel();
        this.initGridConstraints();
        this.dealCards();
        frame.add(panel);
        frame.setVisible(true);
    }

    public void initMouse(JPanel p) {
        lForMouse1 = new ListenForFirstMouse();
        p.addMouseListener(lForMouse1);
    }

    public void initGridConstraints() {
        panel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 6;
        gridConstraints.gridy = 10;
        gridConstraints.gridwidth = 1;
        gridConstraints.gridheight = 1;
        gridConstraints.weightx = 0;
        gridConstraints.weighty = 0;
        gridConstraints.insets = new Insets(5,5,-100,5);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.fill = GridBagConstraints.BOTH;

    }

    public void dealCards() {
        // initialize the deck
        d = new Deck();
        d.makeDeck();
        d.shuffleDeck();
        cards = d.getCardDeck();

        // firstCardLocations keeps track of the "top" card in a column
        // the top card will be the only card in which other cards may be
        // placed on top of
        firstCardLocations =  new int[7];
        firstCardLocations[0] = 0;
        firstCardLocations[1] = 5;
        firstCardLocations[2] = 6;
        firstCardLocations[3] = 7;
        firstCardLocations[4] = 8;
        firstCardLocations[5] = 9;
        firstCardLocations[6] = 10;
        cardPanelLocations = new JPanel[7][52];

        // layout cards in the standard Russian Solitaire format
        int top = 10;
        for (Card card : cards) {
            card.setXpos(gridConstraints.gridx);
            card.setYpos(gridConstraints.gridy);
            cardPanelLocations[gridConstraints.gridx][gridConstraints.gridy] = card.getCardImg();
            initMouse(cardPanelLocations[gridConstraints.gridx][gridConstraints.gridy]);
            panel.add(cardPanelLocations[gridConstraints.gridx][gridConstraints.gridy], gridConstraints);

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

    }

    private class ListenForFirstMouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            System.out.println("FIRSTMOUSECLICK");
            for (Card c : cards) {
                if (c.getCardImg() == (JPanel)mouseEvent.getSource()) {
                    firstCard = c;
                }
            }
            int i = 0;
            for (int n : firstCardLocations) {
                if (cardPanelLocations[i][n].getMouseListeners().length > 0) {
                    cardPanelLocations[i][n].removeMouseListener(cardPanelLocations[i][n].getMouseListeners()[0]);
                }
                lForMouse2 = new ListenForSecondMouse();
                cardPanelLocations[i][n].addMouseListener(lForMouse2);
                i++;
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {



        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    private class ListenForSecondMouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            System.out.println("SECONDMOUSECLICK");
            for (Card c : cards) {
                if (c.getCardImg() == (JPanel)mouseEvent.getSource()) {
                    secondCard = c;
                }
            }

            // check to see if firstCard's value is one less than secondCard's value
            // &&
            // check to see if firstCard and secondCard have same suit
            // if so, this is a legal move
            // else do nothing
            // either way reset the mouse listeners
            String suit1 = firstCard.getType();
            String suit2 = secondCard.getType();
            int val1 = firstCard.getValue();
            int val2 = secondCard.getValue();
            if (suit1.equals(suit2) && (val1 + 1) == val2) {
                System.out.println("LEGAL");
            }
            else {
                System.out.println("ILLEGAL");
            }


        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
