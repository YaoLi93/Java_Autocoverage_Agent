package edu.utdallas.group5;
import java.util.*;
import java.lang.Object;


public class TimeCache {
    public static Map sStartTime = new HashMap<>();
    public static Map sEndTime = new HashMap<>();
    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
    }
    public static void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
    }
    public static String getCostTime(String methodName) {
        String str1=String.valueOf(sStartTime.get(methodName));
        long start=Long.valueOf(str1);

        String str2=String.valueOf(sEndTime.get(methodName));
        long end=Long.valueOf(str2);


        return "method: " + methodName + " main " + Long.valueOf(end - start) + " ns";
    }
}
