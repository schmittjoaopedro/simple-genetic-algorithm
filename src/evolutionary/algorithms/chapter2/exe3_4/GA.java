package evolutionary.algorithms.chapter2.exe3_4;

import java.util.Locale;
import java.util.Set;

/**
 * Created by root on 17/08/17.
 */
public class GA {

    private static double pM = 0.015;

    public static void main(String[] args) {

        for (int t = 0; t < 100; t++) {
            double[][] population = new double[50][3]; //10 individual by 3 variables
            randomInit(population);
            double fittest = getFittest(population);
            for(int i = 0; i < 1000; i++) {
                if(getFittest(population) < fittest) {
                    fittest = getFittest(population);
                    //System.out.printf("%.15f\n", getFittest(population));
                }
                double[][] newPopulation = new double[population.length][population[0].length];
                int popOffset = population.length / 2;
                for(int p = 0; p < popOffset; p++) {
                    double[] p1 = selectIndividual(population);
                    double[] p2 = selectIndividual(population);
                    double[] c1 = new double[p1.length];
                    double[] c2 = new double[p2.length];
                    //SBXCrossover.crossover(p1, p2, c1, c2);
                    UNDXCrossover.crossover(p1, p2, c1, c2);
                    newPopulation[p] = c1;
                    newPopulation[popOffset + p] = c2;
                }
                mutate(newPopulation);
                population = newPopulation;
            }
            System.out.format(Locale.US, "%.15f,\n", fittest);
        }
    }

    /**
     *
     * Interactions         |          SBX          |          UNDX         |
     *                      | mean      | sd        | mean      | sd        |
     * 100                  | 0.6375408 | 0.9104150 | 1.3188870 | 1.2244720 |
     * 500                  | 0.0553351 | 0.0532705 | 0.1134227 | 0.1355401 |
     * 1000                 | 0.0252303 | 0.0249075 | 0.0732929 | 0.0628975 |
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

    public static void mutate(double[][] population) {
        for(int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                if(Math.random() < pM) {
                    population[i][j] = Settings.lowerBound + Math.random() * (Settings.upperBound - Settings.lowerBound);
                }
            }
        }
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
