package testUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;

public class MultipleTree extends JFrame implements TreeSelectionListener  
{  
	class NodeRenderer extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded,boolean leaf, int row, boolean hasFocus)
		{
			super.getTreeCellRendererComponent(tree, value,selected, expanded,leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if(orgnode.contains(node)){
				setForeground(Color.ORANGE);
			}
			else if(rednode.contains(node)){
				setForeground(Color.RED);
			}
			return this;
		}
	}
    private JLabel label;  
    private JTree tree=null;
    public Set<DefaultMutableTreeNode> rednode;
    public Set<DefaultMutableTreeNode> orgnode;
    private void addpoint(DefaultTreeModel treeModel,DefaultMutableTreeNode node,OWLNamedClass root)
    {
    	for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
			OWLNamedClass check=(OWLNamedClass)iter.next();
			DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(check.getPrefixedName());  
			treeModel.insertNodeInto(node1, node, node.getChildCount());
			addpoint(treeModel,node1,check);
		}
    }
    private void createowlmodel(OWLModel to, OWLNamedClass node1, MutableTreeNode node2)
    {
    	Enumeration te=node2.children();
    	while(te.hasMoreElements()){
    		MutableTreeNode tnode2=(MutableTreeNode) te.nextElement();
    		System.out.println(tnode2);
    		OWLNamedClass tnode1=to.createOWLNamedSubclass(tnode2.toString(), node1);
    		createowlmodel(to,tnode1,tnode2);
    	}
    }
    private int saveexcel(XSSFSheet sheet, DefaultMutableTreeNode node, int line,int col)
    {
    	XSSFRow row=null;
    	if(sheet.getRow(line)==null)	row=sheet.createRow(line);
    	else							row=sheet.getRow(line);
    	if(node.getChildCount()==0){
    		XSSFCell cell=row.createCell(col);
    		cell.setCellValue(node.toString());
    		return line+1;
    	}
    	else{
    		XSSFCell cell=row.createCell(col);
    		cell.setCellValue(node.toString());
    		Enumeration te=node.children();
    		while(te.hasMoreElements()){
    			DefaultMutableTreeNode tnode=(DefaultMutableTreeNode) te.nextElement();
    			line=saveexcel(sheet,tnode,line,col+1);
    		}
    	}
    	return line;
    }
    public MultipleTree(final String filepath)  
    {  
        super("功能树模型");  setSize(400,400);  
        Container container = getContentPane();  
        DefaultTreeModel treeModel =null;
        try{
        	FileInputStream fin=new FileInputStream(filepath);
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
        //创建树对象  
        tree = new JTree(treeModel);  
        //设置Tree的选择为一次只能选择一个节点  
        tree.getSelectionModel().setSelectionMode(  
                            TreeSelectionModel.SINGLE_TREE_SELECTION);  
        //注册监听器  
        tree.addTreeSelectionListener(this);  
        tree.setRowHeight(20);    
        //创建节点绘制对象  
        DefaultTreeCellRenderer cellRenderer =  
                            (DefaultTreeCellRenderer)tree.getCellRenderer();   
        //设置字体  
        cellRenderer.setFont(new Font("Serif",Font.PLAIN,14));  
        cellRenderer.setBackgroundNonSelectionColor(Color.white);  
        cellRenderer.setBackgroundSelectionColor(Color.yellow);  
        cellRenderer.setBorderSelectionColor(Color.red);    
        //设置选或不选时，文字的变化颜色  
        cellRenderer.setTextNonSelectionColor(Color.black);  
        cellRenderer.setTextSelectionColor(Color.blue);    
        rednode=new HashSet<DefaultMutableTreeNode>();
        orgnode=new HashSet<DefaultMutableTreeNode>();
        tree.setCellRenderer(new NodeRenderer());
        //把树对象添加到内容面板  
        container.add(new JScrollPane(tree));    
        //创建标签  
        label = new JLabel("你当前选择的节点为：",JLabel.CENTER);  
        label.setFont(new Font("Serif",Font.PLAIN,14));  
        container.add(label,BorderLayout.SOUTH);  
  
        setVisible(true);   //设置可见  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置窗口关闭动作  
        final JPopupMenu pop = new JPopupMenu();  
        pop.add(new AbstractAction("添加") {  
            private static final long serialVersionUID = 1L;  
  			@Override
			public void actionPerformed(ActionEvent e) {
  				DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                System.out.println(tree.getSelectionPath());
                TreePath tp=tree.getSelectionPath();
                MutableTreeNode selectnode=(MutableTreeNode) tp.getLastPathComponent();
                AddWindow ta=new AddWindow(tree);
			}  
        });  
        pop.add(new AbstractAction("删除") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
                DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                System.out.println(tree.getSelectionPath());
                TreePath tp=tree.getSelectionPath();
                treeModel.removeNodeFromParent((MutableTreeNode) tp.getLastPathComponent());
                tree.setModel(treeModel);
            }  
        });  
        pop.add(new AbstractAction("查询") {  
            private static final long serialVersionUID = 1L;  
  			@Override
			public void actionPerformed(ActionEvent e) {
                SearchWindow ta=new SearchWindow(tree,rednode);
			}  
        });  
        pop.add(new AbstractAction("保存") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
                DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                String ts=((MutableTreeNode)treeModel.getRoot()).toString();                
                System.out.println(ts);
                try {
					OWLModel to=ProtegeOWL.createJenaOWLModel();
					OWLNamedClass node1=to.createOWLNamedClass(ts);
					createowlmodel(to,node1,(MutableTreeNode)treeModel.getRoot());
					System.out.println(filepath);
					FileOutputStream outFile= new FileOutputStream(filepath);
				    Writer out = new OutputStreamWriter(outFile,"UTF-8");
				    OWLModelWriter omw = new OWLModelWriter(to, to.getTripleStoreModel().getActiveTripleStore(), out);
				    omw.write();
				    out.close();
				} catch (OntologyLoadException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }  
        });  
        pop.add(new AbstractAction("导出为excel文件") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
            	JFileChooser jfc=new JFileChooser();
				jfc.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e2) {
						if (e2.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
						{
							System.out.println(1);
							DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
			                try {
			                	XSSFWorkbook wb= new XSSFWorkbook();
			    				XSSFSheet sheet=wb.createSheet();
			    				int line=0;
			    				saveexcel(sheet,(DefaultMutableTreeNode)treeModel.getRoot(),line,0);
			    				System.out.println(wb);
			    				System.out.println(((JFileChooser)e2.getSource()).getSelectedFile());
			    				OutputStream out =new FileOutputStream(((JFileChooser)e2.getSource()).getSelectedFile());  
			    	            wb.write(out);
			    	            Runtime.getRuntime().exec("cmd  /c  start  "+((JFileChooser)e2.getSource()).getSelectedFile().toString());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}			
				});
				System.out.println(e.getSource());
				jfc.showSaveDialog((Component) e.getSource());
            }  
        });  
        tree.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseReleased(MouseEvent e) {  
                if (e.isMetaDown()) {  
                    pop.show(tree, e.getX(), e.getY());  
                }  
            }  
        });
    }  
    public MultipleTree(Map<String,TreeSet<String>> map, final String filepath)  
    {
    	super("属性态值");  setSize(400,400);  
        Container container = getContentPane();  
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
    	DefaultTreeModel treeModel = new DefaultTreeModel(root);  
    	for(Map.Entry<String, TreeSet<String>> entry:map.entrySet()){
    		DefaultMutableTreeNode node1=new DefaultMutableTreeNode(entry.getKey());
    		treeModel.insertNodeInto(node1,root,root.getChildCount());
    		for(String ts:entry.getValue()){
    			DefaultMutableTreeNode node2=new DefaultMutableTreeNode(ts);
        		treeModel.insertNodeInto(node2,node1,node1.getChildCount());
    		}
        }
    	tree = new JTree(treeModel);  
        //设置Tree的选择为一次只能选择一个节点  
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);  
        //注册监听器  
        tree.addTreeSelectionListener(this);
        tree.setRowHeight(20);    
        //创建节点绘制对象  
        DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer)tree.getCellRenderer();    
        //设置字体  
        cellRenderer.setFont(new Font("Serif",Font.PLAIN,14));  
        cellRenderer.setBackgroundNonSelectionColor(Color.white);  
        cellRenderer.setBackgroundSelectionColor(Color.yellow);  
        cellRenderer.setBorderSelectionColor(Color.red);    
        //设置选或不选时，文字的变化颜色  
        cellRenderer.setTextNonSelectionColor(Color.black);  
        cellRenderer.setTextSelectionColor(Color.blue);
        rednode=new HashSet<DefaultMutableTreeNode>();
        orgnode=new HashSet<DefaultMutableTreeNode>();
        tree.setCellRenderer(new NodeRenderer());
        //把树对象添加到内容面板  
        container.add(new JScrollPane(tree));    
        //创建标签  
        label = new JLabel("你当前选择的节点为：",JLabel.CENTER);  
        label.setFont(new Font("Serif",Font.PLAIN,14));  
        container.add(label,BorderLayout.SOUTH);    
        setVisible(true);   //设置可见  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置窗口关闭动作  
        
        final JPopupMenu pop = new JPopupMenu();  
        pop.add(new AbstractAction("添加") {  
            private static final long serialVersionUID = 1L;  
  			@Override
			public void actionPerformed(ActionEvent e) {
  				DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                System.out.println(tree.getSelectionPath());
                TreePath tp=tree.getSelectionPath();
                MutableTreeNode selectnode=(MutableTreeNode) tp.getLastPathComponent();
                AddWindow ta=new AddWindow(tree);
			}  
        });  
        pop.add(new AbstractAction("删除") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
                DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                System.out.println(tree.getSelectionPath());
                TreePath tp=tree.getSelectionPath();
                treeModel.removeNodeFromParent((MutableTreeNode) tp.getLastPathComponent());
                tree.setModel(treeModel);
            }  
        });  
        pop.add(new AbstractAction("查询") {  
            private static final long serialVersionUID = 1L;  
  			@Override
			public void actionPerformed(ActionEvent e) {
                SearchWindow ta=new SearchWindow(tree,rednode);
			}  
        });  
        pop.add(new AbstractAction("保存") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
                DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                String ts=((MutableTreeNode)treeModel.getRoot()).toString();                
                System.out.println(ts);
                try {
    				XSSFWorkbook wb= new XSSFWorkbook();
    				XSSFSheet sheet=wb.createSheet();
    				int line=0;
    				Enumeration te=((MutableTreeNode)treeModel.getRoot()).children();
    				while(te.hasMoreElements()){
    					MutableTreeNode node=(MutableTreeNode) te.nextElement();
    					Enumeration node_e=node.children();
    					while(node_e.hasMoreElements()){
    						MutableTreeNode node2=(MutableTreeNode) node_e.nextElement();
    						Row trow=sheet.createRow(line++);
    						Cell tcell=trow.createCell(0);
    						tcell.setCellValue(node.toString());
    						tcell=trow.createCell(1);
    						tcell.setCellValue(node2.toString());
    					}
    				}
    				OutputStream out =new FileOutputStream(filepath);  
    	            wb.write(out);
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
            }  
        });  
        pop.add(new AbstractAction("导出为excel文件") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
            	JFileChooser jfc=new JFileChooser();
				jfc.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e2) {
						if (e2.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
						{
							System.out.println(1);
							DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
			                try {
			                	XSSFWorkbook wb= new XSSFWorkbook();
			    				XSSFSheet sheet=wb.createSheet();
			    				int line=0;
			    				saveexcel(sheet,(DefaultMutableTreeNode)treeModel.getRoot(),line,0);
			    				System.out.println(wb);
			    				System.out.println(((JFileChooser)e2.getSource()).getSelectedFile());
			    				OutputStream out =new FileOutputStream(((JFileChooser)e2.getSource()).getSelectedFile());  
			    	            wb.write(out);
			    	            Runtime.getRuntime().exec("cmd  /c  start  "+((JFileChooser)e2.getSource()).getSelectedFile().toString());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}			
				});
				System.out.println(e.getSource());
				jfc.showSaveDialog((Component) e.getSource());
            }  
        });  
        tree.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseReleased(MouseEvent e) {  
                if (e.isMetaDown()) {  
                    pop.show(tree, e.getX(), e.getY());  
                }  
            }  
        });
    }
	public MultipleTree(Map<String, Map<String, Set<String>>> map,
			Map<String, Set<String>> map2, final String filepath) {
		super("态值归类");  setSize(400,400);  
        Container container = getContentPane();  
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
    	DefaultTreeModel treeModel = new DefaultTreeModel(root);  
    	for(Map.Entry<String, Map<String, Set<String>>> entry:map.entrySet()){
    		DefaultMutableTreeNode node1=new DefaultMutableTreeNode(entry.getKey());
    		treeModel.insertNodeInto(node1,root,root.getChildCount());
    		for(Map.Entry<String, Set<String>> te:entry.getValue().entrySet()){
    			DefaultMutableTreeNode node2=new DefaultMutableTreeNode(te.getKey());
        		treeModel.insertNodeInto(node2,node1,node1.getChildCount());
        		for(String ts:te.getValue()){
        			DefaultMutableTreeNode node3=new DefaultMutableTreeNode(ts);
            		treeModel.insertNodeInto(node3,node2,node2.getChildCount());
        		}
    		}
        }
    	rednode=new HashSet<DefaultMutableTreeNode>();
    	orgnode=new HashSet<DefaultMutableTreeNode>();
    	for(Map.Entry<String,Set<String>> te:map2.entrySet()){
    		String leiming=te.getKey();
    		int contins=0;
    		for(Map.Entry<String, Map<String, Set<String>>> entry:map.entrySet()){
    			String ts=entry.getKey();
    			if(leiming.length()>=ts.length()&&leiming.lastIndexOf(ts)==leiming.length()-ts.length()){
    				contins=1;
    				leiming=ts;
    				break;
    			}
    		}
    		if(contins==1){
    			for(String ts:te.getValue()){
    				int bool=0;
    				for(Map.Entry<String,Set<String>> entry:map.get(leiming).entrySet()){
    					if(ts==entry.getKey()||entry.getValue().contains(ts)){
    						bool=1;
    						break;
    					}
    				}
    				if(bool==0){
    					System.out.println(3);
    					Enumeration children=((MutableTreeNode)treeModel.getRoot()).children();
    					while(children.hasMoreElements()){
    						DefaultMutableTreeNode parent=(DefaultMutableTreeNode) children.nextElement();
    						System.out.println(parent.toString()+leiming);
    						if(parent.toString()==leiming){
    							orgnode.add(parent);
    							DefaultMutableTreeNode newchild=new DefaultMutableTreeNode(ts);
    							rednode.add(newchild);
    							treeModel.insertNodeInto(newchild, parent, parent.getChildCount());
    							break;
    						}
    					}
    				}
    			}
    		}
    		else{
    			System.out.println(2);
    			DefaultMutableTreeNode newnode=new DefaultMutableTreeNode(leiming);
    			rednode.add(newnode);
    			treeModel.insertNodeInto(newnode,root,root.getChildCount());
    			for(String ts:te.getValue()){
    				DefaultMutableTreeNode node2=new DefaultMutableTreeNode(ts);
    				rednode.add(node2);
            		treeModel.insertNodeInto(node2,newnode,newnode.getChildCount());
    			}
    		}
    	}
    	tree = new JTree(treeModel);  
        //设置Tree的选择为一次只能选择一个节点  
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);  
        //注册监听器  
        tree.addTreeSelectionListener(this);
        tree.setRowHeight(20);    
        //创建节点绘制对象  
        DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer)tree.getCellRenderer();    
        //设置字体  
        cellRenderer.setFont(new Font("Serif",Font.PLAIN,14));  
        cellRenderer.setBackgroundNonSelectionColor(Color.white);  
        cellRenderer.setBackgroundSelectionColor(Color.yellow);  
        cellRenderer.setBorderSelectionColor(Color.red);
        tree.setCellRenderer(new NodeRenderer());
        //设置选或不选时，文字的变化颜色  
        cellRenderer.setTextNonSelectionColor(Color.black);  
        cellRenderer.setTextSelectionColor(Color.blue);            
        //把树对象添加到内容面板  
        container.add(new JScrollPane(tree));    
        //创建标签  
        label = new JLabel("你当前选择的节点为：",JLabel.CENTER);  
        label.setFont(new Font("Serif",Font.PLAIN,14));  
        container.add(label,BorderLayout.SOUTH);    
        setVisible(true);   //设置可见  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置窗口关闭动作  
        
        final JPopupMenu pop = new JPopupMenu();  
        pop.add(new AbstractAction("添加") {  
            private static final long serialVersionUID = 1L;  
  			@Override
			public void actionPerformed(ActionEvent e) {
  				DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                System.out.println(tree.getSelectionPath());
                TreePath tp=tree.getSelectionPath();
                MutableTreeNode selectnode=(MutableTreeNode) tp.getLastPathComponent();
                AddWindow ta=new AddWindow(tree);
			}  
        });  
        pop.add(new AbstractAction("删除") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
                DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                System.out.println(tree.getSelectionPath());
                TreePath tp=tree.getSelectionPath();
                treeModel.removeNodeFromParent((MutableTreeNode) tp.getLastPathComponent());
                tree.setModel(treeModel);
            }  
        });  
        pop.add(new AbstractAction("查询") {  
            private static final long serialVersionUID = 1L;  
  			@Override
			public void actionPerformed(ActionEvent e) {
                SearchWindow ta=new SearchWindow(tree,rednode);
			}  
        });  
        pop.add(new AbstractAction("保存") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
                DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
                String ts=((MutableTreeNode)treeModel.getRoot()).toString();                
                System.out.println(ts);
                try {
    				XSSFWorkbook wb= new XSSFWorkbook();
    				XSSFSheet sheet=wb.createSheet();
    				int line=0;
    				Enumeration te=((MutableTreeNode)treeModel.getRoot()).children();
    				while(te.hasMoreElements()){
    					MutableTreeNode node=(MutableTreeNode) te.nextElement();
    					Enumeration node_e=node.children();
    					while(node_e.hasMoreElements()){
    						MutableTreeNode node2=(MutableTreeNode) node_e.nextElement();
    						Row trow=sheet.createRow(line++);
    						Cell tcell=trow.createCell(0);
    						tcell.setCellValue(node.toString());
    						tcell=trow.createCell(1);
    						tcell.setCellValue(node2.toString());
    					}
    				}
    				OutputStream out =new FileOutputStream(filepath);  
    	            wb.write(out);
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
            }  
        });  
        pop.add(new AbstractAction("导出为excel文件") {  
            private static final long serialVersionUID = 1L;  
            @Override
            public void actionPerformed(ActionEvent e) {  
            	JFileChooser jfc=new JFileChooser();
				jfc.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e2) {
						if (e2.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
						{
							System.out.println(1);
							DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
			                try {
			                	XSSFWorkbook wb= new XSSFWorkbook();
			    				XSSFSheet sheet=wb.createSheet();
			    				int line=0;
			    				saveexcel(sheet,(DefaultMutableTreeNode)treeModel.getRoot(),line,0);
			    				System.out.println(wb);
			    				System.out.println(((JFileChooser)e2.getSource()).getSelectedFile());
			    				OutputStream out =new FileOutputStream(((JFileChooser)e2.getSource()).getSelectedFile());  
			    	            wb.write(out);
			    	            Runtime.getRuntime().exec("cmd  /c  start  "+((JFileChooser)e2.getSource()).getSelectedFile().toString());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}			
				});
				System.out.println(e.getSource());
				jfc.showSaveDialog((Component) e.getSource());
            }  
        });  
        tree.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseReleased(MouseEvent e) {  
                if (e.isMetaDown()) {  
                    pop.show(tree, e.getX(), e.getY());  
                }  
            }  
        });
	}
	@Override
	public void valueChanged(TreeSelectionEvent event) {
		JTree tree = (JTree)event.getSource();  
        //获取目前选取的节点  
        DefaultMutableTreeNode selectionNode =  
            (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();  
        String nodeName = "";
        if(selectionNode!=null){
        	nodeName=selectionNode.toString();
        }
        label.setText("你当前选取的节点为： "+nodeName);  
	}
}  