
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class SimplePredictor implements Predictor
{
    @Override
    public Board predict(Board board, double time)
    {
        Board b = new Board(board);
        b.move(time);
        return b;
    }

}
