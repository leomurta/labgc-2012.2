/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.userInterface.gui;

/**
 *
 * @author Leonardo
 */
    
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileTreePanel extends JPanel {
  
   private JTree tree;
  DefaultMutableTreeNode root;
  public FileTreePanel(String strRootPath) 
  {
    root = new DefaultMutableTreeNode("root", true);
    getList(root, new File(strRootPath));
    setLayout(new BorderLayout());
    tree = new JTree(root);
    tree.setRootVisible(false);
    
    tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
                    boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    if (node.getUserObject() instanceof File) {
                        setText(((File) node.getUserObject()).getName());
                    }
                }
                return this;
            }
        });
    add(new JScrollPane((JTree)getTree()),"Center");
  }

  public Dimension getPreferredSize()
  {
    return new Dimension(200, 120);
  }

  public void getList(DefaultMutableTreeNode node, File f) 
  {
     if(f.isDirectory()) 
     {
         System.out.println("DIRECTORY  -  " + f.getName());
         DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
         //child.setUserObject(f);
         node.add(child);
         FileFilter filter = new FileFilter() 
         {

            @Override
            public boolean accept(File pathname) 
            {
               return  !(pathname.getName().contains(".labgc")); 
            }
         };
         File fList[] = f.listFiles(filter);
         for(int i = 0; i  < fList.length; i++)
             getList(child, fList[i]);
     }
 }

    /**
     * @return the tree
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(JTree tree) {
        this.tree = tree;
    }

class WindowCloser extends WindowAdapter 
{
  public void windowClosing(WindowEvent e) 
  {
    Window win = e.getWindow();
    win.setVisible(false);
    System.exit(0);
   }
}

}


  /*private JTree fileTree;

  private FileSystemModel fileSystemModel;

  private JTextArea fileDetailsTextArea = new JTextArea();

  public FileTreePanel(String directory) {
    //super("JTree FileSystem Viewer");
    fileDetailsTextArea.setEditable(false);
    fileSystemModel = new FileSystemModel(new File(directory));
    fileTree = new JTree(fileSystemModel);
    fileTree.setEditable(false);
    
    fileTree.setCellRenderer( new DirectoryRenderer() );  
    
    fileTree.addTreeSelectionListener(new TreeSelectionListener() 
    {
      public void valueChanged(TreeSelectionEvent event) 
      {
        File file = (File) fileTree.getLastSelectedPathComponent();
        //fileDetailsTextArea.setText(getFileDetails(file));
      }
    });
    /*JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(
        fileTree), new JScrollPane(fileDetailsTextArea));
    getContentPane().add(splitPane);
    */
    //setDefaultCloseOperation(EXIT_ON_CLOSE);
    //setSize(138, 365);
    
   //JScrollPane sp = new JScrollPane(fileTree);
   /*this.add(fileTree);
   setVisible(true);
   this.validate();
  }

  private String getFileDetails(File file) {
    if (file == null)
      return "";
    StringBuffer buffer = new StringBuffer();
    buffer.append("Name: " + file.getName() + "\n");
    buffer.append("Path: " + file.getPath() + "\n");
    buffer.append("Size: " + file.length() + "\n");
    return buffer.toString();
  }

  public static void main(String args[]) {
    new FileTreePanel("c:\\");
  }
}

class FileSystemModel implements TreeModel {
  private File root;

  private Vector listeners = new Vector();

  public FileSystemModel(File rootDirectory) {
    root = rootDirectory;
  }

  public Object getRoot() {
    return root;
  }

  public Object getChild(Object parent, int index) {
    File directory = (File) parent;
    String[] children = directory.list();
    return new TreeFile(directory, children[index]);
  }

  public int getChildCount(Object parent) {
    File file = (File) parent;
    if (file.isDirectory()) {
      String[] fileList = file.list();
      if (fileList != null)
        return file.list().length;
    }
    return 0;
  }

  public boolean isLeaf(Object node) {
    File file = (File) node;
    return file.isFile();
  }

  public int getIndexOfChild(Object parent, Object child) {
    File directory = (File) parent;
    File file = (File) child;
    String[] children = directory.list();
    for (int i = 0; i < children.length; i++) {
      if (file.getName().equals(children[i])) {
        return i;
      }
    }
    return -1;

  }

  public void valueForPathChanged(TreePath path, Object value) {
    File oldFile = (File) path.getLastPathComponent();
    String fileParentPath = oldFile.getParent();
    String newFileName = (String) value;
    File targetFile = new File(fileParentPath, newFileName);
    oldFile.renameTo(targetFile);
    File parent = new File(fileParentPath);
    int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
    Object[] changedChildren = { targetFile };
    fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);

  }

  private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
    TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
    Iterator iterator = listeners.iterator();
    TreeModelListener listener = null;
    while (iterator.hasNext()) {
      listener = (TreeModelListener) iterator.next();
      listener.treeNodesChanged(event);
    }
  }

  public void addTreeModelListener(TreeModelListener listener) {
    listeners.add(listener);
  }

  public void removeTreeModelListener(TreeModelListener listener) {
    listeners.remove(listener);
  }

  private class TreeFile extends File {
    public TreeFile(File parent, String child) {
      super(parent, child);
    }

    public String toString() {
      return getName();
    }
  }
}


class DirectoryRenderer extends DefaultTreeCellRenderer 
{  
          
        public Component getTreeCellRendererComponent( JTree tree, Object value,  
                boolean sel, boolean expanded, boolean leaf, int row,   
                boolean hasFocus ) {  
            JLabel l = (JLabel)super.getTreeCellRendererComponent( tree, value, sel,  
                    expanded, leaf, row, hasFocus);  
            Object userObject = ((DefaultMutableTreeNode)value).getUserObject();  
            if ( userObject instanceof File ) {  
                String name = ((File)userObject).getName();  
                if ( name.trim().equals( "" ) ) {  
                    l.setText( ((File)userObject).toString() );  
                }  
                else {  
                    l.setText( ((File)userObject).getName() );  
                }  
            }  
            return l;   
        }  
}*/  