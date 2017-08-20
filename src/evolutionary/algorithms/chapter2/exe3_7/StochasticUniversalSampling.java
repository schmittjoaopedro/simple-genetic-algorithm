package evolutionary.algorithms.chapter2.exe3_7;

public class StochasticUniversalSampling {

    public static double[] select(double[] population, int selectionSize) {
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
