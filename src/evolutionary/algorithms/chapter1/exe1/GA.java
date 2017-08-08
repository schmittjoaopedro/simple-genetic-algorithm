package evolutionary.algorithms.chapter1.exe1;

/**
 * Created by root on 08/08/17.
 */
public class GA {

    private static int popSize = 50;
    private static int maxGen = 100;
    private static double pC = 0.8;
    private static double pM = 0.01;

    public static void main(String[] args) {
        Population current = new Population(popSize, true);
        System.out.println("Init: " + getFittest(current).getFitness());
        Individual fittest = getFittest(current);
        for(int i = 0; i < maxGen; i++) {
            Population mating = rouletteSelection(current);
            Population newPopulation = crossover(mating);
            mutating(newPopulation);
            current = newPopulation;
            if(fittest.getFitness() <= getFittest(current).getFitness()) fittest = getFittest(current);
            if(i % 10 == 0)
                System.out.println("Generation " + i + " : " + getFittest(current).getFitness());
        }
        System.out.println("End: " + getFittest(current).getFitness());
        System.out.println("Best: " + fittest.getFitness());
    }

    public static void mutating(Population population) {
        for(int i = 0; i < population.size(); i++) {
            for(int g = 0; g < population.getIndividual(i).size(); g++) {
                if(Math.random() < pM) {
                    if(population.getIndividual(i).getGene(g) == 1) {
                        population.getIndividual(i).setGene(g, (byte) 0);
                    } else {
                        population.getIndividual(i).setGene(g, (byte) 1);
                    }
                }
            }
        }
    }

    public static Population crossover(Population population) {
        Population newPopulation = new Population(population.size(), false);
        int i = 0;
        while(i < population.size()) {
            int i1 = i++, i2 = i++;
            Individual indCur1 = population.getIndividual(i1);
            Individual indCur2 = population.getIndividual(i2);
            Individual indNew1 = new Individual(indCur1.size(), false);
            Individual indNew2 = new Individual(indCur2.size(), false);
            double randPC = Math.random();
            int randCut = (int) (Math.random() * (indCur1.size() - 1));
            for(int g = 0; g < indCur1.size(); g++) {
                if(randPC < pC && randCut <= g) {
                    indNew1.setGene(g, indCur2.getGene(g));
                    indNew2.setGene(g, indCur1.getGene(g));
                } else {
                    indNew1.setGene(g, indCur1.getGene(g));
                    indNew2.setGene(g, indCur2.getGene(g));
                }
            }
            newPopulation.setIndividual(i1, indNew1);
            newPopulation.setIndividual(i2, indNew2);
        }
        return newPopulation;
    }

    public static Population rouletteSelection(Population population) {
        Population newPopulation = new Population(population.size(), false);
        double freqCum = 0.0;
        for(int i = 0; i < population.size(); i++) freqCum += population.getIndividual(i).getFitness();
        double p[] = new double[population.size()];
        double cumSum = 0.0;
        for(int i = 0; i < population.size(); i++) {
            cumSum += population.getIndividual(i).getFitness() / freqCum;
            p[i] = cumSum;
        }
        for(int i = 0; i < population.size(); i++) {
            double rand = Math.random();
            for (int j = 0; j < population.size(); j++) {
                if ((j == 0 || p[j - 1] < rand) && (rand < p[j])) {
                    newPopulation.setIndividual(i, population.getIndividual(j));
                    break;
                }
            }
        }
        return newPopulation;
    }

    public static Individual getFittest(Population population) {
        Individual fittest = population.getIndividual(0);
        for(int i = 1; i < population.size(); i++) {
            if(fittest.getFitness() <= population.getIndividual(i).getFitness()) {
                fittest = population.getIndividual(i);
            }
        }
        return fittest;
    }

}
