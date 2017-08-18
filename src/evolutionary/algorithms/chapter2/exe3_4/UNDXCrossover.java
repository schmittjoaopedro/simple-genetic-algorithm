package evolutionary.algorithms.chapter2.exe3_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Unimodal Normal Distribution Crossover
 *
 * Ono propos um crossover de distribuicao normal unimodal com abilidade de tratar variáveis não separáveis
 * e a preferencia de centróide. Ele quize que as variáveis tivessem uma centróide próxima à dos pais. Ele
 * introduziu a variação direcional ortogonal para a direção dos dois pais.
 *
 *
 *          p_3
 *           |
 *           |          0
 *         D |        ,'|
 *           |      ,'  |
 * p_1 ---------k--------- p_2          (fig 3.7)
 *
 *
 * Nós precisamos de 3 pais, p_1, p_2 e p_3 para gerar um descendente. Primeiro a centróide de p_1 e p_2, onde
 * k é calculado com k = (p_1 + p_2) / 2. Então o vetor d é calculado = p_2 - p_1 que é o vetor que inicia em p_1 e termina
 * em p_2. D é ilustra a distância do ponto p_3 até o vetor d. Usando d e qualquer outro n-1 vetor independente no espaco R^n,
 * nós podemos gerar uma unidade orthogonal de n bases Gram-Schmidt ortonormalization.
 * Nós usamos e_1,...,e_n-1 para ilustrar as outras n-1 unidades bases ortogonais por trás de d/|d|. Então podemos gerar os
 * descendentes pela seguinte equação:
 *
 * o = k + ξ*d + D*∑(η_i*e_i)                               (eq 3.9)
 *
 * onde η é dado por N(0,σn^2) e ξ por N(0,σe^2). Da equação 3.9 nós podemos ver que os descendentes iniciam na centróide dos pais p_1 e p_2,
 * com k extendendo na direção do vetor d e tamanho ξ, e então extendo para outra direção ortogonal de d. Por causa que ξ e η são
 * média zero então o descendente fica próximo a centróide de p_1 e p_2 com alta probabilidade. As duas primeiras partes da equação 3.9
 * são da primeira componente de busca, e a terceira parte é a componente de busca secundária. Geralmente isso irá gerar um descendente
 * dentro de uma elipsoide onde o eixo principal é d. Também é sugerido que σe = 0.5 e σn = 0.35 / sqrt(n).
 *
 * Projeção ortogonal: http://www.somatematica.com.br/emedio/espacial/espacial5.php
 * Gram-Schmidt ortonormalização: https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process
 */
public class UNDXCrossover {

    private static Random random = new Random();

    private static final double zeta = 0.5;

    private static final double eta = 0.35;

    public static void crossover(double[] p1, double[] p2, double[] c1, double[] c2) {
        UNDXCrossover.crossover(p1, p2, c1);
        UNDXCrossover.crossover(p1, p2, c2);
    }

    public static void crossover(double[] p1, double[] p2, double[] result) {

        int k = 2; //Two parents
        int n = p1.length; //Number of variables
        double[][] x = new double[k][n];

        for(int i = 0; i < n; i++) {
            x[0][i] = p1[i];
            x[1][i] = p2[i];
        }

        double[] g = Vector.mean(x);
        List<double[]> e_zeta = new ArrayList<double[]>();
        List<double[]> e_eta = new ArrayList<double[]>();

        // basis vectors defined by parents
        for (int i = 0; i < k - 1; i++) {
            double[] d = Vector.subtract(x[i], g);
            if (!Vector.isZero(d)) {
                double dbar = Vector.magnitude(d);
                double[] e = Vector.orthogonalize(d, e_zeta);
                if (!Vector.isZero(e)) {
                    e_zeta.add(Vector.multiply(dbar, Vector.normalize(e)));
                }
            }
        }

        double D = Vector.magnitude(Vector.subtract(x[k - 1], g));

        // create the remaining basis vectors
        for (int i = 0; i < n - e_zeta.size(); i++) {
            double[] d = randomVector(n);
            if (!Vector.isZero(d)) {
                double[] e = Vector.orthogonalize(d, e_eta);
                if (!Vector.isZero(e)) {
                    e_eta.add(Vector.multiply(D, Vector.normalize(e)));
                }
            }
        }

        // construct the offspring
        double[] variables = g;
        for (int i = 0; i < e_zeta.size(); i++) {
            variables = Vector.add(variables, Vector.multiply(nextGaussian(0.0, zeta), e_zeta.get(i)));
        }
        for (int i = 0; i < e_eta.size(); i++) {
            variables = Vector.add(variables, Vector.multiply(nextGaussian(0.0, eta / Math.sqrt(n)), e_eta.get(i)));
        }

        for (int j = 0; j < n; j++) {
            double value = variables[j];
            if (value < Settings.lowerBound) {
                value = Settings.lowerBound;
            } else if (value > Settings.upperBound) {
                value = Settings.upperBound;
            }
            result[j] = value;
        }

    }

    private static double[] randomVector(int n) {
        double[] v = new double[n];
        for (int i = 0; i < n; i++) {
            v[i] = random.nextGaussian();
        }
        return v;
    }

    public static double nextGaussian(double mean, double stdev) {
        return stdev * random.nextGaussian() + mean;
    }

}
