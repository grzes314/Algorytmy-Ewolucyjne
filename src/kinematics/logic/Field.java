
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
   
public enum Field
{
    Empty(0),
    Wall(1),
    Goal(2);
    
    public final int code;

    private Field(int code)
    {
        this.code = code;
    }
    
    public static Field make(int code)
    {
        switch (code)
        {
            case 0:
                return Empty;
            case 1:
                return Wall;
            case 2:
                return Goal;
            default:
                throw new RuntimeException("Invalid Field code");
        }
    }
}