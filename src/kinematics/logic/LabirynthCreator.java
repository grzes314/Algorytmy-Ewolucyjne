
package kinematics.logic;

import java.util.ArrayList;
import kinematics.logic.Utils.Dir;
import kinematics.logic.Utils.LocInCell;
import static kinematics.logic.Utils.getLocInCell;
import static kinematics.logic.Utils.leftOf;
import static kinematics.logic.Utils.opposite;
import static kinematics.logic.Utils.rightOf;

/**
 *
 * @author Grzegorz Los
 */
public class LabirynthCreator
{
    private LabData labData;
    private boolean[][] visited;
    private Labirynth lab;
    
    public Labirynth makeLab(LabData labData) throws InvalidDataException
    {
        this.labData = labData;
        init();
        makePieces();
        return lab;
    }

    private void init() throws InvalidDataException
    {
        visited = new boolean[labData.rows][labData.cols];
        lab = new Labirynth(labData.getGoal());
    }

    private void makePieces()
    {
        for (int r = 0; r < labData.rows; ++r)
        {
            for (int c = 0; c < labData.cols; ++c)
            {
                if (visited[r][c])
                    continue;
                if (isNewStartPoint(r,c))
                    lab.pieces.add(makeNewPiece(r,c));
                visited[r][c] = true;
            }
        }
    }

    private boolean isNewStartPoint(int r, int c)
    {
        if (!isWall(r,c))
            return false;
        if (isWall(r-1,c) && visited[r-1][c])
            return false;
        if (isWall(r+1,c) && visited[r+1][c])
            return false;
        if (isWall(r,c-1) && visited[r][c-1])
            return false;
        if (isWall(r,c+1) && visited[r][c+1])
            return false;
        return true;
    }

    private boolean inLab(int r, int c)
    {
        return r >= 0
            && r < labData.rows
            && c >= 0
            && c < labData.cols;
    }
    
    private boolean inLab(LabCoor lc) {
        return inLab(lc.r, lc.c);
    }

    private LabPiece makeNewPiece(int begR, int begC)
    {
        Dir d = Dir.Down;
        ArrayList<Point> points = new ArrayList<>();
        points.add(labData.toPoint(begR, begC, LocInCell.UpperLeft));
        LabCoor curr = new LabCoor(begR, begC);
        do {
            visited[curr.r][curr.c] = true;
            
            // may turn right?
            Dir right = rightOf(d);
            if ( isWall(move(curr, right)) )
            {
                LocInCell lic = getLocInCell(opposite(d), right);
                points.add(labData.toPoint(curr.r, curr.c, lic));
                d = right;
                right = rightOf(d);
            }
            
            // turn left if we may not move forward
            if ( !isWall(move(curr, d)) )
            {
                LocInCell lic = getLocInCell(d, right);
                points.add(labData.toPoint(curr.r, curr.c, lic));
                d = leftOf(d);
                right = rightOf(d);
            }
            
            //move forward if possible
            if (isWall(move(curr, d)))
                curr = move(curr, d);            
        }
        while(curr.r != begR || curr.c != begC || d != Dir.Left);
        
        return new LabPiece(points);
    }
    
    private LabCoor move(LabCoor curr, Dir dir)
    {
        LabCoor delta = asLabCoor(dir);
        return new LabCoor(
            curr.r + delta.r,
            curr.c + delta.c
        );
    }

    private LabCoor asLabCoor(Dir dir)
    {
        switch(dir)
        {
            case Up:
                return new LabCoor(-1, 0);
            case Right:
                return new LabCoor(0,1);
            case Down:
                return new LabCoor(1,0);
            case Left:
                return new LabCoor(0,-1);
            default:
                throw new RuntimeException("Impossible");
        }
    }

    private boolean isWall(int r, int c)
    {
        if (!inLab(r, c))
            return false;
        return labData.fields[r][c] == Field.Wall;
    }

    private boolean isWall(LabCoor coor)
    {
        return isWall(coor.r, coor.c);
    }

    private static class LabCoor {
        final int r, c; 
        public LabCoor(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
