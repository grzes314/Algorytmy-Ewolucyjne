
package flowshop;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemData
{
    public final int nrOfMachines;
    public final int nrOfTasks;
    public final double deadline;
    public final ArrayList<Task> tasks;

    public ProblemData(int nrOfMachines, int nrOfTasks, double deadline)
    {
        this.nrOfMachines = nrOfMachines;
        this.nrOfTasks = nrOfTasks;
        this.deadline = deadline;
        tasks = new ArrayList<>();
    }
    
    public void addTask(Task task)
    {
        tasks.add(task);
    }
    
    public static class Task
    {
        public final double[] times;
        public final double price;
        
        public Task(int nrOfMachines, double price)
        {
            times = new double[nrOfMachines];
            this.price = price;
        }
    }
}
