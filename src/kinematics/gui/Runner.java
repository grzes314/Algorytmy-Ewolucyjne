
package kinematics.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import kinematics.logic.Arm;
import kinematics.logic.Board;
import kinematics.logic.Configuration;
import kinematics.logic.DummyPredictor;
import kinematics.logic.DynamicFunction;
import kinematics.logic.MutationPerformerIK;
import kinematics.logic.NoCrossoverIK;
import kinematics.logic.Predictor;
import kinematics.logic.ProblemData;
import kinematics.logic.RandomPopulationGeneratorIK;
import kinematics.logic.ReplacementWithNonFeasible;
import kinematics.logic.SimplePredictor;
import kinematics.logic.SimpleValuator;
import kinematics.logic.Simulator;
import kinematics.logic.StaticValuator;
import sga.Population;
import sga.ProgressObserver;
import sga.RandomParentSelector;
import sga.SGA;
import sga.SGA_Params;
import sga.SimpleReplacementPerformer;
import sga.StaticFunction;

/**
 *
 * @author Grzegorz Los
 */
public class Runner implements ProgressObserver
{
    private final ProblemData pData;
    private final Canvas canvas;
    private SGA<Configuration> sga;
    private DynamicFunction<Configuration> fun;
    private Simulator simulator;
    private Predictor predictor;
    private Timer viewUpdater;
    Mode mode;


    public Runner(ProblemData pData, Canvas canvas)
    {
        this.pData = pData;
        this.canvas = canvas;

    }    
    
    public void run(Mode mode)
    {
        Board board = new Board(pData);
        Arm arm = new Arm(pData.sData);
        canvas.setBoard(board);
        canvas.setArm(arm);
        
        this.mode = mode;
        switch(mode)
        {
            case Simple:
                prepareSimulationSimple(board);
                prepareSGASimple();
                break;
            case Static:
                prepareSimulationStatic(board);
                prepareSGAStatic();
                break;
            case Dynamic:
                prepareSimulationDynamic(board);
                prepareSGADynamic();
                break;
        }
        Thread threadSGA = new Thread( () -> {
            sga.maximize(fun);
        });
        Thread threadSimulator = new Thread( () -> {
            simulator.run();
        });
        createViewUpdater();
        threadSGA.start();
        threadSimulator.start();
        viewUpdater.start();
    }
    
    private void prepareSimulationSimple(Board board)
    {
        fun = new StaticFunction<>(new SimpleValuator(pData));
        simulator = new Simulator(board, defaultConf(), true, true);
        predictor = new DummyPredictor();
    }
    
    private void prepareSimulationStatic(Board board)
    {
        fun = new StaticFunction<>(new StaticValuator(pData, board));      
        simulator = new Simulator(board, defaultConf(), true, true); 
        predictor = new DummyPredictor(); 
    }
    
    private void prepareSimulationDynamic(Board board)
    {
        fun = new DynamicFunction<>(new StaticValuator(pData, board));
        simulator = new Simulator(board, defaultConf(), false, false);
        predictor = new SimplePredictor();
    }
    
    private void prepareSGASimple()
    {        
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
        //sga.setLocalSearch(new LocalSearchIK(pData));        
    }

    private void prepareSGAStatic()
    {
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
        //sga.setLocalSearch(new LocalSearchIK(pData));        
    }

    private void prepareSGADynamic()
    {
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
        //sga.setLocalSearch(new LocalSearchIK(pData));
    }
    
    public void stop()
    {
        sga.interrupt();
        simulator.finish();
        viewUpdater.stop();
    }

    private void createViewUpdater()
    {
        int delay = 40; //40ms -- aktualizacja 25 razy na sekunde
        ActionListener taskPerformer = (ActionEvent e) -> {
            canvas.repaint();
            Toolkit.getDefaultToolkit().sync();
        };
        viewUpdater = new Timer(delay, taskPerformer);
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
        updateAll();
        if (i % 200 == 0)
        {
            Population<Configuration> pop = sga.getCurrPopulation();
            String str = String.format("It=%d, best=%.6f, mean=%.6f, worst=%.6f",
                                          i, pop.getMaxValue(), pop.getMeanValue(), pop.getMinValue());
            System.out.println(str);
        }
    }
    
    private void updateAll()
    {
        updateArm();
        updateFun();
    }
    
    private void updateArm()
    {
        //Population<Configuration> pop = sga.getCurrPopulation();
        //simulator.setTargetConf(pop.getMaxIndividual(), pop.getMaxValue(), pop.getMeanValue());
        //List<Configuration> confs = sga.getCurrPopulation().getSolutions();
        //simulator.setTargetConf(confs);
        simulator.setTargetConf(sga.getCurrBestInd());
        canvas.getArm().setConfiguration(simulator.getCurrConf());
    }

    private void updateFun()
    {
        Board nextBoard = predictor.predict(simulator.getBoard(), 1.0);
        fun.update(new StaticValuator(pData, nextBoard));
    }
    
    public enum Mode
    {
        Simple, Static, Dynamic
    }
}
