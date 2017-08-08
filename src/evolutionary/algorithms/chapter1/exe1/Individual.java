package evolutionary.algorithms.chapter1.exe1;

public class Individual {

    private byte[] chromosome;

    private double fitness = Double.MIN_VALUE;

    public Individual(int size, boolean initialize) {
        chromosome = new byte[size];
        if(initialize) {
            for(int i = 0; i < size; i++) {
                setGene(i, (byte) (Math.random() > 0.98 ? 1 : 0));
            }
        }
    }

    public void setGene(int index, byte value) {
        chromosome[index] = value;
    }

    public byte getGene(int index) {
        return chromosome[index];
    }

    public int size() {
        return chromosome.length;
    }

    //Upper and lower bound [-1,2]
    public double getFitness() {
        if(fitness == Double.MIN_VALUE) {
            double x = 0.0;
            for(int i = 0; i < chromosome.length; i++) {
                x += getGene(i) * Math.pow(2, i);
            }
            x = (x / Math.pow(2, chromosome.length)) * (2 + 1) - 1;
            fitness = x * Math.sin(10 * Math.PI * x) + 2.0;
        }
        return fitness;
    }

    @Override
    public String toString() {
        String out = "";
        for(int i = 0; i < size(); i++) {
            out += chromosome[i];
        }
        return out;
    }
}
