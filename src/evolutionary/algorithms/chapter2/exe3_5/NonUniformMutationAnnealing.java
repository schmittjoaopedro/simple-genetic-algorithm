package evolutionary.algorithms.chapter2.exe3_5;

import evolutionary.algorithms.chapter2.Settings;

/**
 * Non Uniform Mutation
 *
 * Procedimento para realizar mutação em um formato não uniforme. Quando falamos
 * em reduzir a escala de busca, podemos mencionar o famoso (SA - simulated annealing)
 * simulação de cozimento sugerido por Kirkpatrick. SA é um algoritmo de busca global
 * beaseado em um método de busca local. A razão de sua busca global é que ele pode
 * aceitar um resultado pior em uma busca local com uma especifica probabilidade.
 *
 * SA usa busca local para explorar o território de uma solução i. O procedimento de busca
 * local pode ser diferente para vários problemas e diferentes métodos de codificação.
 * Assim a busca local irá sugerir uma nova solução j de mutação. A probabilidade de
 * aceitar a solução j no lugar da solução i será dada por:
 *
 * p(i -> j) = { 1,                         f(j) <= f(i)
 *             { exp((f(i) - f(j)) / t),    otherwise               (eq 3.12)
 *
 * onde t é um parâmetro de controle chamado temperatura. Se j não é pior que i, j toma
 * o lugar de i com probabilidade de 1. Caso contrário, a probabilidade de escolher j
 * depende de quão ruim seja essa solução e do valor da temperatura. Assim, quanto pior
 * j menor a probabilidade de escolher ele, e quanto menor a temperatura menor a probabilidade
 * de escolher j também.
 */
public class NonUniformMutationAnnealing {

    private static double pM = 0.015;

    private static double temperature = 0.2;

    public static void mutate(double[][] population) {
        for(int i = 0; i < population.length; i++) {
            double[] individual = population[i].clone();
            for (int j = 0; j < individual.length; j++) {
                if(Math.random() < pM) {
                    individual[j] = Settings.lowerBound + Math.random() * (Settings.upperBound - Settings.lowerBound);
                }
            }
            if(Settings.getFitness(individual) < Settings.getFitness(population[i])) {
                population[i] = individual;
            } else {
                double fit1 = Settings.getFitness(population[i]);
                double fit2 = Settings.getFitness(individual);
                double prob = Math.exp(((fit1 - fit2) / (fit1 + fit2)) / temperature);
                if(Math.random() < prob) {
                    population[i] = individual;
                }
            }
        }
    }


}
