package testUI;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by jinchuyang on 2017/3/23.
 */
public class ExcelReader {
    private XSSFWorkbook wb;
    private XSSFSheet sheet;

    public ExcelReader(File file){
        try {
            wb = new XSSFWorkbook(OPCPackage.openOrCreate(file));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得core内excel文件中的案例
     * @return
     */
    public List<TestCase> getTextCaseDes() {
        List<TestCase> caseDes = new ArrayList<TestCase>();
        for (int i = 0; i< wb.getNumberOfSheets();i++){
            XSSFSheet sheet= (XSSFSheet) wb.getSheetAt(i);
            if (sheet == null){
                continue;
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();

            XSSFRow row=sheet.getRow(firstRowNum);
            //获得当前行的开始列
            int firstCellNum = row.getFirstCellNum();
            //获得当前行的列数
            int lastCellNum = row.getPhysicalNumberOfCells();
            //存储需要找的列数
            int caseDesIndex = -1;
            int stepDesIndex = -1;
            for (int rowNum = firstCellNum; rowNum < lastCellNum; rowNum++ ){
                XSSFCell cell = row.getCell(rowNum);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if ("用例描述*".equals(cell.getStringCellValue())){
                    caseDesIndex = rowNum;
                }
                if ("步骤描述*".equals(cell.getStringCellValue())){
                    stepDesIndex = rowNum;
                }
            }

            for (int k = firstCellNum + 1;  k < lastRowNum; k++) {
                TestCase tc = new TestCase();
                XSSFRow xssfRow = sheet.getRow(k);
                if (xssfRow != null){
                    if (caseDesIndex > 0){
                        XSSFCell cell = xssfRow.getCell(caseDesIndex);
                        if (cell != null){
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            tc.caseDes = cell.getStringCellValue();
                        }

                    }
                    if (stepDesIndex > 0){
                        XSSFCell cell = xssfRow.getCell(stepDesIndex);
                        if (cell != null){
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            tc.stepDes = cell.getStringCellValue();
                        }

                    }
                }

                caseDes.add(tc);
            }
        }
        return caseDes;
    }

    /**
     * 获得01testCase内excel文件中的案例
     * @return
     */
    public List<TestCase> getTextCaseDes01() {
        List<TestCase> testCases = new ArrayList<TestCase>();
        XSSFSheet sheet= (XSSFSheet) wb.getSheetAt(0);
        if (sheet == null){
            return testCases;
        }
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        int beginRowNum = -1;
        for (int rowNum = firstRowNum; rowNum < lastRowNum; rowNum++){
            XSSFRow row = sheet.getRow(rowNum);
            XSSFCell cell = row.getCell(0);
            if (cell != null){
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if ("测试工程".equals(cell.getStringCellValue())){
                    beginRowNum = rowNum;
                    break;
                }
            }

        }
        //获得当前行的开始列

        if (beginRowNum != -1){
            for (int rowNum = beginRowNum+1; rowNum < lastRowNum; rowNum++){
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null){
                    continue;
                }
                TestCase testCase = new TestCase();
                XSSFCell cell = null;
                cell = row.getCell(0);
                if (cell == null || cell.getRawValue() == null){
                    continue;
                }
                if (cell != null){
                   cell.setCellType(Cell.CELL_TYPE_STRING);
                   testCase.testEngineering = cell.getStringCellValue();
                }

                cell = row.getCell(2);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.testCaseId = cell.getStringCellValue();
                }

                cell = row.getCell(5);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.testGoal = cell.getStringCellValue();
                }

                cell = row.getCell(6);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.stepDes = cell.getStringCellValue();
                }

                cell = row.getCell(7);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.caseDes = cell.getStringCellValue();
                }

                cell = row.getCell(8);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.checkPoint = cell.getStringCellValue();
                }

