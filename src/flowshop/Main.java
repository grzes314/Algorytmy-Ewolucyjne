
package flowshop;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import optimization.Function;
import optimization.Permutation;
import optimization.ValuedIndividual;
import sga.PMX;
import sga.PermutationMutationPerformer;
import sga.RandomParentSelector;
import sga.SGA;
import sga.SGA_Params;
import sga.SimpleRandomPopulationGenerator;
import sga.SimpleReplacementPerformer;
import simplealgs.HillClimbing;
import simplealgs.ConsecutiveTransChooser;
import simplealgs.RandPermChooser;
import simplealgs.RandomBrowse;
import simplealgs.SimulatedAnnealing;
import simplealgs.TranspositionChooser;

/**
 *
 * @author Grzegorz Los
 */
public class Main
{
    private Looper looper;
    
    public static void main(String[] args)
    {
        //new Main().run(args[0], args[1]);
        new Main().run("flowshop/in/dupa", args[1]);
        /*String out = args[0] + "/dupa.txt";
        ProblemData pData = (new DataGenerator(20,200)).generate();
        (new ProblemWriter()).write(pData, out);*/
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
        //looper.addAlgRunner(makeHillClimbing1());
        looper.addAlgRunner(makeHillClimbing2());
        looper.addAlgRunner(makeRandomBrowse());
        looper.addAlgRunner(makeSimulatedAnnealing());
        looper.addAlgRunner(makeSGA());
    }

    private AlgRunner makeHillClimbing1()
    {
        final int trials = 1500;
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
                return "HILL_CLIMBING1";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials + "\n" +
                    "    -- neighberhood: transposition of consecutive elements";
            }
        };
    }

    private AlgRunner makeHillClimbing2()
    {
        final int trials = 1000;
        RandPermChooser randPermChooser = new RandPermChooser(1);
        TranspositionChooser transpositionChooser = new TranspositionChooser(100);
        
        final HillClimbing hill = new HillClimbing(trials, randPermChooser, transpositionChooser);
        
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
                return "HILL_CLIMBING2";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials + "\n" +
                    "    -- neighberhood: 100 random transpositions";
            }
        };
    }

    private AlgRunner makeRandomBrowse()
    {
        final int trials = 200000;
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
                return "RANDOM_BROWSE";
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
        final int trials = 100;
        final int nrOfIterations = 5000;
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
                return "SIMULATED_ANNEALING";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials + "\n" +
                    "    -- nrOfIterations = " + nrOfIterations + "\n" +
                    "    -- neighberhood: transposition of consecutive elements";
            }
        };    }

    private AlgRunner makeSGA()
    {
        SGA_Params params = new SGA_Params(100,     // populationSize
                                    200,     // nr of parents
                                    1.0,     // probability of crossover
                                    0.05,    //probability of mutation
                                    800   // max iterations
        );
        SGA<Permutation> sga = new SGA<>(params);
        RandPermChooser randPermChooser = new RandPermChooser(1);
        sga.setRandomPopoluationGenerator(new SimpleRandomPopulationGenerator(randPermChooser));
        sga.setParentSelector(new RandomParentSelector<>());
        sga.setCrossoverPerformer(new PMX());
        sga.setMutationPerformer(new PermutationMutationPerformer(new TranspositionChooser(100)));
        sga.setReplacementPerformer(new SimpleReplacementPerformer());
        
        return new AlgRunner()
        {
            @Override
            public ValuedIndividual<Permutation> run(Function<Permutation> F)
            {
                sga.maximize(F);
                double best = sga.getBestVal();
                Permutation perm = sga.getBestIndividual();
                return new ValuedIndividual<>(perm, best);
            }

            @Override
            public void prepare(ProblemData pData)
            {
                randPermChooser.setPermLength(pData.nrOfTasks);
            }

            @Override
            public String getName()
            {
                return "SGA";
            }

            @Override
            public String getDesc()
            {
                return params.toString();
            }  
        };
    }

}
