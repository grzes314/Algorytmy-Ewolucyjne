
package sga_presentation;

import sga.Function;
import sga.Permutation;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class CycleValuator implements Function<Permutation>
{
    private final FullGraph graph;
    private int[] cycle;
    
    public CycleValuator(FullGraph graph)
    {
        this.graph = graph;
    }

    @Override
    public ValuedIndividual<Permutation> value(Permutation perm)
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
