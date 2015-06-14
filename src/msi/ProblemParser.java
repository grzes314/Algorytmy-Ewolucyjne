
package msi;

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
        int N = nrOfTasks[0];
        int M = nrOfMachines[0];
        double[][] exTimes = readExTimes(N, M);
        double[][][] prepTimes = readPrepTimes(N, M);
        ProblemData pData = new ProblemData(N, M, exTimes, prepTimes);
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

    private double[][] readExTimes(int nrOfTasks, int nrOfMachines) throws IOException
    {
        double[][] exTimes = new double[nrOfMachines][];
        for (int m = 0; m < nrOfMachines; ++m)
        {
            exTimes[m] = readDoubles(nrOfTasks);
        }
        return exTimes;
    }

    private double[][][] readPrepTimes(int nrOfTasks, int nrOfMachines) throws IOException
    {
        double[][][] prepTimes = new double[nrOfMachines][][];
        for (int m = 0; m < nrOfMachines; ++m)
        {
            prepTimes[m] = readDoubleTable(nrOfTasks, nrOfTasks);
        }
        return prepTimes;
    }

    private double[][] readDoubleTable(int rows, int cols) throws IOException
    {
        double res[][] = new double[rows][];
        for (int r = 0; r < rows; ++r)
            res[r] = readDoubles(cols);
        return res;
    }
    

}
