import java.util.LinkedList;

/**
 * Created by colinstclaire on 9/26/16.
 */
public class NFA {
    public int startState;
    public int finalState;
    public LinkedList<Transition> transitions;

    public NFA(int start, int finalState) {
        startState = start;
        this.finalState = finalState;
        transitions = new LinkedList<>();
    }

    public static NFA concatenate(NFA nfa1, NFA nfa2, int state) {
        NFA conNFA = new NFA(nfa1.startState, nfa2.finalState);
        Transition st1 = nfa1.transitions.getFirst();
        Transition mt1 = new Transition(nfa1.finalState, nfa2.startState, false, RegexConverter.EPSILON);
        Transition ft1 = new Transition(nfa2.startState, nfa2.finalState, true, nfa2.transitions.getFirst().symbol);
        //conNFA.transitions.add(st1);
        //conNFA.transitions.add(mt1);
        //conNFA.transitions.addAll(nfa1.transitions);
        //conNFA.transitions.addAll(nfa2.transitions);
        for (Transition t : nfa1.transitions) {
            nfa1.transitions.element().isFinalState = false;
            conNFA.transitions.add(t);
        }
        conNFA.transitions.add(mt1);
        for (Transition t : nfa2.transitions) {
            //nfa2.transitions.element().isFinalState = false;
            conNFA.transitions.add(t);
        }
        //conNFA.transitions.add(ft1);

        return conNFA;
    }

    public static NFA union(NFA nfa1, NFA nfa2, int state) {
        NFA unionNFA = new NFA(state, state+1);
        Transition st1 = new Transition(state, nfa1.startState, false, RegexConverter.EPSILON);
        Transition st2 =  new Transition(state, nfa2.startState, false, RegexConverter.EPSILON);
        Transition mt1 = nfa1.transitions.getFirst();
        Transition mt2 = nfa2.transitions.getFirst();
        Transition ft1 = new Transition(nfa1.finalState, state+1, true, RegexConverter.EPSILON);
        Transition ft2 = new Transition(nfa2.finalState, state+1, true, RegexConverter.EPSILON);
        unionNFA.transitions.add(st1);
        unionNFA.transitions.add(st2);
        //unionNFA.transitions.add(mt1);
        //unionNFA.transitions.add(mt2);
        //unionNFA.transitions.addAll(nfa1.transitions);
        //unionNFA.transitions.addAll(nfa2.transitions);
        for (Transition t : nfa1.transitions) {
            nfa1.transitions.element().isFinalState = false;
            unionNFA.transitions.add(t);
        }
        for (Transition t : nfa2.transitions) {
            nfa2.transitions.element().isFinalState = false;
            unionNFA.transitions.add(t);
        }
        unionNFA.transitions.add(ft1);
        unionNFA.transitions.add(ft2);

        return unionNFA;
    }

    public static NFA star(NFA nfa, int state) {
        NFA starNFA = new NFA(state, nfa.finalState);
        Transition st1 = new Transition(state, nfa.startState, false, RegexConverter.EPSILON);
        Transition mt1 = new Transition(nfa.startState, nfa.finalState, false, nfa.transitions.getFirst().symbol);
        Transition ft1 = new Transition(nfa.finalState, state, true, RegexConverter.EPSILON);
        starNFA.transitions.add(st1);
        for (Transition t : nfa.transitions) {
            nfa.transitions.element().isFinalState = false;
            starNFA.transitions.add(t);
        }
        //starNFA.transitions.add(mt1);
        starNFA.transitions.add(ft1);
        return starNFA;
    }

}
