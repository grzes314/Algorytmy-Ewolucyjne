
package simplealgs;

import java.util.ArrayList;
import java.util.List;
import optimization.Permutation;
import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class ConsecutiveTransChooser implements NeighbourhoodChooser<Permutation>
{

    @Override
    public List<Permutation> choose(Permutation perm)
    {
        int n = perm.getSize();
        ArrayList<Permutation> perms = new ArrayList<>();
        for (int i = 0; i < n-1; ++i)
        {
            perms.add(new Permutation(perm, i, i+1));
        }
        return perms;
    }

    @Override
    public Permutation chooseOne(Permutation ind)
    {
        if (ind.getSize() < 2)
            return null;
        int i = RandomnessSource.rand.nextInt(ind.getSize()-1);
        return new Permutation(ind, i, i+1);
    }

}
