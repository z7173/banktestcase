package testUI;
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

public class EntityLib {
	static ArrayList<Case> getKeyValue(String path1,String path2) {
		//String path="E:/bank/测试案例库数据/01测试案例";
		File file=new File(path1);
		File[] filelist=file.listFiles();
		System.out.println("该目录下对象个数："+filelist.length);
		//String fileName ="E:/bank/业务字典-第六周.xlsx";		
		File file2=new File(path2);
		ArrayList<String> shuxin=new ArrayList<String>();
		ArrayList<String> taizhi=new ArrayList<String>();	
		int testcasenum=0;
		ArrayList<Case> result=new ArrayList<Case>();
		try {
			//File file3=new File("E:/bank/temp.txt");
			//FileWriter fileWriter = new FileWriter(file3);
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
				String functionname=filelist[filenum].getName().replaceAll(".xlsx", "");
				System.out.println(functionname);
				sheet=wb.getSheetAt(0);
				//fileWriter.write(sheet.getRow(12).getCell(3).getStringCellValue());
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
					if(row.getCell(num2).getCellTypeEnum()!=CellType.STRING)	continue;
					//fileWriter.write("\t");
					//fileWriter.write(row.getCell(4).getStringCellValue());
					Case newcase = new Case();
					String buzhou=row.getCell(num2).getStringCellValue();
					String tempshuxin="";
					newcase.purpose=row.getCell(num1).getStringCellValue();
//					String[] tempmiaoshu=row.getCell(num1).getStringCellValue().split("，");
//					for(int j=0;j<tempmiaoshu.length;j++){
//						if(tempmiaoshu[j].contains("进行")){
//							tempstring+=tempmiaoshu[j].substring(tempmiaoshu[j].indexOf("进行")+2);
//						}
//					}
					//tempstring+=row.getCell(4).getStringCellValue();
					//tempstring+="\t";
					for(int j=0;j<shuxin.size();j++){
						if(buzhou.contains(shuxin.get(j))&&!tempshuxin.contains(shuxin.get(j))&&buzhou.contains(taizhi.get(j))){
					//		fileWriter.write("\t"+shuxin.get(j)+"\t"+taizhi.get(j)+"\n\t");
					//		if(row.getCell(4).getStringCellValue().contains(shuxin.get(j))||row.getCell(num1).getStringCellValue().contains(taizhi.get(j)))
					//			tempstring+="【"+shuxin.get(j)+"="+taizhi.get(j)+"】 ";
							newcase.key_value.put(shuxin.get(j), taizhi.get(j));
							tempshuxin+=shuxin.get(j);
						}
					}
					//tempstring+="\t";
					if(row.getCell(num1).getStringCellValue().contains("成功")){
						newcase.success="成功";
					}
					if(row.getCell(num1).getStringCellValue().contains("失败")){
						newcase.success="失败";
					}
					newcase.function=functionname;
					result.add(newcase);
				}
			}
			System.out.println(testcasenum);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	static ArrayList<Integer> getbreakpoint(ArrayList<Integer> tnum)
	{
		ArrayList<Integer> result=new ArrayList<Integer>();
		Integer[] num=new Integer[tnum.size()];
		for(int i=0;i<tnum.size();i++){
			num[i]=tnum.get(i);
		}
		
		for(int i=0;i<num.length-1;i++){
			int minp=i;
			for(int j=i+1;j<num.length;j++){
				if(num[j]<num[minp]){
					minp=j;
				}
			}
			int temp=num[i];
			num[i]=num[minp];
			num[minp]=temp;
		}
		for(int i=0;i<num.length;i++){
			int newbreak=0;
			if(i<num.length-1&&(num[i+1]-num[i])<num[i+1]/5){
				int temp=10;
				while(num[i]-num[i]%temp+temp<=num[i+1]){
					temp*=10;
				}
				temp/=10;
				newbreak=num[i]-num[i]%temp+temp;
				i++;
			}
			else{
				int wei=1;
				int temp=num[i];
				while(temp>=1000){
					temp/=10;
					wei*=10;
				}
				int nf,ns,nt;
				nf=temp/100;
				ns=temp%100/10;
				nt=temp%10;
				if(nt>5){
					ns+=1;
				}
				if(ns==1){
					ns=0;
				}
				if(ns==9){
					ns=0;
					nf+=1;
				}
				if(nf==9){
					nf=10;					
				}
				newbreak=(nf*10+ns)*wei*10;
			}
			if(result.size()==0){
				result.add(newbreak);
			}
			else if(result.get(result.size()-1)<newbreak){
				result.add(newbreak);
			}
		}		
		return result;
	}
	
}
