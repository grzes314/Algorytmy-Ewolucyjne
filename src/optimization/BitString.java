
package optimization;

/**
 *
 * @author Grzegorz Los
 */
public class BitString implements Copyable<BitString>
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

    @Override
    public BitString getCopy()
    {
        BitString bstr = new BitString(bits.length);
        for (int i = 0; i < bits.length; ++i)
            bstr.bits[i] = bits[i];
        return bstr;
    }
}
