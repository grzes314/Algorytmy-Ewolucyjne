
package sga_presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import sga.GrPMX;
import sga.GrPerm;
import sga.Population;
import optimization.RandomnessSource;
import sga.SGA_Params;
import sga.SimplePopulation;

/**
 *
 * @author Grzegorz Los
 */
public class SGA_Presentation
{    
    //For tests only;
    public static SGA_Params paramsForTests = new SGA_Params(
        1000, // populationSize
        500, // nrOfParents,
        0.99, // thetaC,
        0.1, // thetaM,
        1000 // maxIterations
    );
    
    private final String mode;
    private final String pathToData;
    private final String pathToResults;
    private String nameOfFileWithBestRes;
    private double bestOfTheBest;
    
    public SGA_Presentation(String[] args)
    {
        mode = args[0];
        pathToData = args[1];
        pathToResults = args[2];
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //RandomnessSource.reset(112358);
        //new SGA_Presentation().setVisible(true);
        //testOneMax();
        //testTSP();
        //testPMX();
        new SGA_Presentation(args).runOneTSP(new File("tsp/kroA200_ready.tsp"), paramsForTests, 10, 50);
        //new SGA_Presentation(args).run();
        
    }
    
    public void run() throws IOException
    {
        switch(mode)
        {
            case "TSP":
                runManyTSP();
                break;
            default:
                System.err.println("Unknown type of task: " + mode);
        }
    }
    
    private void runManyTSP()
    {
        File dir = new File(pathToData);
        File[] tasks = dir.listFiles((File pathname) -> pathname.getName().endsWith("_ready.tsp"));
        for (File file: tasks)
        {
            try {
                runOneTSP(file);
            } catch (Exception ex) {
                System.err.println(file.getName() + " failed :((((((((");
                ex.printStackTrace();
            }
        }
    }
    
    private void runOneTSP(File task)
    {
        String name = readMainName(task.getName());
        FullGraph g = new GraphReader().read(task);
        System.out.println("********************************************************************");
        System.out.println("**************************** " + name);
        System.out.println("*********************************************************************");
        bestOfTheBest = Double.NEGATIVE_INFINITY;
        SGA_ParamsExt[] params = makeParamsForTSP();
        for (int i = 0; i < params.length; ++i)
        {
            System.out.println("CONFIGURATION " + (i+1) + "/" + params.length);
            RandomnessSource.reset(112358);
            runOneConfTSP(g, params[i].main, params[i].nrOfPermGroups, name, i+1, 5);            
        }
        saveBestOfTheBest(name);
        System.out.println("\n\n");
    }
    
    private void runOneTSP(File task, SGA_Params params, int nrOfGroups, int repetitions)
    {
        String name = readMainName(task.getName());
        FullGraph g = new GraphReader().read(task);
        RandomnessSource.reset(112358);
        runOneConfTSP(g, params, nrOfGroups, name, 0, repetitions);    
        System.out.println();
    }
    
    private SGA_ParamsExt[] makeParamsForTSP()
    {
        int[] nrOfGroups = {5, 10, 20};
        int[] popSize = {1000, 5000};
        int[] nrOfParents = {500, 1000};
        double[] thetaC = {0.95, 1.0};
        double[] thetaM = {0, 0.05, 0.1};
        int maxIterations = 500;
        SGA_ParamsExt[] params = new SGA_ParamsExt[ nrOfGroups.length * popSize.length *
                                                    nrOfParents.length * thetaC.length * thetaM.length ];
        int paramCounter = 0;
        for (int a = 0; a < nrOfGroups.length; ++a)
        for (int b = 0; b < popSize.length; ++b)
        for (int c = 0; c < nrOfParents.length; ++c)
        for (int d = 0; d < thetaC.length; ++d)
        for (int e = 0; e < thetaM.length; ++e)
        {
            SGA_Params main = new SGA_Params(
                popSize[b],
                nrOfParents[c],
                thetaC[d],
                thetaM[e],
                maxIterations
            );
            params[paramCounter] = new SGA_ParamsExt(main, nrOfGroups[a]);
            paramCounter++;
        }
        return params;
    }
    
    private String readMainName(String fullName)
    {
        int l = fullName.length();
        return fullName.substring(0, l-10);
    }
    
    public void runOneConfTSP(FullGraph g, SGA_Params params, int nrOfPermGroups, String name, int conf, int repetitions)
    {
        CycleValuator valuator = new CycleValuator(g);
        SGA_Runner runner = new SGA_RunnerForTSP(g.getNrOfNodes(), nrOfPermGroups);
        runner.run(params, valuator,
                   pathToResults + "/" + name + "/conf" + conf + "_" + nrOfPermGroups + ".plot",
                   pathToResults + "/" + name + "/conf" + conf + "_" + nrOfPermGroups + ".txt",
                   repetitions);
        double res = runner.getBestOfRepetions();
        if (res > bestOfTheBest)
        {
            nameOfFileWithBestRes = "conf" + conf + "_" + nrOfPermGroups + ".txt";
            bestOfTheBest = res;
        }
    }
    
    private void saveBestOfTheBest(String name)
    {
        String path = pathToResults + "/" + name + "/BEST.txt";
        try (PrintWriter out = new PrintWriter(path))
        {
            out.println("Best value: " + bestOfTheBest);
            out.println("In file: " + nameOfFileWithBestRes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SGA_Presentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    
    
    
    public static void testOneMax()
    {
        SGA_Runner runner = new SGA_RunnerForBitString(100);
        runner.run(paramsForTests, new OneMax(), "forPlot.data", "summary.txt", 5);        
    }
    
    public static void testTSP()
    {
        FullGraph g = new GraphReader().read("tsp/kroA150_ready.tsp");
        CycleValuator valuator = new CycleValuator(g);
        SGA_Runner runner = new SGA_RunnerForTSP(g.getNrOfNodes(), 5);
        runner.run(paramsForTests, valuator, "tspForPlot.data", "tspSummary.txt", 5);
    }
    
    public static void testPMX()
    {
        int[] t1 = { 0, 1, 2, 3, 4, 10, 5, 6, 7, 8, 11, 9 };
        int[] t2 = { 4, 3, 5, 11, 6, 0, 1, 2, 9, 7, 10, 8 };
        GrPerm p1 = new GrPerm(10, 3);
        GrPerm p2 = new GrPerm(10, 3);
        p1.readSeperatorCode(t1);
        p2.readSeperatorCode(t2);
        SimplePopulation<GrPerm> pop = new SimplePopulation();
        pop.addIndividual(p1);
        pop.addIndividual(p2);
        GrPMX pmx = new GrPMX();
        Population<GrPerm> newPop = pmx.crossover(pop, 1);
        System.out.println(newPop.getIndividual(0));
        System.out.println(newPop.getIndividual(1));
    }

}

class SGA_ParamsExt
{
    SGA_Params main;
    int nrOfPermGroups;

    public SGA_ParamsExt(SGA_Params main, int nrOfPermGroups)
    {
        this.main = main;
        this.nrOfPermGroups = nrOfPermGroups;
    }
}