
package kinematics.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinematics.logic.PrDataForLab;

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
        throw new RuntimeException("No supported yet");
    }
}
