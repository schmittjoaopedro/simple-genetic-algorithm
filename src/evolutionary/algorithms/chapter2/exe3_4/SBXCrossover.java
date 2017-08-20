package evolutionary.algorithms.chapter2.exe3_4;

/**
 * Simulated Binary Crossover
 *
 * Deb e Agrawal pesquisaram o crossover de ponto único para codificação de números por meio de representação binária e
 * econtraram que os descendentes de um crossover de ponto único tem o mesmo centróido dos pais, assim então foi
 * sugerido o "simulated binary crossover (SBX)" para números reais.
 * Eles definiram um termo chamado de fator de dispersção "Beta_i" para um gene de número real como segue:
 *
 * beta_i = |(c_i1 - c_i2)/(p_i1 - p_i2)|
 *
 * onde c_i1 e c_i2 são o gene i de dois descendentes, e p_i1 e p_i2 são os genes i dos pais. If beta_1 < 1, o operador
 * é chamado de crossover de contração. Se beta_1 > 1, o operador é chamado de crossover de expansão. Se beta = 1 então
 * o operador é chamado de crossover estacionário. Para simular o compartilhamento da centróide para números reais, Deb
 * e Agrawal queriam que o crossover binário para números reais tivesse uma alta probabilidade de ser estácionário e baixa
 * probabilidade de ser de contração ou expansão. Para implementar isso, eles consideraram a variável beta_1 como um número
 * randômico e sugeriram um função de densidade probabilistica como segue:
 *
 * p(beta_1) =  { 0.5*(n+1)*beta_1^n,      beta_1 <= 1
 *              { 0.5*(n+1)/beta_1^(n+2),  beta_1 > 1           (eq 3.5)
 *
 * onde n é uma variável de controle. Normalmente usa-se n valendo 2 ou 5, para maiores valores de n tem-se uma maior chance
 * de beta_1 ficar próximo do valor 1.
 * Para gerar um número randômico da equação 3.5 pode user usada feita a transformação para a função inversa para gerar um
 * número randômico beta_1 uniformemente distribuído entre u_i = U(0,1). Assim a integração da equação 3.5 é dada por:
 *
 * F(beta_1) = { (0,b1)∫ 0.5*(n+1)*x^n dx = 0.5*beta_1^(n+1),               beta_1 <= 1
 *             { 0.5 + (1,b1)∫ 0.5*(n+1)*x^-(n+2) dx = 1-0.5*beta_1^-(n+1), beta_1 > 1          (eq 3.6)
 *
 * Deixando u_i como F^-1(beta_1) e resolvendo beta_1 temos
 *
 * beta_1 = { (2*u_i)^(1/(n+1)),        u_i <= 0.5
 *          { (2*(2-u_i))^-(1/(n+1)),   u_i > 0.5           (eq 3.7)
 *
 * De acordo com a equação 3.7, pequenos valores de u_i significam menores valores de dispersão beta_1, que fazem o crossover um
 * crossover de contração aonde as gerações estão mais próximas da centróide. Valores mais largos de u_i significam fatores
 * de dispersão beta_1 maiores, que fazem os crossovers serem crossovers de expansão. Mas de acordo com a equação 3.6, essa função
 * tende a ter uma maior probabilidade de ser estacionário, beta_1 = 1.
 * A geração dos genes da próxima geração é dada pela equação 3.8:
 *
 * c_i1 = 0.5*(p_i1 + p_i2) + 0.5*beta_1*(p_i1 - p_i2)
 * c_i2 = 0.5*(p_i1 + p_i2) + 0.5*beta_1*(p_i2 - p_i1)
 *
 * Como SBX tem um boa probabilidade de ser 1 os filhos tendem a ser mais próximos dos pais, garantindo que as próximas gerações
 * possuam as mesmas centróides dos pais. Se o pais estão longes um dos outros, os descendentes também tendem a estar longe um dos outros
 * que pode promover a exploração do espaço de busca. Se os pais estão próximos os filhos tendem a estar próximos também o que tende
 * a promover a intensificação.
 * SBX é baseado em eixos, assim sua capacidade de busca é limitada quando lida com problemas de variáveis não separáveis.
 *
 */
public class SBXCrossover {

    private static double n = 5.0; //Control parameter normally between [2,5]

    public static void crossover(double[] p1, double[] p2, double[] c1, double[] c2) {
        for(int i = 0; i < p1.length; i++) {
            if(Math.random() < 0.9) {
                double u = Math.random();
                double beta = 1;
                if(u < 0.5) {
                    beta = Math.pow(2.0 * u, 1.0 / (n + 1.0));
                } else if (u > 0.5) {
                    beta = Math.pow(2.0 * (1.0 - u), -1.0 / (n + 1.0));
                }
                c1[i] = 0.5 * (p1[i] + p2[i]) + 0.5 * beta * (p1[i] - p2[i]);
                c2[i] = 0.5 * (p1[i] + p2[i]) + 0.5 * beta * (p2[i] - p1[i]);
            } else {
                c1[i] = p1[i];
                c2[i] = p2[i];
            }
        }
    }


}
