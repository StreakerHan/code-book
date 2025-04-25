package com.rzfk.common.utils;

import java.util.Random;

public class NameUtils {

    /**
     * 字符串池
     */
    private static String[] STR_ARR = new String[] { "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "0" };

    public static void main(String[] args) {
        String str1 = generateRandomString(8);
        String str2 = generateRandomString(6);
        System.out.println(str1);
        System.out.println(str2);
    }

    /**
     *
     * 根据指定的长度生成的含有大小写字母及数字的字符串
     *
     * @param length
     *            指定的长度
     * @return 按照指定的长度生成的含有大小写字母及数字的字符串
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(STR_ARR[rand.nextInt(STR_ARR.length)]);
        }
        return sb.toString();
    }
}
