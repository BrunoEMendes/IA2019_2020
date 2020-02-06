import java.util.Iterator;
import java.util.Scanner;

import tests.Timer;

public class Main {
    public static void solvePuzzle(Container start, Container goal) {
        AStar s = new AStar();
        Iterator<AStar.State> it = s.solve(start, goal);
        if (it == null)
            System.out.println("no solution was found");
        else {
            while (it.hasNext()) {
                AStar.State i = it.next();
                // System.out.println(i);
                if (!it.hasNext())
                    System.out.println((int) i.getG());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Timer timer = new Timer(); //TESTING

        solvePuzzle(new Container(sc.next()), new Container(sc.next()));

        double time = timer.elapsedSeconds(); //TESTING
        System.out.println(time); //TESTING

        sc.close();
    }

}