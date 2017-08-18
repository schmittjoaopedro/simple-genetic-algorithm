package evolutionary.algorithms.chapter2.exe3_4;

import java.util.Set;

/**
 * Created by root on 17/08/17.
 */
public class GA {

    public static void main(String[] args) {

        for (int t = 0; t < 100; t++) {
            double[][] population = new double[10][3]; //10 individual by 3 variables
            randomInit(population);
            double fittest = getFittest(population);
            for(int i = 0; i < 5000; i++) {
                if(getFittest(population) < fittest) {
                    fittest = getFittest(population);
                    //System.out.printf("%.15f\n", getFittest(population));
                }
                double[] p1 = selectIndividual(population);
                double[] p2 = selectIndividual(population);
                double[] c1 = new double[p1.length];
                double[] c2 = new double[p2.length];
                //SBXCrossover.crossover(p1, p2, c1, c2);
                UNDXCrossover.crossover(p1, p2, c1, c2);
                population[(int) Math.random() * population.length] = c1;
                population[(int) Math.random() * population.length] = c2;
            }
            System.out.printf("%.15f\n", fittest);
        }
    }

    /**
     *
     * Interactions         |       SBX     |      UNDX         |
     *                      | mean   | sd   |   mean   | sd     |
     * 200                  | 0.12   | 0.56 | 8.60042  | 2.70   |
     * 500                  | 0.0    | 0.0  | 6.79165  | 1.97   |
     * 5000                 | 0.0    | 0.0  | 4.52933  | 1.53   |
     */

    public static double[] selectIndividual(double[][] population) {
        double fSum = 0.0;
        double invSum = 0.0;
        double cumSum = 0.0;
        double invVal[] = new double[population.length];
        double prob[] = new double[population.length];
        for(int i = 0; i < population.length; i++) {
            fSum += Settings.getFitness(population[i]);
        }
        for(int i = 0; i < population.length; i++) {
            invVal[i] = fSum / Settings.getFitness(population[i]);
            invSum += invVal[i];
        }
        for(int i = 0; i < population.length; i++) {
            cumSum += invVal[i] / invSum;
            prob[i] = cumSum;
        }
        double rand = Math.random();
        for(int i = 0; i < population.length; i++) {
            if(rand < prob[i]) {
                return population[i];
            }
        }
        return population[(int) Math.random()];
    }

    public static void randomInit(double[][] population) {
        for(int i = 0; i < population.length; i++) {
            for(int j = 0; j < population[i].length; j++) {
                population[i][j] = Settings.lowerBound + Math.random() * (Settings.upperBound - Settings.lowerBound);
            }
        }
    }

    public static double getFittest(double[][] population) {
        double[] fittest = population[0];
        for(int i = 1; i < population.length; i++) {
            if(Settings.getFitness(fittest) > Settings.getFitness(population[i])) {
                fittest = population[i];
            }
        }
        return Settings.getFitness(fittest);
    }

}
