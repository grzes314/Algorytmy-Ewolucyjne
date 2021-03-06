
package kinematics.logic;

import java.util.List;

/**
 *
 * @author Grzegorz Los
 */
public class Simulator
{
    private final Board board;
    private Configuration currConf;
    private Configuration targetConf;
    private double targetVal = Double.NEGATIVE_INFINITY;
    private final double armSpeed = Math.PI/2; // 45 degrees per second
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
            //if (deltaTime > 0.005)
            //    System.out.println("!!!!!!!!!!! " + deltaTime);
            if (!lockObstacles)
                board.move(deltaTime);
            moveArm(deltaTime);
            lastTime = currTime;
        }
    }

    private synchronized void moveArm(double deltaTime)
    {
        if (targetConf == null)
        {
            //currConf = null;
            return;
        }
        double[] curr = currConf.angle;
        double[] target = targetConf.angle;
        for (int i = 0; i < curr.length; ++i)
        {
            //double diff = deltaTime * armSpeed * (curr.length + 3*i) / curr.length / 4;
            double diff = deltaTime * armSpeed;
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

    public synchronized void setTargetConf(Configuration conf)
    {
        targetConf = conf;
    }
    
    public void setTargetConf(List<Configuration> confs)
    {
        Configuration best = null;
        double MIN = Double.POSITIVE_INFINITY;
        for (Configuration conf: confs)
        {
            normalize(conf);
            double d = distToCurr(conf);
            if (d < MIN)
            {
                best = conf;
                MIN = d;
            }
        }
        if (best != null)
            targetConf = best;
    }
    
    private double distToCurr(Configuration conf)
    {
        double sum = 0;
        int l = conf.angle.length;
        for (int i = 0; i < conf.angle.length; ++i)
        {
            double v = Math.abs(conf.angle[i] - currConf.angle[i]);
            sum += (l-i)*v*v;
        }
        return sum;
    }
    public void setTargetConf(Configuration targetConf, double targetVal, double currMean)
    {
        if (this.targetVal < currMean)
        {
            this.targetConf = targetConf.getCopy();
            this.targetVal = targetVal;
        }
    }

    synchronized public Configuration getCurrConf()
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
