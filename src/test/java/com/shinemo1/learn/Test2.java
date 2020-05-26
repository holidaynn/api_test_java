package com.shinemo1.learn;

import java.util.Scanner;

/**
 * 有一叠扑克牌，每张牌介于 1 和 10 之间有四种出牌方法单出 1 张出 2 张对子
 * 出五张顺子，如 12345
 * 出三连对子，如 112233
 * 给 10 个数，表示 1-10 每种牌有几张，问最少要多少次能出完
 */

public  class  Test2{
    public static void main(String[] args) {
        int remain=7;
        int nums[]={2, 1, 1, 1, 1, 1, 0, 0, 0, 0};

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入总牌数");
        remain=sc.nextInt();
        for (int i = 0; i < 10; i++) {
            System.out.println("请输入张数");
            nums[i]=sc.nextInt();
        }
        int sum=0;
        for (int i = 0; i < nums.length; i++) {
            sum+=nums[i];
        }
        if(sum!=remain){
            System.out.println("输入错误");
            return;
        }

        for (int i = 1; i <11 ; i++) {
            System.out.println(i+" ");  //输出1-10
        }




    }

}