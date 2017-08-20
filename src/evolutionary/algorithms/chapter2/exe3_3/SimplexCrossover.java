package evolutionary.algorithms.chapter2.exe3_3;

import java.util.Random;

public class SimplexCrossover {

    public static void main(String[] args) {
        double parents[][] = {
            { 2.2, 50.0 },
            { 3.5, 71.1 },
            { 1.0, 55.5 }
        };

        double offspring[][] = evolve(parents, 1);

        for(int i = 0; i < offspring.length; i++) {
            for(int j = 0; j < offspring[i].length; j++) {
                System.out.print(offspring[i][j] + " - ");
            }
            System.out.println();
        }
    }

    public static double[][] evolve(double[][] parents, double epsilon) {
        int n = parents.length; // Number of parents
        int m = parents[0].length; // Number of genes
        double[] G = new double[m]; // Center of mass
        double[][] x = new double[n][m]; // expanded simplex vertices
        double[] r = new double[n - 1]; // random numbers
        double[][] C = new double[n][m]; // random offset vectors
        double[][] offspring = new double[n][m];
        // compute center of mass
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                G[j] += parents[i][j];
            }
        }
        for(int j = 0; j < m; j++) {
            G[j] /= n;
        }
        // compute simplex vertices expanded by epsilon
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                x[i][j] = G[j] + epsilon * (parents[i][j] - G[j]);
            }
        }
        // generate offspring
        for(int k = 0; k < n; k++) {
            double[] solution = new double[m];
            for(int i = 0; i < n - 1; i++) {
                r[i] = Math.pow(new Random().nextDouble(), 1.0 / (i + 1.0));
            }
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < m; j++) {
                    if(i == 0) {
                        C[i][j] = 0;
                    } else {
                        C[i][j] = r[i - 1] * (x[i - 1][j] - x[i][j] + C[i - 1][j]);
                    }
                }
            }
            for(int j = 0; j < m; j++) {
                double value = x[n - 1][j] + C[n - 1][j];
                solution[j] = value;
            }
            offspring[k] = solution;
        }

        return offspring;
    }

}
