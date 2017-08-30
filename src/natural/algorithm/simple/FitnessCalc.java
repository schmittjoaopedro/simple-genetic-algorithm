package natural.algorithm.simple;

public class FitnessCalc {

    static byte[] solution = new byte[64];

    static void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        for (int i = 0; i < newSolution.length(); i++) {
            solution[i] = Byte.parseByte(newSolution.substring(i, i + 1));
        }
    }

    // Calculate individuals fitness by comparing it to our candidate solution
    static int getFitness(Individual individual) {
        int fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates
        for (int i = 0; i < individual.size() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        return solution.length;
    }

}
