package natural.algorithm.simple;

public class Population {

    public Individual[] individuals;

    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        if (initialise) {
            for (int i = 0; i < individuals.length; i++) {
                individuals[i] = new Individual();
                individuals[i].generateIndividual();
            }
        }
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < individuals.length; i++) {
            if (fittest.getFitness() <= individuals[i].getFitness()) {
                fittest = individuals[i];
            }
        }
        return fittest;
    }

}
