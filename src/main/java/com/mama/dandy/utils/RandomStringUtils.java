package com.mama.dandy.utils;

import java.util.Random;

public class RandomStringUtils {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 产生len长度的随机字符串
     * @param len
     * @return
     */
    public static String generateStr(int len){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i <len ; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return  sb.toString();
    }


    public static void main(String args[]){
        System.out.print(RandomStringUtils.generateStr(10));
    }

}
