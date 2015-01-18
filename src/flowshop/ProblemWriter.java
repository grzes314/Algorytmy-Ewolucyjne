
package flowshop;

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
            Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
            return ;
        }
        try {
            write(pData);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void write(ProblemData pData) throws IOException
    {
        out.write("" + pData.nrOfTasks + "\n");
        out.write("" + pData.nrOfMachines + "\n");
        out.write("" + pData.deadline + "\n");
        for (int i = 0; i < pData.nrOfTasks; ++i)
        {
            out.write("" + pData.tasks.get(i).price);
            if (i < pData.nrOfTasks-1)
                out.write(" ");
            else
                out.write("\n");
        }
        for (int m = 0; m < pData.nrOfMachines; ++m)
        {
            for (int i = 0; i < pData.nrOfTasks; ++i)
            {
                out.write("" + pData.tasks.get(i).times[m]);
                if (i < pData.nrOfTasks-1)
                    out.write(" ");
                else
                    out.write("\n");
            }
        }
    }
}

