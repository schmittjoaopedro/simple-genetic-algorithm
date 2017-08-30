package natural.algorithm.pso;

import java.util.Random;
import java.util.Vector;

public class PSOProcess implements PSOConstants {

    private Vector<Particle> swarm = new Vector<Particle>(); //Population

    private double[] pBest = new double[SWARM_SIZE]; //Local best fitness values
    private Vector<Location> pBestLocation = new Vector<Location>(); //Local best locations

    private double gBest; //Global best value
    private Location gBestLocation; //Global best location

    private double[] fitnessValueList = new double[SWARM_SIZE];

    private Random generator = new Random();

    public void execute() {
        //Initialize a random swarm
        initializeSwarm();
        //Calculate fitness
        updateFitnessList();

        //Update local best
        for (int i = 0; i < SWARM_SIZE; i++) {
            pBest[i] = fitnessValueList[i];
            pBestLocation.add(swarm.get(i).getLocation());
        }

        int t = 0;
        double w;
        double err = 9999;

        while (t < MAX_ITERATION && err > ProblemSet.ERR_TOLERANCE) {
            //Step 1 - update local best
            for (int i = 0; i < SWARM_SIZE; i++) {
                if (fitnessValueList[i] < pBest[i]) {
                    pBest[i] = fitnessValueList[i];
                    pBestLocation.set(i, swarm.get(i).getLocation());
                }
            }

            //Step 2 - update global best
            int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
            if (t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
                gBest = fitnessValueList[bestParticleIndex];
                gBestLocation = swarm.get(bestParticleIndex).getLocation();
            }

            //Inert value
            w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);

            for (int i = 0; i < SWARM_SIZE; i++) {
                double r1 = generator.nextDouble();
                double r2 = generator.nextDouble();

                Particle p = swarm.get(i);

                //Step 3 - update velocity
                double newVel[] = new double[PROBLEM_DIMENSION];
                for (int d = 0; d < PROBLEM_DIMENSION; d++) {
                    newVel[d] = (w * p.getVelocity().getPos()[d]) +
                            (r1 * C1) * (pBestLocation.get(i).getLoc()[d] - p.getLocation().getLoc()[d]) +
                            (r2 * C2) * (gBestLocation.getLoc()[d] - p.getLocation().getLoc()[d]);
                }
                Velocity velocity = new Velocity(newVel);
                p.setVelocity(velocity);

                //Step 4 - update location
                double[] newLoc = new double[PROBLEM_DIMENSION];
                for (int d = 0; d < PROBLEM_DIMENSION; d++) {
                    newLoc[d] = p.getLocation().getLoc()[d] + newVel[d];
                }
                Location loc = new Location(newLoc);
                p.setLocation(loc);
            }

            err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0

            System.out.println("ITERATION " + t + ": ");
            System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
            System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
            System.out.println("     Value: " + ProblemSet.evaluate(gBestLocation));

            t++;
            updateFitnessList();
        }

        System.out.println("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
        System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
        System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);

    }

    public void initializeSwarm() {
        Particle p;
        for (int i = 0; i < SWARM_SIZE; i++) {
            p = new Particle();

            // randomize location inside a space defined in Problem Set
            double[] loc = new double[PROBLEM_DIMENSION];
            for (int d = 0; d < PROBLEM_DIMENSION; d++) {
                loc[d] = ProblemSet.LOC_LOW[d] + generator.nextDouble() * (ProblemSet.LOC_HIGH[d] - ProblemSet.LOC_LOW[d]);
            }
            Location location = new Location(loc);

            // randomize velocity in the range defined in Problem Set
            double[] vel = new double[PROBLEM_DIMENSION];
            for (int d = 0; d < PROBLEM_DIMENSION; d++) {
                loc[d] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
            }
            Velocity velocity = new Velocity(vel);

            p.setLocation(location);
            p.setVelocity(velocity);
            swarm.add(p);
        }
    }

    public void updateFitnessList() {
        for (int i = 0; i < SWARM_SIZE; i++) {
            fitnessValueList[i] = swarm.get(i).getFitnessValue();
        }
    }

}