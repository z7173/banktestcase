package testUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MapUtils {
    public static List<Map.Entry<String,Integer>> sortMap(Map map){
        List<Map.Entry<String,Integer>> list =
                new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        return list;
    }
}
