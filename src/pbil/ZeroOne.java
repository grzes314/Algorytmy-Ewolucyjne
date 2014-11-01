
package pbil;

/**
 *
 * @author grzes
 */
public enum ZeroOne implements Chromosome
{
    ZERO, ONE;
    
    public int val()
    {
        return this == ZERO ? 0 : 1;
    } 
}
