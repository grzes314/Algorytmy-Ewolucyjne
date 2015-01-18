
package flowshop;

import optimization.Function;
import optimization.Permutation;
import optimization.ValuedIndividual;
import simplealgs.HillClimbing;
import simplealgs.ConsecutiveTransChooser;
import simplealgs.RandPermChooser;
import simplealgs.RandomBrowse;
import simplealgs.SimulatedAnnealing;

/**
 *
 * @author Grzegorz Los
 */
public class Main
{
    private Looper looper;
    
    public static void main(String[] args)
    {
        new Main().run(args[0], args[1]);
    }

    private void run(String inPath, String outPath)
    {
        looper = new Looper();
        makeAlgRunners();
        looper.setRepetitions(10);
        looper.runAllTests(inPath, outPath);
    }

    private void makeAlgRunners()
    {
        looper.addAlgRunner(makeHillClimbing());
        looper.addAlgRunner(makeRandomBrowse());
        looper.addAlgRunner(makeSimulatedAnnealing());
    }

    private AlgRunner makeHillClimbing()
    {
        final int trials = 10000;
        RandPermChooser randPermChooser = new RandPermChooser(1);
        ConsecutiveTransChooser consecutiveTransChooser = new ConsecutiveTransChooser();
        
        final HillClimbing hill = new HillClimbing(trials, randPermChooser, consecutiveTransChooser);
        
        return new AlgRunner()
        {
            @Override
            public ValuedIndividual<Permutation> run(Function<Permutation> F)
            {
                return hill.maximize(F);
            }

            @Override
            public void prepare(ProblemData pData)
            {
                randPermChooser.setPermLength(pData.nrOfTasks);
            }

            @Override
            public String getName()
            {
                return "HILL CLIMBING";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials + "\n" +
                    "    -- neighberhood: transposition of consecutive elements";
            }
        };
    }

    private AlgRunner makeRandomBrowse()
    {
        final int trials = 1000000;
        RandPermChooser randPermChooser = new RandPermChooser(1);
        RandomBrowse randomBrowse = new RandomBrowse(trials, randPermChooser);
        return new AlgRunner()
        {

            @Override
            public ValuedIndividual<Permutation> run(Function<Permutation> F)
            {
                return randomBrowse.maximize(F);
            }

            @Override
            public void prepare(ProblemData pData)
            {
                randPermChooser.setPermLength(pData.nrOfTasks);
            }

            @Override
            public String getName()
            {
                return "RANDOM BROWSE";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials;
            }
        };
    }

    private AlgRunner makeSimulatedAnnealing()
    {
        final int trials = 50;
        final int nrOfIterations = 10000;
        RandPermChooser randPermChooser = new RandPermChooser(1);
        ConsecutiveTransChooser consecutiveTransChooser = new ConsecutiveTransChooser();
        SimulatedAnnealing sa = new SimulatedAnnealing(trials, nrOfIterations, randPermChooser, consecutiveTransChooser);
        return new AlgRunner()
        {
            @Override
            public ValuedIndividual<Permutation> run(Function<Permutation> F)
            {
                return sa.maximize(F);
            }

            @Override
            public void prepare(ProblemData pData)
            {
                randPermChooser.setPermLength(pData.nrOfTasks);
            }

            @Override
            public String getName()
            {
                return "SIMULATED ANNEALING";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials + "\n" +
                    "    -- nrOfIterations = " + nrOfIterations + "\n" +
                    "    -- neighberhood: transposition of consecutive elements";
            }
        };    }

}
