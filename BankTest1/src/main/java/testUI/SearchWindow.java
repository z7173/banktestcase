package testUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

public class SearchWindow extends JFrame implements ActionListener{

	JTextField jtf1=null;
	JPanel jp1,jp2=null;
	JButton jb1,jb2=null;
	JTree tree=null;
	Set<DefaultMutableTreeNode> rednode;
	public SearchWindow(JTree jTree,Set<DefaultMutableTreeNode> trednode) {
		jtf1=new JTextField(15);
		jb1=new JButton("查询");
		jb2=new JButton("返回");
		jp1=new JPanel();
		jp2=new JPanel();
		jtf1.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jp1.add(jtf1);
		jp2.add(jb1);
		jp2.add(jb2);
		tree=jTree;
		rednode=trednode;
		this.add(jp1);
		this.add(jp2);
		this.setLayout(new GridLayout(2,1));
		//给窗口设置标题
		this.setTitle("测试案例库系统");
		//设置窗体大小
		this.setSize(250,100);
		//设置窗体初始位置
		this.setLocation(200, 150);
		//设置当关闭窗口时，保证JVM也退出
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//显示窗体
		this.setVisible(true);
		this.setResizable(true);
	}
	private DefaultMutableTreeNode searchnode(DefaultMutableTreeNode node,String name)
	{
		if(node.toString().equals(name))	return node;
		System.out.println(node.toString());
		Enumeration te=node.children();
		while(te.hasMoreElements()){
			DefaultMutableTreeNode tnode=(DefaultMutableTreeNode) te.nextElement();
			DefaultMutableTreeNode res=searchnode(tnode,name);
			if(res!=null)	return res;
		}
		return null;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="查询"){
			String ts=jtf1.getText();
			DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode ans=searchnode((DefaultMutableTreeNode)treeModel.getRoot(),ts);
			rednode.clear();
			if(ans!=null){
				while(ans!=treeModel.getRoot()){
					rednode.add(ans);
					ans=(DefaultMutableTreeNode) ans.getParent();
				}
			}
			System.out.println(rednode);
			tree.repaint();
			tree.setVisible(true);
		}
		else if(e.getActionCommand()=="返回"){				
			this.dispose();
		}
	}

}
