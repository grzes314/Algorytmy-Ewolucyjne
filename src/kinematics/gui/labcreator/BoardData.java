
package kinematics.gui.labcreator;

import java.io.Serializable;
import kinematics.logic.ObstacleData;
import kinematics.logic.Point;
import kinematics.logic.ProblemData;
import kinematics.logic.ArmData;

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
    
    public BoardData(int rows, int cols)
    {
        armData = new ArmData();
        labData = new LabData(rows, cols);
    }
    
    public ProblemData toProblemData() throws InvalidDataException
    {
        ObstacleData oData = labData.toObstacleData();
        Point minArea = labData.getAreaMinPoint();
        Point maxArea = labData.getAreaMaxPoint();
        Point goal = labData.getGoal();
        return new ProblemData(minArea, maxArea, goal, armData, oData);
    }
}
