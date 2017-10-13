

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.Object;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
	


























import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.POIXMLDocument;
import org.bson.Document;
//
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.hankcs.hanlp.HanLP;
//import com.hankcs.hanlp.seg.CRF.CRFSegment;
//import com.hankcs.hanlp.seg.Segment;
//import com.hankcs.hanlp.seg.common.Term;
//
//import edu.stanford.smi.protegex.owl.ProtegeOWL;
//import edu.stanford.smi.protegex.owl.model.OWLModel;
//import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
//import edu.stanford.smi.protegex.*;
//import edu.stanford.smi.protege.model.Model;
//import edu.stanford.smi.protegex.owl.ProtegeOWL;
//import edu.stanford.smi.protegex.owl.model.OWLModel;
//import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
//import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;



































































import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;
import javassist.bytecode.Descriptor.Iterator;

import javax.script.*;




public class Main {
	
//
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		try{
//			FileWriter fileWriter2 = new FileWriter(new File("E:/bank/lib2.txt"));
//			FileInputStream fin2=new FileInputStream("E:\\ttesst5.owl");
//			Reader in2 = new InputStreamReader(fin2,"UTF-8");
//			OWLModel to2=ProtegeOWL.createJenaOWLModelFromReader(in2);
//			OWLNamedClass root=to2.getOWLNamedClass("���ڵ�");
//			for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
//				OWLNamedClass temp=(OWLNamedClass)iter.next();
//				for(java.util.Iterator iter2=temp.getDirectSubclasses().iterator();iter2.hasNext();){
//					OWLNamedClass temp2=(OWLNamedClass)iter.next();
//					for(java.util.Iterator iter3=temp2.getDirectSubclasses().iterator();iter3.hasNext();){
//						OWLNamedClass temp3=(OWLNamedClass)iter.next();
//						fileWriter2.write(root.getN+"\t"+temp+"\t"+temp2+"\t"+temp3+"\n");
//					}
//					if(temp2.getDirectSubclassCount()==0){
//						fileWriter2.write(root+"\t"+temp+"\t"+temp2+"\n");
//					}
//				}
//			}	
//		}
//			catch (Exception e)
//			  {
//			  }
//	}
	public static void createpoint(OWLNamedClass root,OWLNamedClass temp,OWLModel to){
		OWLNamedClass newroot = to.createOWLNamedSubclass(temp.getName(), root);
		for(java.util.Iterator iter=temp.getDirectSubclasses().iterator();iter.hasNext();){
			OWLNamedClass newtemp=(OWLNamedClass)iter.next();
			createpoint(newroot,newtemp,to);
		}
	}
	public static void hebin(OWLNamedClass root,OWLNamedClass root2,OWLModel to){
		for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
			OWLNamedClass temp=(OWLNamedClass)iter.next();
			String name=temp.getName();
			for(int i=0;i<10;i++){
				name=name.replace(i+"", "");
			}
			java.util.Iterator iter2=root2.getDirectSubclasses().iterator();
			for(;iter2.hasNext();){
				OWLNamedClass temp2=(OWLNamedClass)iter2.next();
				String name2=temp2.getName();
				if(name2.contains(name)){
					hebin(temp,temp2,to);
					break;
				}
			}
			if(!iter2.hasNext()){
				createpoint(root2,temp,to);
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static void main5(String[] args) {
		try{
			Map<String,Integer> map=new HashMap<String,Integer>(); 
			FileInputStream fin=new FileInputStream("E:\\ttesst4.owl");
			Reader in = new InputStreamReader(fin,"UTF-8");
			OWLModel to=ProtegeOWL.createJenaOWLModelFromReader(in);
			OWLNamedClass root=to.getOWLNamedClass("���ڵ�");
			in.close();
			fin.close();
			FileInputStream fin2=new FileInputStream("E:\\ttesst5.owl");
			Reader in2 = new InputStreamReader(fin2,"UTF-8");
			OWLModel to2=ProtegeOWL.createJenaOWLModelFromReader(in2);
			OWLNamedClass root2=to2.getOWLNamedClass("���ڵ�");
			hebin(root,root2,to2);			
			in2.close();
			fin2.close();
			FileOutputStream outFile= new FileOutputStream("E:\\hebing3.owl");
		    Writer out = new OutputStreamWriter(outFile,"UTF-8");
		    OWLModelWriter omw = new OWLModelWriter(to2, to2.getTripleStoreModel().getActiveTripleStore(), out);
		    omw.write();
		    out.close();			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void main7(String[] args) {
		String path="E:/bank/���԰���������/new";
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("��Ŀ¼�¶��������"+filelist.length);
		String fileName ="E:/bank/ҵ���ֵ�-������.xlsx";		
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
				Map<String,Map<String,String>> allpro=new HashMap<String,Map<String,String>>();
				for(int i=2;sheet.getRow(i)!=null;i++){
					testcasenum++;
					XSSFRow row=sheet.getRow(i);
					if(row.getCell(4)==null)	continue;
					if(row.getCell(5)==null)	continue;
					String buzhou=row.getCell(4).getStringCellValue()+"��"+row.getCell(5).getStringCellValue()+"��"+row.getCell(6).getStringCellValue()+"��"+row.getCell(7).getStringCellValue();
					String tempshuxin="";
					String tempstring="";
					String xinzhi="";
					if(row.getCell(7).getStringCellValue().contains("�ɹ�")){
						xinzhi="true";
					}
					if(row.getCell(7).getStringCellValue().contains("ʧ��")){
						xinzhi="false";
					}
					String buzhuz[]=buzhou.split("��");
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
								if(buzhuz[k].contains("����ͬ")||buzhuz[k].contains("��һ��")){
									if(!tempmap.containsKey("��"+taizhi.get(j)+"��ͬ")){
										tempmap.put("��"+taizhi.get(j)+"��ͬ", xinzhi);
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
							ts+=entry2.getKey()+"��";
						}
						else{
							ts2+=entry2.getKey()+"��";
						}
					}
					fileWriter.write("��֤��"+entry.getKey());
					if(!ts.isEmpty()){
						fileWriter.write("Ϊ"+ts.substring(0,ts.length()-1)+"ʱ�ɹ���");
					}
					if(!ts2.isEmpty()){
						fileWriter.write("Ϊ"+ts2.substring(0,ts2.length()-1)+"ʱʧ��");
					}
					fileWriter.write("\t"+ts3+"\n");
				}
			}
			System.out.println(testcasenum);
			fileWriter.flush();
			fileWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main11(String[] args) throws IOException
	{
//		File file3=new File("E:/bank/temp.txt");
//		FileWriter fileWriter = new FileWriter(file3);
//		List<Case> temp=EntityLib.getKeyValue("E:/bank/���԰���������/01���԰���", "E:/bank/ҵ���ֵ�-������(1).xlsx");
//		for(int i=0;i<temp.size();i++){
//			fileWriter.write(temp.get(i).function+"\t"+temp.get(i).purpose+"\t"+temp.get(i).success+"\n");
//			for(Map.Entry<String, String> entry : temp.get(i).key_value.entrySet()){
//				fileWriter.write("\t"+entry.getKey()+"\t"+entry.getValue()+"\n");
//			}
//		}
//		fileWriter.flush();
//		fileWriter.close();
		ArrayList<Integer> test=new ArrayList<Integer>();
		test.add(2100);
		test.add(1999);
		test.add(110000);
		test.add(100001);
		test.add(500001);
		test.add(499999);
		test.add(888888);
		test.add(99000);
		System.out.println(EntityLib.getbreakpoint(test));
		
	}
	
	
	public static void main10(String[] args) {
		String path="E:/bank/���԰���������/new";
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("��Ŀ¼�¶��������"+filelist.length);
		String fileName ="E:/bank/ҵ���ֵ�-������.xlsx";		
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
				Map<String,Integer> allpro=new HashMap<String,Integer>();
				for(int i=2;sheet.getRow(i)!=null;i++){
					testcasenum++;
					XSSFRow row=sheet.getRow(i);
					String m=row.getCell(7).getStringCellValue();
					Integer bool=0;
					if(m.contains("�ɹ�")||m.contains("��ȷ"))	bool=1;
					if(m.contains("ʧ��"))	bool=-1;
					m=m.replace("��"," ");
					m=m.replace("��"," ");
					m=m.replace("\n"," ");
					String miaoshu[]=m.split(" ");
					for(int k=0;k<miaoshu.length;k++){
						allpro.put(miaoshu[k], bool);
					}							
				}
				for(Map.Entry<String, Integer> entry : allpro.entrySet()){
					String ts=entry.getKey();
					String ts2="";
					if(entry.getValue()==1){
						ts2="����";
					}
					if(entry.getValue()==-1){
						ts2="����";
					}
					fileWriter.write(ts+"\t"+ts2+"\n");
				}
			}
			System.out.println(testcasenum);
			fileWriter.flush();
			fileWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		try {
			String path="E:/bank/ҵ���ֵ�-������(1).xlsx";
			File file=new File(path);
			File file2=new File("E:/bank/temp.txt");
			XSSFWorkbook wb=new XSSFWorkbook(file);
			XSSFSheet sheet=wb.getSheetAt(0);
			Set<String> set=new HashSet<String>();
			Set<String> typeset=new HashSet<String>();
			String keytype[]={"����","����״̬","ʹ��״̬","���","����","��1","����","��ϵ","����","��ʽ","��־","�˻�","����","����","����","����","�˻�","���","����","����","ƾ֤״̬","�˺�״̬"};
			Map<String,Integer> map=new HashMap<String,Integer>();
			Map<String,Set<String>> map2=new HashMap<String,Set<String>>();
			Map<String,String> keyword=new HashMap<String,String>();
			int btotal=0,ntotal=0;
			for(int i=1;sheet.getRow(i)!=null;i++){
				String temp=sheet.getRow(i).getCell(0).getStringCellValue();
				set.add(temp);
			}
			System.out.println(set);
			InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(file2),"UTF-8"); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader);
            for(String ts:set){
            	String ts2=br.readLine();
            	if(map.containsKey(ts2)){
            		map.put(ts2,map.get(ts2)+1);
            	}
            	else{
            		map.put(ts2,1);
            	}
            	keyword.put(ts,ts2);
            }
            for(Map.Entry<String, Integer> entry:map.entrySet()){
            	if(entry.getValue()>8){
            		System.out.println(entry.getKey()+entry.getValue());
            	}
            }
			for(String ts:set){				
				int bool=0;
				for(int i=0;i<keytype.length;i++){
					if(ts.length()>=keytype[i].length()&&ts.lastIndexOf(keytype[i])==ts.length()-keytype[i].length()){
						bool=1;
//						if(i==0||i==5||i==6||i==9||i==10){
//							System.out.println(ts);
//						}
						break;
					}
				}
				if(bool==0){
					typeset.add(ts);
					if(map.containsKey(ts.substring(ts.length()-2))){
						map.put(ts.substring(ts.length()-2), map.get(ts.substring(ts.length()-2))+1);
					}
					else{
						map.put(ts.substring(ts.length()-2), 1);
					}
				}
			}
			for(Map.Entry<String,String> te:keyword.entrySet()){
				map2.put(te.getValue(),new HashSet<String>());
			}
			for(int i=1;sheet.getRow(i)!=null;i++){
				String temp=sheet.getRow(i).getCell(0).getStringCellValue();
				Set<String> tset=map2.get(keyword.get(temp));
				sheet.getRow(i).getCell(1).setCellType(CellType.STRING);
				tset.add(sheet.getRow(i).getCell(1).getStringCellValue());
				map2.put(keyword.get(temp), tset);
				btotal++;
			}
			System.out.println(typeset.size());
			for(Map.Entry<String, Set<String>> te:map2.entrySet()){
				System.out.print(te.getKey());
				for(String ts:te.getValue()){
					System.out.println("\t"+ts);
					ntotal++;
				}
			}
			System.out.println(btotal+"  "+ntotal);
			for(int i=0;i<keytype.length;i++){
				for(String ts:set){
					if(ts.length()>=keytype[i].length()&&ts.lastIndexOf(keytype[i])==ts.length()-keytype[i].length()){
						System.out.println(ts);
					}						
				}
			}
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public static void main6(String[] args) {
		String path="E:/bank/���԰���������/new";
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("��Ŀ¼�¶��������"+filelist.length);
		String fileName ="E:/bank/ҵ���ֵ�-������.xlsx";		
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
				fileWriter.write(sheet.getRow(12).getCell(3).getStringCellValue());
				int num1=5;
				int num2=7;
				for(int i=0;i<10;i++){
					XSSFRow row=sheet.getRow(11);
					if(row.getCell(i)==null)	continue;
					if(row.getCell(i).getStringCellValue().equals("����Ŀ��/����"))	num1=i;
					if(row.getCell(i).getStringCellValue().equals("����"))			num2=i;
				}
				for(int i=2;sheet.getRow(i)!=null;i++){
					testcasenum++;
					XSSFRow row=sheet.getRow(i);
					if(row.getCell(4)==null)	continue;
					if(row.getCell(5)==null)	continue;
					if(row.getCell(5).getCellTypeEnum()!=CellType.STRING)	continue;
					fileWriter.write("\t");
					fileWriter.write(row.getCell(4).getStringCellValue());
					String buzhou=row.getCell(4).getStringCellValue()+row.getCell(5).getStringCellValue()+row.getCell(6).getStringCellValue()+row.getCell(7).getStringCellValue();
					String tempshuxin="";
					String tempstring="";
					String[] tempmiaoshu=row.getCell(num1).getStringCellValue().split("��");
					for(int j=0;j<tempmiaoshu.length;j++){
						if(tempmiaoshu[j].contains("����")){
							tempstring+=tempmiaoshu[j].substring(tempmiaoshu[j].indexOf("����")+2);
						}
					}
					//tempstring+=row.getCell(4).getStringCellValue();
					//tempstring+="\t";
					System.out.println(buzhou);
					for(int j=0;j<shuxin.size();j++){
						if(buzhou.contains(shuxin.get(j))&&!tempshuxin.contains(shuxin.get(j))&&buzhou.contains(taizhi.get(j))){
					//		fileWriter.write("\t"+shuxin.get(j)+"\t"+taizhi.get(j)+"\n\t");
							if(row.getCell(4).getStringCellValue().contains(shuxin.get(j))||row.getCell(num1).getStringCellValue().contains(taizhi.get(j)))
								tempstring+="��"+shuxin.get(j)+"="+taizhi.get(j)+"�� ";
							tempshuxin+=shuxin.get(j);
						}
					}
					tempstring+="\t";
					if(row.getCell(4).getStringCellValue().contains("�ɹ�")){
						tempstring+="�ɹ�";
					}
					if(row.getCell(4).getStringCellValue().contains("ʧ��")){
						tempstring+="ʧ��";
					}
					fileWriter.write("\t"+tempstring+"\n");
				}
			}
			System.out.println(testcasenum);
			fileWriter.flush();
			fileWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public static void main3(String[] args) {
		String path="E:/bank/���԰���������/����";
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("��Ŀ¼�¶��������"+filelist.length);
		try {
			int n=0;
			File filename = new File("E:/bank/attrList.txt"); // Ҫ��ȡ����·����input��txt�ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename),"UTF-8"); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line = "";
            ArrayList<String> shuxin=new ArrayList<String>();
            while((line = br.readLine()) != null){
            	shuxin.add(line);
            }
			File file3=new File("E:/bank/words2.txt");
			FileWriter fileWriter = new FileWriter(file3);
			FileWriter fileWriter2 = new FileWriter(new File("E:/bank/lib.txt"));
			int rulecount=0;
			ArrayList<Set> set=new ArrayList<Set>();
			Map<String,Set> map=new HashMap<String,Set>();
			Map<String,Set> map2=new HashMap<String,Set>();
			Map<String,Set> map3=new HashMap<String,Set>();
			Map<String,Set> map4=new HashMap<String,Set>();
			for(int i=0;i<shuxin.size();i++){
				map2.put(shuxin.get(i), new HashSet());
				map3.put(shuxin.get(i), new HashSet());
				map4.put(shuxin.get(i), new HashSet());
			}
			for(int filenum=0;filenum<filelist.length;filenum++){
				System.out.println(filelist[filenum]);
				XSSFWorkbook wb= new XSSFWorkbook(filelist[filenum]);
				for(int num=0;num<wb.getNumberOfSheets();num++){
					XSSFSheet sheet=wb.getSheetAt(num);
					if(sheet.getRow(0)==null)	continue;
					if(sheet.getRow(0).getCell(0)==null)	continue;
					if(sheet.getRow(0).getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
					if(!sheet.getRow(0).getCell(0).getStringCellValue().equals("����ģ��Ŀ¼*")){
						System.out.println(num);
						continue;
					}
					for(int i=1;sheet.getRow(i)!=null;i++){
						XSSFRow row=sheet.getRow(i);
						if(row.getCell(0)==null)	break;
						if(row.getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
						String s=row.getCell(0).getStringCellValue();
						for(int j=0;j<10;j++){
							s=s.replaceAll(""+j, "");
						}
						String[] s2=new String[10];
						s2=s.split("\\\\");
						String chanpin=s2[s2.length-2];
						String changjin=s2[s2.length-1];
						
						s=row.getCell(7).getStringCellValue()+"\n"+
							row.getCell(8).getStringCellValue()+"\n"+
//							row.getCell(9).getStringCellValue()+"\n"+
							row.getCell(11).getStringCellValue();
						if(row.getCell(9)!=null){
							s+="\n"+row.getCell(9).getStringCellValue();
						}
						for(int j=0;j<shuxin.size();j++){
							if(s.contains((shuxin.get(j)))){
								if(map.containsKey(chanpin+" "+changjin)){
									Set tset=map.get(chanpin+" "+changjin);
									tset.add(shuxin.get(j));
									map.put(chanpin+" "+changjin, tset);
								}
								else{
									Set tset=new HashSet();
									tset.add(shuxin.get(j));
									map.put(chanpin+" "+changjin, tset);
								}
								Set tset=map2.get(shuxin.get(j));
								tset.add(chanpin);
								map2.put(shuxin.get(j), tset);
								tset=map3.get(shuxin.get(j));
								tset.add(changjin);
								map3.put(shuxin.get(j), tset);
								tset=map4.get(shuxin.get(j));
								tset.add(chanpin+" "+changjin);
								map4.put(shuxin.get(j), tset);
							}
						}
					}
				}
			}
			int onlynum=0;
			int unknownnum=0;
			for(int i=0;i<shuxin.size();i++){
				if(map2.get(shuxin.get(i)).size()==0&&map3.get(shuxin.get(i)).size()==0)	continue;
				//System.out.println(shuxin.get(i)+map2.get(shuxin.get(i)));
				//System.out.println(shuxin.get(i)+map3.get(shuxin.get(i)));
				if(map2.get(shuxin.get(i)).size()==1&&map3.get(shuxin.get(i)).size()==1){
					onlynum++;
					System.out.println(shuxin.get(i)+"\t"+"only"+"\t"+map4.get(shuxin.get(i)));
				}
				else if(map2.get(shuxin.get(i)).size()>2*map3.get(shuxin.get(i)).size()){
					System.out.println(shuxin.get(i)+"\t"+"��������"+"\t"+map4.get(shuxin.get(i))+"\t"+map3.get(shuxin.get(i)));
				}
				else if(2*map2.get(shuxin.get(i)).size()<map3.get(shuxin.get(i)).size()){
					System.out.println(shuxin.get(i)+"\t"+"��Ʒ����"+"\t"+map4.get(shuxin.get(i))+"\t"+map2.get(shuxin.get(i)));
				}
				else{
					System.out.println(shuxin.get(i)+"\t"+"unknown"+"\t"+map4.get(shuxin.get(i)));
					if(map2.get(shuxin.get(i)).size()==map3.get(shuxin.get(i)).size()&&map2.get(shuxin.get(i)).size()==map4.get(shuxin.get(i)).size()){
						unknownnum++;
					}
				}
			}
			System.out.println(onlynum+" "+unknownnum);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main2(String[] args) {
		String path="E:/bank/���԰���������/����";
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("��Ŀ¼�¶��������"+filelist.length);
		String fileName ="E:/bank/��������_����_���ģ��_�Թ�����_���ܲ��԰���_����.xlsx";		
		File file2=new File(fileName);
		try {
			
			
			FileInputStream fin=new FileInputStream("E:\\ttesst5.owl");
			Reader in = new InputStreamReader(fin,"UTF-8");
			OWLModel to=ProtegeOWL.createJenaOWLModelFromReader(in);
			OWLNamedClass root=to.getOWLNamedClass("���ڵ�");
			FileWriter fileWriter2 = new FileWriter(new File("E:/bank/lib2.txt"));
			for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
				OWLNamedClass temp=(OWLNamedClass)iter.next();
				String ts=temp.getBrowserText();
				for(int i=0;i<10;i++){
					ts=ts.replaceAll(""+i, "");
				}
				fileWriter2.write(ts+"\t");
				for(java.util.Iterator iter2=temp.getDirectSubclasses().iterator();iter2.hasNext();){
					OWLNamedClass secondclass=(OWLNamedClass)iter2.next();
					ts=secondclass.getBrowserText();
					for(int i=0;i<10;i++){
						ts=ts.replaceAll(""+i, "");
					}
					fileWriter2.write(ts);
					for(java.util.Iterator iter3= secondclass.getDirectSubclasses().iterator();iter3.hasNext();){
						OWLNamedClass thirdclass=(OWLNamedClass)iter3.next();
						ts=thirdclass.getBrowserText();
						if(ts.contains("����")||ts.contains("����")||ts.contains("ȡ��")||ts.contains("��Ϣ����")||ts.contains("��Ϣ˰")||ts.contains("���")||ts.contains("����")||ts.contains("֧ȡ")){
							
						}
						for(int i=0;i<10;i++){
							ts=ts.replaceAll(""+i, "");
						}
						fileWriter2.write("\t"+ts+"\n\t");
					}
					if(secondclass.getDirectSubclassCount()==0)
						fileWriter2.write("\n\t");
				}
				fileWriter2.write("\n");
			}
			in.close();
			fin.close();
			fileWriter2.close();
			
			
			
			
			
			
			
			
//���ܸ���
//			Map<String,Integer> map=new HashMap<String,Integer>(); 
//			FileInputStream fin=new FileInputStream("E:\\ttesst4.owl");
//			Reader in = new InputStreamReader(fin,"UTF-8");
//			OWLModel to=ProtegeOWL.createJenaOWLModelFromReader(in);
//			OWLNamedClass root=to.getOWLNamedClass("���ڵ�");
//			for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
//				OWLNamedClass temp=(OWLNamedClass)iter.next();
//				for(java.util.Iterator iter2=temp.getDirectSubclasses().iterator();iter2.hasNext();){
//					OWLNamedClass secondclass=(OWLNamedClass)iter2.next();
//					map.put(secondclass.getName(), secondclass.getDirectSubclassCount());
//				}
//			}
//			in.close();
//			fin.close();
//			FileInputStream fin2=new FileInputStream("E:\\ttesst5.owl");
//			Reader in2 = new InputStreamReader(fin2,"UTF-8");
//			OWLModel to2=ProtegeOWL.createJenaOWLModelFromReader(in2);
//			root=to2.getOWLNamedClass("���ڵ�");
//			for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
//				OWLNamedClass temp=(OWLNamedClass)iter.next();
//				for(java.util.Iterator iter2=temp.getDirectSubclasses().iterator();iter2.hasNext();){
//					OWLNamedClass secondclass=(OWLNamedClass)iter2.next();
//					double percent=0;
//					String name=secondclass.getName();
//					for(int i=0;i<10;i++){
//						name=name.replace(i+"", "");
//					}
//					if(map.containsKey(name)){
//						if(map.get(name)==0)	percent=1.0;
//						else	percent =(double)map.get(name)/secondclass.getSubclassCount();						
//						map.put(name,-1);
//					}
//					else{
//						for(String key:map.keySet()){
//							String tname=key;
//							for(int i=0;i<10;i++){
//								tname=tname.replace(i+"", "");
//							}
//							if(name.contains(tname)){
//								if(map.get(key)==-1){
//									System.out.print("something wrong");
//									System.out.println(tname);
//								}
//								if(map.get(key)==0)		percent=1.0;
//								else	percent =(double)map.get(key)/secondclass.getSubclassCount();
//								map.put(key,-1);
//								break;
//							}
//						}
//					}
//				}
//			}
//			java.util.Iterator iter=map.entrySet().iterator();
//			int a=0,b=0;
//			while(iter.hasNext()){
//				Map.Entry<String, Integer> temp=(Entry<String, Integer>) iter.next();
//				if(temp.getValue()!=-1){
//					System.out.println(temp.getKey());
//					OWLNamedClass temp2=to.getOWLNamedClass(temp.getKey());
//					OWLNamedClass parent=(OWLNamedClass)temp2.getDirectSuperclasses().iterator().next();
//					for(java.util.Iterator iter2=root.getDirectSubclasses().iterator();iter2.hasNext();){
//						OWLNamedClass check=(OWLNamedClass)iter2.next();
//						if(check.getName().contains(parent.getName())){
//							temp2=check;
//							break;
//						}					
//					}
//					if(!temp2.getName().contains(parent.getName())){
//						temp2=to2.createOWLNamedSubclass(parent.getName(), root);
//						temp2.addComment("new");
//					}
//					OWLNamedClass check=to2.createOWLNamedSubclass(temp.getKey(), temp2);
//					temp2=to.getOWLNamedClass(temp.getKey());
//					for(java.util.Iterator iter2=temp2.getDirectSubclasses().iterator();iter2.hasNext();){
//						to2.createOWLNamedSubclass(((OWLNamedClass)iter2.next()).getName(), check);
//					}
//					check.addComment("new");
//					a++;
//				}
//				else{
//					System.out.println("success");
//					b++;
//				}
//			}
//			in2.close();
//			fin2.close();
//			System.out.println(a);
//			System.out.print(b);
//			FileOutputStream outFile= new FileOutputStream("E:\\hebing.owl");
//		    Writer out = new OutputStreamWriter(outFile,"UTF-8");
//		    OWLModelWriter omw = new OWLModelWriter(to2, to2.getTripleStoreModel().getActiveTripleStore(), out);
//		    omw.write();
//		    out.close();
			
//			OWLModel owlModel = ProtegeOWL.createJenaOWLModel();
//			OWLNamedClass root=owlModel.createOWLNamedClass("���ڵ�");
//			for(int filenum=0;filenum<filelist.length;filenum++){
//				XSSFWorkbook wb= new XSSFWorkbook(filelist[filenum]);
//				for(int num=0;num<wb.getNumberOfSheets();num++){
//					XSSFSheet sheet=wb.getSheetAt(num);
//					if(sheet.getRow(0)==null)	continue;
//					if(sheet.getRow(0).getCell(0)==null)	continue;
//					if(sheet.getRow(0).getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
//					if(!sheet.getRow(0).getCell(0).getStringCellValue().equals("����ģ��Ŀ¼*")){
//						System.out.println(num);
//						continue;
//					}
//					for(int i=1;sheet.getRow(i)!=null;i++){
//						XSSFRow row=sheet.getRow(i);
//						if(row.getCell(0)==null)	break;
//						if(row.getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
//						String s=row.getCell(5).getStringCellValue();
//						String s2=row.getCell(7).getStringCellValue()+" "+
//								row.getCell(8).getStringCellValue()+" "+
//								row.getCell(11).getStringCellValue();
//						s2=s2.replace("\n"," ");
//						String[] s3=s2.split(" ");
//						String[] smark={"����","С��","����",">","<","="};
//						for(int t=0;t<s3.length;t++){
//							for(int k=0;k<6;k++){
//								if(s3[t].contains(smark[k])){
//									
//								}
//							}
//						}						
//					}
//				}
//			}

//ҵ����	
			
//			String[] sheetnamelist={"���ܶ��ձ�","ҵ���ܶ��ձ�"};
//			for(int filenum=0;filenum<filelist.length;filenum++){
//				XSSFWorkbook wb= new XSSFWorkbook(filelist[filenum]);
//				for(int sheetname=0;sheetname<sheetnamelist.length;sheetname++){
//					XSSFSheet sheet=wb.getSheet(sheetnamelist[sheetname]);
//					if(sheet==null)	continue;
//					for(int i=1;sheet.getRow(i)!=null;i++){
//						XSSFRow row=sheet.getRow(i);
//						OWLNamedClass temp=root;
//						for(int j=0;j<3;j++){
//							String s="";
//							for(int k=i;k>=0;k--){
//								if(sheet.getRow(k).getCell(j)==null)	continue;
//								if(sheet.getRow(k).getCell(j).getStringCellValue().equals(""))	continue;
//								s=sheet.getRow(k).getCell(j).getStringCellValue();
//								break;
//							}
//							int bool=1;
//							for(java.util.Iterator iter=temp.getDirectSubclasses().iterator();iter.hasNext();){
//								OWLNamedClass check=(OWLNamedClass)iter.next();
//								if(check.getName().contains(s)){
//									temp=check;
//									bool=0;
//									break;
//								}								
//							}
//							if(bool==1){
//								for(int k=0;k<1000;k++){
//									if(owlModel.getOWLNamedClass(s+k)==null){
//										temp=owlModel.createOWLNamedSubclass(s+k, temp);
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
//			}

//·������
//			int n=0;
//			File filename = new File("E:/bank/Output.txt"); // Ҫ��ȡ����·����input��txt�ļ�  
//            InputStreamReader reader = new InputStreamReader(  
//                    new FileInputStream(filename),"UTF-8"); // ����һ������������reader  
//            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
//            String line = "";  
//            line = br.readLine();  
//        	String[] sx=line.split(" ");
//			File file3=new File("E:/bank/words2.txt");
//			FileWriter fileWriter = new FileWriter(file3);
//			FileWriter fileWriter2 = new FileWriter(new File("E:/bank/lib.txt"));
//			String[] smark={"����","С��","����","Ϊ",">","<","="};
//			int rulecount=0;
//			for(int filenum=0;filenum<filelist.length;filenum++){
//				System.out.println(filelist[filenum]);
//				XSSFWorkbook wb= new XSSFWorkbook(filelist[filenum]);
//				for(int num=0;num<wb.getNumberOfSheets();num++){
//					XSSFSheet sheet=wb.getSheetAt(num);
//					if(sheet.getRow(0)==null)	continue;
//					if(sheet.getRow(0).getCell(0)==null)	continue;
//					if(sheet.getRow(0).getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
//					if(!sheet.getRow(0).getCell(0).getStringCellValue().equals("����ģ��Ŀ¼*")){
//						System.out.println(num);
//						continue;
//					}
//					for(int i=1;sheet.getRow(i)!=null;i++){
//						XSSFRow row=sheet.getRow(i);
//						if(row.getCell(0)==null)	break;
//						if(row.getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
//						String s=row.getCell(0).getStringCellValue();
//						for(int j=0;j<10;j++){
//							s=s.replaceAll(""+j, "");
//						}
//						String[] s2=new String[10];
//						s2=s.split("\\\\");
//						OWLNamedClass temp=root;
//						for(int j=1;j<s2.length;j++){	
//							int bool=1;
//							for(java.util.Iterator iter=temp.getDirectSubclasses().iterator();iter.hasNext();){
//								OWLNamedClass check=(OWLNamedClass)iter.next();
//								if(check.getName().contains(s2[j])){
//									temp=check;
//									bool=0;
//									break;
//								}								
//							}
//							if(bool==1){
//								for(int k=0;k<1000;k++){
//									if(owlModel.getOWLNamedClass(s2[j]+k)==null){
//										temp=owlModel.createOWLNamedSubclass(s2[j]+k, temp);			
//										break;
//									}
//								}
//							}
//						}
//						s=row.getCell(7).getStringCellValue()+"\n"+
//							row.getCell(8).getStringCellValue()+"\n"+
////							row.getCell(9).getStringCellValue()+"\n"+
//							row.getCell(11).getStringCellValue();
//						s=s.replace(" ","");
//						s=s.replace("\n"," ");
//						s=s.replace("��", " ");
//						s=s.replace("��", " ");
//						s=s.replace("��", " ");
//						s=s.replace("��", " ");
//						String[] s3=s.split(" ");
//						ArrayList<String> ts=new ArrayList<String>();
//						int tcount=0;
//						for(int t=0;t<s3.length;t++){
//							for(int k=0;k<smark.length;k++){
//								if(s3[t].contains(smark[k])){
//									ts.add(s3[t]);
//									s=s.replace(s3[t], "");
//									tcount=1;
////									String[] stwo=s3[t].replace(smark[k], " ").split(" ");
////									String prev="",next="";
////									if(stwo.length!=2){
////										System.out.println(s3[t]);
////										break;
////									}
////									for(int j=0;j<sx.length;j++){
////										if(stwo[0].contains(sx[j])){
////											prev=sx[j];
////											break;
////										}
////									}
////									for(int j=0;j<sx.length;j++){
////										if(stwo[1].contains(sx[j])){
////											next=sx[j];
////											break;
////										}
////									}
////									if(!s3[t].contains(prev+smark[k]+next)){
////										System.out.println(s3[t]+" "+prev+" "+next);
////									}
//									break;
//								}
//							}
//							if(!ts.contains(s3[t])){
//								int lastpos=-1;
//								for(int charnum=0;charnum<10;charnum++){
//									if(s3[t].lastIndexOf('0'+charnum)>lastpos){
//										lastpos=s3[t].lastIndexOf('0'+charnum);
//									}
//								}
//								if(lastpos!=-1&&(lastpos==s3[t].length()-1||s3[t].charAt(lastpos+1)!='��')){
//									ts.add(s3[t]);
//								}								
//							}
//						}
//						for(int k=0;k<sx.length;k++){
//							if(s.contains(sx[k])&&!ts.contains(sx[k])){
//								ts.add(sx[k]);
//							}
//						}
//						//ts+=row.getCell(5).getStringCellValue();
//						//temp.addComment(ts);
//						for(int first=0;first<ts.size();first++){
//							int second=0;
//							for(;second<ts.size();second++){
//								if(second!=first&&ts.get(second).contains(ts.get(first))){
//									break;
//								}
//							}
//							if(second==ts.size()){
//								fileWriter.write(ts.get(first)+" ");
//							}
//						}
//						fileWriter.write("\n");
//						n++;
//						rulecount+=tcount;
//						row=sheet.getRow(i);
//					}
//				}
//				wb.close();
//			}
//			fileWriter.close();
//			System.out.println(rulecount);
//			System.out.println(n);
			
//			FileOutputStream outFile= new FileOutputStream("E:\\ttesst7.owl");
//		    Writer out = new OutputStreamWriter(outFile,"UTF-8");
//		    OWLModelWriter omw = new OWLModelWriter(owlModel, owlModel.getTripleStoreModel().getActiveTripleStore(), out);
//		    omw.write();
//		    out.close();
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (InvalidFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
				
//			while(row.getCell(0)!=null){
//				if(row.getCell(0).getStringCellValue()=="")	break;
//				String temp="";
//				for(int j=3;j<7;j++){
//					if(row.getCell(j).getCellTypeEnum()==CellType.NUMERIC){
//						temp+=row.getCell(j).getNumericCellValue()+";\n";
//					}
//					else{
//						temp+=row.getCell(j).getStringCellValue()+";\n";
//					}
//				}
//				System.out.println(temp);
//				i+=2;
//				row=sheet.getRow(i);
//			}
		
//		Integer[] h1=new Integer[20];
//		Integer[] h2=new Integer[30];
//		Double[] score=new Double[1000];
//		
//		try {
//			String fileName ="E:/bank/t.xlsx";
//			File file=new File(fileName);
//			XSSFWorkbook wb= new XSSFWorkbook(file);
//			XSSFSheet sheet=wb.getSheetAt(0);
//			XSSFRow row=sheet.getRow(2);
//			XSSFRow row2=sheet.getRow(14);
//			String[] colunname1= new String [20];
//			String[] colunname2= new String [30];
//			int len1=0;
//			int len2=0;
//			for(int i=1;row.getCell(i)!=null;i++,len1++)
//				colunname1[i-1]=row.getCell(i).getStringCellValue();
//			for(int i=1;row2.getCell(i)!=null;i++,len2++)
//				colunname2[i-1]=row2.getCell(i).getStringCellValue();

//            JSONObject obj1 = new JSONObject();
//            JSONObject obj2 =new JSONObject();
//            JSONArray obj3=new JSONArray();
//            JSONObject input = new JSONObject();
//            obj1.element("type", 0);
//            obj1.element("items", obj3);
//            obj2.element("type", 0);
//            obj2.element("items", obj3);
//            input.element("type", 0);
//            System.out.print(len1);
//            for(int i=0;i<len1;i++){
//            	obj1.element("terms_sequence", colunname1[i]);
//            	obj3.add(obj1);
//            	input.element("qslots", obj3);
//            	obj3.clear();
//            	for(int j=0;j<len2;j++){
//            		obj2.element("terms_sequence", colunname2[j]);            		
//            		obj3.add(obj2);
//            		input.element("tslots", obj3);
//            		obj3.clear();
//            		JSONObject fin = new JSONObject();
//            		fin.element("input",input);
//            		URL u=new URL("https://aip.baidubce.com/rpc/2.0/nlp/v1/simnet?access_token=24.a2ae1700622cdaae2592f755e1152b5f.2592000.1491372279.282335-9359812");
//        			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
//        			connection.setDoOutput(true);
//                    connection.setDoInput(true);
//                    connection.setRequestMethod("POST");
//                    connection.setUseCaches(false);
//                    connection.setInstanceFollowRedirects(true);
//                    connection.setRequestProperty("Content-Type","application/json");
//                    connection.connect();
//            		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//            		out.write(fin.toString().getBytes("GBK"));
//            		out.flush();
//            		out.close();
//            		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            		String lines;
//                    String sb = "";
//                    while ((lines = reader.readLine()) != null) {
//                        lines = new String(lines.getBytes(), "GBK");
//                        sb+=lines;
//                    }
//                    JSONObject inp=JSONObject.fromObject(sb);
//                    if(!inp.containsKey("output")){
//                    	System.out.print("wrong");
//                    	j--;
//                    	System.out.println(inp);
//                    }else{
//                    	System.out.print(colunname1[i]+" "+colunname2[j]+" ");
//                    	System.out.println(inp.getJSONObject("output").getDouble("score"));
//                    	score[i*len2+j]=inp.getJSONObject("output").getDouble("score");
//                    }
//                    reader.close();
//                    connection.disconnect();
//                    Thread.sleep(1000);
//            	}
//            }
//			for(int i=0;i<len1;i++){
//				for(int j=0;j<len2;j++){
//					JSONObject fin = new JSONObject();
//					fin.element("tid", 1);
//					fin.element("query1",colunname1[i]);
//					fin.element("query2",colunname2[j]);
//					URL u=new URL("https://aip.baidubce.com/rpc/2.0/nlp/v1/wordembedding?access_token=24.a2ae1700622cdaae2592f755e1152b5f.2592000.1491372279.282335-9359812");
//        			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
//        			connection.setDoOutput(true);
//                    connection.setDoInput(true);
//                    connection.setRequestMethod("POST");
//                    connection.setUseCaches(false);
//                    connection.setInstanceFollowRedirects(true);
//                    connection.setRequestProperty("Content-Type","application/json");
//                    connection.connect();
//            		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//            		out.write(fin.toString().getBytes("GBK"));
//            		out.flush();
//            		out.close();
//            		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            		String lines;
//                    String sb = "";
//                    while ((lines = reader.readLine()) != null) {
//                        lines = new String(lines.getBytes(), "GBK");
//                        sb+=lines;
//                    }
//                    JSONObject inp=JSONObject.fromObject(sb);
//                    if(!inp.containsKey("sim")){
//                    	System.out.print("wrong");
//                    	j--;
//                    	System.out.println(inp);
//                    }else{
//                    	System.out.print(colunname1[i]+" "+colunname2[j]+" ");
//                    	System.out.println(inp.getJSONObject("sim").getDouble("sim"));
//                    	score[i*len2+j]=inp.getJSONObject("sim").getDouble("sim");
//                    }
//                    reader.close();
//                    connection.disconnect();
//                    Thread.sleep(1000);
//				}
//			}
//            System.out.println("finish");
//            wb.close();
//            double max=0;
//            int pos=0;
//            for(int i=0;i<len1*len2;i++){
//            	if(score[i]>max){
//            		max=score[i];
//            		pos=i;
//            	}
//            }
//            while(max!=0){
//            	int a,b;
//            	a=pos/len2;
//            	b=pos%len2;
//            	System.out.println(colunname1[a]+" "+colunname2[b]+" "+max);
//            	for(int i=0;i<len2;i++)
//            		score[a*len2+i]=0.0;
//            	for(int i=0;i<len1;i++)
//            		score[i*len2+b]=0.0;
//            	max=0;
//            	for(int i=0;i<len1*len2;i++){
//                	if(score[i]>max){
//                		max=score[i];
//                		pos=i;
//                	}
//                }
//            }
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try{
//			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//			System.out.println(mongoClient.getDatabaseNames());
//			MongoDatabase mgdb=mongoClient.getDatabase("test");
//			MongoCollection<Document> mgc=mgdb.getCollection("names");
//			Document temp=new Document();
//			temp.append("name", "�������");
//			mgc.insertOne(temp);
//			System.out.println(mgc);
//		}catch(Exception e){
//			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//		}
		
//		PythonInterpreter interpreter =new PythonInterpreter();
//		interpreter.execfile("D:\\Downloads\\WordSegmentation\\ChineseWordSegmentation\\MainExtract.py");
//		
//		
//		String file2 = "E:/bank/t.txt";
//		
//		
//			String file ="E:/bank/t.xlsx";
//			XSSFWorkbook outwb=new XSSFWorkbook();
//			XSSFWorkbook wb;
//			try {
//				FileInputStream fin=new FileInputStream(file2);
//				BufferedReader reader = new BufferedReader(new InputStreamReader(fin,"UTF-8"));
//				String lines;
//				String[] s=new String[100];
//				int sn=0;
//				while((lines = reader.readLine()) != null){
//					System.out.println(lines);
//					s[sn]=lines;
//					sn++;
//				}
//				wb = new XSSFWorkbook(file);
//				XSSFSheet sheet=wb.getSheetAt(0);
//				for(int i=0;i<35;i++){
//					XSSFRow row=sheet.getRow(i);
//					if(row!=null){
//						int j=1;
//						Integer[] hash=new Integer[sn];
//						for(int k=0;k<sn;k++)
//							hash[k]=0;
//						while(row.getCell(j)!=null){
//							row.getCell(j).setCellType(CellType.STRING);
//							for(int k=0;k<sn;k++){
//								if(row.getCell(j).getStringCellValue().indexOf(s[k])!=-1){
//									hash[k]=1;
//								}
//							}
//							j++;
//						}
//						System.out.print(i);
//						for(int k=0;k<sn;k++){
//							if(hash[k]==1){
//								System.out.print(" "+s[k]);								
//							}
//						}
//						System.out.println();
//					}
//					System.out.println();
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
//		HanLP.Config.ShowTermNature = false;    // �رմ�����ʾ
//        Segment segment = new CRFSegment().enableCustomDictionary(false);
//        String[] sentenceArray = new String[]
//                {
//                        "HanLP����һϵ��ģ�����㷨��ɵ�Java���߰���Ŀ�����ռ���Ȼ���Դ��������������е�Ӧ�á�",
//                        "�F�U��ꠑ�ŭ��w���Y �RӢ�Ÿ����ܔ�",           // ������ѹ��
//                        "�RӢ�Żؑ��B���ġ�ؤ���f�����Q�h��ͬ־ՄԒ��֔��",
//                        "������أ�ǿ���������Ϻ�ɫ���壬������ˮ�����Ҵ�������ԭ����������������ˮ����������������Ư�׼����������ռ���������̼���Ƽ��ȡ�", // רҵ������һ����ʶ����
//                        "��ҹ������ӡ�ͨ������ǳ�ݵ���Ů�ڰ�ҹ�������ӵ��龰,���������߶����������������",    // ����������
//                        "����������[ί��]ǰ���Ǹ����̫�����ˣ�һ�㲻��Ʒ...@hankcs",                       // ΢��
//                        "��̩���С��һ��ζ��Ҳû��...ÿ����������...�����ģ�����ʳ��2A�ĺô�",
//                        "���ӳ����ֻ��ܷ���ؼֲ�˹����̬��"
//                };
//        for (String sentence : sentenceArray)
//        {
//            List<Term> termList = segment.seg(sentence);
//            System.out.println(termList);
//        }
//		System.out.println("hello");
		
		
		 
		        
//		  try {
//			  FileInputStream fin=new FileInputStream("E:\\ttess.owl");
//		      Reader in = new InputStreamReader(fin,"UTF-8");
//			  OWLModel to=ProtegeOWL.createJenaOWLModelFromReader(in);
//			  System.out.println(to);
//			   OWLNamedClass dest=to.getOWLNamedClass("testclass");
//			   System.out.println(dest);
//			   OWLNamedClass sub=to.createOWLNamedSubclass("subclass4", dest);
//			  
////		   OWLModel owlModel = ProtegeOWL.createJenaOWLModel();
////		   System.out.println(owlModel);
////		   owlModel.createOWLNamedClass("testclass");
////		   OWLNamedClass dest=owlModel.getOWLNamedClass("testclass");
////		   OWLNamedClass sub=owlModel.createOWLNamedSubclass("subclass", dest);
////		   System.out.println(dest);
////		   System.out.println(dest.getName());
//		   FileOutputStream outFile= new FileOutputStream("E:\\ttess.owl");
//		    Writer out = new OutputStreamWriter(outFile,"UTF-8");
//		    OWLModelWriter omw = new OWLModelWriter(to, to.getTripleStoreModel().getActiveTripleStore(), out);
//		    omw.write();
//		    out.close();
//		  } 
//		  catch (Exception e)
//		  {
//		   e.printStackTrace();
//		  }
	}

}