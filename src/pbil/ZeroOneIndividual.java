
package pbil;

/**
 *
 * @author grzes
 */
public class ZeroOneIndividual implements Individual
{

    public ZeroOneIndividual(int nrOfChromosomes) {
        this.nrOfChromosomes = nrOfChromosomes;
        chromosomes = new ZeroOne[nrOfChromosomes];
    }

    @Override
    public int getNrOfChromosomes() {
        return nrOfChromosomes;
    }

    @Override
    public ZeroOne getChromosome(int nr) {
        return chromosomes[nr];
    }
    
    public void setChromosome(int nr, ZeroOne c) {
        chromosomes[nr] = c;
    }
    
    final int nrOfChromosomes;
    final ZeroOne[] chromosomes;
}
