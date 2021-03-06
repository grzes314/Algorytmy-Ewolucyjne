
package kinematics.logic;

import sga.Population;
import sga.SimplePopulation;
import static optimization.RandomnessSource.rand;
/**
 *
 * @author Grzegorz Los
 */
public class RandomPopulationGeneratorIK implements sga.RandomPopulationGenerator<Configuration>
{
    private final ArmData armData;

    public RandomPopulationGeneratorIK(ArmData armData)
    {
        this.armData = armData;
    }

    @Override
    public Population<Configuration> generate(int N)
    {
        SimplePopulation<Configuration> population = new SimplePopulation<>();
        for (int i = 0; i < N; ++i)
            population.addIndividual( generateConfiguration() );
        return population; 
    }

    private Configuration generateConfiguration()
    {
        int n = armData.getSize();
        Configuration conf = new Configuration(n);
        for (int i = 0; i < n; ++i)
        {
            OneSegment s = armData.get(i);
            conf.angle[i] = randAngle(s.minAngle, s.maxAngle);
        }
        return conf;
    }

    private double randAngle(double a, double b)
    {
        double u = rand.nextDouble();
        return a + u * (b - a);
    }

}
