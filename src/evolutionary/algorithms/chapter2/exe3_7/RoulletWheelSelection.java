package evolutionary.algorithms.chapter2.exe3_7;

import java.util.Arrays;

public class RoulletWheelSelection {

    public static double[] select(double[] population, int selectionSize) {
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

}
