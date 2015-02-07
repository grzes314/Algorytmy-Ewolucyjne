
package kinematics.gui.labcreator;

import kinematics.logic.InvalidDataException;
import kinematics.logic.LabData;
import java.io.Serializable;
import kinematics.logic.ArmData;
import kinematics.logic.PrDataForLab;

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
    
    public BoardData(int rows, int cols, double  edgeSize)
    {
        armData = new ArmData();
        labData = new LabData(rows, cols, edgeSize);
    }
    
    public PrDataForLab toPrDataForLab() throws InvalidDataException
    {
        return new PrDataForLab(armData, labData);
    }
}
