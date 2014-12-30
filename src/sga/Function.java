
package sga;

/**
 *
 * @author Grzegorz Los
 */
public interface Function<ArgType>
{
    public ValuedIndividual<ArgType> value(ArgType x);
}
