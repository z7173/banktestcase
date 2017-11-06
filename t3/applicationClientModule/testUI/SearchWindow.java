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
		jb1=new JButton("��ѯ");
		jb2=new JButton("����");
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
		//���������ñ���
		this.setTitle("���԰�����ϵͳ");
		//���ô����С
		this.setSize(250,100);
		//���ô����ʼλ��
		this.setLocation(200, 150);
		//���õ��رմ���ʱ����֤JVMҲ�˳�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//��ʾ����
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
		if(e.getActionCommand()=="��ѯ"){
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
		else if(e.getActionCommand()=="����"){				
			this.dispose();
		}
	}

}
