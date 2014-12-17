
package sga_presentation;

/**
 *
 * @author Grzegorz Los
 */
public class FullGraph
{
    private final int nodes;
    private final double[][] dist;

    public FullGraph(int nodes)
    {
        this.nodes = nodes;
        dist = new double[nodes][nodes];
    }
    
    public double get(int i, int j)
    {
        return dist[i][j];
    }
    
    public void set(int i, int j, double val)
    {
        dist[i][j] = val;
    }

    int getNrOfNodes()
    {
        return nodes;
    }

}
