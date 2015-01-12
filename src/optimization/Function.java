
package optimization;

/**
 *
 * @author Grzegorz Los
 */
public interface Function<ArgType>
{
    public ValuedIndividual<ArgType> value(ArgType x);
}
