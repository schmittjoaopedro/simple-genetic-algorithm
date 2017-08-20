package evolutionary.algorithms.chapter2.exe3_5;

import evolutionary.algorithms.chapter2.Settings;

/**
 * Com base nos aspectos dos algoritmos de cozimento em EA, podemos ainda
 * modificar o processo de conzimento com base em um processo não uniforme
 * aonde a temperatura é sensível a número de gerações. A seguinte equação
 * determina o novo valor de mutação para um dada variável:
 *
 * xj' = { xj + delta(g, Uj - xj),    rand >= 0.5
 *       { xj - delta(g, Lj - xj),    rand < 0.5
 *
 * onde xj' é a variável mutante de xj, Uj e Lj são os limites superiores e
 * inferiores, respectivamente, e delta(g,y) é a extensão da mutação das quais
 * as variáveis geração g e uma possível extensão do tamnho de j. Assim a variável
 * j tem probabilidade de 0.5 de aumentar de valor ou de diminuir. A função delta
 * é definada por:
 *
 * delta(g,y) = y * (1.0 - rand^(1 - (g/maxGen))^b)
 *
 * onde rand ~ U(0,1), b é um parâmetro de controle da velocidade de cozimento, g
 * é o número corrente da geração e maxGen é o número total de gerações. Assim
 * valores altos de b significam um cozimento maior que reduz proporcional ao avanço
 * das gerações.
 */
public class SelAdaptativeNonUniformMutation {

    private static double pM = 0.015;

    private static int b = 2; //Control speed, values -> [2,5]

    public static void mutate(double[][] population, int maxGen, int curGen) {
        for(int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                if(Math.random() < pM) {
                    double currentValue = population[i][j];
                    double newValue = 0.0;
                    double rand = Math.random();
                    if(rand >= 0.5) {
                        newValue = currentValue + delta(curGen, maxGen, Settings.upperBound - currentValue);
                    } else {
                        newValue = currentValue - delta(curGen, maxGen, currentValue - Settings.lowerBound);
                    }
                    if(newValue <= Settings.lowerBound) {
                        newValue = Settings.lowerBound;
                    } else if(newValue >= Settings.upperBound) {
                        newValue = Settings.upperBound;
                    }
                    population[i][j] = newValue;
                }
            }
        }
    }

    public static double delta(int currentGen, int maxGen, double value) {
        return (value * (1.0 -
            Math.pow(Math.random(),
                Math.pow((1.0 - currentGen / (double) maxGen), b)
        )));
    }

}
