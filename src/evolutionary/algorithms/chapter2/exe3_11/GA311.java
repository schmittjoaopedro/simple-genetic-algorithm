package evolutionary.algorithms.chapter2.exe3_11;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GA311 {

    private static int maxSamples = 100;

    private static int maxGen = 1000;

    private static double n = 5.0; //Control parameter normally between [2,5]

    private static double pM = 0.015;

    private static int b = 2; //Control speed, values -> [2,5]

    public static double lowerBound = -20;

    public static double upperBound = 30;

    public static double scaleAlpha = 0.0;

    public static double scaleBeta = 0.0;

    public static double alpha = 1.5;

    public static double beta = 0.5;

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
                calculateScaleCoefficientes(population);
                for(int p = 0; p < popOffset; p++) {
                    //double[] p1 = selectIndividualScaled(population);
                    //double[] p2 = selectIndividualScaled(population);
                    double[] p1 = selectIndividualRanked(population);
                    double[] p2 = selectIndividualRanked(population);
                    //double[] p1 = selectIndividual(population);
                    //double[] p2 = selectIndividual(population);
                    double[] c1 = new double[p1.length];
                    double[] c2 = new double[p2.length];
                    crossover(p1, p2, c1, c2);
                    newPopulation[p] = c1;
                    newPopulation[popOffset + p] = c2;
                }
                mutate(newPopulation, maxGen, i);
                population = newPopulation;
            }
            results[t] = fittest;
            System.out.format(Locale.US, "Fittest %.15f\n", fittest);
        }

        System.out.format(Locale.US, "Mean %.8f\tStd %.8f\n", mean(results), std(results));
    }

    /**
     *
     * Interactions  |         NotScaled     |         Scaled        |    RankedSelection    |
     *               | mean      | sd        | mean      | sd        | mean      | sd        |
     * 100           | 0.2027800 | 0.5092900 | 1.2065500 | 1.7914700 | 0.3872500 | 0.7040000 |
     * 500           | 0.0032800 | 0.0041300 | 0.0087800 | 0.0058000 | 0.0000200 | 0.0000200 |
     * 1000          | 0.0006000 | 0.0007200 | 0.0031000 | 0.0026900 | 0.0000047 | 0.0000052 |
     */

    public static double[] selectIndividual(double[][] population) {
        double fSum = 0.0;
        double invSum = 0.0;
        double cumSum = 0.0;
        double invVal[] = new double[population.length];
        double prob[] = new double[population.length];
        for(int i = 0; i < population.length; i++) {
            fSum += getFitness(population[i]);
        }
        for(int i = 0; i < population.length; i++) {
            invVal[i] = fSum / getFitness(population[i]);
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

    public static double[] selectIndividualScaled(double[][] population) {
        double fSum = 0.0;
        double invSum = 0.0;
        double cumSum = 0.0;
        double invVal[] = new double[population.length];
        double prob[] = new double[population.length];
        for(int i = 0; i < population.length; i++) {
            fSum += getFitnessScaled(population[i]);
        }
        for(int i = 0; i < population.length; i++) {
            invVal[i] = fSum / getFitnessScaled(population[i]);
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

    public static double[] selectIndividualRanked(double[][] population) {
        List<double[]> pop = Arrays.asList(population);
        double[] prob = new double[population.length];
        pop.sort((a, b) -> {
            double diff = getFitness(a) - getFitness(b);
            if(diff > 0) return 1;
            if(diff < 0) return -1;
            return 0;
        });
        for(double i = 0.0; i < population.length; i++) {
            prob[(int) i] = (alpha + (i / (population.length - 1.0)) * (beta - alpha)) / (double) population.length;
            if(i > 0.0) {
                prob[(int) i] += prob[(int) (i - 1.0)];
            }
        }
        double rand = Math.random();
        for(int i = 0; i < population.length; i++) {
            if(rand < prob[i]) {
                return pop.get(i);
            }
        }
        return population[(int) Math.random()];
    }

    public static void randomInit(double[][] population) {
        for(int i = 0; i < population.length; i++) {
            for(int j = 0; j < population[i].length; j++) {
                population[i][j] = lowerBound + Math.random() * (upperBound - lowerBound);
            }
        }
    }

    public static double getFittest(double[][] population) {
        double[] fittest = population[0];
        for(int i = 1; i < population.length; i++) {
            if(getFitness(fittest) > getFitness(population[i])) {
                fittest = population[i];
            }
        }
        return getFitness(fittest);
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

    public static void crossover(double[] p1, double[] p2, double[] c1, double[] c2) {
        for(int i = 0; i < p1.length; i++) {
            if(Math.random() < 0.9) {
                double u = Math.random();
                double beta = 1;
                if(u < 0.5) {
                    beta = Math.pow(2.0 * u, 1.0 / (n + 1.0));
                } else if (u > 0.5) {
                    beta = Math.pow(2.0 * (1.0 - u), -1.0 / (n + 1.0));
                }
                c1[i] = 0.5 * (p1[i] + p2[i]) + 0.5 * beta * (p1[i] - p2[i]);
                c2[i] = 0.5 * (p1[i] + p2[i]) + 0.5 * beta * (p2[i] - p1[i]);
            } else {
                c1[i] = p1[i];
                c2[i] = p2[i];
            }
        }
    }

    public static void mutate(double[][] population, int maxGen, int curGen) {
        for(int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                if(Math.random() < pM) {
                    double currentValue = population[i][j];
                    double newValue = 0.0;
                    double rand = Math.random();
                    if(rand >= 0.5) {
                        newValue = currentValue + delta(curGen, maxGen, upperBound - currentValue);
                    } else {
                        newValue = currentValue - delta(curGen, maxGen, currentValue - lowerBound);
                    }
                    if(newValue <= lowerBound) {
                        newValue = lowerBound;
                    } else if(newValue >= upperBound) {
                        newValue = upperBound;
                    }
                    population[i][j] = newValue;
                }
            }
        }
    }

    public static double delta(int currentGen, int maxGen, double value) {
        return (value * (1.0 -
                Math.pow(Math.random(),
                        Math.pow((1.0 - currentGen / (double) maxGen), b)
                )));
    }

    public static double getFitness(double[] individual) {
        double n = (double) individual.length;
        return -20.0 * Math.exp(-0.2 * Math.sqrt((1.0/n) * sumSquare(individual))) - Math.exp((1.0/n) * sumCosine(individual)) + 20.0 + Math.E;
    }

    public static double getFitnessScaled(double[] individual) {
        return scaleAlpha * getFitness(individual) + scaleBeta;
    }

    /**
     * Requirements of scale
     *
     * 1 - The original average fitness value does not change its value after scaling
     * 2 - The original maximum fitness value becomes twice as large as the original average fitness value
     *
     * fi' = (favg / (fmax - favg)) * fi + (favg * (fmax - 2*favg) / (fmax - favg))
     */
    public static void calculateScaleCoefficientes(double[][] population) {
        double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        for(int i = 0; i < population.length; i++) {
            double fitness = getFitness(population[i]);
            sum += fitness;
            if(fitness < min) {
                min = fitness;
            }
            if(fitness > max) {
                max = fitness;
            }
        }
        double avg = sum / population.length;
        scaleAlpha = avg / (max - avg);
        scaleBeta = avg * (max - 2 * avg) / (max - avg);
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
