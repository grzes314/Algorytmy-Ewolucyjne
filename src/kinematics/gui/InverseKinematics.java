
package kinematics.gui;

import javax.swing.JFrame;

/**
 *
 * @author Grzegorz Los
 */
public class InverseKinematics extends JFrame
{
    public static void main(String[] args)
    {
        JFrame frame = new InverseKinematics();
        frame.setVisible(true);
    }
    
    public InverseKinematics()
    {
        setSize(1024, 768);
        setTitle("Inverse Kinematics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MainPanel());
    }

}
