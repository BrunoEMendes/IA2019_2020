import java.util.LinkedList;
import java.util.List;

public class Container implements Ilayout {
    private static final int dim = 9;
    private int[] pallets;
    private double g;

    public Container(String str) throws IllegalStateException {
        if (str.length() != dim) {
            throw new IllegalStateException("Invalid arg in Container constructor");
        }
        pallets = new int[dim];
        g = 0.0;
        for (int i = 0; i < dim; ++i) {
            pallets[i] = Character.getNumericValue(str.charAt(i));
        }
    }

    private Container(int[] fatherPallets, int pos1, int pos2) {
        this.pallets = fatherPallets.clone();
        this.pallets[pos1] = fatherPallets[pos2];
        this.pallets[pos2] = fatherPallets[pos1];

        if (isPair(pallets[pos1]) && isPair(pallets[pos2]))
            g = 20.0;
        else if (!isPair(pallets[pos1]) && !isPair(pallets[pos2]))
            g = 1.0;
        else
            g = 5.0;
    }

    private boolean isPair(int x) {
        return x % 2 == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; ++i)
            sb.append(pallets[i]);

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Container that = (Container) other;

        boolean result = true;
        for (int i = 0; i < dim; ++i) {
            result = result && (this.pallets[i] == that.pallets[i]);
        }

        return result;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> resultList = new LinkedList<Ilayout>();
        for (int i = 0; i < dim-1; ++i) {
            for (int j = i+1; j < dim; ++j) {
                resultList.add(new Container(pallets, i, j));
            }
        }

        return resultList;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public double getG() {
        return g;
    }

    @Override
    public double getH(Ilayout l) throws IllegalArgumentException {
        if (getClass() != l.getClass())
            throw new IllegalArgumentException("Argument doesn't have the same class as the object");

        Container goal = (Container) l;
        double h = 0.0;

        for (int i = 0; i < dim; ++i) {
            if (pallets[i] != goal.pallets[i]) {
                if (isPair(pallets[i]))
                    h += 5.0;
                else
                    h += 1.0;
            }
        }

        return h;
    }

}