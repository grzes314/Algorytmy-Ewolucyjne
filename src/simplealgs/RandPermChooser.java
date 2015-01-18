
package simplealgs;

import optimization.Permutation;

/**
 *
 * @author Grzegorz Los
 */
public class RandPermChooser implements RandIndChooser<Permutation>
{
    private int n;

    public RandPermChooser(int permLength)
    {
        this.n = permLength;
    }

    public void setPermLength(int n)
    {
        this.n = n;
    }
    
    @Override
    public Permutation getNext()
    {
        return Permutation.getRand(n);
    }

}
