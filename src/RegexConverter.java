import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
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
                states.push(NFA.concatenate(nfa1, nfa2));
            } else if (c == OR) {
                nfa2 = states.pop();
                nfa1 = states.pop();
                states.push(NFA.union(nfa1, nfa2));
            } else if (c == STAR) {
                nfa1 = states.pop();
                states.push(NFA.star(nfa1));
            } else {
                t = new Transition(0, 1, true, c);
                newNFA = new NFA(0, 1);
                newNFA.transitions.add(t);
                states.push(newNFA);
            }
        }
        //printNFA(newNFA, t.symbol);
        return;
    }

    public void printNFA(NFA nfa, char transition) {
        String n = "(" + nfa.startState + ", " + transition + ") -> " + nfa.finalState;
        System.out.println(n);
    }

    public static void main(String[] args) {
        String filePath = "src/regex.txt";
        RegexConverter r = new RegexConverter();
        r.convert("ab|");
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
            String lineText = null;

            while ((lineText = lineReader.readLine()) != null) {
                //r.convert(lineText);
            }

            lineReader.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
