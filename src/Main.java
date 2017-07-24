/**
 * Created by mitchell on 7/15/17.
 */

import com.sun.org.apache.xpath.internal.SourceTree;

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
    private ListenForAceProgression lForAce;
    private ListenForKing lForKing;
    private int[] firstCardLocations;
    private Card firstCard, secondCard;
    private JPanel[] aceProgression;
    private Card[] aceProgCards;
    private int[] aceProgTop;

    public static void main(String[] args) {
        Main m = new Main();
        m.initFrame();
        m.initAceProgression();
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

    public void initAceProgression() {
        aceProgCards = new Card[4];
        aceProgTop = new int[4];
        aceProgTop[0] = -1;
        aceProgTop[1] = -1;
        aceProgTop[2] = -1;
        aceProgTop[3] = -1;
        gridConstraints.gridx = 8;
        gridConstraints.gridy = 1;
        aceProgression = new JPanel[4];
        JLabel[] suits = {new JLabel("clubs"), new JLabel("spades"), new JLabel("hearts"), new JLabel("diamonds")};
        for (int i = 0; i < 4; i++) {
            aceProgression[i] = new JPanel();
            aceProgression[i].add(suits[i]);
            lForAce = new ListenForAceProgression();
            aceProgression[i].addMouseListener(lForAce);
            panel.add(aceProgression[i], gridConstraints);
            gridConstraints.gridy += 3;
        }
        frame.revalidate();
        frame.repaint();
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

    public Card getCardAt(int x, int y) {
        for (Card c : cards) {
            if (c.getXpos() == x && c.getYpos() == y) {
                return c;
            }
        }
        System.out.println("ERROR");
        return null;
    }

    // return array of card c and all cards "below" (v) it
    public Card[] getCardStack(Card c) {
        Card[] stack = new Card[firstCardLocations[c.getXpos()] - c.getYpos() + 1];
        int x = c.getXpos();
        int y = c.getYpos() + 1;
        int last = firstCardLocations[x];
        int i = 0;
        stack[i] = c;
        while (y <= last) {
            i++;
            stack[i] = getCardAt(x, y);
            y++;
        }

        return stack;
    }

    public void removeCards(Card[] cs) {
        for (Card c : cs) {
            panel.remove(cardPanelLocations[c.getXpos()][c.getYpos()]);
            cardPanelLocations[c.getXpos()][c.getYpos()] = null;

        }
        panel.revalidate();
        panel.repaint();
    }

    public void moveCards(Card[] newStack, Card[] oldStack) {

        Card firstOld = oldStack[oldStack.length - 1];
        int x = firstOld.getXpos();
        int y = firstOld.getYpos() + newStack.length;
        gridConstraints.gridx = x;

        firstCardLocations[x] = newStack.length + oldStack.length - 1;
        firstCardLocations[newStack[0].getXpos()] -= newStack.length;
        removeCards(oldStack);
        removeCards(newStack);

        for (int i = newStack.length - 1; i >= 0; i--) {
            gridConstraints.gridy = y;
            newStack[i].setXpos(x);
            newStack[i].setYpos(y);
            cardPanelLocations[x][y] = newStack[i].getCardImg();
            panel.add(cardPanelLocations[x][y], gridConstraints);
            y--;
        }
        for (int j = oldStack.length - 1; j >= 0; j--) {
            gridConstraints.gridy = y;
            cardPanelLocations[x][y] = oldStack[j].getCardImg();
            panel.add(cardPanelLocations[x][y], gridConstraints);
            y--;
        }
        panel.revalidate();
        panel.repaint();
    }

    public void moveCards(Card[] newStack, int loc) {
        int x = loc;
        int y = newStack.length - 1;
        gridConstraints.gridx = x;

        firstCardLocations[x] += newStack.length;
        firstCardLocations[newStack[0].getXpos()] -= newStack.length;
        removeCards(newStack);

        for (int i = newStack.length - 1; i >= 0; i--) {
            gridConstraints.gridy = y;
            newStack[i].setXpos(x);
            newStack[i].setYpos(y);
            cardPanelLocations[x][y] = newStack[i].getCardImg();
            panel.add(cardPanelLocations[x][y], gridConstraints);
            y--;
        }
    }

    public void resetMouseListeners() {
        for (int i = 0; i < cardPanelLocations.length; i++) {
            for (int j = 0; j < cardPanelLocations[0].length; j++) {
                if (cardPanelLocations[i][j] != null) {
                    MouseListener[] mListeners = cardPanelLocations[i][j].getMouseListeners();
                    for (int k = 0; k < mListeners.length; k++) {
                        cardPanelLocations[i][j].removeMouseListener(mListeners[k]);
                    }
                    if (firstCardLocations[i] >= 0) {
                        ListenForFirstMouse lForMouse1 = new ListenForFirstMouse();
                        cardPanelLocations[i][j].addMouseListener(lForMouse1);
                    }
                    else {
                        ListenForKing lForKing = new ListenForKing();
                        cardPanelLocations[i][j].addMouseListener(lForKing);
                    }
                }

            }
        }
    }

    public void checkEmptyColumns() {
        for (int z = 0; z < firstCardLocations.length; z++) {
            if (firstCardLocations[z] < 0) {
                cardPanelLocations[z][0] = new JPanel();
                gridConstraints.gridx = z;
                gridConstraints.gridy = 0;
                cardPanelLocations[z][0].add(new JLabel("EMPTY"));
                lForKing = new ListenForKing();
                cardPanelLocations[z][0].addMouseListener(lForKing);
                panel.add(cardPanelLocations[z][0], gridConstraints);
                frame.revalidate();
                frame.repaint();

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
                // CARE WHEN OPEN SPOT IMPLEMENTED
                if (n >= 0) {

                    if (cardPanelLocations[i][n] != null && cardPanelLocations[i][n].getMouseListeners().length > 0) {
                        for (MouseListener l : cardPanelLocations[i][n].getMouseListeners()) {
                            cardPanelLocations[i][n].removeMouseListener(l);
                        }
                    }
                    if (cardPanelLocations[i][n] != null) {
                        lForMouse2 = new ListenForSecondMouse();
                        cardPanelLocations[i][n].addMouseListener(lForMouse2);
                    }
                }
                /*else {
                    System.out.println("i: " + i + " n: " + n);
                    if (cardPanelLocations[i][0] != null) {
                        ListenForKing lForKing = new ListenForKing();
                        cardPanelLocations[i][0].addMouseListener(lForKing);
                    }

                }*/
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
            if (suit1.equals(suit2) && (val1 + 1) == val2 && firstCard.getXpos() != secondCard.getXpos()) {
                System.out.println("LEGAL");
                // 1) find cards underneath firstCard
                //Card[] cardStack = getCardStack(firstCard);
                // 2) move stack over below secondCard
                moveCards(getCardStack(firstCard), getCardStack(getCardAt(secondCard.getXpos(), 0)));
            }
            else {
                System.out.println("ILLEGAL");
            }

            resetMouseListeners();
            checkEmptyColumns();




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

    private class ListenForAceProgression implements MouseListener {

//        private JPanel[] aceProgression;
//        private Card[][] aceProgCards;
//        private int[] aceProgTop;

        /*
         * 1) check if firstCard is at the top
         * 2) check if firstCard is ace (val = 0)
         *   a) if ace: find first null spot in aceProgCards
         *   b) place ace there
         *   c) increment corresponding aceProgTop by 1
         */

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            System.out.println("3 MC");
            // we have ace find null stack to start with it
            if (firstCard.getValue() == 0 && firstCardLocations[firstCard.getXpos()] == firstCard.getYpos()) {
                boolean found = false;
                for (int i = 0; i < aceProgCards.length; i++) {
                    if (aceProgCards[i] == null && found == false) {
                        panel.remove(aceProgression[i]);
                        aceProgCards[i] = firstCard;
                        firstCardLocations[firstCard.getXpos()] -= 1;
                        cardPanelLocations[firstCard.getXpos()][firstCard.getYpos()] = null;
                        //aceProgression[i].remove(aceProgression[i])
                        aceProgression[i] = firstCard.getCardImg();
                        System.out.println(aceProgression[i].getMouseListeners().length);
                        MouseListener[] aceMLS = aceProgression[i].getMouseListeners();
                        for (int j = 0; j < aceMLS.length; j++) {
                            aceProgression[i].removeMouseListener(aceMLS[j]);
                        }
                        lForAce = new ListenForAceProgression();
                        aceProgression[i].addMouseListener(lForAce);
                        aceProgTop[i]++;
                        gridConstraints.gridx = 8;
                        gridConstraints.gridy = (i) * 3;
                        aceProgCards[i].setXpos(gridConstraints.gridx);
                        aceProgCards[i].setYpos(gridConstraints.gridy);
                        panel.add(aceProgression[i], gridConstraints);
                        found = true;
                    }
                }
            }

            if (firstCard.getValue() > 0 && firstCardLocations[firstCard.getXpos()] == firstCard.getYpos()) {
                // find stack
                for (int i = 0; i < aceProgCards.length; i++) {
                    if (aceProgCards[i] != null && aceProgCards[i].getType().equals(firstCard.getType()) && (firstCard.getValue() - 1) == aceProgCards[i].getValue()) {
                        panel.remove(cardPanelLocations[firstCard.getXpos()][firstCard.getYpos()]);
                        cardPanelLocations[firstCard.getXpos()][firstCard.getYpos()] = null;
                        firstCardLocations[firstCard.getXpos()] -= 1;
                        panel.remove(aceProgression[i]);
                        gridConstraints.gridx = aceProgCards[i].getXpos();
                        gridConstraints.gridy = aceProgCards[i].getYpos();
                        aceProgCards[i] = firstCard;
                        aceProgCards[i].setXpos(gridConstraints.gridx);
                        aceProgCards[i].setYpos(gridConstraints.gridy);
                        aceProgression[i] = aceProgCards[i].getCardImg();
                        lForAce = new ListenForAceProgression();
                        aceProgression[i].addMouseListener(lForAce);
                        panel.add(aceProgression[i], gridConstraints);
                        frame.revalidate();
                        frame.repaint();

                    }
                }
            }
//            if (firstCard.getValue() == 0 && aceProgCards[0] == null && firstCardLocations[firstCard.getXpos()] == firstCard.getYpos()) {
//                cardPanelLocations[firstCard.getXpos()][firstCard.getYpos()] = null;
//                firstCardLocations[firstCard.getXpos()] = firstCard.getYpos() - 1;
//                // should I udpate the x and y pos of done card ?
//                panel.remove(aceProgression[0]);
//                gridConstraints.gridx = 8;
//                gridConstraints.gridy = 1;
//                panel.add(firstCard.getCardImg(), gridConstraints);
//            }


            resetMouseListeners();
            checkEmptyColumns();

            frame.revalidate();
            frame.repaint();


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

    private class ListenForKing implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

            System.out.println("king spot clicked");
            if (firstCard.getValue() == 12) {
                //moveCards(getCardStack(firstCard), mouseEvent.getX())
                for (int i = 0; i < cardPanelLocations.length; i++) {
                    for (int j = 0; j < cardPanelLocations[0].length; j++) {
                        if (cardPanelLocations[i][j] != null && cardPanelLocations[i][j] == (JPanel)mouseEvent.getSource()) {
                            System.out.println("GOT HERE");
                            panel.remove(cardPanelLocations[i][j]);
                            cardPanelLocations[i][j] = null;
                            moveCards(getCardStack(firstCard), i);
                        }
                    }
                }
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
