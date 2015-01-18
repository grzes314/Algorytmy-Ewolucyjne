
package sga_presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import optimization.Copyable;
import sga.CrossoverPerformer;
import optimization.Function;
import sga.MutationPerformer;
import sga.ParentSelector;
import sga.ProgressObserver;
import sga.RandomPopulationGenerator;
import sga.ReplacementPerformer;
import sga.SGA;
import sga.SGA_Params;
import sga.SGA_Result;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public abstract class SGA_Runner<Individual extends Copyable<Individual>>
{
    private Function<Individual> F;
    private String pathForPlotData;
    private String pathForSummary;
    private SGA<Individual> sga;
    private int repetitions;
    private SGA_Params params;
    private int lastDisplayedPerc = 0;
    private SGA_Result<Individual> results[];
    private int bestRes;
    private double bestVal;
    
    public void run(SGA_Params params, Function<Individual> F, String pathForPlotData, String pathForSummary, int repetitions)
    {
        this.params = params;
        this.F = F;
        this.pathForPlotData = pathForPlotData;
        this.pathForSummary = pathForSummary;
        this.repetitions = repetitions;
        sga = new SGA<>(params);
        sga.setRandomPopoluationGenerator( makeRandomPopulationGenerator() );
        sga.setParentSelector( makeParentSelector() );
        sga.setCrossoverPerformer( makeCrossoverPerformer() );
        sga.setMutationPerformer( makeMutationPerformer() );
        sga.setReplacementPerformer( makeReplacementPerformer() );
        sga.addObserver(new myProgressObserver());
        results = new SGA_Result[repetitions];
        for (int i = 0; i < repetitions; ++i)
            runOneReptition(i+1);
        findBestResult();
        writeResults();
    }
    
    public void runOneReptition(int nr)
    {
        System.out.println("Repetition " + nr + "/" + repetitions);
        lastDisplayedPerc = 0;
        sga.maximize(F);
        results[nr-1] = sga.getResult();
        //System.out.println();
    }
    
    private class myProgressObserver implements ProgressObserver
    {
        @Override
        public void currentIteration(int i, boolean solutionImproved)
        {
            int p = 100 * i / params.maxIterations;
            /*if (p > lastDisplayedPerc)
            {
                System.out.print(p + " ");
                System.out.flush();
                lastDisplayedPerc = p;
            }*/
        }        
    }

    abstract protected RandomPopulationGenerator<Individual> makeRandomPopulationGenerator();

    abstract protected ParentSelector<Individual> makeParentSelector();

    abstract protected CrossoverPerformer<Individual> makeCrossoverPerformer();

    abstract protected MutationPerformer<Individual> makeMutationPerformer();
    
    abstract protected ReplacementPerformer<Individual> makeReplacementPerformer();

    private void findBestResult()
    {
        bestRes = 0;
        bestVal = results[0].bestVal;
        for (int i = 1; i < repetitions; ++i)
        {
            if (bestVal < results[i].bestVal )
            {
                bestVal = results[i].bestVal;
                bestRes = i;
            }
        }        
    }
    
    private void writeResults()
    {
        try {
            writePlotData(results[bestRes]);
            writeSummary();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SGA_Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writePlotData(SGA_Result result) throws FileNotFoundException
    {
        ensureDirExists(pathForPlotData);
        try (PrintWriter out = new PrintWriter(pathForPlotData))
        {
            out.println("Iteration;Worst;Mean;Best");
            for (int i = 0; i <= result.performedIterations; ++i)
            {
                String line =   i + ";" +
                    result.worst.get(i) + ";" +
                    result.mean.get(i) + ";" +
                    result.best.get(i);
                out.println(line);
            }
        }
    }

    private void writeSummary() throws FileNotFoundException
    {
        ensureDirExists(pathForSummary);
        try (PrintWriter out = new PrintWriter(pathForSummary))
        {
            for (int i = 0; i < repetitions; ++i)
            {
                out.println("Repetion " + (i+1) + ":");
                out.println("    Best value: " + results[i].bestVal);
                out.println("    For individual: " + results[i].bestInd);                
            }
            out.println("\n------------PARAMS------------");
            out.println(params.toString());
        }
    }

    private void ensureDirExists(String pathToFile)
    {
        int i = pathToFile.lastIndexOf('/');
        if (i > 0) {
            String pathToDir = pathToFile.substring(0, i);
            new File(pathToDir).mkdirs();
        }
    }

    public double getBestOfRepetions()
    {
        return bestVal;
    }
}
