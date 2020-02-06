import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

class Chromossome
{
    private String gene;
    private int size;
    private double fitness;
    private Random rng;

    public Chromossome(final int n, final Random rng)
    {
        double d;
        this.rng = rng;
        final StringBuilder sb = new StringBuilder();
        for (int i=0; i< n; i++) {
            d = rng.nextDouble();
            if(d < 0.5)
                sb.append(0);
            else
                sb.append(1);
            this.gene = sb.toString();
            this.fitness = one_max();
        }

    }

    public Chromossome(String gene, double fitness, Random rng)
    {
        this.rng = rng;
        this.gene = gene;
        this.fitness = fitness;
    }

    public int one_max()
    {
        int res = 0;
        for(int i = 0; i < gene.length(); i++)
            if(gene.charAt(i) == '1')
                res++;
        return res;
    }

    public void set_fitness(double i)
    {
        this.fitness = i;
    }

    public double get_fitness()
    {
        return this.fitness;
    }

    public Chromossome(String str, Random rng)
    {
        this.rng = rng;
        this.size = str.length();
        this.gene = str;
        this.fitness = one_max();
    }


    public void append_gene(final String s,final int pos)
    {

    }

    public String get_gene()
    {
        return this.gene;
    }

    public int get_gene_as_Integer()
    {
        return Integer.parseInt(this.gene, 2);
    }

    public int get_size()
    {
        return this.gene.length();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(gene);
        return sb.toString();
    }

    public Chromossome one_point_crossover(Chromossome x, int point)
    {
        return new Chromossome(this.gene.substring(0, point) + x.gene.substring(point, x.gene.length()), this.rng);
    }

    public int fitness_square()
    {
        return (int) Math.pow(this.get_gene_as_Integer(), 2);
    }

    public void exchange(int pos, char s)
    {
        StringBuilder sb = new StringBuilder(this.gene);
        sb.setCharAt(pos, s);
        this.gene = sb.toString();
    }

    public void change(int pos, Chromossome b)
    {
        if(b.get_gene().charAt(pos) != this.gene.charAt(pos))
        {
            char temp = b.get_gene().charAt(pos);
            b.exchange(pos, this.gene.charAt(pos));
            this.exchange(pos, temp);
        }
    }

    public void update_fitness()
    {
        this.fitness = one_max();
    }

    public Chromossome(String s)
    {
        this.gene = s;
        this.fitness = one_max();
    }


    public Chromossome bit_flip_mutation(double p)
    {
        StringBuilder sb = new StringBuilder(gene);
        double d = 0.0;
        String s ="";
        // System.out.println("before");
        // System.out.println(gene);
        for(int i = 0; i < gene.length(); i++)
        {
            d = rng.nextDouble();
            // System.out.println(d);
            if(d < p )
            {
                // System.out.println(gene + " " + i + " " + d);
                s += gene.charAt(i) == '0' ? '1' : '0';
            }
            else
                s+= gene.charAt(i);
        }
        // System.out.println("after");
        // System.out.println(s);
        // System.out.println(s);
        return new Chromossome(s, rng);
    }
}