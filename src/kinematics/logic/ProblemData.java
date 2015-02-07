
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemData
{
    public final PrDataForDynamic dynData;
    public final PrDataForLab labData;
    public final DataKind kind;
    
    public ProblemData(PrDataForDynamic dynData)
    {
        this.dynData = dynData;
        this.labData = null;
        this.kind = DataKind.Dynamic;
    }
    
    public ProblemData(PrDataForLab labData)
    {
        this.dynData = null;
        this.labData = labData;
        this.kind = DataKind.Labirynth;
    }
    
    public enum DataKind {
        Dynamic, Labirynth
    }
}
