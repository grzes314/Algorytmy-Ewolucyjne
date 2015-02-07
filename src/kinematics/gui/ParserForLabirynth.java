
package kinematics.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinematics.logic.ArmData;
import kinematics.logic.Field;
import kinematics.logic.LabData;
import kinematics.logic.OneSegment;
import kinematics.logic.Point;
import kinematics.logic.PrDataForLab;
import kinematics.logic.ProblemData;
import static kinematics.logic.Utils.degToRad;
/**
 *
 * @author Grzegorz Los
 */
public class ParserForLabirynth
{
    private BufferedReader in;
    public ProblemData read(String pathToFile) throws FileNotFoundException, IOException
    {
        File file = new File(pathToFile);
        return read(file);
    }
    
    public ProblemData read(File file)
    {
        ProblemData pData = null;
        try {
            in = new BufferedReader(new FileReader(file));
            pData = read();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParserForDynamicData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserForDynamicData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pData;
    }
    
    private ProblemData read() throws IOException
    {
        ArmData armData = readArmData();
        LabData labData = readLabData();
        return new ProblemData(
            new PrDataForLab(armData, labData) );
    }

    private ArmData readArmData() throws IOException
    {
        String line = readNextLine();
        int n = Integer.parseInt(line);
        ArmData ad = new ArmData();
        for (int i = 0; i < n; ++i)
        {
            double[] d = readDoubles(3);
            double len = d[0];
            double alfa = degToRad(d[1]);
            double beta = degToRad(d[2]);
            ad.add(new OneSegment(len, alfa, beta));
        }
        return ad;
    }


    private LabData readLabData() throws IOException
    {
        double[] ds = readDoubles(4);
        Point minArea = new Point(ds[0], ds[1]);
        Point maxArea = new Point(ds[2], ds[3]);
        
        int[] rc = readInts(2);
        LabData labData = new LabData(rc[0], rc[1], minArea, maxArea);
        readGrid(labData);
        return labData;
    }

    private void readGrid(LabData labData) throws IOException
    {
        for (int r = 0; r < labData.rows; ++r)
            readRow(labData, r);
    }
    
    private void readRow(LabData labData, int r) throws IOException
    {
        int[] is = readInts(labData.cols);
        for (int c = 0; c < labData.cols; ++c)
            labData.fields[r][c] = Field.make(is[c]);
    }
    
    private String readNextLine() throws IOException
    {
        String line = "";
        while (line.isEmpty() || line.startsWith("#") || line.startsWith("@"))
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
        int[] is = new int[k];
        int next = 0;
        for (int i = 0; next < k; i++)
        {
            if (tokens[i].trim().isEmpty())
                continue;
            is[next++] = Integer.parseInt(tokens[i]);
        }
        return is;
    }

}
