
package sga;

/**
 *
 * @author Grzegorz Los
 */
public class SGA_Params
{
    public final int populationSize;
    public final int nrOfParents;
    public final double thetaC;
    public final double thetaM;
    public final int maxIterations;

    public SGA_Params(int populationSize, int nrOfParents, double thetaC, double thetaM, int maxIterations)
    {
        this.populationSize = populationSize;
        this.nrOfParents = nrOfParents;
        this.thetaC = thetaC;
        this.thetaM = thetaM;
        this.maxIterations = maxIterations;
    }

    @Override
    public String toString()
    {
        return 
            "populationSize = " + populationSize + "\n" +
            "nrOfParents = " + nrOfParents + "\n" +
            "thetaC = " + thetaC + "\n" +
            "thetaM = " + thetaM + "\n" +
            "maxIterations = " + maxIterations + "\n";
    }
}
