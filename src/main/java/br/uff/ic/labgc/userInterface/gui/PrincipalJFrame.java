/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.userInterface.gui;

import br.uff.ic.labgc.client.Client;
import br.uff.ic.labgc.client.IClient;
import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Leonardo
 */
public class PrincipalJFrame extends javax.swing.JFrame {

    boolean m_bRoot;
    /**
     * Creates new form PrincipalJFrame
     */
    public PrincipalJFrame() {
        initComponents();
        //AddFileChooser();
        this.repaint();
         Image im = null;
        im = Toolkit.getDefaultToolkit().createImage("Images//Logo.jpg");
        this.setIconImage(im);
        CreateTree("");
        m_bRoot  = true;
       
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TreejPanel = new javax.swing.JPanel();
        OutputjPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        MainjPanel = new javax.swing.JPanel();
        MainjMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        LoginMenuItem = new javax.swing.JMenuItem();
        CheckOutjMenuItem = new javax.swing.JMenuItem();
        AboutjMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCVE - Sistema de Controle de Versão Experimental");

        TreejPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout TreejPanelLayout = new javax.swing.GroupLayout(TreejPanel);
        TreejPanel.setLayout(TreejPanelLayout);
        TreejPanelLayout.setHorizontalGroup(
            TreejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );
        TreejPanelLayout.setVerticalGroup(
            TreejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );

        OutputjPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout OutputjPanelLayout = new javax.swing.GroupLayout(OutputjPanel);
        OutputjPanel.setLayout(OutputjPanelLayout);
        OutputjPanelLayout.setHorizontalGroup(
            OutputjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        OutputjPanelLayout.setVerticalGroup(
            OutputjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );

        MainjPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout MainjPanelLayout = new javax.swing.GroupLayout(MainjPanel);
        MainjPanel.setLayout(MainjPanelLayout);
        MainjPanelLayout.setHorizontalGroup(
            MainjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        MainjPanelLayout.setVerticalGroup(
            MainjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        MainjMenuBar.setName("LabGC-2012/2");

        jMenu1.setText("Workspace");
        jMenu1.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu1MenuSelected(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenu1KeyPressed(evt);
            }
        });
        MainjMenuBar.add(jMenu1);

        jMenu2.setText("Action");

        LoginMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        LoginMenuItem.setText("Login");
        LoginMenuItem.setEnabled(false);
        jMenu2.add(LoginMenuItem);

        CheckOutjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        CheckOutjMenuItem.setText("Checkout");
        CheckOutjMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CheckOutjMenuItemMouseClicked(evt);
            }
        });
        CheckOutjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckOutjMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(CheckOutjMenuItem);

        MainjMenuBar.add(jMenu2);

        AboutjMenu.setText("About");
        AboutjMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                AboutjMenuMenuSelected(evt);
            }
        });
        AboutjMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutjMenuActionPerformed(evt);
            }
        });
        AboutjMenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AboutjMenuKeyPressed(evt);
            }
        });
        MainjMenuBar.add(AboutjMenu);

        setJMenuBar(MainjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TreejPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(MainjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(OutputjPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TreejPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MainjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(OutputjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckOutjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckOutjMenuItemActionPerformed
        // TODO add your handling code here:
        Cursor cursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );  
        this.setCursor( cursor ); 
        CheckOutJFrame checkOutScreen = new CheckOutJFrame();
        checkOutScreen.setVisible(true);
        checkOutScreen.toFront();
        cursor = Cursor.getDefaultCursor();  
        this.setCursor( cursor );
    }//GEN-LAST:event_CheckOutjMenuItemActionPerformed

    private void CheckOutjMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckOutjMenuItemMouseClicked
        // TODO add your handling code here:
        
        CheckOutJFrame checkOutScreen = new CheckOutJFrame();
        checkOutScreen.setVisible(true);
        checkOutScreen.toFront();
    }//GEN-LAST:event_CheckOutjMenuItemMouseClicked

    private void AboutjMenuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AboutjMenuKeyPressed
        // TODO add your handling code here:
        Cursor cursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );  
        this.setCursor( cursor ); 
        AboutJFrame about=null;
        try {
            about = new AboutJFrame();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        about.setVisible(true);
        about.toFront();
        cursor = Cursor.getDefaultCursor();  
        this.setCursor( cursor );
    }//GEN-LAST:event_AboutjMenuKeyPressed

    private void AboutjMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutjMenuActionPerformed
        // TODO add your handling code here:
        Cursor cursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );  
        this.setCursor( cursor ); 
        AboutJFrame about=null;
        try {
            about = new AboutJFrame();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        about.setVisible(true);
        about.toFront();
        cursor = Cursor.getDefaultCursor();  
        this.setCursor( cursor );
    }//GEN-LAST:event_AboutjMenuActionPerformed

    private void AboutjMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_AboutjMenuMenuSelected
        // TODO add your handling code here:
        Cursor cursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );  
        this.setCursor( cursor ); 
        AboutJFrame about=null;
        try {
            about = new AboutJFrame();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        about.setVisible(true);
        about.toFront();
        cursor = Cursor.getDefaultCursor();  
        this.setCursor( cursor );
    }//GEN-LAST:event_AboutjMenuMenuSelected

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
        RunFileChooser();
        
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenu1KeyPressed
        // TODO add your handling code here:
       RunFileChooser();
    }//GEN-LAST:event_jMenu1KeyPressed

    private void jMenu1MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu1MenuSelected
        // TODO add your handling code here:
         Cursor cursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );  
        this.setCursor( cursor ); 
        RunFileChooser();
        cursor = Cursor.getDefaultCursor();  
        this.setCursor( cursor );
    }//GEN-LAST:event_jMenu1MenuSelected
    
     private void RunFileChooser()
    {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Select Workpace");
        fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setControlButtonsAreShown(true);
        fileChooser.setSize(163, 337);
        TreejPanel.setLayout(new BorderLayout());
        TreejPanel.add(fileChooser, BorderLayout.EAST);
        fileChooser.addActionListener(new ActionListener() 
        {
         public void actionPerformed(ActionEvent e) 
         {
            //System.out.println(e.getActionCommand());
             if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) 
            {
               if(isValidaWorkspace(fileChooser.getSelectedFile().getPath()))
               {
                   strWorkspaceLocation =fileChooser.getSelectedFile().getPath();
                   CreateTree(strWorkspaceLocation);
                   m_bRoot =true;
               }
               else
               {
                   
                    JOptionPane.showMessageDialog(null,"Invalid workspace!");
               }
            }
         }
      });
        
       fileChooser.showOpenDialog(this);
    }
     
    private void CreateTree(String strPath)
    {
        
        TreejPanel.removeAll(); 
        tree = new  FileTreePanel(strPath);
        tree.setLocation(0,0);
        tree.setSize(438,365);
        TreejPanel.add(tree);
        tree.setVisible(true);
        tree.validate();
        tree.invalidate();
        TreejPanel.repaint();
        
        if(!strPath.isEmpty())
        {
           workspaceStatus = getWorkspaceStatus(strPath);
           FillTable(workspaceStatus);
        }
       
           
    }
    
    private void FillTable(VersionedItem item ) 
    {
        
        if(item.isDir())
        {
            VersionedDir vDir = (VersionedDir) item;
            List<VersionedItem> listItem =vDir.getContainedItens();
            table = new JTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addColumn("Item Name"); 
            model.addColumn("Item Status"); 
            for (VersionedItem vItem : listItem) 
            {
                String path = vItem.getName();
                if(!vItem.isDir())
                    model.addRow(new Object[]{path, getStatus(vItem.getStatus())});
                
            }
            
            PlaceTable(table);
            AddListener();
        }
    }
    
     private String getStatus(int status) 
     {
        switch (status) {
            case EVCSConstants.UNMODIFIED:
                return "Unmodified";
            case EVCSConstants.MODIFIED:
                return "Modified";
            case EVCSConstants.ADDED:
                return "Added";
            case EVCSConstants.DELETED:
                return "Deleted";
        }
        return "";
    }
     
    private void PlaceTable(JTable table )
    {
        JScrollPane scrollPane = new JScrollPane(table);
        MainjPanel.removeAll();
        scrollPane.setLocation(0,0);
        scrollPane.setSize(495,400);
        MainjPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setVisible(true);
        scrollPane.validate();
        scrollPane.invalidate();
        MainjPanel.repaint();
    }
    
     private boolean isValidaWorkspace(String strPath)
    {
        File file = new File(strPath);
        FileFilter filter = new FileFilter() 
        {

            @Override
            public boolean accept(File pathname) 
            {
               return  pathname.getName().contains(".labgc"); 
            }
        };
        
        File[] lisFiles = file.listFiles(filter);
        
        if(lisFiles.length<=0)
            return false;
        
        return true;
    }
     
    private VersionedItem getWorkspaceStatus(String strPath)
    {
        VersionedItem status = new VersionedDir();
        IClient client = new Client(strPath) ;
        try 
        {
            status= client.status();
        } 
        catch (ApplicationException ex) 
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        
        
        return (VersionedDir)status;
    }
    
    
    private void AddListener()
    {
        tree.getTree().addTreeSelectionListener(new TreeSelectionListener() 
        {
            public void valueChanged(TreeSelectionEvent event) 
            {

                Object obj = tree.getTree().getLastSelectedPathComponent();
                if (obj instanceof DefaultMutableTreeNode) 
                {
                    Object userObject = ((DefaultMutableTreeNode) obj).getUserObject();
                    if (userObject instanceof File) 
                    {
                        File file = (File) userObject;
                        
                            //String strDirName = file.getCanonicalPath();
                            VersionedItem vItem=null;
                            String strDirName = file.getName();
                              
                            vItem = FindPath(strDirName,workspaceStatus);
                            if(vItem!=null)
                            {
                               FillTable(vItem);
                               m_bRoot =false;
                            }
                            else
                            { 
                                 if(!m_bRoot)
                                 {
                                   CreateTree(strWorkspaceLocation);
                                   m_bRoot =true;
                                 }
                            }

                    }
                }
            }
        });
    }
    
    
    private VersionedItem FindPath(String strDirName,VersionedItem parentItem)
    {
       if(parentItem.getName()!=null)
       {
           if(parentItem.getName().equals(strDirName))
              return parentItem;
       }
        
         VersionedDir vDir = (VersionedDir) parentItem;
         List<VersionedItem> listItem =vDir.getContainedItens();
         for (VersionedItem vItem : listItem) 
         {
             if(vItem.isDir())
             {
                //String strFullPath= parentItem.getName()+vItem.getName();
                 String strFullPath=vItem.getName();
                if(strFullPath.equals(strDirName))
                    return vItem;
                 
                 FindPath(strDirName,vItem);
             }
         }
         
         return null;
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PrincipalJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AboutjMenu;
    private javax.swing.JMenuItem CheckOutjMenuItem;
    private javax.swing.JMenuItem LoginMenuItem;
    private javax.swing.JMenuBar MainjMenuBar;
    private javax.swing.JPanel MainjPanel;
    private javax.swing.JPanel OutputjPanel;
    private javax.swing.JPanel TreejPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
    private JFileChooser fileChooser;
    private String strWorkspaceLocation;
    VersionedItem workspaceStatus;
    JTable table;
    FileTreePanel tree;

    
}
