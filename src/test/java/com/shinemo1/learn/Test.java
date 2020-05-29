package com.shinemo1.learn;

import java.util.*;



public class Test {
//    public  static  List<Integer> blankIndex=new ArrayList<>();//空格
//    public  static  LinkedList<String> characters=new LinkedList<>();//字母
//    public  static Map<Integer,String> otherChars= new HashMap<>();//其他字符

    public static int reverseInteger(int number) {
        int result = 0;
        StringBuffer sb = new StringBuffer();
        sb.append(number);
        result = Integer.parseInt(sb.reverse()+"");
        return result;
    }
    public static void main(String[] args) {
        int number = 190;
        int result = reverseInteger(number);
        System.out.println(result);
    }
}
