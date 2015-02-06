
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemData
{
    
    
    public final Point minArea, maxArea;
    public final Point goal;
    public final ArmData armData;
    public final ObstacleData oData;

    public ProblemData(Point minArea, Point maxArea, Point goal, ArmData sData, ObstacleData oData)
    {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.goal = goal;
        this.armData = sData;
        this.oData = oData;
    }
}
