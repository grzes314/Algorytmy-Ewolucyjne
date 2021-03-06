
package kinematics.gui.labcreator;

import kinematics.logic.InvalidDataException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import kinematics.gui.ParserForLabirynth;
import kinematics.gui.ProblemLabWriter;
import kinematics.logic.PrDataForLab;

/**
 *
 * @author Grzegorz Los
 */
public class LabCreator extends JFrame
{
    private final SizeQuestion sq;
    private final BoardDesigner boardDesigner;
    private final JFileChooser chooser;
    private final ProblemLabWriter pWriter = new ProblemLabWriter();
    
    public static void main(String[] args)
    {
        LabCreator lc = new LabCreator();
        lc.setVisible(true);
    }
    
    LabCreator()
    {
        sq = new SizeQuestion(this);
        boardDesigner = new BoardDesigner();
        chooser = new JFileChooser(".");
        
        setSize(800,600);
        this.setContentPane(boardDesigner);
        setTitle("Labirynth Creator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
    }

    private void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener((ActionEvent e) -> {
            newClicked();
        });
        menu.add(newItem);
        
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener((ActionEvent e) -> {
            saveClicked();
        });
        menu.add(saveItem);
        
        JMenuItem loadItem = new JMenuItem("Open");
        loadItem.addActionListener((ActionEvent e) -> {
            openClicked();
        });
        menu.add(loadItem);
        
        /*JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener((ActionEvent e) -> {
            exportClicked();
        });
        menu.add(exportItem);*/
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((ActionEvent e) -> {
            dispose();
            System.exit(0);
        });
        menu.add(exitItem);
        
        this.setJMenuBar(menuBar);
    }
    
    private void newClicked()
    {
        SizeQuestion.Choice ch = sq.showQuestion();
        if (ch == SizeQuestion.Choice.OK)
        {
            int rows = sq.getRows();
            int cols = sq.getCols();
            double edgeSize = sq.getEdgeSize();
            boardDesigner.setLabDesigner(new LabDesigner(rows, cols, edgeSize));
            boardDesigner.setArmDesigner(new ArmDesigner());
        }
        this.revalidate();
    }
    
    
    private void saveClicked()
    {
        String path = getSavePath();
        if (path == null)
            return;
        try {
            PrDataForLab pData = boardDesigner.getBoardData().toPrDataForLab();
            pWriter.write(path, pData);
        } catch (InvalidDataException ex) {
            Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void openClicked()
    {
        String path = getOpenPath();
        if (path == null)
            return;
        try {
            PrDataForLab pData = new ParserForLabirynth().read(path).labData;
            boardDesigner.setArmDesigner(new ArmDesigner(pData.armData));
            boardDesigner.setLabDesigner(new LabDesigner(pData.labData));
            revalidate();
        } catch (IOException ex) {
            Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*private void saveClicked()
    {
        BoardData data = boardDesigner.getBoardData();
        String path = getSavePath();
        if (path == null)
            return;
        ObjectOutputStream out = null;
        try {
            try (FileOutputStream fileOut = new FileOutputStream(path)) {
                out = new ObjectOutputStream(fileOut);
                out.writeObject(data);
                out.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void openClicked()
    {
        String path = getOpenPath();
        BoardData bData = null;
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(path);
            try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
                bData = (BoardData) in.readObject();
            } catch (IOException ex) {
                Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                Logger.getLogger(LabCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setBoardData(bData);
    }*/
    
    private void setBoardData(BoardData bData)
    {
        if (bData == null)
        {
            JOptionPane.showMessageDialog(this, "Failed to read labirynth data", "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        boardDesigner.setLabDesigner(new LabDesigner(bData.labData));
        boardDesigner.setArmDesigner(new ArmDesigner(bData.armData));
        revalidate();
    }

    private String getSavePath()
    { 
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        else return null;
    }

    private String getOpenPath()
    { 
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        else return null;
    }
}
