import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

class Population
{
    private List<Chromossome> ll = new ArrayList<>();
    private double min_fitness;
    private double max_fitness;
    private Random rng;

    public Population(int n, int length, Random rng)
    {
        for(int i = 0; i < n; i++)
        {
            ll.add(new Chromossome(length, rng));
        }
        this.rng = rng;
    }

    public Population(List<Chromossome> pp, Random rng)
    {
        this.ll = pp;
        this.rng = rng;
    }

    public int get_size()
    {
        return ll.size();
    }

    public void set_fitness_population(List<Double> fitness)
    {
        for(int i = 0; i < ll.size(); i++)
        {
            ll.get(i).set_fitness(fitness.get(i));
            this.min_fitness = min_fitness > fitness.get(i) ? fitness.get(i) : min_fitness;
            this.max_fitness = max_fitness < fitness.get(i) ? fitness.get(i) : max_fitness;
        }
    }
    public double population_fitness()
    {
        return ll.stream().mapToDouble(f -> f.get_fitness()).sum();
    }

    public double max_fitness()
    {
        return max_fitness;
    }

    public double min_fitness()
    {
        return min_fitness;
    }

    public Chromossome get_chromossome(int i)
    {
        return ll.get(i);
    }

    public double get_chromossome_fitness(int i)
    {
        return ll.get(i).get_fitness();
    }

    public Population(Random rng)
    {
        this.rng = rng;
    }

    public void add_chromossome(Chromossome s)
    {
        ll.add(s);
    }

    public List<Chromossome> get_list()
    {
        return ll;
    }

    @Override
    public String toString()
    {
        return ll.stream().map(c -> c.toString()).collect(Collectors.joining("\n"));
    }

    @SuppressWarnings("unchecked")
    public void sort_fitness(Comparator c)
    {
        ll.sort(c);
    }

    public String get_info()
    {   DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", unusualSymbols);

        return "" + df.format(ll.stream().mapToDouble(Chromossome::get_fitness).max().orElse(0)) + " " + df.format(ll.stream().mapToDouble(Chromossome::get_fitness).average().orElse(0)) + " " + df.format(ll.stream().mapToDouble(Chromossome::get_fitness).min().orElse(0));
    }

    // public void cross_over()
    // {
    //     List<Chromossome> res = new ArrayList<>();
    //     int crossover = 0;
    //     for(int i = 0; i < this.get_size(); i += 2)
    //     {
    //         Chromossome a = ll.get(i);
    //         Chromossome b = ll.get(i + 1);
    //         crossover = (int)Math.ceil(rng.nextDouble() * a.get_gene().length());
    //         res.add(a.one_point_crossover(b, crossover));
    //         res.add(b.one_point_crossover(a, crossover));
    //     }
    //     this.ll = res;
    // }

    public void mutate(double p)
    {
        List<Chromossome> res = new ArrayList<>();
        for(Chromossome c : ll)
            res.add(c.bit_flip_mutation(p));
        this.ll = res;

    }

    public void cross_over(double dc)
    {
        List<Chromossome> res = new ArrayList<>();
        int crossover = 0;
        double d = 0.0;
        for(int i = 0; i < this.get_size(); i += 2)
        {
            Chromossome a = ll.get(i);
            Chromossome b = ll.get(i + 1);
            d = rng.nextDouble();
            if(d < dc)
            {
                crossover = 1 + (int)Math.round(rng.nextDouble() * (a.get_gene().length() - 2));
                res.add(a.one_point_crossover(b, crossover));
                res.add(b.one_point_crossover(a, crossover));
            }
            else
            {
                res.add(a);
                res.add(b);
            }
        }
        this.ll = res;
    }

    public void uniform_crossover()
    {
        Chromossome a = ll.get(0);
        Chromossome b = ll.get(1);
        double d = 0.0;
        for(int i = 0; i < a.get_gene().length(); i++)
        {
            d = rng.nextDouble();
            if(d < 0.5)
                a.change(i, b);
        }
    }

    public void update_fitness_population()
    {
        ll.forEach(x -> x.update_fitness());
    }

    public void permutation()
    {
        int r;
        double d = 0;
        for(int i = 0; i < ll.size() - 1; i++)
        {
            d = rng.nextDouble();
            r = (int)(i + Math.round(d * (ll.size() - 1  - i )));
            Chromossome temp = ll.get(r);
            ll.set(r, ll.get(i));
            ll.set(i, temp);
        }
    }

    public void tournment()
    {
        int fst, snd;
        List <Chromossome> res = new ArrayList<>();
        for(int i = 0; i < this.ll.size() ; i++)
        {
            fst = (int)(0 + Math.round(rng.nextDouble() * ( this.ll.size() - 1)));
            snd = (int)(0 + Math.round(rng.nextDouble() * ( this.ll.size() - 1)));
            res.add(ll.get(fst).get_fitness() >= ll.get(snd).get_fitness() ? ll.get(fst) : ll.get(snd));
        }
        this.ll = res;
    }

    public void tournment_selection_without_replacement(int s)
    {
        List<Chromossome> winners = new ArrayList<>();
        int fst, snd;
        Population permuted;
        List <Chromossome> temp;
        for(int j = 0; j < s; j++)
        {
            permuted = new Population(this.get_list().stream().collect(Collectors.toList()), rng);
            permuted.permutation();
            for(int i = 0; i < permuted.get_size(); i += s)
            {
                temp = permuted.get_list().subList(i, i + s);
                Chromossome tempa = temp.stream().max(Comparator.comparing(Chromossome::get_fitness)).orElse(null);
                winners.add(tempa);
            }
        }
        this.ll = winners;
    }

    public void roulette_wheel_selection()
    {
        double d = 0.0;
        double total_fitness = this.population_fitness();
        List<Chromossome>  res = new ArrayList<>();
        ll.sort((x, y) ->(int) (y.get_fitness() - x.get_fitness()));

        for(int i = 0; i < this.get_size(); i++)
        {
            d = rng.nextDouble();

            double sum = 0;
            d *= total_fitness;
            for(int j = 0; j <  this.get_size(); j++)
            {
                if(sum + this.get_chromossome_fitness(j) > d )
                {
                    res.add(this.get_chromossome(j));
                    break;
                }
                else
                    sum += this.get_chromossome_fitness(j);
            }
        }
        res.sort((x, y) -> x.toString().compareTo(y.toString()));
        this.ll = res;
    }

    public void stochastic_universal_sampling()
    {
        double total_fitness = this.population_fitness();
        double start = rng.nextDouble() * (total_fitness / this.get_size());
        double distance = total_fitness / this.get_size();
        List<Double> ll  = new ArrayList<>();
        ll.add(start);
        for(int i = 1; i < this.get_size(); i++)
            ll.add(ll.get(i - 1) + distance);
        List <Chromossome> res = new ArrayList<>();
        for(int i = 0; i < ll.size(); i++)
        {
            double sum = 0;
            for(int j = 0; j <  this.get_size(); j++)
            {
                if(sum + this.get_chromossome_fitness(j) > ll.get(i) )
                {
                    res.add(this.get_chromossome(j));
                    break;
                }
                else
                    sum += this.get_chromossome_fitness(j);
            }
        }
        this.ll = res;
    }
}