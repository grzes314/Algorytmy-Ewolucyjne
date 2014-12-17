
package sga;

/**
 *
 * @author Grzegorz Los
 */
public class BitString
{
    public final boolean[] bits;

    public BitString(int n)
    {
        bits = new boolean[n];
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length; ++i)
            sb.append(bits[i] ? '1' : '0');
        return sb.toString();
    }
}
