import java.util.ArrayList;

/**
 * Created by colinstclaire on 9/26/16.
 */
public class NFA {
    public int startState;
    public int finalState;
    public ArrayList<Transition> transitions;

    public NFA(int start, int finalState) {
        startState = start;
        this.finalState = finalState;
        transitions = new ArrayList<>();

    }

    public static NFA concatenate(NFA nfa1, NFA nfa2) {
        Transition newT = new Transition(nfa1.finalState, nfa2.startState, false, RegexConverter.EPSILON);
        NFA conNFA = new NFA(nfa1.startState, nfa2.finalState);
        conNFA.transitions.add(newT);
        return conNFA;
    }

    public static NFA union(NFA nfa1, NFA nfa2) {
        NFA newNFA;

        return null;
    }

    public static NFA star(NFA nfa1) {
        return null;
    }

}
