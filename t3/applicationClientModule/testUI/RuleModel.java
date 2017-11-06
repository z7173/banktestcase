package testUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RuleModel {
	static void Rulemerge01Text(String path,String fileName){
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("该目录下对象个数："+filelist.length);
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
	}
	static void Rulemerge03Text(String path,String fileName){
		File file=new File(path);
		File[] filelist=file.listFiles();
		System.out.println("该目录下对象个数："+filelist.length);
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
				for(int sheetnum=1;sheetnum<wb.getNumberOfSheets();sheetnum++){
					sheet=wb.getSheetAt(sheetnum);
					fileWriter.write(sheet.getRow(0).getCell(1).getStringCellValue()+"\n");
					outsheet.createRow(line).createCell(0).setCellValue(sheet.getRow(0).getCell(0).getStringCellValue());
					outsheet.getRow(line).createCell(2).setCellValue(sheet.getRow(0).getCell(1).getStringCellValue());
					line++;
					Map<String,Map<String,String>> allpro=new HashMap<String,Map<String,String>>();
					int num1=5;
					int num2=7;
					for(int i=0;i<10;i++){
						XSSFRow row=sheet.getRow(1);
						if(row.getCell(i)==null)	continue;
						if(row.getCell(i).getStringCellValue().equals("测试意图"))	num1=i;
						if(row.getCell(i).getStringCellValue().equals("测试步骤"))			num2=i;
					}
					for(int i=2;sheet.getRow(i)!=null;i++){
						testcasenum++;
						XSSFRow row=sheet.getRow(i);
						if(row.getCell(num1)==null)	continue;
						if(row.getCell(num2)==null)	continue;
						String buzhou=row.getCell(num1).getStringCellValue()+"，"+row.getCell(num2).getStringCellValue();
						String tempshuxin="";
						String tempstring="";
						String xinzhi="";
						if(row.getCell(10).getStringCellValue().contains("成功")){
							xinzhi="true";
						}
						if(row.getCell(10).getStringCellValue().contains("失败")){
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
								ts+="【"+entry2.getKey()+"】、";
							}
							else{
								ts2+="【"+entry2.getKey()+"】、";
							}
						}
						String cellstring="验证当【"+entry.getKey()+"】\n";
						fileWriter.write("验证当【"+entry.getKey()+"】");
						if(!ts.isEmpty()){
							fileWriter.write("为"+ts.substring(0,ts.length()-1)+"时成功，");
							cellstring+="为"+ts.substring(0,ts.length()-1)+"时成功\n";
						}
						if(!ts2.isEmpty()){
							fileWriter.write("为"+ts2.substring(0,ts2.length()-1)+"时失败");
							cellstring+="为"+ts2.substring(0,ts2.length()-1)+"时失败";
						}
						fileWriter.write("\t"+ts3+"\n");
						XSSFRow row=outsheet.createRow(line);
						line++;
						row.createCell(6).setCellValue(entry.getKey());
						row.createCell(7).setCellValue(cellstring);
						row.createCell(9).setCellValue(entry.getKey());
						int col=10;
						for(Map.Entry<String, String> entry2 : entry.getValue().entrySet()){
							row.createCell(col).setCellValue(entry2.getKey());
							if(entry2.getValue().equals("false1")){
								Font tcs=wb.createFont();
								tcs.setColor(HSSFColor.RED.index);
								CellStyle tc=wb.createCellStyle();
								tc.setFont(tcs);
								row.getCell(col).setCellStyle(tc);
							}
							col++;
						}
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
	}
}
