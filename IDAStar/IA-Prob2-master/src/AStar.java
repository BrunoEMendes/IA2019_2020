import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class AStar {
    static class State {
        private Ilayout layout;
        private State father;
        private Ilayout goal;
        private double g;
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
            f = g + l.getH(goal);
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

    protected Queue<State> abertos;
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
        objective = goal;
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getF() - s2.getF()));
        fechados = new ArrayList<>();
        abertos.add(new State(s, null, goal));
        List<State> sucs;
        boolean found = false;
        while (!found) {
            if (abertos.isEmpty())
                return null;

            actual = abertos.poll();

            if (actual.layout.isGoal(objective))
                found = true;
            else {
                sucs = sucessores(actual);

                fechados.add(actual);

                for (State suc : sucs)
                    if (!fechados.contains(suc))
                        abertos.add(suc);
            }
            System.out.println(actual.depth);
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