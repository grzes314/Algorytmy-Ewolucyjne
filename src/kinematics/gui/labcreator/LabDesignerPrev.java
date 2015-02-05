
package kinematics.gui.labcreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Los
 */
public class LabDesignerPrev extends JPanel
{
    public final LabDataPrev labData;
    private GridLayout layout;    
    
    public LabDesignerPrev(int rows, int cols)
    {
        labData = new LabDataPrev(rows, cols);
        makeLayout();
    }

    public LabDesignerPrev(LabDataPrev labData)
    {
        this.labData = labData;
        makeLayout();
    }

    private void makeLayout()
    {
        int rows = labData.rows;
        int cols = labData.cols;
        layout = new GridLayout(rows, cols);
        setLayout(layout);
        for (int r = 0; r < rows; ++r)
        {
            for (int c = 0; c < cols; ++c)
            {
                MyButton myButton = new MyButton(r, c);
                add(myButton);
                myButton.addActionListener((ActionEvent e) -> {
                    int row = myButton.row;
                    int col = myButton.col;
                    labData.fields[row][col] = toggle(labData.fields[row][col]);
                });
            }
        }
    }
    
    private Field toggle(Field field)
    {
        switch(field)
        {
            case Empty:
                return Field.Wall;
            case Wall:
                return Field.Goal;
            case Goal:
                return Field.Empty;
            default:
                throw new RuntimeException("Unknown field type");
        }
    }
    
    class MyButton extends JButton
    {
        public final int row, col;
        
        private MyButton(int r, int c)
        {
            row = r;
            col = c;
        }
        
        @Override
        public void paint(Graphics gr)
        {
            Color color = getColor();
            Dimension d = getSize();
            gr.setColor(color);
            gr.fillRect(0, 0, d.width, d.height);
        }

        private Color getColor()
        {
            Field field = labData.fields[row][col];
            switch(field)
            {
                case Empty:
                    return Color.WHITE;
                case Wall:
                    return new Color(128,64,0);
                case Goal:
                    return Color.RED;
                default:
                    throw new RuntimeException("Unknown field type");
            }
        }
    }
}
