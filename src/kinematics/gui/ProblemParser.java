
package kinematics.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinematics.logic.Point;
import kinematics.logic.ProblemData;
import kinematics.logic.ObstacleData;
import kinematics.logic.ArmData;
import kinematics.logic.Vector;

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
    
    public ProblemData read(File file)
    {
        ProblemData pData = null;
        try {
            in = new BufferedReader(new FileReader(file));
            pData = read(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProblemParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProblemParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pData;
    }
    
    private ProblemData read(BufferedReader in) throws IOException
    {
        double[] ds = readDoubles(4);
        Point minArea = new Point(ds[0], ds[1]), maxArea = new Point(ds[2], ds[3]);
        Point goal = readGoal();    
        ArmData sData = readSegmentData();
        ObstacleData oData = readObstacleData();
        return new ProblemData(minArea, maxArea, goal, sData, oData);
    }

    private ArmData readSegmentData() throws IOException
    {
        String line = readNextLine();
        int n = Integer.parseInt(line);
        double[] length = new double[n];
        double[] alfa   = new double[n];
        double[] beta   = new double[n];
        for (int i = 0; i < n; ++i)
        {
            double[] d = readDoubles(3);
            length[i] = d[0];
            alfa[i] = d[1] * Math.PI / 180.0;
            beta[i] = d[2] * Math.PI / 180.0;
        }
        return new ArmData(length, alfa, beta);
    }

    private ObstacleData readObstacleData() throws IOException
    {
        String line = readNextLine();
        int k = Integer.parseInt(line);
        Point[] from = new Point[k];
        Point[] to = new Point[k];
        double[] h   = new double[k];
        double[] w   = new double[k];
        double[] v   = new double[k];
        for (int i = 0; i < k; ++i)
        {
            double[] d = readDoubles(7);
            from[i] = new Point(d[0], d[1]);
            to[i] = new Point(d[2], d[3]);
            h[i] = d[4];
            w[i] = d[5];
            v[i] = d[6];
        }
        return new ObstacleData(k, from, to, h, w, v); 
    }

    private Point readGoal() throws IOException
    {
        double[] d = readDoubles(2);
        return new Point(d[0], d[1]);
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
}
