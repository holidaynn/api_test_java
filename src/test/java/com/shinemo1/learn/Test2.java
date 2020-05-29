package com.shinemo1.learn;

import java.util.*;

public class Test2 {

    public static int reverseInteger(int number){

        Scanner sc =new Scanner(System.in);
        String num = sc.nextLine();
        char[] chars=num.toCharArray();
        String str="";
        //定义个flag用于判断尾部是否有连续的0
        boolean flag=false;
        for (int i = chars.length-1; i >=0 ; i--) {
            //如果尾部是不是是0 ，则不加至字符串中
            if((chars[i]-'0')!=0 || flag){
                flag=true;
                str+=chars[i];
            }
        }

        return Integer.parseInt(str);
    }


    public static void main(String[] args){

        reverseInteger(123);

    }
}
