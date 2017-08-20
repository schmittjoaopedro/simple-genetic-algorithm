package evolutionary.algorithms.chapter2.exe3_7;

public class TournamentSelection {

    public static double[] select(double[] population, int k) {
        double[] newPopulation = new double[population.length];
        for(int i = 0; i < newPopulation.length; i++) {
            double[] tournament = new double[k];
            for(int t = 0; t < k; t++) {
                tournament[t] = population[(int) (Math.random() * population.length)];
            }
            double best = tournament[0];
            for(int t = 1; t < k; t++) {
                if(best < tournament[t]) {
                    best = t;
                }
            }
            newPopulation[i] = best;
        }
        return newPopulation;
    }

}
