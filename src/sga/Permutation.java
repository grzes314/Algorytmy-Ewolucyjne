
package sga;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class Permutation
{
    /**
     * Number of objects. Objects are numbered from 0 to n-1.
     */
    public final int n;
    /**
     * Number of groups;
     */
    public final int k;
    
    protected final ArrayList< ArrayList<Integer> > segments;

    public Permutation(int n, int k)
    {
        this.n = n;
        this.k = k;
        segments = new ArrayList<>();
        for (int i = 0; i < k; ++i)
            segments.add(new ArrayList<>());
    }
    
    public void readSeperatorCode(int[] perm)
    {
        int j = 0;
        for (int seg = 0; seg < k; ++seg)
        {
            while (j < perm.length && perm[j] < n)
            {
                segments.get(seg).add(perm[j]);
                j++;
            }
            j++;
        }
    }
    
    public int[] toSeperatorCode()
    {
        int[] res = new int[n+k-1];
        int pos = 0;
        for (int i = 0; i < k; ++i)
        {
            ArrayList<Integer> segment = segments.get(i);
            for (int j = 0; j < segment.size(); ++j)
            {
                res[pos] = segment.get(j);
                pos++;
            }
            if (i < k - 1)
            {
                res[pos] = n + i;
                pos++;
            }
        }
        return res;
    }
    
    public int getNrOfObjects()
    {
        return n;
    }
    
    public int getNrOfGroups()
    {
        return k;
    }

    public int[] toArray()
    {
        int[] res = new int[n];
        int pos = 0;
        for (int i = 0; i < k; ++i)
        {
            ArrayList<Integer> segment = segments.get(i);
            for (int j = 0; j < segment.size(); ++j)
            {
                res[pos] = segment.get(j);
                pos++;
            }
        }
        return res;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int[] arr = toSeperatorCode();
        for (int i = 0; i < arr.length; ++i)
            if (arr[i] < n)
                sb.append(arr[i]).append(' ');
            else
                sb.append('|').append(' ');
        return sb.toString();
    }
}
