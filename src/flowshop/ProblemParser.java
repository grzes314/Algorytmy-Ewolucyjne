
package flowshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemParser 
{    
    private BufferedReader in;
    public ProblemData read(String pathToFile) throws FileNotFoundException, IOException
    {
        File file = new File(pathToFile);
        return read(file);
    }
    
    public ProblemData read(File file) throws IOException
    {
        ProblemData pData = null;
        try {
            in = new BufferedReader(new FileReader(file));
            pData = read(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProblemParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProblemParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            in.close();
        }
        return pData;
    }
    
    public ProblemData read(BufferedReader in) throws IOException
    {
        int[] nrOfTasks = readInts(1);
        int[] nrOfMachines = readInts(1);
        double[] deadline = readDoubles(1);
        ProblemData pData = new ProblemData(nrOfMachines[0], nrOfTasks[0], deadline[0]);
        readTasks(pData);
        return pData;
    }

    private String readNextLine() throws IOException
    {
        String line = "";
        while (line.isEmpty() || line.startsWith("#"))
        {
            line = in.readLine();
            line = line.trim();
        }
        return line;
    }

    private double[] readDoubles(int k) throws IOException
    {
        String line = readNextLine();
        String[] tokens = line.split(" ");
        return readDoubles(k, tokens);
    }

    private double[] readDoubles(int k, String[] tokens)
    {
        double[] ds = new double[k];
        int next = 0;
        for (int i = 0; next < k; i++)
        {
            if (tokens[i].trim().isEmpty())
                continue;
            ds[next++] = Double.parseDouble(tokens[i]);
        }
        return ds;
    }


    private int[] readInts(int k) throws IOException
    {
        String line = readNextLine();
        String[] tokens = line.split(" ");
        return readInts(k, tokens);
    }

    private int[] readInts(int k, String[] tokens)
    {
        int[] ints = new int[k];
        int next = 0;
        for (int i = 0; next < k; i++)
        {
            if (tokens[i].trim().isEmpty())
                continue;
            ints[next++] = Integer.parseInt(tokens[i]);
        }
        return ints;
    }

    private void readTasks(ProblemData pData) throws IOException
    {
        double[] prices = readDoubles(pData.nrOfTasks);
        double[][] table = new double[pData.nrOfMachines][];
        for (int i = 0; i < pData.nrOfMachines; ++i)
            table[i] = readDoubles(pData.nrOfTasks);
        for (int i = 0; i < pData.nrOfTasks; ++i)
            pData.addTask(makeTask(prices, table, pData.nrOfMachines, i));
    }

    private ProblemData.Task makeTask(double[] prices, double[][] table, int nrOfMachines, int nr)
    {
        ProblemData.Task task = new ProblemData.Task(nrOfMachines, prices[nr]);
        for (int i = 0; i < nrOfMachines; ++i)
            task.times[i] = table[i][nr];
        return task;
    }

}
