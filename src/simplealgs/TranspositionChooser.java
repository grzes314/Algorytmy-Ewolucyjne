
package simplealgs;

import java.util.ArrayList;
import java.util.List;
import optimization.Permutation;
import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class TranspositionChooser implements NeighbourhoodChooser<Permutation>
{
    private int nrOfNeighbours;

    public TranspositionChooser(int nrOfNeighbours)
    {
        this.nrOfNeighbours = nrOfNeighbours;
    }

    public int getNrOfNeighbours()
    {
        return nrOfNeighbours;
    }

    public void setNrOfNeighbours(int nrOfNeighbours)
    {
        this.nrOfNeighbours = nrOfNeighbours;
    }

    @Override
    public List<Permutation> choose(Permutation ind)
    {
        ArrayList<Permutation> neighs = new ArrayList<>();
        for (int i = 0; i < nrOfNeighbours; ++i)
            neighs.add(chooseOne(ind));
        return neighs;
    }

    @Override
    public Permutation chooseOne(Permutation ind)
    {
        int i = RandomnessSource.rand.nextInt(ind.getSize());
        int j = RandomnessSource.rand.nextInt(ind.getSize());
        return new Permutation(ind, i, j);
    }

}
