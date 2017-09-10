package cn.jxau.yuan.utils;

import cn.jxau.yuan.conf.Config;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类
 */
public class Utils {

    /**
     * 输入合法性校验工具
     * @param msg
     * @return true ：合法 false ： 非法
     */
    public static boolean checkMsg(String msg){
        String[] msgs = msg.split(" ");
        if (msgs.length != 4 && msgs.length != 5)
            return false;

        String date = msgs[1];
        String time = msgs[2];
        String addr = msgs[3];

        //校验 “取消” 合法性
        if (msgs.length == 5)
            if (!msgs[4].equals("C"))
                return false;

        //校验日期合法性
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }

        //校验时间合法性
        String[] times = time.split("~");
        int befor = 0, after = 0;

        String[] left = times[0].split(":");
        String[] right = times[1].split(":");
        if (left[0].length() != 2 || !left[1].equals("00")|| right[0].length() != 2 || !right[1].equals("00"))
            return false;

        try {
            befor = Integer.parseInt(left[0]);
            after = Integer.parseInt(right[0]);
        }catch (Exception e){
            return false;
        }

        if (after - befor < 1)
            return false;

        //校验场地合法性
        if (addr.compareTo("A") < 0  || addr.compareTo("D") > 0)
            return false;

        return true;
    }


    /**
     * 字符串转换
     * @param string U002 2017-08-01 19:00~22:00
     * @return string 2016-06-03 20:00~22:00 120元
     */
    public static String fixString2book(String string){
        String[] strings = string.split(" ");
        BigDecimal money = new BigDecimal(0);
        if (isWeek(strings[1]))
            money = Config.getInstance().getTotalByPriceDis(fixString2time(strings[2]),true);
        else
            money = Config.getInstance().getTotalByPriceDis(fixString2time(strings[2]),false);

        return new StringBuilder(strings[1]).append(" ").append
                (strings[2]).append(" ").append(money).append("元").toString();
    }

    /**
     * 字符串转换
     * 19:00~22:00 @ 19-22
     * @param string
     * @return
     */
    public static String fixString2time(String string){
        String[] times = string.split("~");
        int befor = 0, after = 0;
        //提取整形数字
        if (times[0].charAt(1) == ':')
            befor = Integer.parseInt(times[0].charAt(0) + "");
        else if (times[0].charAt(2) == ':')
            befor = Integer.parseInt(times[0].substring(0,2));


        if (times[1].charAt(1) == ':')
            after = Integer.parseInt(times[1].charAt(0) + "");
        else if (times[0].charAt(2) == ':')
            after = Integer.parseInt(times[1].substring(0,2));

        return new StringBuilder().append(befor).append("-").append(after).toString();
    }

    /**
     * 判断是否周末
     * @param string : 2017-08-05
     * @return
     */
    public static boolean isWeek(String string){
        Date date = null;
        try {
            date = str2Date(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }

        if (week_index == 0 || week_index == 6 )
            return true;
        else
            return false;
    }

    /**
     * 字符串转换
     * @param string:2017-08-05
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String  string) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(string);
    }

    /**
     * 字符串处理
     * 判断是否预定
     * @param msg
     * @return true: booking false: cancel
     */
    public static boolean isBook(String msg){
        if (msg.split(" ").length == 4)
            return true;
        else
            return false;
    }

    /**
     * 工具类
     * U002 2017-08-01 19:00~22:00 A C
     * @param msg
     * @return A
     */
    public static String getAddr(String msg){
        return msg.split(" ")[3];
    }

    /**
     * 字符串转换工具类
     * U002 2017-08-01 19:00~22:00 A TO 2016-06-02 09:00~10:00 违约⾦ 15元
     * @param string
     * @return string
     */
    public static String fixString2Cancel(String string) {
        String[] strings = string.split(" ");
        BigDecimal money = new BigDecimal(0);
        if (isWeek(strings[1]))
            money = Config.getInstance().getCancelMoney(fixString2time(strings[2]),true);
        else
            money = Config.getInstance().getCancelMoney(fixString2time(strings[2]),false);

        return new StringBuilder(strings[1]).append(" ").append
                (strings[2]).append(" ").append("违约金 ").append(money.intValue()).append("元").toString();
    }
}
