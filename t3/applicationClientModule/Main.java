

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.List;
import java.util.Map;

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
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;
import javassist.bytecode.Descriptor.Iterator;

import javax.script.*;




public class Main {
	

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String fileName ="E:/bank/甘肃银行_核心_存款模块_对公类存款_功能测试案例_完整.xlsx";		
		File file=new File(fileName);
		try {
			OWLModel owlModel = ProtegeOWL.createJenaOWLModel();
			OWLNamedClass root=owlModel.createOWLNamedClass("根节点");
			XSSFWorkbook wb= new XSSFWorkbook(file);
			for(int num=4;num<15;num++){
				XSSFSheet sheet=wb.getSheetAt(num);
				for(int i=1;sheet.getRow(i)!=null;i++){
					XSSFRow row=sheet.getRow(i);
					if(row.getCell(0)==null)	break;
					String s=row.getCell(0).getStringCellValue();
					for(int j=0;j<10;j++){
						s=s.replaceAll(""+j, "");
					}
					String[] s2=new String[10];
					s2=s.split("\\\\");
					
					OWLNamedClass temp=root;
					for(int j=1;j<s2.length;j++){						
						if(owlModel.getOWLNamedClass(s2[j])==null){
							temp=owlModel.createOWLNamedSubclass(s2[j], temp);
						}
						else{
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
								if(s2[j].equals("利息处理"))	System.out.println(s);
								for(int k=0;k<1000;k++){
									if(owlModel.getOWLNamedClass(s2[j]+k)==null){
										temp=owlModel.createOWLNamedSubclass(s2[j]+k, temp);
										break;
									}
								}
							}
						}
					}					
					row=sheet.getRow(i);
				}
			}
			FileOutputStream outFile= new FileOutputStream("E:\\ttesst.owl");
		    Writer out = new OutputStreamWriter(outFile,"UTF-8");
		    OWLModelWriter omw = new OWLModelWriter(owlModel, owlModel.getTripleStoreModel().getActiveTripleStore(), out);
		    omw.write();
		    out.close();
			
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
			wb.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
 catch (OntologyLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
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
//			temp.append("name", "案例编号");
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
		
//		HanLP.Config.ShowTermNature = false;    // 关闭词性显示
//        Segment segment = new CRFSegment().enableCustomDictionary(false);
//        String[] sentenceArray = new String[]
//                {
//                        "HanLP是由一系列模型与算法组成的Java工具包，目标是普及自然语言处理在生产环境中的应用。",
//                        "鐵桿部隊憤怒情緒集結 馬英九腹背受敵",           // 繁体无压力
//                        "馬英九回應連勝文“丐幫說”：稱黨內同志談話應謹慎",
//                        "高锰酸钾，强氧化剂，紫红色晶体，可溶于水，遇乙醇即被还原。常用作消毒剂、水净化剂、氧化剂、漂白剂、毒气吸收剂、二氧化碳精制剂等。", // 专业名词有一定辨识能力
//                        "《夜晚的骰子》通过描述浅草的舞女在暗夜中扔骰子的情景,寄托了作者对庶民生活区的情感",    // 非新闻语料
//                        "这个像是真的[委屈]前面那个打扮太江户了，一点不上品...@hankcs",                       // 微博
//                        "鼎泰丰的小笼一点味道也没有...每样都淡淡的...淡淡的，哪有食堂2A的好次",
//                        "乐视超级手机能否承载贾布斯的生态梦"
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