
package flowshop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import optimization.Permutation;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class Looper
{
    private final ProblemParser parser = new ProblemParser();
    private final List<AlgRunner> algRunners = new ArrayList<>();
    int repetitions = 1;

    public void setRepetitions(int repetitions)
    {
        this.repetitions = repetitions;
    }
    
    public void addAlgRunner(AlgRunner ar)
    {
        algRunners.add(ar);
    }
    
    public void runAllTests(String pathIn, String pathOut)
    {
        File dir = new File(pathIn);
        File[] tests = dir.listFiles((File pathname) -> pathname.getName().endsWith(".txt"));
        for (File file: tests)
        {
            System.out.println("RUNNING " + file.getName());
            try {
                runOneTest(file, getOutFile(file, pathOut));
            } catch (IOException ex) {
                Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(file.getName() + " failed :((((((((");
            }
        }
    }

    private File getOutFile(File inFile, String pathOut)
    {
        String inName = inFile.getName();
        int l = inName.length();
        String outName = pathOut + "/" + inFile.getName().substring(0, l-3) + "out";
        return new File(outName);
    }

    private void runOneTest(File inFile, File outFile) throws IOException
    {
        ProblemData pData = parser.read(inFile);
        Valuator F = new Valuator(pData);
        ArrayList<Results> results = runAllAlgsOnOneTest(F);
        writeResults(outFile, results);
    }

    private ArrayList<Results> runAllAlgsOnOneTest(Valuator F)
    {
        ArrayList<Results> results = new ArrayList<>();
        for (AlgRunner ar: algRunners)
        {
            System.out.println("     -- " + ar.getName());
            Results r = runAlgOnOneTest(ar, F);
            r.calcStats();
            results.add(r);            
        }
        return results;
    }

    private Results runAlgOnOneTest(AlgRunner ar, Valuator F)
    {
        Results results = new Results();
        ar.prepare(F.pData);
        for (int i = 0; i < repetitions; ++i)
        {
            double t = System.nanoTime();
            ValuedIndividual<Permutation> vi = ar.run(F);
            double diff = (System.nanoTime() - t) / 1e9;
            results.addResult(vi, diff);
        }
        return results;
    }

    private void writeResults(File outFile, ArrayList<Results> results)
    {
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(outFile));
        } catch (IOException ex) {
            Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
            return ;
        }
        for (int i = 0; i < algRunners.size(); ++i)
        {
            try {
                appendResult(out, algRunners.get(i), results.get(i));
            } catch (IOException ex) {
                Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void appendResult(BufferedWriter out, AlgRunner ar, Results results) throws IOException
    {
        out.write("************ " + ar.getName() + " ************\n");
        out.write("Params:\n");
        out.write(ar.getDesc()); out.newLine();
        out.write("Results:\n");
        out.write("average time = " + String.format("%.2f", results.getAvgTime())); out.newLine();
        out.write("best = " + results.getBest()); out.newLine();
        out.write("mean = " + results.getMean()); out.newLine();
        out.write("worst = " + results.getWorst()); out.newLine();
        out.write("ind = " + results.getBestInd().getString(true)); out.newLine();
        out.write("_____________________________________________________________________________");
        out.newLine(); out.newLine();
    }
}
