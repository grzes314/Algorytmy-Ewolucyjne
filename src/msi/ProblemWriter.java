
package msi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemWriter
{
    BufferedWriter out;
    
    public void write(ProblemData pData, String pathToFile)
    {
        try {
            out = new BufferedWriter(new FileWriter(pathToFile));
        } catch (IOException ex) {
            Logger.getLogger(ProblemWriter.class.getName()).log(Level.SEVERE, null, ex);
            return ;
        }
        try {
            write(pData);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ProblemWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void write(ProblemData pData) throws IOException
    {
        out.write("# Nr of tasks\n");
        out.write("" + pData.nrOfTasks + "\n");
        out.write("# Nr of machines\n");
        out.write("" + pData.nrOfMachines + "\n");
        out.write("# Execution times\n");
        for (int m = 0; m < pData.nrOfMachines; ++m)
        {
            for (int i = 0; i < pData.nrOfTasks; ++i)
            {
                out.write("" + pData.exTimes[m][i]);
                if (i < pData.nrOfTasks-1)
                    out.write(" ");
                else
                    out.write("\n");
            }
        }
        out.write("\n\n########### Preperation times #############\n");
        for (int m = 0; m < pData.nrOfMachines; ++m)
        {
            out.write("\n# Prep time on machine " + m + "\n");
            for (int i = 0; i < pData.nrOfTasks; ++i)
            {
                for (int j = 0; j < pData.nrOfTasks; ++j)
                {
                    out.write("" + pData.prepTimes[m][i][j]);
                    if (j < pData.nrOfTasks-1)
                        out.write(" ");
                    else
                        out.write("\n");
                }
            }
        }
    }
}

