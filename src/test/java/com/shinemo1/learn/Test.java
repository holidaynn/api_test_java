package com.shinemo1.learn;

import java.util.*;

/**
 * # 问题：
 * # 编写一个程序，将输入字符串中的字符按如下规则排序。
 * # 规则 1: 英文字母从 A 到 Z 排列，不区分大小写
 * # 如，输入：Type 输出：epTy
 * # 规则 2: 同一个英文字母的大小写同时存在时，按照输入顺序排列。如，输入：BabA 輸出：aABb
 * # 规则 3: 非英文字母的其它字符保持原来的位置。
 * # 如，输入：By? e 输出：Bery
 * # 样例：输入A Famous Saying: Much Ado About Nothing (2012/8）
 * #  输出：A aaAAbc dFgghh: iimM nNn oooos Sttuuuy ( 2012/8)
 */

public class Test {
//    public  static  List<Integer> blankIndex=new ArrayList<>();//空格
//    public  static  LinkedList<String> characters=new LinkedList<>();//字母
//    public  static Map<Integer,String> otherChars= new HashMap<>();//其他字符

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("请输入字符串");
        String s = scanner.nextLine();
        LinkedList<String> list=new LinkedList<>();
        char[] chars=s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(Character.isLetter(chars[i])){
                list.add(String.valueOf(chars[i]));
            }
        }

        Collections.sort(list,String.CASE_INSENSITIVE_ORDER);

        for(int i = 0,j=0; i < chars.length; i++){
            if(Character.isLetter(chars[i])){
                System.out.print(list.get(j));
                j++;
            }else {
                System.out.print(chars[i]);
            }
        }
        System.out.println();


//        //查找空格的位置索引,保存到list中
//        for (int i = 0; i <chars.length ; i++) {
//            if(Character.isSpaceChar(chars[i])){
//                blankIndex.add(i);
//            }else if (Character.isLetter(chars[i])){//字母
//                characters.add(String.valueOf(chars[i]));
//            }else {
//                otherChars.put(i, String.valueOf(chars[i]));
//            }
//        }
//        Collections.sort(characters,String.CASE_INSENSITIVE_ORDER);
//        String[]  newString= new String[s.length()];
//        for(Integer i :blankIndex ){
//            newString[i]=" ";
//        }
//        for(Integer otherKey:otherChars.keySet()){
//            newString[otherKey]=otherChars.get(otherKey);
//        }
//
//        StringBuilder str= new StringBuilder();
//        for (int i = 0; i < newString.length; i++) {
//            if (newString[i] == null) {
//                newString[i]=characters.element();
//                characters.remove();
//                }
//            str.append(newString[i]);
//            }
//        System.out.println(str);

    }
}
