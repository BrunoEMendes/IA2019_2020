import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.naming.ldap.Rdn;

public class AG
{
    private int size;
    static Random rng = new Random(0);
    private double pc;
    private double pm;
    private int s;
    private Population pp;

    public AG(int total, int  len, int s, double pc, double pm)
    {
        this.pp = new Population(total, len, rng);
        this.s = s;
        this.pc = pc;
        this.pm = pm;
    }

    public void next_generation()
    {
        // System.out.println("before tournment");
        // System.out.println(pp);
        pp.tournment_selection_without_replacement(s);
        // System.out.println("selection");
        // System.out.println(pp);
        // System.out.println("crossover");
        pp.cross_over(pc);
        // System.out.println(pp);
        pp.mutate(pm);
        // System.out.println("mutation");
        // System.out.println(pp);
    }

    @Override
    public String toString()
    {
        pp.update_fitness_population();
        return pp.get_info();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // test_f(sc);
        // test_g(sc);
        // test_h(sc);
        // test_i(sc);
        // test_j(sc);
        // test_k(sc);
        // test_l(sc);
        // test_m(sc);
        // test_n(sc);
        // test_o(sc);
        // test_p(sc);
        // test_q(sc);
        test_r(sc);
        sc.close();
    }

    public static void test_r(Scanner sc)
    {
        int size = sc.nextInt();
        int len = sc.nextInt();
        int s = sc.nextInt();
        double pm = Double.parseDouble(sc.next());
        double pc = Double.parseDouble(sc.next());
        int num_gens = sc.nextInt();
        AG ag = new AG(size, len, s, pc, pm);
        for(int i = 0; i < num_gens + 1; i++)
        {
            System.out.println(i + ": " + ag);
            ag.next_generation();
        }
        // System.out.println(num_gens + ": "+ ag);
    }

    public static void test_q(Scanner sc)
    {
        int size = sc.nextInt();
        int len = sc.nextInt();
        int s = sc.nextInt();
        double pm = Double.parseDouble(sc.next());
        double pc = Double.parseDouble(sc.next());
        AG ag = new AG(size, len, s, pc, pm);
        System.out.println("0: " + ag);
        ag.next_generation();
        System.out.println("1: " + ag);
    }

    public static void test_p(Scanner sc)
    {
        int p =  Integer.parseInt(sc.nextLine());
        Population pp = new Population(AG.rng);
        while(sc.hasNext())
            pp.add_chromossome(new Chromossome(sc.next(), Double.parseDouble(sc.next()), AG.rng));
        pp.tournment_selection_without_replacement(p);
        System.out.println(pp);

    }

    public static void test_o(Scanner sc)
    {
        int p =  Integer.parseInt(sc.nextLine());
        Population pp = new Population(AG.rng);
        for(int i = 0; i < p; i++)
            pp.add_chromossome(new Chromossome(i+"", AG.rng));
        pp.permutation();
        System.out.println(pp);

    }

    public static void test_n(Scanner sc)
    {
        double p =  Double.parseDouble(sc.nextLine());
        Chromossome cr= new Chromossome(sc.nextLine(), AG.rng);
        cr.bit_flip_mutation(p);
        System.out.println(cr);
    }

    public static void test_m(Scanner sc)
    {

        Population pp = new Population(AG.rng);
        pp.add_chromossome(new Chromossome(sc.nextLine(), AG.rng));
        pp.add_chromossome(new Chromossome(sc.nextLine(), AG.rng));
        pp.uniform_crossover();
        pp.update_fitness_population();
        System.out.println(pp);
    }

    public static void test_l(Scanner sc)
    {

        // Population pp = new Population(AG.rng);
        // pp.add_chromossome(new Chromossome(sc.nextLine(), AG.rng));
        // pp.add_chromossome(new Chromossome(sc.nextLine(), AG.rng));
        // pp.cross_over();
        // System.out.println(pp);
    }

    public static void test_k(Scanner sc)
    {
        List <Chromossome> ll = new ArrayList<>();
        while(sc.hasNext())
            ll.add(new Chromossome(sc.next(), Double.parseDouble(sc.next()), AG.rng));
        Population pp = new Population(ll, AG.rng);
        pp.stochastic_universal_sampling();
        System.out.println(pp);
    }

    public static void test_j(Scanner sc)
    {
        List <Chromossome> ll = new ArrayList<>();
        while(sc.hasNext())
            ll.add(new Chromossome(sc.next(), Double.parseDouble(sc.next()), AG.rng));
        Population pp = new Population(ll, AG.rng);
        pp.roulette_wheel_selection();
        System.out.println(pp);
    }

    public static void test_i(Scanner sc)
    {
        Population pp = new Population(sc.nextInt(), sc.nextInt(), AG.rng);
        List<Double> ll = new ArrayList<>();
        for(int i = 0; i < pp.get_size(); i++)
            ll.add(sc.nextDouble());
        pp.set_fitness_population(ll);
        pp.tournment();
        System.out.println(pp);
    }

    public static void test_h(Scanner sc)
    {
        String s = sc.nextLine();
        Chromossome cr = new Chromossome(s, AG.rng);
        System.out.println(cr.fitness_square());
    }

    public static void test_g(Scanner sc)
    {
        String s = sc.nextLine();
        Chromossome cr = new Chromossome(s, AG.rng);
        System.out.println(cr.one_max());
    }

    public static void test_f(Scanner sc)
    {
        int n= sc.nextInt();
        int d = sc.nextInt();
        Population c = new Population(n, d, AG.rng);
        System.out.print(c);
    }

}