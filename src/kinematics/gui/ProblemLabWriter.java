
package kinematics.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinematics.logic.Field;
import kinematics.logic.LabData;
import kinematics.logic.OneSegment;
import kinematics.logic.PrDataForLab;
import static kinematics.logic.Utils.radToDeg;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemLabWriter
{
    private BufferedWriter out;
    private PrDataForLab pData;
    
    public void write(String pathToFile, PrDataForLab pData)
    {
        write(new File(pathToFile), pData);
    }
    
    public void write(File file, PrDataForLab pData)
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
        out.write("@ Labirynth\n");
        writeSegments();
        writeAreaLimits();
        writeGrid();
    }

    private void writeAreaLimits() throws IOException
    {
        String line = "" +
            pData.labData.minArea.x + " " +
            pData.labData.minArea.y + " " +
            pData.labData.maxArea.x + " " +
            pData.labData.maxArea.y + "\n";
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
                radToDeg(s.minAngle) + " " +
                radToDeg(s.maxAngle) + "\n";
            out.write(line);
        }
    }

    private void writeGrid() throws IOException
    {
        LabData ld = pData.labData;
        out.write(ld.rows + " " + ld.cols + "\n");
        for (int r = 0; r < ld.rows; ++r)
            writeRow(r);
    }

    private void writeRow(int r) throws IOException
    {
        LabData ld = pData.labData;
        StringBuilder sb = new StringBuilder();
        for (int c = 0; c < ld.cols; ++c)
        {
            Field f = ld.fields[r][c];
            sb.append(f.code).append(" ");
        }
        sb.append("\n");
        out.write(sb.toString());
    }

}
