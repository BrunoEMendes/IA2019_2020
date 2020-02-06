import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class IDAStar {
    static class State {
        private Ilayout layout;
        private State father;
        private Ilayout goal;
        private double g;
        private double h;
        private double f;
        public int depth = 0; //TESTING

        public State(Ilayout l, State n, Ilayout goal) {
            layout = l;
            father = n;
            this.goal = goal;
            if (father != null) {
                g = father.g + l.getG();
                depth = father.depth + 1; //TESTING
            }
            else
                g = 0.0;
            this.h = l.getH(goal);
            f = g + h;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public double getF() {
            return f;
        }

        public double getH()
        {
            return h;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this)
                return true;
            if (other == null)
                return false;
            if (getClass() != other.getClass())
                return false;
            State that = (State) other;
            boolean result = g <= that.g + 1e-4 && g >= that.g - 1e-4;
            result = result && father.equals(that.father);
            result = result && layout.equals(that.layout);

            return result;
        }
    }

    protected Stack<State> abertos;
    private List<State> fechados;
    private State actual;
    private Ilayout objective;

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n, n.goal);
                sucs.add(nn);
            }
        }

        return sucs;
    }

    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        State first_State = new State(s, null, goal);
        double f_limit = first_State.getF();
        boolean found = false;
        while(!found)
        {
            double f_next = Double.POSITIVE_INFINITY;
            boolean stuck = false;
            abertos = new Stack<>();
            fechados = new ArrayList<>();
            abertos.add(new State(s, null, goal));
            List<State> sucs;
            while(!stuck)
            {
                if(abertos.isEmpty())
                {
                    stuck = true;
                    f_limit = f_next;
                    break;
                }
                actual = abertos.pop();
                if(actual.getF()  > f_limit && actual.getF() < f_next)
                    f_next = actual.getH();
                if (actual.layout.isGoal(goal)){
                    stuck = true;
                    found = true;
                    break;
                }
                else {
                    sucs = sucessores(actual);
                    fechados.add(actual);

                    for(State suc : sucs){
                        if(!fechados.contains(suc))
                        {
                            if(suc.getF()  > f_limit)
                            {
                                fechados.add(suc);
                                if(suc.getF() < f_next)
                                    f_next = suc.getF();
                            }
                            else
                                abertos.push(suc);
                        }
                    }
                }

            }
        }
        List<State> cfg = new LinkedList<State>();

        while (actual != null) {
            cfg.add(actual);
            actual = actual.father;
        }

        Collections.reverse(cfg);

        return cfg.iterator();

    }
}