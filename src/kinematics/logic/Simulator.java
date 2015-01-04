
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class Simulator
{
    private final Board board;
    private final Configuration currConf;
    private Configuration targetConf;
    private final double armSpeed = Math.PI/4; // 45 degrees per second
    private State state = State.NOT_STARTED;
    private boolean lockObstacles;
    private boolean teleport;

    public Simulator(Board board, Configuration initialConf, boolean lockObstacles, boolean teleport)
    {
        this.board = board;
        this.currConf = new Configuration( initialConf.angle );
        this.targetConf = new Configuration( initialConf.angle );
        normalize(this.currConf);
        normalize(this.targetConf);
        this.lockObstacles = lockObstacles;
        this.teleport = teleport;
    }
    
    public Simulator(Board board, Configuration initialConf)
    {
        this(board, initialConf, false, false);
    }
    
    public void finish() {
        state = State.FINISHED;
    }
    
    public void run()
    {
        state = State.RUNNING;
        long lastTime = System.nanoTime();
        while (state == State.RUNNING)
        {
            long currTime = System.nanoTime();
            double deltaTime = (double)(currTime - lastTime) / 1.0e9;
            if (deltaTime > 0.005)
                System.out.println("!!!!!!!!!!! " + deltaTime);
            if (!lockObstacles)
                board.move(deltaTime);
            moveArm(deltaTime);
            lastTime = currTime;
        }
    }

    synchronized private void moveArm(double deltaTime)
    {
        double diff = deltaTime * armSpeed;
        double[] curr = currConf.angle;
        double[] target = targetConf.angle;
        for (int i = 0; i < curr.length; ++i)
        {
            double t_c = target[i] - curr[i];
            double dir =  Math.signum(Math.sin(t_c));
            if (teleport || Math.abs(curr[i] - target[i]) < diff)
                curr[i] = target[i];
            else
                curr[i] = normalize(curr[i] + dir * diff);
        }
    }

    public Board getBoard()
    {
        return board;
    }

    public Configuration getTargetConf()
    {
        return targetConf;
    }

    synchronized public void setTargetConf(Configuration targetConf)
    {
        this.targetConf = targetConf;
        normalize(targetConf);
    }

    public Configuration getCurrConf()
    {
        return currConf;
    }
    

    public State getState()
    {
        return state;
    }

    public boolean isLockObstacles()
    {
        return lockObstacles;
    }

    public void setLockObstacles(boolean lockObstacles)
    {
        this.lockObstacles = lockObstacles;
    }

    private void normalize(Configuration conf)
    {
        double[] a = conf.angle;
        for (int i = 0; i < a.length; ++i)
        {
            a[i] = normalize(a[i]);
        }
    }
    
    private double normalize(double angle)
    {
        if (angle <= Math.PI && angle >= -Math.PI)
            return angle;
        double s = Math.signum(angle);
        double v = Math.abs(angle);
        int k = (int)((v + Math.PI) / (2.0 * Math.PI));
        v -= k * 2 * Math.PI;
        return s * v;
    }
    
    public enum State {
        NOT_STARTED, RUNNING, FINISHED
    }
}
