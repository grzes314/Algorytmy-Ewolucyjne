
package optimization;

import java.util.Arrays;
import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class Permutation implements Copyable<Permutation>
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
        perm[i] = perm[j];
        perm[j] = aux;
    }
    
    public Permutation(int[] perm)
    {
        this.perm = perm;
        
        /*int[] a = new int[perm.length];
        for (int i = 0; i < perm.length; ++i)
            a[perm[i]] = 1;
        for (int i = 0; i < perm.length; ++i)
            if (a[i] != 1)
                throw new RuntimeException("To nie permutacja!!!!1!11oneone");   */     
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

    @Override
    public Permutation getCopy()
    {
        return new Permutation(this);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int its = (perm.length <= 12 ? perm.length : 10);
        for (int i = 0; i < its; ++i)
            sb.append(perm[i]).append(' ');
        if (perm.length > 12)
            sb.append("... (").append(perm.length-10).append(" more)");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj.getClass() != Permutation.class)
            return false;
        Permutation other = (Permutation) obj;
        if (perm.length != other.perm.length)
            return false;
        for (int i = 0; i < perm.length; ++i)
            if (perm[i] != other.perm[i])
                return false;
        return true;
    }
}
