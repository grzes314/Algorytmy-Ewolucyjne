
package sga;

/**
 *
 * @author Grzegorz Los
 */
public interface Function<ArgType>
{
    public double value(ArgType x);
    
    public boolean isLastFisible();
}
