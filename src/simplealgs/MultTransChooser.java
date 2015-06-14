
package simplealgs;

import java.util.List;
import optimization.Permutation;
import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class MultTransChooser implements NeighbourhoodChooser<Permutation>
{
    int upto;

    public MultTransChooser(int upto)
    {
        this.upto = upto;
    }
    
    @Override
    public List<Permutation> choose(Permutation ind)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Permutation chooseOne(Permutation ind)
    {
        int[] perm = ind.toArray();
        int k = RandomnessSource.rand.nextInt(upto) + 1;
        for (int xxx = 0; xxx < k; ++xxx)
        {
            int i = RandomnessSource.rand.nextInt(ind.getSize());
            int j = RandomnessSource.rand.nextInt(ind.getSize());
            int aux = perm[i];
            perm[i] = perm[j];
            perm[j] = aux;    
        }
        return new Permutation(perm);
    }

}
