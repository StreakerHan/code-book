package com.rzfk.common.utils;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description : 签到工具类
 */
public class SignUtils {

    @Data
    static
    class SignIn{
        private Date signInDay;

        public SignIn(){

        }

        public SignIn(String day) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            signInDay = sdf.parse(day);
        }
    }

    public static void main(String[] args) throws Exception{
        // 注意时间倒叙
        List<SignIn> signInList = new ArrayList<>();
        signInList.add(new SignIn("2022-08-13"));
        signInList.add(new SignIn("2022-08-12"));
//        signInList.add(new SignIn("2020-11-17"));
        signInList.add(new SignIn("2022-08-10"));
        signInList.add(new SignIn("2022-08-09"));
        int continuousSignInDay = getContinuousSignInDay(signInList);
        System.out.println("连续签到日期："+ continuousSignInDay);
    }

    public static List<SignIn> change(List<String> list) throws ParseException {
        List<SignIn> signInList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            signInList.add(new SignIn(list.get(i)));
        }
        return signInList;
    }
    /**
     * 连续签到天数
     *
     * @return int
     * @Author fangyunhe
     * @Date 2019-08-15 17:16:01
     * @Param
     **/
    public static int getContinuousSignInDay(List<SignIn> signInList) {
        //continuousDay 连续签到数
        int continuousDay = 1;
        boolean todaySignIn = false;
        Date today = new Date();
        for (int i = 0; i < signInList.size(); i++) {
            SignIn signIn = signInList.get(i);
            int intervalDay = distanceDay(today, signIn.getSignInDay());
            //当天签到
            if (intervalDay == 0 && i == 0) {
                todaySignIn = true;
            }
            else if (intervalDay == continuousDay) {
                continuousDay++;
            }else {
                //不连续，终止判断
                break;
            }
        }
        if (!todaySignIn) {
            continuousDay--;
        }
        return continuousDay;
    }

    /**
     * 两个日期对比间隔天数
     *
     * @param smallDay
     * @return boolean
     * @Author fangyunhe
     * @Date 2019-08-13 18:42:41
     * @Param largeDay
     **/
    private static int distanceDay(Date largeDay, Date smallDay) {
        int day = (int) ((largeDay.getTime() - smallDay.getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }

}