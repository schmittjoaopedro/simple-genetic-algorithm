package evolutionary.algorithms.chapter2.exe3_4;

/**
 * Created by root on 17/08/17.
 */
public class Settings {

    /**
     * The sphere model from Eiben and Smith
     * Domain:  [-5,5]
     * f(x):    ∑x^2
     *
     * Ackley’s function from Bäck
     * Domain:  [-20,30]
     * f(x):    -20*exp(-0.2*sqrt((1/n)*∑x^2))-exp((1/n)*∑cos(2*PI*x))+20+e
     */

    public static final double lowerBound = -20;

    public static final double upperBound = 30;

    public static double getFitness(double[] individual) {
        double n = (double) individual.length;
        return -20.0 * Math.exp(-0.2 * Math.sqrt((1.0/n) * sumSquare(individual))) - Math.exp((1.0/n) * sumCosine(individual)) + 20.0 + Math.E;
    }

    public static double sumSquare(double[] individual) {
        double best = 0.0;
        for(int i = 0; i < individual.length; i++) {
            best += Math.pow(individual[i], 2);
        }
        return best;
    }

    public static double sumCosine(double[] individual) {
        double best = 0.0;
        for(int i = 0; i < individual.length; i++) {
            best += Math.cos(2*Math.PI*individual[i]);
        }
        return best;
    }

}
