package evolutionary.algorithms.chapter2.exe3_7;

import java.util.Arrays;

/**
 * Created by root on 19/08/17.
 */
public class SelectiveErrors {


    public static void main(String[] args) {
        double[] population = { 20, 15, 2, 1, 7 };
        calculateSelectiveError(population, RoulletWheelSelection.select(population, 100));
        calculateSelectiveError(population, StochasticUniversalSampling.select(population, 100));
        calculateSelectiveError(population, TournamentSelection.select(population, 2));
    }

    public static void calculateSelectiveError(double[] population, double[] selection) {
        double[] probs = new double[population.length];
        double fSum = 0.0;
        for(int i = 0; i < population.length; i++) {
            fSum += population[i];
        }
        for(int i = 0; i < population.length; i++) {
            probs[i] = population[i] / fSum;
        }
        double countSelected[] = new double[population.length];
        for(int i = 0; i < selection.length; i++) {
            for(int s = 0; s < population.length; s++) {
                if(population[s] == selection[i]) {
                    countSelected[s]++;
                    break;
                }
            }
        }
        double[] probs2 = new double[population.length];
        for(int i = 0; i < probs2.length; i++) {
            probs2[i] = countSelected[i] / selection.length;
        }
        for(int i = 0; i < probs2.length; i++) {
            System.out.format("%.2f[%.4f]\t", population[i], Math.abs(probs[i] - probs2[i]));
        }
        System.out.println();
    }

}
