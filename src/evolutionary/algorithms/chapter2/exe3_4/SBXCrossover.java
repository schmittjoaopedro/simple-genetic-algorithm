package evolutionary.algorithms.chapter2.exe3_4;

/**
 * Created by root on 17/08/17.
 */
public class SBXCrossover {

    private static double n = 2.0; //Control parameter normally between [2,5]

    public static void crossover(double[] p1, double[] p2, double[] c1, double[] c2) {
        for(int i = 0; i < p1.length; i++) {
            if(Math.random() < 0.5) {
                double u = Math.random();
                double beta;
                if(u <= 0.5) {
                    beta = Math.pow(2.0 * u, 1.0 / (n + 1.0));
                } else {
                    beta = Math.pow(2.0 * (1.0 - u), -1.0 / (n + 1));
                }
                c1[i] = 0.5 * (p1[i] - p2[i]) + 0.5 * beta * (p1[i] - p2[i]);
                c2[i] = 0.5 * (p1[i] - p2[i]) + 0.5 * beta * (p2[i] - p1[i]);
            } else {
                c1[i] = p1[i];
                c2[i] = p2[i];
            }
        }
    }


}
