package evolutionary.algorithms.chapter2.exe3_5;

import evolutionary.algorithms.chapter2.Settings;
import evolutionary.algorithms.chapter2.exe3_4.SBXCrossover;

import java.util.Locale;

/**
 * Created by root on 17/08/17.
 */
public class GA {

    private static int maxSamples = 100;

    private static int maxGen = 1000;

    public static void main(String[] args) {

        double results[] = new double[maxSamples];
        for (int t = 0; t < maxSamples; t++) {
            double[][] population = new double[50][3]; //10 individual by 3 variables
            randomInit(population);
            double fittest = getFittest(population);
            for(int i = 0; i < maxGen; i++) {
                if(getFittest(population) < fittest) {
                    fittest = getFittest(population);
                }
                //System.out.printf(Locale.US,"%.15f,\n", getFittest(population));
                double[][] newPopulation = new double[population.length][population[0].length];
                int popOffset = population.length / 2;
                for(int p = 0; p < popOffset; p++) {
                    double[] p1 = selectIndividual(population);
                    double[] p2 = selectIndividual(population);
                    double[] c1 = new double[p1.length];
                    double[] c2 = new double[p2.length];
                    SBXCrossover.crossover(p1, p2, c1, c2);
                    newPopulation[p] = c1;
                    newPopulation[popOffset + p] = c2;
                }
                SelAdaptativeNonUniformMutation.mutate(newPopulation, maxGen, i);
                //NonUniformMutationAnnealing.mutate(newPopulation);
                //UniformMutation.mutate(newPopulation);
                population = newPopulation;
            }
            results[t] = fittest;
            System.out.format(Locale.US, "Fittest %.15f\n", fittest);
        }

        System.out.format(Locale.US, "Mean %.5f\tStd %.5f\n", mean(results), std(results));
    }

    /**
     *
     * Interactions  |      Uniform      |     NonUniform    |    SelfAdaptive   |
     *               | mean    | sd      | mean    | sd      | mean    | sd      |
     * 100           | 0.70100 | 0.86258 | 1.00383 | 1.03089 | 0.31933 | 0.69099 |
     * 500           | 0.05113 | 0.04521 | 0.16273 | 0.32445 | 0.00280 | 0.00283 |
     * 1000          | 0.02557 | 0.02603 | 0.05385 | 0.05465 | 0.00047 | 0.00048 |
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

    public static double mean(double[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }

    public static double std(double a[]) {
        double sum = 0;
        double sq_sum = 0;
        for(int i = 0; i < a.length; ++i) {
            sum += a[i];
            sq_sum += a[i] * a[i];
        }
        double mean = sum / a.length;
        double variance = sq_sum / a.length - mean * mean;
        return Math.sqrt(variance);
    }
}
