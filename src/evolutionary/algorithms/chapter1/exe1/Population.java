package evolutionary.algorithms.chapter1.exe1;

public class Population {

    private Individual[] individuals;

    public Population(int popSize, boolean initialize) {
        individuals = new Individual[popSize];
        if(initialize) {
            for(int i = 0; i < popSize; i++) {
                setIndividual(i, new Individual(12, true));
            }
        }
    }

    public void setIndividual(int i, Individual individual) {
        individuals[i] = individual;
    }

    public Individual getIndividual(int i) {
        return individuals[i];
    }

    public int size() {
        return individuals.length;
    }

}