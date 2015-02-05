
package kinematics.gui.labcreator;

import java.io.Serializable;
import kinematics.logic.Point;
import kinematics.logic.ProblemData;

/**
 *
 * @author Grzegorz Los
 */
public class BoardData implements Serializable
{
    public final ArmData armData;
    public final LabData labData;
    
    public BoardData(ArmData armData, LabData labData)
    {
        this.armData = armData;
        this.labData = labData;
    }
    
    public BoardData(Point minArea, Point maxArea)
    {
        armData = new ArmData();
        labData = new LabData(minArea, maxArea);
    }
    
    public ProblemData toProblemData() throws InvalidDataException
    {
        ProblemData.SegmentData sData = armData.toSegmentData();
        ProblemData.ObstacleData oData = labData.toObstacleData();
        Point minArea = labData.getAreaMinPoint();
        Point maxArea = labData.getAreaMaxPoint();
        Point goal = labData.getGoal();
        return new ProblemData(minArea, maxArea, goal, sData, oData);
    }
}
