
package sga;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import static sga.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class GrPMX implements CrossoverPerformer<GrPerm>
{
    private SimplePopulation<GrPerm> newPopulation;

    @Override
    public Population<GrPerm> crossover(Population<GrPerm> parents, double thetaC)
    {
        newPopulation = new SimplePopulation<>();
        int N = parents.getSize();
        if (N % 2 != 0)
            throw new RuntimeException("Size of parent populations should be even");
        for (int i = 0; i < N; i += 2)
        {
            if (rand.nextDouble() < thetaC)
                crossover(parents.getIndividual(i), parents.getIndividual(i+1));
            else
            {
                newPopulation.addIndividual(parents.getIndividual(i));
                newPopulation.addIndividual(parents.getIndividual(i+1));
            }
        }
        return newPopulation;
    }
    
    private void crossover(GrPerm p1, GrPerm p2)
    {
        int k = rand.nextInt(p1.getNrOfGroups());
        ArrayList<Integer> s1 = p1.segments.get(k);
        ArrayList<Integer> s2 = p2.segments.get(k);
        newPopulation.addIndividual( replacement(p1, k, s2) );
        newPopulation.addIndividual( replacement(p2, k, s1) );
    }

    private GrPerm replacement(GrPerm p, int segIndex, ArrayList<Integer> replacement)
    {
        ArrayList<Integer> prevSegment = p.segments.get(segIndex);
        Map<Integer, Integer> map = makeMap(prevSegment, replacement);
        GrPerm newPerm = new GrPerm(p.getNrOfObjects(), p.getNrOfGroups());
        for (int i = 0; i < p.getNrOfGroups(); ++i)
        {
            if (i == segIndex) {
                newPerm.segments.set(i, replacement(prevSegment, replacement, map));
            } else {
                fillSegment(newPerm.segments.get(i), p.segments.get(i), map);
            }
        }
        return newPerm;
    }

    private ArrayList<Integer> replacement(ArrayList<Integer> prevSegment, ArrayList<Integer> replacement, Map<Integer, Integer> map)
    {
        ArrayList<Integer> finalVersion = new ArrayList<>();
        for (int i = 0; i < replacement.size(); ++i)
            finalVersion.add(replacement.get(i));
        if (replacement.size() < prevSegment.size())
        {
            for (int i = replacement.size(); i < prevSegment.size(); ++i)
            {                
                int v = findNewValue(prevSegment.get(i), map);
                if (v >= 0)
                    finalVersion.add(v);
            }
        }
        return finalVersion;
    }

    private void fillSegment(ArrayList<Integer> newSegment, ArrayList<Integer> prevSegment, Map<Integer, Integer> map)
    {
        for (int i = 0; i < prevSegment.size(); ++i)
        {
            int v = findNewValue(prevSegment.get(i), map);
            if (v >= 0)
                newSegment.add(v);
        }
    }

    private int findNewValue(int prevValue, Map<Integer, Integer> map)
    {
        int i = 0;
        while (map.containsKey(prevValue))
        {
            prevValue = map.get(prevValue);
            if (i > 10000)
                throw new RuntimeException("Something went terribly wrong");
        }
        return prevValue;
    }

    private Map<Integer, Integer> makeMap(ArrayList<Integer> prevSegment, ArrayList<Integer> replacement)
    {
        int l = replacement.size();
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < l; ++i)
        {
            if (i < prevSegment.size())
                map.put(replacement.get(i), prevSegment.get(i));
            else
                map.put(replacement.get(i), -1);
        }
        return map;
    }
}
