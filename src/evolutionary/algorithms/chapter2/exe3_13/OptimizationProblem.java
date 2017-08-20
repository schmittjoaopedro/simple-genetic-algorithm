package evolutionary.algorithms.chapter2.exe3_13;

import evolutionary.algorithms.chapter2.Settings;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

/**
 * Created by root on 20/08/17.
 */
public class OptimizationProblem extends AbstractProblem {

    public OptimizationProblem() {
        super(3, 1);
    }

    @Override
    public void evaluate(Solution solution) {
        double[] values = EncodingUtils.getReal(solution);
        double fitness = Settings.getFitness(values);
        solution.setObjective(0, fitness);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(3, 1);
        solution.setVariable(0, EncodingUtils.newReal(-20, 30));
        solution.setVariable(1, EncodingUtils.newReal(-20, 30));
        solution.setVariable(2, EncodingUtils.newReal(-20, 30));
        return solution;
    }
}
