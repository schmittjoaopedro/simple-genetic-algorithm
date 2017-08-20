package evolutionary.algorithms.chapter2.exe3_12;

/**
 * Created by root on 20/08/17.
 */
public class Exe312 {

    public static double upperBound = 10.0;

    public static double lowerBound = -10.0;

    public static int popSize = 20;

    public static void main(String[] args) {
        double population[] = new double[popSize];
        for(int i = 0; i < popSize; i++) {
            population[i] = lowerBound + Math.random() * (upperBound - lowerBound);
        }
        double mean = mean(population);
        double sd = std(population);
        for(int i = 0; i < popSize; i++) {
            double fitness = population[i];
            for (double c = 1.0; c < 6.0; c++) {
                double fitnessScaled = fitness - (mean - c * sd);
                System.out.format("%d=%.1f -> %.1f\t", (int) c, fitness, fitnessScaled);
            }
            System.out.println();
        }
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
