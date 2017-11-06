package testUI;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.stanford.smi.protegex.owl.model.OWLNamedClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
public class testUI extends JFrame implements ActionListener{
	JButton btestcase,bkeyvalue,bowl1,bowl2=null;
	JButton jb1,jb2,jb3,jb4,jb5,jb6=null;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5=null;
	JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8=null;
    JTextField jtf=null;
	JFileChooser jfc1,jfc2,jfc3,jfc4,jfc5=null;
	JTextArea jta=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testUI  ms=new testUI();						
	}
	public testUI(){
		btestcase=new JButton("选择案例文件夹");
		btestcase.setPreferredSize(new Dimension(130,25));
		bkeyvalue=new JButton("选择键值表");
		bkeyvalue.setPreferredSize(new Dimension(130,25));
		bowl1=new JButton("owl文件1");
		bowl1.setPreferredSize(new Dimension(130,25));
		bowl2=new JButton("owl文件2");
		bowl2.setPreferredSize(new Dimension(130,25));
		jtf1=new JTextField(25);
		jtf2=new JTextField(25);
		jtf3=new JTextField(25);
		jtf4=new JTextField(25);
		jtf5=new JTextField(25);
		jta=new JTextArea(10,40);
		jtf1.setEditable(false);
		jtf2.setEditable(false);
		jtf3.setEditable(false);
		jtf4.setEditable(false);
		jta.setEditable(false);
		jb1=new JButton("生成功能树");
		jb2=new JButton("合并功能树");
		jb3=new JButton("提取属性态值对");
		jb4=new JButton("提取业务规则");
		jb6=new JButton("业务规则合并");
		jb5=new JButton("态值归类");
		
		
		btestcase.addActionListener(this);
		bkeyvalue.addActionListener(this);
		bowl1.addActionListener(this);
		bowl2.addActionListener(this);
		jtf1.addActionListener(this);
		jtf2.addActionListener(this);
		jtf3.addActionListener(this);
		jtf4.addActionListener(this);
		jtf5.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		jb5.addActionListener(this);
		jb6.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel();
		jp6=new JPanel();
		jp7=new JPanel();
		jp8=new JPanel();
		
		jp1.add(btestcase);
		jp1.add(jtf1);
		jp2.add(bkeyvalue);
		jp2.add(jtf2);
		jp3.add(bowl1);
		jp3.add(jtf3);
		jp4.add(bowl2);
		jp4.add(jtf4);
		jp5.add(jb1);
		jp5.add(jb2);
		jp5.add(jb3);
		jp6.add(jta);
		jp7.add(jb4);
		jp7.add(jb6);
		jp7.add(jb5);
		
		jfc1=new JFileChooser();
		jfc1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					jtf1.setText(jfc1.getSelectedFile().toString());
				}
			}			
		});
		jfc2=new JFileChooser();
		jfc2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					jtf2.setText(jfc2.getSelectedFile().toString());
				}
			}			
		});
		jfc3=new JFileChooser();
		jfc3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					jtf3.setText(jfc3.getSelectedFile().toString());
				}
			}			
		});
		jfc4=new JFileChooser();
		jfc4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					jtf4.setText(jfc4.getSelectedFile().toString());
				}
			}			
		});
		jfc5=new JFileChooser();
		jfc5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					jtf5.setText(jfc5.getSelectedFile().toString());
				}
			}			
		});
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
		this.add(jp7);
		this.add(jp6);
		this.setLayout(new GridLayout(8,1));
		//给窗口设置标题
		this.setTitle("测试案例库系统");
		//设置窗体大小
		this.setSize(500,350);
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
		System.out.println("action Commmand:"+ e.getActionCommand());
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="选择案例文件夹"){
			jfc1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc1.showOpenDialog(this);
		}
		else if(e.getActionCommand()=="选择键值表"){				
			jfc2.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc2.showOpenDialog(this);
		}
		else if(e.getActionCommand()=="owl文件1"){				
			jfc3.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc3.showOpenDialog(this);
		}
		else if(e.getActionCommand()=="owl文件2"){				
			jfc4.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc4.showOpenDialog(this);
		}
		else if(e.getActionCommand()=="生成功能树"){
			//jta.setText("正在生成功能树");
			//jta.paintImmediately(jta.getBounds());
			
			try {
			File file=new File(jfc1.getSelectedFile().toString());
			File[] filelist=file.listFiles();
			for(int filenum=0;filenum<filelist.length;filenum++){
				System.out.println(filelist[filenum]);
				XSSFWorkbook wb;
				wb = new XSSFWorkbook(filelist[filenum]);				
				XSSFWorkbook owb=new XSSFWorkbook();
				for(int num=0;num<wb.getNumberOfSheets();num++){
					XSSFSheet sheet=wb.getSheetAt(num);
					XSSFSheet osheet=owb.createSheet();
					if(sheet.getRow(0)==null)	continue;
					int n=0;
					for(int i=12;sheet.getRow(i)!=null;i++){
						XSSFRow row=sheet.getRow(i);
						XSSFRow orow=osheet.createRow(n);
						for(int j=0;j<13;j++){
							if(row.getCell(j)!=null&&row.getCell(j).getCellTypeEnum()==CellType.STRING){
								orow.createCell(j).setCellValue(row.getCell(j).getStringCellValue());
							}
						}
						n++;
						row=sheet.getRow(i);
					}
				}
				wb.close();
				OutputStream out =new FileOutputStream(filelist[filenum].toString().replace(".xlsx", "1.xlsx"));  
	            owb.write(out);
			}
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
//			int num=FunctionModel.create(jfc1.getSelectedFile().toString(), "E:/bank/createtemp.owl");
//			jfc3.setSelectedFile(new File("E:/bank/createtemp.owl"));
//			jtf3.setText("E:/bank/createtemp.owl");
//			jta.setText("已完成功能树生成"+"\n"+"总计案例数量为："+num);
//			MultipleTree tree=new MultipleTree("E:/bank/createtemp.owl");
		}
		else if(e.getActionCommand()=="合并功能树"){
			FunctionModel.merge(jfc3.getSelectedFile().toString(),jfc4.getSelectedFile().toString(), "E:/bank/mergetemp.owl");
			jta.setText("已完成功能树合并");
			MultipleTree tree=new MultipleTree("E:/bank/mergetemp.owl");
		}
		else if(e.getActionCommand()=="提取业务规则"){
			//File file3=new File("E:/bank/temp.txt");
			//FileWriter fileWriter;
			try {
				//fileWriter = new FileWriter(file3);
				System.out.println(jfc1.getSelectedFile().toString()+ jfc2.getSelectedFile().toString());
				List<Case> temp=EntityLib.getKeyValue(jfc1.getSelectedFile().toString(), jfc2.getSelectedFile().toString());
				XSSFWorkbook wb= new XSSFWorkbook();
				XSSFSheet sheet=wb.createSheet();
				int width=sheet.getColumnWidth(0);
				sheet.setColumnWidth(0, 5*width);
				sheet.setColumnWidth(1, 3*width);
				sheet.setColumnWidth(2, 3*width);
				sheet.setColumnWidth(3, 3*width);
				sheet.setColumnWidth(4, 3*width);
				int line=0;
				for(int i=0;i<temp.size();i++){
					Row trow=sheet.createRow(line++);
					Cell tcell=trow.createCell(0);
					tcell.setCellValue(temp.get(i).function);
					tcell=trow.createCell(1);
					tcell.setCellValue(temp.get(i).purpose);
					tcell=trow.createCell(2);
					tcell.setCellValue(temp.get(i).success);					
					//fileWriter.write(temp.get(i).function+"\t"+temp.get(i).purpose+"\t"+temp.get(i).success+"\n");
					for(Map.Entry<String, String> entry : temp.get(i).key_value.entrySet()){
						trow=sheet.createRow(line++);
						tcell=trow.createCell(1);
						tcell.setCellValue(entry.getKey());
						tcell=trow.createCell(2);
						tcell.setCellValue(entry.getValue());
						//fileWriter.write("\t"+entry.getKey()+"\t"+entry.getValue()+"\n");
					}
				}
				OutputStream out =new FileOutputStream("E:/bank/guize_temp.xlsx");  
	            wb.write(out);
	            Runtime.getRuntime().exec("cmd  /c  start  E:/bank/guize_temp.xlsx");
				//fileWriter.flush();
				//fileWriter.close();
	            jta.setText("已完成业务规则提取"+"\n"+"总计文件数量："+jfc1.getSelectedFile().listFiles().length+"   "+"总计案例数量："+temp.size()+"   "+"平均属性个数："+(line-temp.size())/temp.size());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		else if(e.getActionCommand()=="业务规则合并"){
			String path=jfc1.getSelectedFile().toString();
			File file=new File(path);
			File[] filelist=file.listFiles();
			System.out.println("该目录下对象个数："+filelist.length);
			String fileName =jfc2.getSelectedFile().toString();		
			RuleModel.Rulemerge03Text(path, fileName);
			/*
			File file2=new File(fileName);
			ArrayList<String> shuxin=new ArrayList<String>();
			ArrayList<String> taizhi=new ArrayList<String>();	
			int testcasenum=0;
			try {
				File file3=new File("E:/bank/temp.txt");
				FileWriter fileWriter = new FileWriter(file3);
				int n=0;
				XSSFWorkbook wb= new XSSFWorkbook(file2);
				XSSFSheet sheet=wb.getSheetAt(0);
				int line=0;
				XSSFWorkbook outwb=new XSSFWorkbook();
				XSSFSheet outsheet=outwb.createSheet();
				int width=outsheet.getColumnWidth(0);
				outsheet.setColumnWidth(0, 3*width);
				outsheet.setColumnWidth(1, 3*width);
				outsheet.setColumnWidth(2, 3*width);
				outsheet.setColumnWidth(3, 3*width);
				outsheet.setColumnWidth(4, 3*width);
				for(int i=0;sheet.getRow(i)!=null;i++){
					XSSFRow row=sheet.getRow(i);
					if(row.getCell(0)==null)	continue;
					shuxin.add(row.getCell(0).getStringCellValue());
					row.getCell(1).setCellType(CellType.STRING);
					taizhi.add(row.getCell(1).getStringCellValue());
				}
				for(int filenum=0;filenum<filelist.length;filenum++){
					wb= new XSSFWorkbook(filelist[filenum]);
					sheet=wb.getSheetAt(0);
					fileWriter.write(sheet.getRow(2).getCell(3).getStringCellValue()+"\n");
					outsheet.createRow(line).createCell(0).setCellValue(filelist[filenum].getName());;
					line++;
					Map<String,Map<String,String>> allpro=new HashMap<String,Map<String,String>>();
					int num1=5;
					int num2=7;
					for(int i=0;i<10;i++){
						XSSFRow row=sheet.getRow(11);
						if(row.getCell(i)==null)	continue;
						if(row.getCell(i).getStringCellValue().equals("测试目的/场景"))	num1=i;
						if(row.getCell(i).getStringCellValue().equals("案例"))			num2=i;
					}
					for(int i=12;sheet.getRow(i)!=null;i++){
						testcasenum++;
						XSSFRow row=sheet.getRow(i);
						if(row.getCell(num1)==null)	continue;
						if(row.getCell(num2)==null)	continue;
						String buzhou=row.getCell(num1).getStringCellValue()+"，"+row.getCell(num2).getStringCellValue();
						String tempshuxin="";
						String tempstring="";
						String xinzhi="";
						if(row.getCell(num1).getStringCellValue().contains("成功")){
							xinzhi="true";
						}
						if(row.getCell(num2).getStringCellValue().contains("失败")){
							xinzhi="false";
						}
						String buzhuz[]=buzhou.split("，");
						for(int k=0;k<buzhuz.length;k++){
							String tshuxin="";
							for(int j=0;j<shuxin.size();j++){
								if(buzhuz[k].contains(shuxin.get(j))&&!tempshuxin.contains(shuxin.get(j))&&buzhuz[k].contains(taizhi.get(j))){
									System.out.println(shuxin.get(j)+taizhi.get(j));
									Map<String,String> tempmap;
									if(allpro.containsKey(shuxin.get(j))){
										tempmap=allpro.get(shuxin.get(j));
									}
									else{
										tempmap=new HashMap<String,String>();
									}
									if(buzhuz[k].contains("不相同")||buzhuz[k].contains("不一致")){
										if(!tempmap.containsKey("与"+taizhi.get(j)+"不同")){
											tempmap.put("与"+taizhi.get(j)+"不同", xinzhi);
										}
									}
									else{
										if(!tempmap.containsKey(taizhi.get(j))){
											tempmap.put(taizhi.get(j), xinzhi);
										}
									}							
									allpro.put(shuxin.get(j), tempmap);
									tshuxin+=shuxin.get(j);
								}
							}
							tempshuxin+=tshuxin;
						}
						for(int j=0;j<shuxin.size();j++){
							if(buzhou.contains(shuxin.get(j))&&!tempshuxin.contains(shuxin.get(j))&&buzhou.contains(taizhi.get(j))){
								System.out.println(shuxin.get(j)+taizhi.get(j));
								Map<String,String> tempmap;
								if(allpro.containsKey(shuxin.get(j))){
									tempmap=allpro.get(shuxin.get(j));
								}
								else{
									tempmap=new HashMap<String,String>();
								}
								if(!tempmap.containsKey(taizhi.get(j))){
									tempmap.put(taizhi.get(j), xinzhi);
								}
								allpro.put(shuxin.get(j), tempmap);
								tempshuxin+=shuxin.get(j);
							}
						}					
					}
					for(Map.Entry<String, Map<String,String>> entry : allpro.entrySet()){
						String ts="";
						String ts2="";
						String ts3=entry.getKey();
						for(Map.Entry<String, String> entry2 : entry.getValue().entrySet()){
							ts3+="\t"+entry2.getKey()+entry2.getValue();
							if(entry2.getValue()=="true"){
								ts+=entry2.getKey()+"、";
							}
							else{
								ts2+=entry2.getKey()+"、";
							}
						}
						String cellstring="验证当"+entry.getKey();
						fileWriter.write("验证当"+entry.getKey());
						if(!ts.isEmpty()){
							fileWriter.write("为"+ts.substring(0,ts.length()-1)+"时成功，");
							cellstring+="为"+ts.substring(0,ts.length()-1)+"时成功，";
						}
						if(!ts2.isEmpty()){
							fileWriter.write("为"+ts2.substring(0,ts2.length()-1)+"时失败");
							cellstring+="为"+ts2.substring(0,ts2.length()-1)+"时失败";
						}
						fileWriter.write("\t"+ts3+"\n");
						XSSFRow row=outsheet.createRow(line);
						line++;
						row.createCell(0).setCellValue(cellstring);
						row.createCell(1).setCellValue(entry.getKey());
						int col=2;
						for(Map.Entry<String, String> entry2 : entry.getValue().entrySet()){
							row.createCell(col).setCellValue(entry2.getKey()+entry2.getValue());
							col++;
						}
					}
				}
				System.out.println(testcasenum);
				fileWriter.flush();
				fileWriter.close();
				OutputStream out =new FileOutputStream("E:/bank/guize_hebin_temp.xlsx");  
	            outwb.write(out);
				Runtime.getRuntime().exec("cmd  /c  start  E:/bank/guize_hebin_temp.xlsx");
			}catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (InvalidFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			*/
		}
		else if(e.getActionCommand()=="提取属性态值对"){			
			File path = new File(jfc1.getSelectedFile().toString());
	        File[] files = path.listFiles();
	        ExcelReader excelReader ;
	        List<TestCase> testCases = new ArrayList<TestCase>();
	        List<TestCase> total = new ArrayList<TestCase>();
	        for (File file : files){
	            excelReader = new ExcelReader(file);
	            total.addAll(excelReader.getTextCaseDes03());
	        }
	        for (TestCase testCase : total){
	            System.out.println(testCase.caseDes);
	        }
	        Map<String, TreeSet<String>> setMap =  Rule4.mineFieldAndFld03test(total);
	        File newMatch = new File("E:/bank/temp.txt");
	        FileWriter fileWriter;
			try {
				XSSFWorkbook wb= new XSSFWorkbook();
				XSSFSheet sheet=wb.createSheet();
				int line=0;
		        for (Map.Entry<String, TreeSet<String>> entry : setMap.entrySet()){
		            for (String FLD: entry.getValue()){
		            	Row trow=sheet.createRow(line++);
						Cell tcell=trow.createCell(0);
						tcell.setCellValue(entry.getKey());
						tcell=trow.createCell(1);
						tcell.setCellValue(FLD);
		            }
		        }
				OutputStream out =new FileOutputStream("E:/bank/key_value_temp.xlsx");  
	            wb.write(out);
//	            Runtime.getRuntime().exec("cmd  /c  start  E:/bank/key_value_temp.xlsx");
	            jfc2.setSelectedFile(new File("E:/bank/key_value_temp.xlsx"));
	            jtf2.setText("E:/bank/key_value_temp.xlsx");
	            jta.setText("已完成属性态值提取"+"\n"+"总计文件数量："+jfc1.getSelectedFile().listFiles().length+"   "+"总计属性数量："+setMap.size()+"   "+"总计属性-态值对数量："+line);
	            MultipleTree tree=new MultipleTree(setMap,"E:/bank/key_value_temp.xlsx");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
		else if(e.getActionCommand()=="态值归类"){			
			try {
				XSSFWorkbook wb;
				String filepath=jfc2.getSelectedFile().toString();
				System.out.println(filepath);
				File file=new File("E:/bank/samewords.xlsx");
				wb = new XSSFWorkbook(file);
				XSSFSheet sheet=wb.getSheetAt(0);
				TreeSet<String> leibie=new TreeSet<String>();
				Map<String,Map<String,Set<String>>> map=new HashMap<String,Map<String,Set<String>>>();
				for(int i=0;sheet.getRow(i)!=null;i++){
					Row row=sheet.getRow(i);
					String leiming=row.getCell(0).getStringCellValue();
					if(leiming=="")	continue;
					leibie.add(leiming);
					Map<String,Set<String>> tset=null;
					if(map.containsKey(leiming)){
						tset=map.get(leiming);
					}
					else{
						tset=new HashMap<String,Set<String>>();
					}
					Set<String> samewords=new HashSet<String>();
					String word=null;
					for(int j=1;row.getCell(j)!=null;j++){
						Cell cell=row.getCell(j);
						cell.setCellType(CellType.STRING);
						String temp=cell.getStringCellValue();
						if(j==1)	word=temp;
						samewords.add(temp);
					}
					tset.put(word,samewords);
					map.put(leiming,tset);					
				}
				File file2=new File(filepath);
				wb = new XSSFWorkbook(file2);
				XSSFSheet sheet2=wb.getSheetAt(0);				
				Map<String,Set<String>> map2=new HashMap<String,Set<String>>();
				for(int i=0;sheet2.getRow(i)!=null;i++){
					Row row=sheet2.getRow(i);
					String leiming=row.getCell(0).getStringCellValue();
					Set tset=null;
					if(map2.containsKey(leiming)){
						tset=map2.get(leiming);
					}
					else{
						tset=new HashSet();
					}
					Cell cell=row.getCell(1);
					if(cell==null)	continue;
					cell.setCellType(CellType.STRING);
					String temp=cell.getStringCellValue();
					tset.add(temp);
					map2.put(leiming,tset);					
				}
				MultipleTree tree=new MultipleTree(map,map2,"E:/bank/samewords.xlsx");
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

}
