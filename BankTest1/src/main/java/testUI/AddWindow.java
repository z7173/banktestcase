package testUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

public class AddWindow extends JFrame implements ActionListener{

	JTextField jtf1=null;
	JPanel jp1,jp2=null;
	JButton jb1,jb2=null;
	JTree tree=null;
	public AddWindow(JTree jTree) {
		jtf1=new JTextField(15);
		jb1=new JButton("确定");
		jb2=new JButton("取消");
		jp1=new JPanel();
		jp2=new JPanel();
		jtf1.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jp1.add(jtf1);
		jp2.add(jb1);
		jp2.add(jb2);
		tree=jTree;
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//显示窗体
		this.setVisible(true);
		this.setResizable(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="确定"){
			DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
			TreePath tp=tree.getSelectionPath();
			DefaultMutableTreeNode node1=new DefaultMutableTreeNode(jtf1.getText());
			treeModel.insertNodeInto(node1,(MutableTreeNode) tp.getLastPathComponent(), ((DefaultMutableTreeNode) tp.getLastPathComponent()).getChildCount());
			tree.setModel(treeModel);
			System.out.println(((JButton)e.getSource()).getParent());
			System.out.println(this);
			this.dispose();
		}
		else if(e.getActionCommand()=="取消"){				
			this.dispose();
		}
	}
}
