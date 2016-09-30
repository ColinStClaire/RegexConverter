import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by colinstclaire on 9/22/16.
 */
public class RegexConverter {
    private int state;
    private Stack<NFA> states;
    private final char AND = '&';
    private final char OR = '|';
    private final char STAR = '*';
    public static final char EPSILON = 'E';

    public RegexConverter() {
        states = new Stack<>();
    }

    public void convert(String regex) {
        state = 0;
        char c;
        NFA nfa1;
        NFA nfa2;
        NFA newNFA = null;
        Transition t = null;
        for (int i = 0; i < regex.length(); i++) {
            c = regex.charAt(i);
            if (c == AND) {
                nfa2 = states.pop();
                nfa1 = states.pop();
                states.push(NFA.concatenate(nfa1, nfa2, state++));
            } else if (c == OR) {
                nfa2 = states.pop();
                nfa1 = states.pop();
                states.push(NFA.union(nfa1, nfa2, state++));
            } else if (c == STAR) {
                nfa1 = states.pop();
                states.push(NFA.star(nfa1, state++));
            } else {
                t = new Transition(state, state+1, true, c);
                newNFA = new NFA(state, state+1);
                newNFA.transitions.add(t);
                states.push(newNFA);
                state+=2;
            }
        }
        for (Transition trans : states.peek().transitions) {
            printNFA(trans);
        }
        return;
    }

    public void printNFA(Transition t) {
        String isFinal = (t.isFinalState) ? "F" : "";
        String n = "(" + t.state1 + ", " + t.symbol + ") -> " + t.state2 + isFinal;
        System.out.println(n);
    }

    public static void main(String[] args) {
        String filePath = "regex.txt";
        RegexConverter r = new RegexConverter();
        //r.convert("ab|*b&");
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
            String lineText = null;
            int len;
            int i = 0;
            while ((lineText = lineReader.readLine()) != null) {
                System.out.println("Regular expression " + i + ": " + lineText);
                r.convert(lineText);
                System.out.println();
                i++;
            }

            lineReader.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
