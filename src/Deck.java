import java.util.Random;

/**
 * Created by mitchell on 7/16/17.
 */


public class Deck {
    private Card[] cardDeck;

    // creates on a non-shuffled 52 card deck
    public void makeDeck() {
        cardDeck = new Card[52];
        String[] cardValues = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
        String[] cardTypes = {"clubs", "diamonds", "hearts", "spades"};
        int i = 0;

        for (String val : cardValues) {
            for (String type : cardTypes) {
                cardDeck[i] = new Card(val + "_of_" + type + ".png");
                i++;
            }
        }
    }

    public Card[] getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(Card[] d) {
        this.cardDeck = d;
    }

    // scrambles cards in preparation of dealing the deck
    public void shuffleDeck() {
        Random rn = new Random();
        int r;
        //rn.nextInt(52);
        Card[] tempDeck = new Card[52];
        int cardsShuffled = 0;
        int[] numsUsed = new int[52];
        for (Card c : getCardDeck()) {
            r = rn.nextInt(52);
            while (in_array(numsUsed, cardsShuffled, r)) {
                r = rn.nextInt(52);
            }
            tempDeck[r] = c;
            numsUsed[cardsShuffled] = r;
            cardsShuffled++;
        }
        this.setCardDeck(tempDeck);

    }

    // check if the current random index has already been used when shuffling cards
    public boolean in_array(int[] arr, int finalIndex, int n) {
        int i = 0;
        while (i < finalIndex) {
            if (arr[i] == n) {
                return true;
            }
            i++;
        }
        return false;
    }


}
