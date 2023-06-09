package gui;

import ca.CA;
import java.awt.Color;
import java.util.Random;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 *
 * @author tadaki
 */
public class CAFrame extends javax.swing.JFrame {
    
    private final JComboBox<Integer> ruleSelect;
    private final JSlider pSlider;
    private final JLabel pLabel;
    private final int n = 300;
    private final Random random;
    private CA ca;
    private double p = 0.6;
    private int rule=90;

    /**
     * Creates new form CAFrame
     */
    public CAFrame() {
        initComponents();
        random = new Random(48L);
        ruleSelect = new JComboBox<>();
        for (int k = 1; k < 256; k++) {
            ruleSelect.addItem(k);
        }
        ruleSelect.setBackground(new Color(255, 255, 204));
        ruleSelect.setSelectedIndex(89);
        ca = new CA(n, 90,random);
        drawPanel.setCA(ca);
        menuBar.add(new JLabel("rule"));
        ruleSelect.addActionListener(
                l -> {
                    drawPanel.stop();
                    drawPanel.setT(0);
                    drawPanel.clear();
                    rule = (Integer) ruleSelect.getSelectedItem();
                    ca = new CA(n, rule,random);
                    drawPanel.setCA(ca);
                });
        menuBar.add(ruleSelect);
        pLabel = new JLabel("p=" + p);
        menuBar.add(pLabel);
        pSlider = new JSlider(0, 10, (int) (10 * p));
        pSlider.setBackground(new Color(255, 255, 204));
        pSlider.addChangeListener(l -> {
            int v = pSlider.getValue();
            p = v / 10.;
            pLabel.setText("p=" + p);
        });
        menuBar.add(pSlider);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        drawPanel = new gui.DrawPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        quit = new javax.swing.JMenuItem();
        startMenu = new javax.swing.JMenu();
        startSingle = new javax.swing.JMenuItem();
        startRandom = new javax.swing.JMenuItem();
        stop = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cellular Automata");

        drawPanel.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        getContentPane().add(drawPanel, java.awt.BorderLayout.CENTER);

        menuBar.setBackground(new java.awt.Color(255, 255, 204));

        fileMenu.setText("File");

        quit.setText("quit");
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });
        fileMenu.add(quit);

        menuBar.add(fileMenu);

        startMenu.setText("Start");

        startSingle.setText("start with single site");
        startSingle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startSingleActionPerformed(evt);
            }
        });
        startMenu.add(startSingle);

        startRandom.setText("start with random sites");
        startRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startRandomActionPerformed(evt);
            }
        });
        startMenu.add(startRandom);

        stop.setText("stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });
        startMenu.add(stop);

        menuBar.add(startMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitActionPerformed
        drawPanel.stop();
        dispose();
    }//GEN-LAST:event_quitActionPerformed

    private void startSingleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startSingleActionPerformed
        drawPanel.stop();
        ca = new CA(n, rule,random);
        ca.initialSingle();
        drawPanel.setCA(ca);
        drawPanel.setT(0);
        drawPanel.clear();
        drawPanel.start();
        new Thread(drawPanel).start();
    }//GEN-LAST:event_startSingleActionPerformed

    private void startRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startRandomActionPerformed
        drawPanel.stop();
        ca = new CA(n, rule,random);
        ca.initialize(p);
        drawPanel.setCA(ca);
        drawPanel.setT(0);
        drawPanel.clear();
        drawPanel.start();
        new Thread(drawPanel).start();
    }//GEN-LAST:event_startRandomActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        drawPanel.stop();
    }//GEN-LAST:event_stopActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CAFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.DrawPanel drawPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem quit;
    private javax.swing.JMenu startMenu;
    private javax.swing.JMenuItem startRandom;
    private javax.swing.JMenuItem startSingle;
    private javax.swing.JMenuItem stop;
    // End of variables declaration//GEN-END:variables
}
