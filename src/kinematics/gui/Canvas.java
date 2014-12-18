
package kinematics.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import kinematics.logic.Arm;
import kinematics.logic.Point;
import kinematics.logic.ProblemData;

/**
 *
 * @author Grzegorz Los
 */
public class Canvas extends JPanel
{
    private ProblemData pData;
    private Arm arm;
    private final int goalR = 6;
    private final int nodeR = 4;
    private Graphics gr;
    
    private static class SwingCoord
    {
        int x,y;

        public SwingCoord(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
        
    
    @Override
    public void paint(Graphics gr)
    {        
        this.gr = gr;
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getSize().width, getSize().height);
        if (pData != null)
        {
            drawGoal();
            drawObstacles();
        }
        if (arm != null && arm.getCoord() != null)
            drawSegments();        
    }

    public void setPData(ProblemData pData)
    {
        this.pData = pData;
    }

    public void setArm(Arm arm)
    {
        this.arm = arm;
    }
    
    private SwingCoord translate(Point c)
    {
        int h = getSize().height, w = getSize().width;
        double minX = pData.minArea.x, maxX = pData.maxArea.x, minY = pData.minArea.y, maxY = pData.maxArea.y;
        double x = (c.x - minX) / (maxX - minX) * w;
        double y = (maxY - c.y) / (maxY - minY) * h;
        return new SwingCoord((int)x, (int)y);
    }

    private void drawGoal()
    {
        gr.setColor(Color.RED);
        SwingCoord sc = translate(pData.goal);
        int x = sc.x - goalR;
        int y = sc.y - goalR;
        gr.fillOval(x, y, 2*goalR, 2*goalR); 
    }

    private void drawSegments()
    {
        gr.setColor(Color.BLUE);
        Point prev = new Point(0,0);
        for (Point p: arm.getCoord())
        {
            drawSegment(prev, p);
            prev = p;
        }
    }

    private void drawSegment(Point prev, Point p)
    {
        SwingCoord from = translate(prev);
        SwingCoord to = translate(p);
        gr.drawLine(from.x, from.y, to.x, to.y);
        gr.fillOval(to.x - nodeR, to.y - nodeR, 2*nodeR, 2*nodeR);
    }

    private void drawObstacles()
    {
        gr.setColor(new Color(128,64,0));
        for (int i = 0; i < pData.oData.k; ++i)
            drawObstacle(i);
    }

    private void drawObstacle(int i)
    {
        Point mid = pData.oData.curr[i];
        double h = pData.oData.h[i];
        double w = pData.oData.w[i];
        Point p1 = new Point(mid.x - w/2, mid.y - h/2);
        Point p2 = new Point(mid.x + w/2, mid.y + h/2);
        SwingCoord from = translate(p1);
        SwingCoord to = translate(p2);
        gr.fillRect(from.x, from.y, to.x - from.x, from.y - to.y);
    }
}
