
package kinematics.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinematics.logic.OneSegment;
import kinematics.logic.PrDataForDynamic;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemDynWriter
{
    private BufferedWriter out;
    private PrDataForDynamic pData;
    
    public void write(String pathToFile, PrDataForDynamic pData)
    {
        write(new File(pathToFile), pData);
    }
    
    public void write(File file, PrDataForDynamic pData)
    {
        try {
            out = new BufferedWriter(new FileWriter(file));
            this.pData = pData;
            write();
        } catch (IOException ex) {
            Logger.getLogger(ProblemDynWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException ex) {
                Logger.getLogger(ProblemDynWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void write() throws IOException
    {
        writeAreaLimits();
        writeGoal();
        writeSegments();
        writeObstacles();
    }

    private void writeAreaLimits() throws IOException
    {
        String line = "" +
            pData.minArea.x + " " +
            pData.minArea.y + " " +
            pData.maxArea.x + " " +
            pData.maxArea.y + "\n";
        out.write(line);
    }

    private void writeGoal() throws IOException
    {
        String line = "" +
            pData.goal.x + " " +
            pData.goal.y + "\n";
        out.write(line);
    }

    private void writeSegments() throws IOException
    {
        int n = pData.armData.getSize();
        out.write(n + "\n");
        for (int i = 0; i < n; ++i)
        {
            OneSegment s = pData.armData.segments.get(i);
            String line = "" +
                s.length + " " +
                s.minAngle + " " +
                s.maxAngle + "\n";
            out.write(line);
        }
    }

    private void writeObstacles() throws IOException
    {
        int k = pData.oData.k;
        out.write(k + "\n");
        for (int i = 0; i < k; ++i)
        {
            String line = "" +
                pData.oData.from[i].x + " " +
                pData.oData.from[i].y + " " +
                pData.oData.to[i].x + " " +
                pData.oData.to[i].y + " " +
                pData.oData.h[i] + " " +
                pData.oData.w[i] + " " +
                pData.oData.v[i] + "\n" ;
            out.write(line);
        }
    }
}
