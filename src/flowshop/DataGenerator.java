
package flowshop;

import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class DataGenerator
{
    private int nrOfTasks, nrOfMachines;
    private double maxPrice = 10, maxTime = 10;

    public DataGenerator(int nrOfMachines, int nrOfTasks)
    {
        this.nrOfTasks = nrOfTasks;
        this.nrOfMachines = nrOfMachines;
    }

    public int getNrOfTasks()
    {
        return nrOfTasks;
    }

    public void setNrOfTasks(int nrOfTasks)
    {
        this.nrOfTasks = nrOfTasks;
    }

    public int getNrOfMachines()
    {
        return nrOfMachines;
    }

    public void setNrOfMachines(int nrOfMachines)
    {
        this.nrOfMachines = nrOfMachines;
    }

    public double getMaxPrice()
    {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public double getMaxTime()
    {
        return maxTime;
    }

    public void setMaxTime(double maxTime)
    {
        this.maxTime = maxTime;
    }
    
    public ProblemData generate()
    {
        double deadline = Math.log(nrOfMachines) * nrOfTasks * maxTime / 10;
        ProblemData pData = new ProblemData(nrOfMachines, nrOfTasks, deadline);
        for (int i = 0; i < nrOfTasks; ++i)
            pData.addTask( generateTask() );
        return pData;
    }

    private ProblemData.Task generateTask()
    {
        double price = generateDouble(0, maxPrice);
        ProblemData.Task task = new ProblemData.Task(nrOfMachines, price);
        for (int i = 0; i < nrOfMachines; ++i)
            task.times[i] = generateDouble(0, maxTime);
        return task;
    }
    
    private double generateDouble(double a, double b)
    {
        return RandomnessSource.rand.nextDouble() * (b - a) + a;
    }
}
