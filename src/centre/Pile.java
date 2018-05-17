package centre;

import dechet.Dechet;

import java.util.Stack;

public class Pile {

    private String type;
    private Stack<Dechet> pile;
    private int max;

    public Pile(String type) {
        this.type = type;
        this.pile = new Stack<>();
        this.max = 50;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Stack<Dechet> getPile() {
        return pile;
    }

    public void setPile(Stack<Dechet> pile) {
        this.pile = pile;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}