                cell = row.getCell(9);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.Priority = cell.getStringCellValue();
                }

                cell = row.getCell(10);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.caseNature = cell.getStringCellValue();
                }

                cell = row.getCell(11);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.caseState = cell.getStringCellValue();
                }

                cell = row.getCell(12);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.aurthor = cell.getStringCellValue();
                }

                cell = row.getCell(13);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.remark = cell.getStringCellValue();
                }

                cell = row.getCell(14);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    testCase.QCId = cell.getStringCellValue();
                }


                testCases.add(testCase);
            }
        }
        return testCases;
    }

    public Map<String, Set<String>> getKeyWordsMacth() {
        Map<String, Set<String>> content = new HashMap<String, Set<String>>();
        for (int i = 0; i < 2; i++) {
            XSSFSheet sheet= (XSSFSheet) wb.getSheetAt(i);
            if (sheet == null){
                continue;
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            for (int j = firstRowNum; j < lastRowNum; j++){
                XSSFRow xssfRow = sheet.getRow(j);
                if (xssfRow != null){
                    XSSFCell cell0 = xssfRow.getCell(0);
                    XSSFCell cell1 = xssfRow.getCell(1);
                    cell0.setCellType(Cell.CELL_TYPE_STRING);
                    cell1.setCellType(Cell.CELL_TYPE_STRING);
                    if (content.containsKey(cell1.getStringCellValue())){
                        if (!(cell0.getStringCellValue()==null || "".equals(cell0.getStringCellValue()))) {
                            content.get(cell1.getStringCellValue()).add(cell0.getStringCellValue());
                        }
                    }else {
                        if (!(cell0.getStringCellValue()==null || "".equals(cell0.getStringCellValue()))) {
                            Set<String> set = new HashSet<String>();
                            set.add(cell0.getStringCellValue());
                            content.put(cell1.getStringCellValue(), set);
                        }
                    }
                }
            }
        }
        return content;
    }

    /**
     * 获取待匹配的关键词
     * @return
     */
    public List<String> getWaitMatchKeyWord() {
        List<String> waitMatchKeyWord = new ArrayList<String>();
        XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(2);
        if (sheet != null){
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            for (int i = firstRowNum; i < lastRowNum; i++){
                XSSFRow row = sheet.getRow(i);
                if(row!= null){
                    XSSFCell cell = row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    waitMatchKeyWord.add(cell.getStringCellValue());
                }
            }

        }
        return waitMatchKeyWord;
    }


    public List<String> getTextCaseDes02(int row, int colnum) {
        List<String> list = new ArrayList<String>();
        XSSFSheet sheet= (XSSFSheet) wb.getSheetAt(0);
        if (sheet == null){
            return list;
        }
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        for (int i = row; i < lastRowNum; i++){
            XSSFRow  xssfRow = sheet.getRow(i);
            XSSFCell cell = xssfRow.getCell(colnum);
            if (cell != null){
                cell.setCellType(Cell.CELL_TYPE_STRING);
                list.add(cell.getStringCellValue());
            }
        }
        return list;
    }
    
    public List<TestCase> getTextCaseDes03() {
        List<TestCase> testCases = new ArrayList<TestCase>();
        for(int sheetnum=1;sheetnum<wb.getNumberOfSheets();sheetnum++){
        	XSSFSheet sheet= (XSSFSheet) wb.getSheetAt(sheetnum);
            if (sheet == null){
                return testCases;
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            int beginRowNum = -1;
            for (int rowNum = firstRowNum; rowNum < lastRowNum; rowNum++){
                XSSFRow row = sheet.getRow(rowNum);
                XSSFCell cell = row.getCell(0);
                if (cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if ("被测系统_案例".equals(cell.getStringCellValue())){
                        beginRowNum = rowNum;
                        break;
                    }
                }

            }
            //获得当前行的开始列

            if (beginRowNum != -1){
                for (int rowNum = beginRowNum+1; rowNum < lastRowNum; rowNum++){
                    XSSFRow row = sheet.getRow(rowNum);
                    if (row == null){
                        continue;
                    }
                    TestCase testCase = new TestCase();
                    XSSFCell cell = null;
                    cell = row.getCell(1);
                    if (cell == null || cell.getRawValue() == null){
                        continue;
                    }
                    if (cell != null){
                       cell.setCellType(Cell.CELL_TYPE_STRING);
                       testCase.testEngineering = cell.getStringCellValue();
                    }

                    cell = row.getCell(2);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.testCaseId = cell.getStringCellValue();
                    }

                    cell = row.getCell(4);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.testGoal = cell.getStringCellValue();
                        testCase.caseDes = cell.getStringCellValue();
                    }

                    cell = row.getCell(6);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.caseNature = cell.getStringCellValue();
                    }

                    cell = row.getCell(7);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.Priority = cell.getStringCellValue();
                    }

                    cell = row.getCell(9);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.stepDes = cell.getStringCellValue();
                    }

                    cell = row.getCell(10);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.caseState = cell.getStringCellValue();
                    }

                    cell = row.getCell(11);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.checkPoint = cell.getStringCellValue();
                    }

                    cell = row.getCell(12);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.aurthor = cell.getStringCellValue();
                    }
                    
                    cell = row.getCell(15);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        testCase.remark = cell.getStringCellValue();
                    }


                    testCases.add(testCase);
                }
            }
        }        
        return testCases;
    }
}

