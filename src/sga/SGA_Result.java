
package sga;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class SGA_Result<Individual>
{
    public final ArrayList<Double> worst, best, mean;
    public final double bestVal;
    public final Individual bestInd;
    public final int performedIterations;

    public SGA_Result(ArrayList<Double> worst, ArrayList<Double> best, ArrayList<Double> mean,
                       double bestVal, Individual bestInd, int performedIterations)
    {
        this.worst = worst;
        this.best = best;
        this.mean = mean;
        this.bestVal = bestVal;
        this.bestInd = bestInd;
        this.performedIterations = performedIterations;
    }
    
}
