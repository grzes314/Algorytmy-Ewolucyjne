
package kinematics.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinematics.logic.ProblemData;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemParser
{
    private BufferedReader in;
    private File file;
    private final ParserForDynamicData pDynamic = new ParserForDynamicData();
    private final ParserForLabirynth pLab = new ParserForLabirynth();
    
    public ProblemData read(String pathToFile) throws FileNotFoundException, IOException
    {
        file = new File(pathToFile);
        return read(file);
    }
    
    public ProblemData read(File file)
    {
        ProblemData pData = null;
        try {
            in = new BufferedReader(new FileReader(file));
            pData = read(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParserForDynamicData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserForDynamicData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pData;
    }
    
    
    private ProblemData read(BufferedReader in) throws IOException
    {
        String line = readNextLine().trim();
        if (line.startsWith("@"))
        {
            String kind = line.substring(1).trim();
            switch (kind)
            {
                case "Dynamic":
                    return pDynamic.read(file);
                case "Labirynth":
                    return pLab.read(file);
                default:
                    throw new RuntimeException("Unknown data kind");
            }
        }
        else return pDynamic.read(file);
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

}
