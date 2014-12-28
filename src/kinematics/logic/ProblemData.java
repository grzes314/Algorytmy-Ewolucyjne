
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemData
{
    public static class SegmentData
    {
        public final int n;
        public final double[] length;
        public final double[] alfa;
        public final double[] beta;        

        public SegmentData(int n, double[] length, double[] alfa, double[] beta)
        {
            this.n = n;
            this.length = length;
            this.alfa = alfa;
            this.beta = beta;
        }
    }
    
    public static class ObstacleData
    {
        public final int k;
        public final Point[] from;
        public final Point[] to;
        public final double[] h;
        public final double[] w;
        public final double[] v;

        public ObstacleData(int k, Point[] from, Point[] to, double[] h, double[] w, double[] v)
        {
            this.k = k;
            this.from = from;
            this.to = to;
            this.h = h;
            this.w = w;
            this.v = v;
        }
    }
    
    public final Point minArea, maxArea;
    public final Point goal;
    public final SegmentData sData;
    public final ObstacleData oData;

    public ProblemData(Point minArea, Point maxArea, Point goal, SegmentData sData, ObstacleData oData)
    {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.goal = goal;
        this.sData = sData;
        this.oData = oData;
    }
}
