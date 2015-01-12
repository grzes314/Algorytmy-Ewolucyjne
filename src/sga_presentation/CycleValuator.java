
package sga_presentation;

import optimization.Function;
import sga.GrPerm;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class CycleValuator implements Function<GrPerm>
{
    private final FullGraph graph;
    private int[] cycle;
    
    public CycleValuator(FullGraph graph)
    {
        this.graph = graph;
    }

    @Override
    public ValuedIndividual<GrPerm> value(GrPerm perm)
    {
        cycle = perm.toArray();
        double sum = 0;
        int N = cycle.length;
        for (int i = 1; i < N; ++i)
            sum += graph.get(cycle[i-1], cycle[i]);
        sum += graph.get(cycle[N-1], cycle[0]);
        return new ValuedIndividual<>(perm, -sum);
    }
}
