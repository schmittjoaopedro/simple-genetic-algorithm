package genetic.algorithm.ant;

/**
 * Created by root on 16/08/17.
 */
public class App {

    public static void main(String[] args) {
        AntColonyOptimization ant = new AntColonyOptimization(50);
        int[] solution = ant.solve();
    }

}
