package testUI;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jinchuyang on 2017/4/24.
 */

public class Rule4 {
    private static String splitWords1[] = {"��Ϊ", "=Ϊ", "=", "Ϊ��",">", "<", "����", "С��","Ϊ",":","��", "����"};//
    private static String splitWords[] = {"���ڵ���","����С��", "С�ڵ���", "���ڴ���", "����", "С��", "����", "��Ϊ" , "<=",">=","<>","=",":","��", "Ϊ��","Ϊ",">", "<"};
    private static String addSplitWords[] = {"���ڵ���","����С��", "С�ڵ���", "���ڴ���", "����", "С��", "����"};
  

    private static String removeBeginning(String s) {
        String beginningWords[] = {"��ѯ", "��֤"};
        for (String beginningword : beginningWords) {
            s = s.replace(beginningword, "");
        }
        return s;
    }


    private static String removeTingYongCi(String split) {
        String tingYongCis[] = {"����","һ��", "����"};
        for (String tingYongCi : tingYongCis){
            split = split.replace(tingYongCi, "");
        }
        return split;
    }


    /**
     * �����01testcase�а�����һ�н�������-ֵ̬���ھ�
     * @param total
     */
    public static Map<String, TreeSet<String>> mineFieldAndFld01test(List<TestCase> total) {
        Map<String, TreeSet<String>> map = new HashMap<String, TreeSet<String>>();
        for (TestCase testCase : total){
            if (testCase.caseDes == null){
                continue;
            }

            String splitCaseDes = testCase.caseDes.replaceAll("\n"," ");
            splitCaseDes = splitCaseDes.replaceAll("\r"," ");
            splitCaseDes = splitCaseDes.replace("-"," ");
            String splits[] = splitCaseDes.split(" ");

            for (String split : splits){
                for (String splitWord : splitWords1){
                    if (split.contains(splitWord)&&split.split(splitWord).length == 2){

                        TreeSet<String> set;
                        if (map.containsKey(split.split(splitWord)[0])) {
                            set = map.get(split.split(splitWord)[0]);
                        } else {
                            //System.out.println(str1+"----------------"+split);
                            set = new TreeSet<String>(new StringLengthComparator());
                        }
                        if ("Ϊ����".equals(split.split(splitWord)[1])){
                            System.out.println(split.split(splitWord)[1]+"********"+split);
                        }
                        set.add(split.split(splitWord)[1]);
                        map.put(split.split(splitWord)[0], set);
                        break;
                    }
                }
            }
        }
        return map;
    }
    
    public static Map<String, TreeSet<String>> mineFieldAndFld03test(List<TestCase> total) {
        Map<String, TreeSet<String>> map = new HashMap<String, TreeSet<String>>();
        for (TestCase testCase : total){
            if (testCase.caseDes == null){
                continue;
            }

            String splitCaseDes = testCase.caseDes.replaceAll("\n"," ");
            splitCaseDes = splitCaseDes.replaceAll("\r"," ");
            splitCaseDes = splitCaseDes.replace("-"," ");
            splitCaseDes = splitCaseDes.replace("��"," ");
            splitCaseDes = splitCaseDes.replace("��"," ");
            String splits[] = splitCaseDes.split(" ");

            for (String split : splits){
                for (String splitWord : splitWords1){
                    if (split.contains(splitWord)&&split.split(splitWord).length == 2){

                        TreeSet<String> set;
                        String word=split.split(splitWord)[0];
                        while(word.contains(".")){
                        	System.out.println(word);
                        	String[] tempw=word.split("\\.");
                        	word=tempw[tempw.length-1];
                        }
                        if (map.containsKey(word)) {
                            set = map.get(word);
                        } else {
                            //System.out.println(str1+"----------------"+split);
                            set = new TreeSet<String>(new StringLengthComparator());
                        }
                        if ("Ϊ����".equals(split.split(splitWord)[1])){
                            System.out.println(split.split(splitWord)[1]+"********"+split);
                        }
                        set.add(split.split(splitWord)[1]);
                        map.put(word, set);
                        break;
                    }
                }
            }
        }
        return map;
    }
}
