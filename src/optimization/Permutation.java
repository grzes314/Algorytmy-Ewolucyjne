
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
    
    public Permutation(Permutation other)
    {
        perm = other.toArray();
    }
    
    public Permutation(Permutation other, int i, int j)
    {
        perm = other.toArray();
        int aux = perm[i];
        perm[i] = perm[i+1];
        perm[i+1] = aux;
    }
    
    private Permutation(int[] perm)
    {
        this.perm = perm;
    }
    
    public int at(int i)
    {
        return perm[i];
    }
    
    public int getSize()
    {
        return perm.length;
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
    
    public int[] toArray()
    {
        int[] arr = new int[perm.length];
        for (int i = 0; i < arr.length; ++i)
            arr[i] = perm[i];
        return arr;
    }
    
    public String getString(boolean plusOne)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < perm.length; ++i)
            sb.append(perm[i] + (plusOne ? 1 : 0)).append(' ');
        return sb.toString();
    }
}
