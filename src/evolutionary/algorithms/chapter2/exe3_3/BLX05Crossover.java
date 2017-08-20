package evolutionary.algorithms.chapter2.exe3_3;

import java.util.Random;

public class BLX05Crossover {

    public static void main(String[] args) {
        double gene1 = 2.5;
        double gene2 = 7;
        System.out.println(blxCrossover(gene1, gene2, 0.5));
    }

    /**
     * The standard arithmetic crossover is actually a linear interpolation of two individuals,
     * so it could only generate offspring on the line connecting two parents. So the
     * second guideline discussed above is hard to satisfy. Eshelman and Schaffer suggested
     * blend crossover (BLX) to expand the range of arithmetic crossover [5].
     * BLX is performed on the gene level. For gene x1 and x2, suppose x1 < x2 without
     * xloss of generality; their offspring gene is:
     *      yi = rand((x1 − α(x2 − x1)),(x2 + α(x2 − x1)))
     * where rand(a,b) is a function to generate a uniformly distributed random number
     * in the range (a,b), 12 α is a user-defined parameter that controls the extent of the
     * expansion. So we often use BLX-α to make things clear. Eshelman and Schaffer
     * reported that α = 0.5 is a good choice for most situations. So the most frequently
     * used BLX is BLX-0.5.1
     */
    public static double blxCrossover(double gene1, double gene2, double alpha) {
        if(gene1 >= gene2) {
            double temp = gene1;
            gene1 = gene2;
            gene2 = temp;
        }
        double lowerBound = gene1 - alpha * (gene2 - gene1);
        double upperBound = gene2 + alpha * (gene2 - gene1);
        return Math.random() * (upperBound - lowerBound) + lowerBound;
    }

}
