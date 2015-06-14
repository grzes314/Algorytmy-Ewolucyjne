
package msi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import optimization.Function;
import optimization.Permutation;
import optimization.ValuedIndividual;
import sga.CrossoverPerformer;
import sga.LocalSearch;
import sga.MutationPerformer;
import sga.NoLocalSearch;
import sga.NoMutation;
import sga.PMX;
import sga.ParentSelector;
import sga.PermutationMutationPerformer;
import sga.RandomParentSelector;
import sga.RandomPopulationGenerator;
import sga.ReplacementPerformer;
import sga.SGA;
import sga.SGA_Params;
import sga.SimpleRandomPopulationGenerator;
import sga.SimpleReplacementPerformer;
import sga_presentation.SGA_Runner;
import simplealgs.AllTranspositions;
import simplealgs.HillClimbing;
import simplealgs.NeighbourhoodChooser;
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
        String inPath = args[0];
        String outPath = args[1];
        Main main = new Main();
        try {
            main.runMethodCompOnAllTests(inPath, outPath);
            //main.runLongSGA("flowshop/in/ta115.txt", "flowshop/out/enhanced/dlugo", 11);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getBaseName(String inPath)
    {
        int k = inPath.lastIndexOf("/");
        int l = inPath.lastIndexOf(".");
        return inPath.substring(k+1,l);
    }

    
    private void runLongSGA(String inPath, String outPath, int version) throws IOException 
    {
        ProblemData pData = (new ProblemParser()).read(inPath);
        Valuator F = new Valuator(pData);
        SGA_Params params = new SGA_Params(500,     // populationSize
                                    2000,     // nr of parents
                                    0.95,     // probability of crossover
                                    0.25,    //probability of mutation
                                    100000   // max iterations
        );
        RandPermChooser randPermChooser = new RandPermChooser(pData.nrOfTasks);
        
        SGA_Runner runner = new SGA_Runner() {
            @Override
            protected RandomPopulationGenerator makeRandomPopulationGenerator() {
                return new SimpleRandomPopulationGenerator(randPermChooser);
            }
            @Override
            protected ParentSelector makeParentSelector() {
                return new RandomParentSelector<>();
            }
            @Override
            protected CrossoverPerformer makeCrossoverPerformer() {
                return new PMX();
            }
            @Override
            protected MutationPerformer makeMutationPerformer() {
                switch (version)
                {
                    case 1:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1));
                    case 2:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1));
                    case 3:
                        return new NoMutation<>();
                    case 4:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 5);
                    case 5:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 5);
                    case 6:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 2);
                    case 7:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1));
                    case 8:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 5);
                    case 9:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 3);
                    case 10:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 2);
                    case 11:
                        return new PermutationMutationPerformer(new TranspositionChooser(-1), 3, PermutationMutationPerformer.Mode.UPTO);
                    default:
                        return null;
                }
            }
            @Override
            protected ReplacementPerformer makeReplacementPerformer() {
                return new SimpleReplacementPerformer();
            }
            @Override
            protected LocalSearch makeLocalSearch() {
                return new NoLocalSearch();
            }
        };
        String name = getBaseName(inPath);
        runner.run(params, F,
                           outPath + name + "_" + version + ".plot",
                           outPath + name + "_" + version + ".txt",
                           1);
    }

    private void runMethodCompOnAllTests(String inPath, String outPath)
    {
        looper = new Looper();
        makeAlgRunners();
        looper.setRepetitions(10);
        looper.runAllTests(inPath, outPath);
    }

    private void makeAlgRunners()
    {
        //looper.addAlgRunner(makeHillClimbing());
       // looper.addAlgRunner(makeRandomBrowse());
        looper.addAlgRunner(makeSimulatedAnnealing());
        //looper.addAlgRunner(makeSGA());
    }
    
    private AlgRunner makeHillClimbing()
    {
        final int trials = 1;
        RandPermChooser randPermChooser = new RandPermChooser(1);
        TranspositionChooser neighbourhood = new TranspositionChooser(-1);
        
        final HillClimbing hill = new HillClimbing(trials, randPermChooser, neighbourhood);
        
        return new AlgRunner()
        {
            int neighbourhoodSize;
            
            @Override
            public ValuedIndividual<Permutation> run(Function<Permutation> F)
            {
                return hill.maximize(F);
            }

            @Override
            public void prepare(ProblemData pData)
            {
                neighbourhoodSize = pData.nrOfTasks * pData.nrOfTasks / 4;
                neighbourhood.setNrOfNeighbours(neighbourhoodSize);
                randPermChooser.setPermLength(pData.nrOfTasks);
            }

            @Override
            public String getName()
            {
                return "HILL_CLIMBING";
            }

            @Override
            public String getDesc()
            {
                return "    -- trials = " + trials + "\n" +
                    "    -- neighberhood: " + neighbourhoodSize + " random transpositions";
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
        final int trials = 1;
        RandPermChooser randPermChooser = new RandPermChooser(1);
        NeighbourhoodChooser<Permutation> neighbourhood = new TranspositionChooser(-1);
        SimulatedAnnealing sa = new SimulatedAnnealing(trials, -1, randPermChooser, neighbourhood);
        return new AlgRunner()
        {
            int nrOfIterations;
            @Override
            public ValuedIndividual<Permutation> run(Function<Permutation> F)
            {
                return sa.maximize(F);
            }

            @Override
            public void prepare(ProblemData pData)
            {
                nrOfIterations = 500 * pData.nrOfMachines * pData.nrOfTasks;
                sa.setNrOfIterations(nrOfIterations);
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
                    "    -- neighberhood: random transpositions";
            }
        };    
    }

    private AlgRunner makeSGA()
    {
        SGA_Params params = new SGA_Params(250,     // populationSize
                                    1000,     // nr of parents
                                    1.0,     // probability of crossover
                                    0.5,    //probability of mutation
                                    1000   // max iterations
        );
        SGA<Permutation> sga = new SGA<>(params);
        RandPermChooser randPermChooser = new RandPermChooser(1);
        sga.setRandomPopoluationGenerator(new SimpleRandomPopulationGenerator(randPermChooser));
        sga.setParentSelector(new RandomParentSelector<>());
        sga.setCrossoverPerformer(new PMX());
        sga.setMutationPerformer(new PermutationMutationPerformer(new TranspositionChooser(-1)));
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
                return "SGA1";
            }

            @Override
            public String getDesc()
            {
                return "mutation: one trans., no local serch\n" + params.toString();
            }  
        };
    }

}
