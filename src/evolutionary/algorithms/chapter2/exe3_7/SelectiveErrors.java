package evolutionary.algorithms.chapter2.exe3_7;

import java.util.Arrays;

/**
 * Created by root on 19/08/17.
 */
public class SelectiveErrors {


    public static void main(String[] args) {
        double[] population = { 20, 15, 2, 1, 7 };
        calculateSelectiveError(population, roulletSelection(population, 100));
        calculateSelectiveError(population, stochasticUniversalSampling(population, 100));
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



    public static double[] roulletSelection(double[] population, int selectionSize) {
        double[] cumulativeFitness = new double[population.length];
        cumulativeFitness[0] = population[0];
        for (int i = 1; i < population.length; i++) {
            cumulativeFitness[i] = cumulativeFitness[i - 1] + population[i];
        }
        double[] selection = new double[selectionSize];
        int selectionIndex = 0;
        for (int i = 0; i < selectionSize; i++) {
            double randomFitness = Math.random() * cumulativeFitness[cumulativeFitness.length - 1];
            int index = Arrays.binarySearch(cumulativeFitness, randomFitness);
            if (index < 0) {
                index = Math.abs(index + 1);
            }
            selection[selectionIndex++] = population[index];
        }
        return selection;
    }



    public static double[] stochasticUniversalSampling(double[] population, int selectionSize) {
        double aggregateFitness = 0;
        for (Double candidate : population) {
            aggregateFitness += candidate;
        }
        double[] selection = new double[selectionSize];
        int selectionIndex = 0;
        double startOffset = Math.random();
        double cumulativeExpectation = 0;
        int index = 0;
        for (Double candidate : population) {
            cumulativeExpectation += candidate / aggregateFitness * selectionSize;
            while (cumulativeExpectation > startOffset + index) {
                selection[selectionIndex++] = candidate;
                index++;
            }
        }
        return selection;
    }

}
