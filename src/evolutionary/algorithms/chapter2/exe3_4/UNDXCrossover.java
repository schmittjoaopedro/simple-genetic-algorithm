package evolutionary.algorithms.chapter2.exe3_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by root on 17/08/17.
 */
public class UNDXCrossover {

    private static Random random = new Random();

    private static final double zeta = 0.5;

    private static final double eta = 0.35;

    public static void crossover(double[] p1, double[] p2, double[] c1, double[] c2) {
        UNDXCrossover.crossover(p1, p2, c1);
        UNDXCrossover.crossover(p1, p2, c2);
    }

    public static void crossover(double[] p1, double[] p2, double[] result) {

        int k = 2; //Two parents
        int n = p1.length; //Number of variables
        double[][] x = new double[k][n];

        for(int i = 0; i < n; i++) {
            x[0][i] = p1[i];
            x[1][i] = p2[i];
        }

        double[] g = Vector.mean(x);
        List<double[]> e_zeta = new ArrayList<double[]>();
        List<double[]> e_eta = new ArrayList<double[]>();

        // basis vectors defined by parents
        for (int i = 0; i < k - 1; i++) {
            double[] d = Vector.subtract(x[i], g);
            if (!Vector.isZero(d)) {
                double dbar = Vector.magnitude(d);
                double[] e = Vector.orthogonalize(d, e_zeta);
                if (!Vector.isZero(e)) {
                    e_zeta.add(Vector.multiply(dbar, Vector.normalize(e)));
                }
            }
        }

        double D = Vector.magnitude(Vector.subtract(x[k - 1], g));

        // create the remaining basis vectors
        for (int i = 0; i < n - e_zeta.size(); i++) {
            double[] d = randomVector(n);
            if (!Vector.isZero(d)) {
                double[] e = Vector.orthogonalize(d, e_eta);
                if (!Vector.isZero(e)) {
                    e_eta.add(Vector.multiply(D, Vector.normalize(e)));
                }
            }
        }

        // construct the offspring
        double[] variables = g;
        for (int i = 0; i < e_zeta.size(); i++) {
            variables = Vector.add(variables, Vector.multiply(nextGaussian(0.0, zeta), e_zeta.get(i)));
        }
        for (int i = 0; i < e_eta.size(); i++) {
            variables = Vector.add(variables, Vector.multiply(nextGaussian(0.0, eta / Math.sqrt(n)), e_eta.get(i)));
        }

        for (int j = 0; j < n; j++) {
            double value = variables[j];
            if (value < Settings.lowerBound) {
                value = Settings.lowerBound;
            } else if (value > Settings.upperBound) {
                value = Settings.upperBound;
            }
            result[j] = value;
        }

    }

    private static double[] randomVector(int n) {
        double[] v = new double[n];
        for (int i = 0; i < n; i++) {
            v[i] = random.nextGaussian();
        }
        return v;
    }

    public static double nextGaussian(double mean, double stdev) {
        return stdev * random.nextGaussian() + mean;
    }

}
