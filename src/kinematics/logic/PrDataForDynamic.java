
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class PrDataForDynamic
{    
    public final Point minArea, maxArea;
    public final Point goal;
    public final ArmData armData;
    public final ObstacleData oData;

    public PrDataForDynamic(Point minArea, Point maxArea, Point goal, ArmData armData, ObstacleData oData)
    {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.goal = goal;
        this.armData = armData;
        this.oData = oData;
    }

}
