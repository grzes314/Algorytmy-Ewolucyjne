
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class PrDataForLab
{
    public final ArmData armData;
    public final LabData labData;

    public PrDataForLab(ArmData armData, LabData labData)
    {
        this.armData = armData;
        this.labData = labData;
    }
    
    public PrDataForDynamic toDynamicData()
    {
        Point goal = new Point(0,0);
        try {
            goal = labData.getGoal();
        } catch (InvalidDataException ex) {}
        
        return new PrDataForDynamic(
            labData.minArea,
            labData.maxArea,
            goal,
            armData,
            labData.toObstacleData()
        );
    }
}
