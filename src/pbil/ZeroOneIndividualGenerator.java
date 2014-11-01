
package pbil;

/**
 *
 * @author grzes
 */
public class ZeroOneIndividualGenerator implements IndividualGenerator
{

    public ZeroOneIndividualGenerator(int n)
    {
        this.n = n;
        distrs = new BinaryDistr[n];
        initDistrs();
    }
    
    private void initDistrs()
    {
        for (int i = 0; i < n; ++i)
            distrs[i] = new BinaryDistr(0.5);
    }

    @Override
    public ZeroOneIndividual generate()
    {
        ZeroOneIndividual ind = new ZeroOneIndividual(n);
        for (int i = 0; i < n; ++i)
            ind.setChromosome(i, distrs[i].draw());
        return ind;
    }

    @Override
    public void improve(Individual ind, double learnRate)
    {
        ZeroOneIndividual zo = (ZeroOneIndividual) ind;
        for (int i = 0; i < n; ++i)
            distrs[i].improve(zo.getChromosome(i), learnRate);
    }

    @Override
    public void reset()
    {
        initDistrs();
    }

    @Override
    public void mutate(double mutationProb, double mutationRate)
    {
        for (int i = 0; i < n; ++i)
            distrs[i].mutate(mutationProb, mutationRate);
    }
    
    /**
     * Numbe of chromosomes.
     */
    private final int n;
    private final BinaryDistr[] distrs;
}
