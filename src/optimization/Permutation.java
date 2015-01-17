
package optimization;

import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class Permutation
{
    private final int[] perm;
    
    public Permutation(int n)
    {
        perm = new int[n];
        for (int i = 0; i < n; ++i)
            perm[i] = i;
    }
    
    private Permutation(int[] perm)
    {
        this.perm = perm;
    }
    
    public int at(int i)
    {
        return perm[i];
    }
    
    public static Permutation getRand(int m)
    {     
        int[] perm = new int[m];
        for (int i = 0; i < m; i++) {
            int j = rand.nextInt(i+1);
            perm[i] = perm[j];
            perm[j] = i;
        }
        return new Permutation(perm);
    }
}
