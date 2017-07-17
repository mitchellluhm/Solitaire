/**
 * Created by mitchell on 7/16/17.
 */
public class Node {

    private int index;
    private Node next;

    public Node(int i) {
        this.setIndex(i);
        this.setNext(null);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
