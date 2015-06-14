
package msi;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemData
{
    public final int nrOfTasks;
    public final int nrOfMachines;
    public final double[][] exTimes; //exTime[i][a] to czas wykonywania zadania a na maszynie i.
    public final double[][][] prepTimes; //prepTimes[i][a][b] to czas przygotowania stanowiska i to wykonania zadania b gdy wczesniej wykonywano zadanie a

    public ProblemData(int nrOfTasks, int nrOfMachines, double[][] exTimes, double[][][] prepTimes)
    {
        this.nrOfTasks = nrOfTasks;
        this.nrOfMachines = nrOfMachines;
        this.exTimes = exTimes;
        this.prepTimes = prepTimes;
    }
    
}
