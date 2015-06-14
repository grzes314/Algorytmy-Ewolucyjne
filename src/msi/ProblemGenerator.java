
package msi;

import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class ProblemGenerator
{
    int N;
    int M;
    double maxExTime;
    double maxPrepTime;

    public ProblemGenerator(int N, int M, double maxExTime, double maxPrepTime)
    {
        this.N = N;
        this.M = M;
        this.maxExTime = maxExTime;
        this.maxPrepTime = maxPrepTime;
    }    
    
    public static void main(String[] args)
    {
        ProblemGenerator pg = new ProblemGenerator(100, 10, 100.0, 100.0);
        ProblemWriter pw = new ProblemWriter();
        pw.write(pg.generate(), args[0] + "/test.in");
    }
    
    public ProblemData generate()
    {
        double[][] exTimes = makeExTimes();
        double[][][] prepTimes = makePrepTimes();
        return new ProblemData(N, M, exTimes, prepTimes);
    }

    private double[][] makeExTimes()
    {
        double[][] exTimes = new double[M][N];
        for (int i = 0; i < M; ++i)
            for (int a = 0; a < N; ++a)
                exTimes[i][a] = getRandDouble(0, maxExTime);
        return exTimes;
    }

    private double[][][] makePrepTimes()
    {
        double[][][] prepTimes = new double[M][N][N];
        for (int i = 0; i < M; ++i)
            for (int a = 0; a < N; ++a)
                for (int b = 0; b < N; ++b)
                    prepTimes[i][a][b] = getRandDouble(0, maxExTime);   
        return prepTimes;
    }
    
    private double getRandDouble(double a, double b)
    {
        return a + RandomnessSource.rand.nextDouble() * (b - a);
    }
}
