
package flowshop;

import optimization.Function;
import optimization.Permutation;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class Valuator implements Function<Permutation>
{
    public final ProblemData pData;
    private final double[] finishTimes;
    private final double tolerance = 1.0e-3;

    public Valuator(ProblemData pData)
    {
        this.pData = pData;
        finishTimes = new double[pData.nrOfMachines];
    }
    
    @Override
    public ValuedIndividual<Permutation> value(Permutation x)
    {
        reset();
        double price = 0.0;
        for (int i = 0; i < pData.nrOfTasks; ++i)
        {
            ProblemData.Task task = pData.tasks.get(x.at(i));
            boolean meetsDeadline = perform(task);
            if (meetsDeadline)
                price += task.price;
            else break;
        }
        price -= finishTimes[pData.nrOfMachines-1] / pData.deadline / 10;
        return new ValuedIndividual<>(x, price);
    }

    private void reset()
    {
        for (int i = 0; i < pData.nrOfMachines; ++i)
            finishTimes[i] = 0.0;
    }

    private boolean perform(ProblemData.Task task)
    {
        double t = finishTimes[0]; // t means when we can start on next machine
        int lastM = pData.nrOfMachines - 1;
        for (int i = 0; i < lastM; ++i)
        {
            finishTimes[i] = t + task.times[i];
            t = Math.max(finishTimes[i], finishTimes[i+1]);
        }
        finishTimes[lastM] = t + task.times[lastM];
        return finishTimes[lastM] < pData.deadline + tolerance;
    }

}
