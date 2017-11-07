package testUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;


public class FunctionModel {
	
	static int create(String inputfile, String outputfile)
	{
		try{
			File file=new File(inputfile);
			File[] filelist=file.listFiles();
			System.out.println("该目录下对象个数："+filelist.length);
			OWLModel owlModel = ProtegeOWL.createJenaOWLModel();
			OWLNamedClass root=owlModel.createOWLNamedClass("根节点");
			
			int n=0;
			File filename = new File("E:/bank/Output.txt"); // 要读取以上路径的input。txt文件  
	        InputStreamReader reader = new InputStreamReader(  
	                new FileInputStream(filename),"UTF-8"); // 建立一个输入流对象reader  
	        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
	        String line = "";  
	        line = br.readLine();  
	    	String[] sx=line.split(" ");
			File file3=new File("E:/bank/words2.txt");
			FileWriter fileWriter = new FileWriter(file3);
			FileWriter fileWriter2 = new FileWriter(new File("E:/bank/lib.txt"));
			String[] smark={"大于","小于","等于","为",">","<","="};
			int rulecount=0;
			for(int filenum=0;filenum<filelist.length;filenum++){
				System.out.println(filelist[filenum]);
				XSSFWorkbook wb= new XSSFWorkbook(filelist[filenum]);
				for(int num=0;num<wb.getNumberOfSheets();num++){
					XSSFSheet sheet=wb.getSheetAt(num);
					if(sheet.getRow(0)==null)	continue;
					if(sheet.getRow(0).getCell(0)==null)	continue;
					if(sheet.getRow(0).getCell(0).getCellTypeEnum()!=CellType.STRING)	continue;
					if(!sheet.getRow(0).getCell(0).getStringCellValue().equals("功能模块目录*")){
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
						OWLNamedClass temp=root;
						for(int j=1;j<s2.length;j++){	
							int bool=1;
							for(java.util.Iterator iter=temp.getDirectSubclasses().iterator();iter.hasNext();){
								OWLNamedClass check=(OWLNamedClass)iter.next();
								if(check.getName().contains(s2[j])){
									temp=check;
									bool=0;
									break;
								}								
							}
							if(bool==1){
								for(int k=0;k<1000;k++){
									if(owlModel.getOWLNamedClass(s2[j]+k)==null){
										temp=owlModel.createOWLNamedSubclass(s2[j]+k, temp);			
										break;
									}
								}
							}
						}

						n++;
						row=sheet.getRow(i);
					}
				}
				wb.close();
			}
			fileWriter.close();
			System.out.println(rulecount);
			System.out.println(n);
			
			FileOutputStream outFile= new FileOutputStream(outputfile);
		    Writer out = new OutputStreamWriter(outFile,"UTF-8");
		    OWLModelWriter omw = new OWLModelWriter(owlModel, owlModel.getTripleStoreModel().getActiveTripleStore(), out);
		    omw.write();
		    out.close();
			return n;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	static void merge(String inputfile1, String inputfile2, String outputfile)
	{
		try{
			Map<String,Integer> map=new HashMap<String,Integer>(); 
			FileInputStream fin=new FileInputStream(inputfile1);
			Reader in = new InputStreamReader(fin,"UTF-8");
			OWLModel to=ProtegeOWL.createJenaOWLModelFromReader(in);
			OWLNamedClass root=to.getOWLNamedClass("根节点");
			for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
				OWLNamedClass temp=(OWLNamedClass)iter.next();
				for(java.util.Iterator iter2=temp.getDirectSubclasses().iterator();iter2.hasNext();){
					OWLNamedClass secondclass=(OWLNamedClass)iter2.next();
					String tname=secondclass.getName();
					map.put(tname, secondclass.getDirectSubclassCount());
				}
			}
			in.close();
			fin.close();
			FileInputStream fin2=new FileInputStream(inputfile2);
			Reader in2 = new InputStreamReader(fin2,"UTF-8");
			OWLModel to2=ProtegeOWL.createJenaOWLModelFromReader(in2);
			root=to2.getOWLNamedClass("根节点");
			for(java.util.Iterator iter=root.getDirectSubclasses().iterator();iter.hasNext();){
				OWLNamedClass temp=(OWLNamedClass)iter.next();
				for(java.util.Iterator iter2=temp.getDirectSubclasses().iterator();iter2.hasNext();){
					OWLNamedClass secondclass=(OWLNamedClass)iter2.next();
					double percent=0;
					String name=secondclass.getName();
					for(int i=0;i<10;i++){
						name=name.replace(i+"", "");
					}
					if(map.containsKey(name)){
						if(map.get(name)==0)	percent=1.0;
						else	percent =(double)map.get(name)/secondclass.getSubclassCount();						
						map.put(name,-1);						
						for(java.util.Iterator newiter=to.getOWLNamedClass(name).getDirectSubclasses().iterator();newiter.hasNext();){
							OWLNamedClass newsence=(OWLNamedClass)newiter.next();
							String tname=newsence.getName();
							for(int i=0;i<10;i++){
								tname=tname.replace(i+"", "");
							}
							int needadd=1;
							for(java.util.Iterator iter3=secondclass.getDirectSubclasses().iterator();iter3.hasNext();){
								OWLNamedClass thirdclass=(OWLNamedClass)iter3.next();
								if(thirdclass.getName().contains(tname)){
									needadd=0;
									break;
								}
							}
							if(needadd==1){
								for(int k=0;k<1000;k++){
									if(to2.getOWLNamedClass(tname+k)==null){
										temp=to2.createOWLNamedSubclass(tname+k, secondclass);			
										break;
									}
								}
								//to2.createOWLNamedSubclass(tname, secondclass);
							}
						}
					}
					else{
						for(String key:map.keySet()){
							String tname=key;
							for(int i=0;i<10;i++){
								tname=tname.replace(i+"", "");
							}
							if(name.contains(tname)){
								if(map.get(key)==-1){
									System.out.print("something wrong");
									System.out.println(tname);
								}
								if(map.get(key)==0)		percent=1.0;
								else	percent =(double)map.get(key)/secondclass.getSubclassCount();
								map.put(key,-1);
								for(java.util.Iterator newiter=to.getOWLNamedClass(key).getDirectSubclasses().iterator();newiter.hasNext();){
									OWLNamedClass newsence=(OWLNamedClass)newiter.next();
									String tname2=newsence.getName();
									for(int i=0;i<10;i++){
										tname2=tname2.replace(i+"", "");
									}
									int needadd=1;
									for(java.util.Iterator iter3=secondclass.getDirectSubclasses().iterator();iter3.hasNext();){
										OWLNamedClass thirdclass=(OWLNamedClass)iter3.next();
										if(thirdclass.getName().contains(tname2)){
											needadd=0;
											break;
										}
									}
									if(needadd==1){
										for(int k=0;k<1000;k++){
											if(to2.getOWLNamedClass(tname2+k)==null){
												temp=to2.createOWLNamedSubclass(tname2+k, secondclass);			
												break;
											}
										}
										//to2.createOWLNamedSubclass(tname2, secondclass);
									}
								}
								break;
							}
						}
					}
				}
			}
			java.util.Iterator iter=map.entrySet().iterator();
			int a=0,b=0;
			while(iter.hasNext()){
				Map.Entry<String, Integer> temp=(Entry<String, Integer>) iter.next();
				if(temp.getValue()!=-1){
					System.out.println(temp.getKey());
					OWLNamedClass temp2=to.getOWLNamedClass(temp.getKey());
					OWLNamedClass parent=(OWLNamedClass)temp2.getDirectSuperclasses().iterator().next();
					for(java.util.Iterator iter2=root.getDirectSubclasses().iterator();iter2.hasNext();){
						OWLNamedClass check=(OWLNamedClass)iter2.next();
						if(check.getName().contains(parent.getName())){
							temp2=check;
							break;
						}					
					}
					if(!temp2.getName().contains(parent.getName())){
						temp2=to2.createOWLNamedSubclass(parent.getName(), root);
						temp2.addComment("new");
					}
					OWLNamedClass check=to2.createOWLNamedSubclass(temp.getKey(), temp2);
					temp2=to.getOWLNamedClass(temp.getKey());
					for(java.util.Iterator iter2=temp2.getDirectSubclasses().iterator();iter2.hasNext();){
						to2.createOWLNamedSubclass(((OWLNamedClass)iter2.next()).getName(), check);
					}
					check.addComment("new");
					a++;
				}
				else{
					System.out.println("success");
					b++;
				}
			}
			in2.close();
			fin2.close();
			System.out.println(a);
			System.out.print(b);
			FileOutputStream outFile= new FileOutputStream(outputfile);
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
}
