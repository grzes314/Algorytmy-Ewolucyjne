
package kinematics.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import kinematics.logic.ProblemData;

/**
 *
 * @author grzes
 */
public class MainPanel extends javax.swing.JPanel
{
    private Runner runner;
    JFileChooser chooser;
    ProblemParser pParser = new ProblemParser();
    Canvas canvas;
    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        initComponents();
        imageContainer.setLayout(new BorderLayout());
        canvas = new Canvas();
        canvas.setSize(800, 600);
        imageContainer.add(canvas, BorderLayout.CENTER);
        chooser = new JFileChooser(".");
    }
    
    private void browseClicked()
    {        
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            try {
                ProblemData pData = pParser.read(chooser.getSelectedFile().getName());
                canvas.setPData(pData);
                runner = new Runner(pData, canvas);
            } catch (IOException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void solveClicked()
    {
        browse.setEnabled(false);
        solve.setEnabled(false);
        stop.setEnabled(true);
        runner.run();
    }
    
    private void stopClicked()
    {
        runner.stop();
        browse.setEnabled(true);
        solve.setEnabled(true);
        stop.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        browse = new javax.swing.JButton();
        imageContainer = new javax.swing.JPanel();
        filePath = new javax.swing.JTextField();
        solve = new javax.swing.JButton();
        stop = new javax.swing.JButton();

        browse.setText("Browse");
        browse.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                browseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout imageContainerLayout = new javax.swing.GroupLayout(imageContainer);
        imageContainer.setLayout(imageContainerLayout);
        imageContainerLayout.setHorizontalGroup(
            imageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        imageContainerLayout.setVerticalGroup(
            imageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        filePath.setText("Choose data file");

        solve.setText("Solve");
        solve.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                solveActionPerformed(evt);
            }
        });

        stop.setText("Stop");
        stop.setEnabled(false);
        stop.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filePath)
                    .addComponent(imageContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(browse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(solve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browse)
                    .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(solve)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stop)
                        .addGap(0, 183, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_browseActionPerformed
    {//GEN-HEADEREND:event_browseActionPerformed
        browseClicked();
    }//GEN-LAST:event_browseActionPerformed

    private void solveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_solveActionPerformed
    {//GEN-HEADEREND:event_solveActionPerformed
        solveClicked();
    }//GEN-LAST:event_solveActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stopActionPerformed
    {//GEN-HEADEREND:event_stopActionPerformed
        stopClicked();
    }//GEN-LAST:event_stopActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browse;
    private javax.swing.JTextField filePath;
    private javax.swing.JPanel imageContainer;
    private javax.swing.JButton solve;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
}
