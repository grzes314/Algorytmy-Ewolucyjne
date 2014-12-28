
package kinematics.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import kinematics.logic.Arm;
import kinematics.logic.Board;
import kinematics.logic.Configuration;
import kinematics.logic.LocalSearchIK;
import kinematics.logic.MutationPerformerIK;
import kinematics.logic.NaiveConfigurationValuator;
import kinematics.logic.NoCrossoverIK;
import kinematics.logic.ProblemData;
import kinematics.logic.RandomPopulationGeneratorIK;
import kinematics.logic.ReplacementWithNonFeasible;
import kinematics.logic.ValuatorWithObstacles;
import sga.Function;
import sga.Population;
import sga.ProgressObserver;
import sga.RandomParentSelector;
import sga.SGA;
import sga.SGA_Params;
import sga.SimpleReplacementPerformer;

/**
 *
 * @author Grzegorz Los
 */
public class Runner implements ProgressObserver
{
    private final ProblemData pData;
    private final Board board;
    private final Arm arm;
    private final Canvas canvas;
    private SGA<Configuration> sga;
    private Thread thread;
    private Function<Configuration> fun;
    //private Timer dataUpdater;
    private Timer viewUpdater;
    Mode mode;
    
    public enum Mode
    {
        Simple, Static, Dynamic
    }

    public Runner(ProblemData pData, Canvas canvas)
    {
        this.pData = pData;
        this.canvas = canvas;
        board = new Board(pData);
        arm = new Arm(pData.sData);
        canvas.setArm(arm);
        canvas.setBoard(board);
    }    
    
    public void run(Mode mode)
    {
        this.mode = mode;
        switch(mode)
        {
            case Simple:
                prepareSimple();
                break;
            case Static:
                prepareStatic();
                break;
            case Dynamic:
                prepareDynamic();
                break;
        }
        thread = new Thread( () -> {
            sga.maximize(fun);
            System.out.println("Finished: " + sga.getBestVal());
        });
        arm.setConfiguration(defaultConf());
        
        //createDataUpdater();
        createViewUpdater();
        thread.start();
        viewUpdater.start();
        //dataUpdater.start();
    }
    
    private void prepareSimple()
    {
        fun = new NaiveConfigurationValuator(pData);
        
        SGA_Params params = new SGA_Params(
            100,
            500,
            1,
            4.0 / pData.sData.n,
            Integer.MAX_VALUE
        );
        
        sga = new SGA<>(params);
        sga.addObserver(this);
        sga.setRandomPopoluationGenerator(new RandomPopulationGeneratorIK(pData));
        //sga.setParentSelector(new RouletteParentSelector<>());
        sga.setParentSelector(new RandomParentSelector<>());
        sga.setCrossoverPerformer(new NoCrossoverIK());
        MutationPerformerIK mp = new MutationPerformerIK(pData, 100);
        sga.addObserver(mp);
        sga.setMutationPerformer(mp);
        sga.setReplacementPerformer(new SimpleReplacementPerformer<>());
        sga.setLocalSearch(new LocalSearchIK(pData));        
    }

    private void prepareStatic()
    {
        fun = new ValuatorWithObstacles(pData, board);
        
        SGA_Params params = new SGA_Params(
            100,
            500,
            1,
            4.0 / pData.sData.n,
            Integer.MAX_VALUE
        );
        
        sga = new SGA<>(params);
        sga.addObserver(this);
        sga.setRandomPopoluationGenerator(new RandomPopulationGeneratorIK(pData));
        //sga.setParentSelector(new RouletteParentSelector<>());
        sga.setParentSelector(new RandomParentSelector<>());
        sga.setCrossoverPerformer(new NoCrossoverIK());
        MutationPerformerIK mp = new MutationPerformerIK(pData, 100);
        sga.addObserver(mp);
        sga.setMutationPerformer(mp);
        sga.setReplacementPerformer(new ReplacementWithNonFeasible<>(params.nrOfParents,params.nrOfParents/2));
        sga.setLocalSearch(new LocalSearchIK(pData));        
    }

    private void prepareDynamic()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void stop()
    {
        sga.interrupt();
        viewUpdater.stop();
        //dataUpdater.stop();
    }

    private void createViewUpdater()
    {
        int delay = 40; //40ms -- aktualizacja 25 razy na sekunde
        ActionListener taskPerformer = (ActionEvent e) -> {
            updateArm();
            canvas.repaint();
        };
        viewUpdater = new Timer(delay, taskPerformer);
    }
    
    private void updateArm()
    {
        Configuration conf = sga.getCurrBestInd();
        if (conf != null)            
            arm.setConfiguration(conf);
    }

    private void createDataUpdater()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Configuration defaultConf()
    {
        int n = pData.sData.n;
        Configuration c = new Configuration(n);
        for (int i = 0; i < n; ++i)
            c.angle[i] = Math.PI;
        return c;
    }

    @Override
    public void currentIteration(int i, boolean solutionImproved)
    {
        if (i % 200 == 0)
        {
            Population<Configuration> pop = sga.getCurrPopulation();
            String str = String.format("It=%d, best=%.6f, mean=%.6f, worst=%.6f",
                                          i, pop.getMaxValue(), pop.getMeanValue(), pop.getMinValue());
            System.out.println(str);
        }
    }
}
