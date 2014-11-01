
package pbiltest;

import javax.swing.JFrame;

/**
 *
 * @author grzes
 */
public class PBILFrame extends JFrame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new PBILFrame();
        frame.setVisible(true);
    }
    
    public PBILFrame()
    {
        setSize(1024, 768);
        setTitle("PBIL test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MainPanel());
    }
    
}
