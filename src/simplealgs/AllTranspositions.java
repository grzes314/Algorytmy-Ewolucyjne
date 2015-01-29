
package simplealgs;

import java.util.ArrayList;
import java.util.List;
import optimization.Permutation;
import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class AllTranspositions implements NeighbourhoodChooser<Permutation>
{

    @Override
    public List<Permutation> choose(Permutation ind)
    {
        List<Permutation> list = new ArrayList<>();
        for (int i = 0; i < ind.getSize(); ++i)
        {
            for (int j = i+1; j < ind.getSize(); ++j)
                list.add( new Permutation(ind, i, j) );     
        }
        return list;
    }

    @Override
    public Permutation chooseOne(Permutation ind)
    {
        int i = RandomnessSource.rand.nextInt(ind.getSize());
        int j = RandomnessSource.rand.nextInt(ind.getSize());
        return new Permutation(ind, i, j); 
    }

}
