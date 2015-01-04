
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class DummyPredictor implements Predictor
{

    @Override
    public Board predict(Board board, double time)
    {
        return board;
    }

}
