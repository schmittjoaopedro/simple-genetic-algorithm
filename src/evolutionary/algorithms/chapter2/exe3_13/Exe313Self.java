package evolutionary.algorithms.chapter2.exe3_13;

import org.moeaframework.Executor;
import org.moeaframework.algorithm.StandardAlgorithms;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.LexicographicalComparator;
import org.moeaframework.util.TypedProperties;

import java.util.Properties;

public class Exe313Self {

    public static void main(String[] args) {
        NondominatedPopulation result = new Executor()
                .withProblemClass(OptimizationProblem.class)
                .withAlgorithm("ES")
                .withMaxEvaluations(1000)
                .run();

        // sort the results so the solutions appear in order
        result.sort(new LexicographicalComparator());

        // print the bit string and the objective values
        for (Solution solution : result) {
            System.out.println(solution.getObjective(0));
        }

    }

}
