
package kinematics.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import kinematics.logic.Arm;
import kinematics.logic.Board;
import kinematics.logic.LabPiece;
import kinematics.logic.Labirynth;
import kinematics.logic.Point;
import kinematics.logic.Rectangle;

/**
 *
 * @author Grzegorz Los
 */
public class Canvas extends JPanel
{
    private ArrayList<Arm> arms = new ArrayList<>();
    private Board board;
    private Labirynth lab;
    private final int goalR = 6;
    private final int nodeR = 4;
    private Graphics gr;
    
    @Override
    public void paint(Graphics gr)
    {        
        super.paint(gr);
        this.gr = gr;
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getSize().width, getSize().height);
        if (board != null)
        {
            drawGoal();
            drawObstacles();
        }
        drawArms();      
        drawLab();
    }

    public Arm getArm(int nr)
    {
        return arms.get(nr);
    }

    public void clearArms()
    {
        arms.clear();
    }
    
    public void addArm(Arm arm)
    {
        arms.add(arm);
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    void setLabirynth(Labirynth labirynth)
    {
        this.lab = labirynth;
    }
    
    private SwingCoord translate(Point c)
    {
        int h = getSize().height, w = getSize().width;
        double minX = board.minArea.x, maxX = board.maxArea.x, minY = board.minArea.y, maxY = board.maxArea.y;
        double x = (c.x - minX) / (maxX - minX) * w;
        double y = (maxY - c.y) / (maxY - minY) * h;
        return new SwingCoord((int)x, (int)y);
    }

    private void drawGoal()
    {
        gr.setColor(Color.RED);
        SwingCoord sc = translate(board.goal);
        int x = sc.x - goalR;
        int y = sc.y - goalR;
        gr.fillOval(x, y, 2*goalR, 2*goalR); 
    }

    private void drawArms()
    {
        Color[] colors = {Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.RED };
        for (int i = 0; i < arms.size(); ++i)
            drawSegments(arms.get(i), colors[i]);
    }

    private void drawSegments(Arm arm, Color color)
    {
        if (arm.getCoord() == null)
            return ;
        gr.setColor(color);
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
        List<Rectangle> rects = board.getRects();
        for (Rectangle rect: rects)
            drawObstacle(rect);
    }

    private void drawObstacle(Rectangle rect)
    {
        Point mid = rect.getPos();
        double h = rect.h;
        double w = rect.w;
        Point p1 = new Point(mid.x - w/2, mid.y - h/2);
        Point p2 = new Point(mid.x + w/2, mid.y + h/2);
        SwingCoord from = translate(p1);
        SwingCoord to = translate(p2);
        gr.fillRect(from.x, to.y, to.x - from.x, from.y - to.y);
    }

    private void drawLab()
    {
        if (lab == null)
            return;
        gr.setColor(Color.cyan);
        for (LabPiece lp: lab.pieces)
            drawLabPiece(lp);
    }

    private void drawLabPiece(LabPiece lp)
    {
        Point[] ps = lp.getPoints();
        int n = ps.length;
        for (int i = 0; i < n; ++i)
        {
            SwingCoord p = translate(ps[i]);
            SwingCoord r = translate( ps[(i+1)%n] );
            gr.drawLine(p.x, p.y, r.x, r.y);
        }
    }
    
    private static class SwingCoord
    {
        int x,y;

        public SwingCoord(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
        
}
