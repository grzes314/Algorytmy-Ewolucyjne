
package kinematics.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import kinematics.logic.Arm;
import kinematics.logic.Configuration;
import kinematics.logic.NaiveConfigurationValuator;
import kinematics.logic.ProblemData;
import sga.Function;
import sga.SGA;
import sga.SGA_Params;

/**
 *
 * @author Grzegorz Los
 */
public class Runner
{
    private final ProblemData pData;
    private final Arm arm;
    private final Canvas canvas;
    private SGA<Configuration> sga;
    private Thread thread;
    private final Function<Configuration> fun;
    //private Timer dataUpdater;
    private Timer viewUpdater;

    public Runner(ProblemData pData, Canvas canvas)
    {
        this.pData = pData;
        this.canvas = canvas;
        arm = new Arm(pData.sData);
        canvas.setArm(arm);
        fun = new NaiveConfigurationValuator(pData);
    }    
    
    public void run()
    {
        thread = new Thread( () -> {
            SGA_Params params = new SGA_Params(
                100,
                500,
                1,
                1.0 / pData.sData.n,
                Integer.MAX_VALUE
            );
            sga = new SGA<>(params);
            //sga.maximize(fun);
        });
        arm.setConfiguration(defaultConf());
        //createDataUpdater();
        createViewUpdater();
        thread.start();
        viewUpdater.start();
        //dataUpdater.start();
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
}
