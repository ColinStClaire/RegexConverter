
/**
 * Created by colinstclaire on 9/23/16.
 */
public class Transition {
    public int state1;
    public int state2;
    public char symbol;
    public boolean isFinalState;

    public Transition(int start, int next, boolean isFinal, char sym) {
        state1 = start;
        state2 = next;
        isFinalState = isFinal;
        symbol = sym;
    }

}
