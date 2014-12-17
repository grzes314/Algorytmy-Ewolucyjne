
package sga_presentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Los
 */
public class GraphReader
{
    private int N;
    private FullGraph graph;
    
    FullGraph read(File task)
    {
        try ( BufferedReader in = new BufferedReader(new FileReader(task)) ){
            return read(in);
        } catch (IOException ex) {
            Logger.getLogger(GraphReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public FullGraph read(String pathToFile)
    {
        try ( BufferedReader in = new BufferedReader(new FileReader(pathToFile)) ){
            return read(in);
        } catch (IOException ex) {
            Logger.getLogger(GraphReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private FullGraph read(BufferedReader in) throws IOException
    {
        String firstLine = in.readLine().trim();
        switch(firstLine)
        {
            case "FULL":
                return readFull(in);
            case "HALF":
                return readHalf(in);
            case "COOR":
                return readCoor(in);
            default:
                throw new RuntimeException("Unknown type of file");
        }
    }
    
    public FullGraph readFull(BufferedReader in) throws IOException
    {
        N = Integer.parseInt( in.readLine().trim() );
        graph = new FullGraph(N);
        for (int i = 0; i < N; ++i)
            readLineFull(in, i);
        return graph;
    }

    private void readLineFull(BufferedReader in, int i) throws IOException
    {
        String line = in.readLine().trim();
        String[] segments = line.split(" ");
        int j = 0;
        for (int s = 0; s < segments.length; ++s)
        {
            if (segments[s].isEmpty())
                continue;
            double dist = Double.parseDouble(segments[s]);
            graph.set(i, j, dist);
            j++;
        }
    }

    private FullGraph readHalf(BufferedReader in) throws IOException
    {
        N = Integer.parseInt( in.readLine().trim() );
        graph = new FullGraph(N);
        for (int i = 0; i < N; ++i)
            graph.set(i, i, 0);
        for (int i = 0; i < N-1; ++i)
            readLineHalf(in, i);
        return graph;   
    }

    private void readLineHalf(BufferedReader in, int i) throws IOException
    {
        String line = in.readLine().trim();
        String[] segments = line.split(" ");
        int j = 1;
        for (int s = 0; s < segments.length; ++s)
        {
            if (segments[s].isEmpty())
                continue;
            double dist = Double.parseDouble(segments[s]);
            graph.set(i, j+i, dist);
            graph.set(j+i, i, dist);
            j++;
        }
    }


    class Coor
    {
        public final double x, y;

        public Coor()
        {
            this(0,0);
        }

        public Coor(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
        
        public double dist(Coor other)
        {
            return Math.sqrt(
                (x - other.x) * (x - other.x) +
                (y - other.y) * (y - other.y)  
            );
        }
    }
        
    private FullGraph readCoor(BufferedReader in) throws IOException
    {
        N = Integer.parseInt( in.readLine().trim() );
        graph = new FullGraph(N);
        ArrayList<Coor> coords = readCoords(in);
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
                graph.set(i, j, coords.get(i).dist(coords.get(j)));
        }
        return graph;   
    }

    private ArrayList<Coor> readCoords(BufferedReader in) throws IOException
    {
        ArrayList<Coor> coords = new ArrayList<>(N);
        for (int i = 0; i < N; ++i)
            coords.add(new Coor());
        for (int i = 0; i < N; ++i)
            addCoord(in, coords);
        return coords;
    }

    private void addCoord(BufferedReader in, ArrayList<Coor> coords) throws IOException
    {
        String line = in.readLine().trim();
        String[] segments = line.split(" ");
        int i = 0;
        while (segments[i].isEmpty()) i++;
        int nr = Integer.parseInt(segments[i]) - 1;
        do { i++; } while (segments[i].isEmpty());
        double x = Double.parseDouble(segments[i]);
        do { i++; } while (segments[i].isEmpty());
        double y = Double.parseDouble(segments[i]);
        coords.set(nr, new Coor(x, y));
    }

}
