package testUI;

import javax.swing.*;  
import javax.swing.event.TreeModelEvent;  
import javax.swing.event.TreeModelListener;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeModel;  
import javax.swing.tree.TreePath;  

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;

import java.awt.*;  
import java.awt.event.*;  
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.Enumeration;  
import java.util.List;  
public class TypeTree {  
    private JTree tree;  
    private Component parentComp;  
    private DefaultTreeModel model;
    private void addpoint(DefaultTreeModel treeModel,DefaultMutableTreeNode node,OWLNamedClass root)
    {
    	for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
			OWLNamedClass check=(OWLNamedClass)iter.next();
			DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(check.getPrefixedName());  
			treeModel.insertNodeInto(node1, node, node.getChildCount());
			addpoint(treeModel,node1,check);
		}
    }
    public TypeTree() {  
    	DefaultTreeModel treeModel =null;
        try{
        	FileInputStream fin=new FileInputStream("E:/bank/createtemp.owl");
    		Reader in = new InputStreamReader(fin,"UTF-8");
    		OWLModel to=ProtegeOWL.createJenaOWLModelFromReader(in);
    		DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
            treeModel = new DefaultTreeModel(root);  
            addpoint(treeModel,root,to.getOWLNamedClass("根节点"));
        }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tree = new JTree(treeModel);  
        tree.setCellEditor(new TypeNodeEditor(new JTextField(6)));  
        tree.setDragEnabled(true);  
//        try {  s
//            //tree.setTransferHandler(new MyJTreeTransferHandler());  
//        } catch (ClassNotFoundException e) {  
//            e.printStackTrace();  
//        }  
        setPopupMenu();  
    }  
    public void setPopupMenu(){  
       final JPopupMenu pop=new JPopupMenu();  
//        JMenuItem itemAdd=new JMenuItem("Add");  
//        JMenuItem itemDelete=new JMenuItem("Delete");  
//  
         pop.add(new AbstractAction("Add"){  
             public void actionPerformed(ActionEvent e) {  
                 add();  
             }  
         });  
         pop.add(new AbstractAction("Delete") {  
            public void actionPerformed(ActionEvent e) {  
                delete();  
            }  
        });  
       tree.addKeyListener(new KeyAdapter(){  
           @Override  
           public void keyPressed(KeyEvent e) {  
               if (isNotSelecting()) return ;  
               if (e.getKeyCode()==KeyEvent.VK_DELETE){  
                   delete();  
               }  
           }  
       });  
       tree.addMouseListener(new MouseAdapter(){  
           @Override  
           public void mouseReleased(MouseEvent e) {  
               if (!e.isPopupTrigger()) return;  
               if (isNotSelecting()) return;  
               pop.show(tree,e.getX(),e.getY());  
           }  
       });  
    }  
    private void add() {  
        DefaultMutableTreeNode parentNode=getSelectedNode();
        DefaultMutableTreeNode newNode=new DefaultMutableTreeNode("sdf");  
        model.insertNodeInto(newNode,parentNode,parentNode.getChildCount());//note it  
        getSelectedNode().add(newNode);  
        TreePath path = new TreePath(newNode.getPath());  
        tree.makeVisible(path);//note it  
        tree.setSelectionPath(path);  
        tree.scrollPathToVisible(path);  
        tree.startEditingAtPath(path);  
    }  
    private void delete() {  
    }  
    public DefaultMutableTreeNode getSelectedNode(){  
        return  (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();  
    }  
    public boolean isSelecting(){  
        return getSelectedNode()!=null;  
    }  
    public boolean isNotSelecting(){  
        return !isSelecting();  
    }  
    public JTree getTree() {  
        return tree;  
    }  
    class TypeTreeModelListener implements TreeModelListener {  
        public void treeNodesChanged(TreeModelEvent e) {  
        }  
        public void treeNodesInserted(TreeModelEvent e) {  
            //To change body of implemented methods use File | Settings | File Templates.  
        }  
        public void treeNodesRemoved(TreeModelEvent e) {  
        }  
        public void treeStructureChanged(TreeModelEvent e) {  
            //To change body of implemented methods use File | Settings | File Templates.  
        }  
    }  
    class TypeNodeEditor extends DefaultCellEditor{  
        private JTextField tf;  
        TypeNodeEditor(JTextField tf){  
           super(tf);  
           this.tf=tf;  
        }  
        @Override  
        public boolean stopCellEditing() {  
           return super.stopCellEditing();  
        }  
        @Override  
        public Object getCellEditorValue() {  
            return "hellos";  
        }  
    }  
}  
