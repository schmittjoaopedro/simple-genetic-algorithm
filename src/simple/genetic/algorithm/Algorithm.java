package simple.genetic.algorithm;

public class Algorithm {

    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    // Evolve a population
    public static Population evolvePopulation(Population pop) {

        Population newPopulation = new Population(pop.individuals.length, false);

        // Keep our best individual
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.individuals[0] = pop.getFittest();
            elitismOffset = 1;
        }

        // Loop over the population size and create new individuals with crossover
        for (int i = elitismOffset; i < pop.individuals.length; i++) {
            newPopulation.individuals[i] = crossover(tournamentSelection(pop), tournamentSelection(pop));
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.individuals.length; i++) {
            mutate(newPopulation.individuals[i]);
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual individual1, Individual individual2) {
        Individual newSol = new Individual();
        // Loop through genes
        for (int i = 0; i < individual1.size(); i++) {
            // Crossover
            newSol.setGene(i, Math.random() <= uniformRate ? individual1.getGene(i) : individual2.getGene(i));
        }
        return newSol;
    }

    // Mutate an individual
    private static void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                indiv.setGene(i, (byte) Math.round(Math.random()));
            }
        }
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            tournament.individuals[i] = pop.individuals[(int) (Math.random() * pop.individuals.length)];
        }
        return tournament.getFittest();
    }

}
