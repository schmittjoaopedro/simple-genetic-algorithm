package evolutionary.algorithms.chapter2.exe3_5;

import evolutionary.algorithms.chapter2.Settings;

/**
 * Created by root on 19/08/17.
 */
public class UniformMutation {

    private static double pM = 0.015;

    public static void mutate(double[][] population) {
        for(int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                if(Math.random() < pM) {
                    population[i][j] = Settings.lowerBound + Math.random() * (Settings.upperBound - Settings.lowerBound);
                }
            }
        }
    }

}
