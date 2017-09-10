package interview.thoughtWorks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static boolean checkMsg(String msg){
        String[] msgs = msg.split(" ");
        if (msgs.length != 4 && msgs.length != 5)
            return false;

        String name = msgs[0];
        String date = msgs[1];
        String time = msgs[2];
        String addr = msgs[3];
        boolean cancelFlag = false;
        if (msgs.length == 5){
            if (!msgs[4].equals("C"))
                return false;
            else
                cancelFlag = true;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }

        String[] times = time.split("~");
        int befor = 0, after = 0;
        //提取整形数字
        if (times[0].charAt(1) == ':')
            befor = Integer.parseInt(times[0].charAt(0) + "");
        else if (times[0].charAt(2) == ':')
            befor = Integer.parseInt(times[0].substring(0,2));
        else
            return false;

        if (times[1].charAt(1) == ':')
            after = Integer.parseInt(times[1].charAt(0) + "");
        else if (times[0].charAt(2) == ':')
            after = Integer.parseInt(times[1].substring(0,2));
        else
            return false;

        if (after - befor < 1)
            return false;

        if (addr.compareTo("A") < 0  || addr.compareTo("D") > 0)
            return false;

        return true;
    }


    /**
     * U002 2017-08-01 19:00~22:00 A to 2016-06-03 20:00~22:00 120元
     * @param string
     * @return string
     */
    //TODO:money计算有误
    public static String fixString2book(String string){
        String[] strings = string.split(" ");
        int price = 0;
        int money = 0;
        if (isWeek(strings[1]))
            price = Config.getInstance().getPrice(fixString2time(strings[2]),true);
        else
            price = Config.getInstance().getPrice(fixString2time(strings[2]),false);

        money = price * (Integer.parseInt(fixString2time(strings[2]).
                split("-")[1]) - Integer.parseInt(fixString2time(strings[2]).split("-")[0]));

        return new StringBuilder(strings[1]).append(" ").append
                (strings[2]).append(" ").append(money).append("远").toString();
    }

    /**
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
     *
     * @param string
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

        if (week_index == 0 && week_index == 6 )
            return true;
        else
            return false;
    }

    public static Date str2Date(String  string) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(string);
    }

    /**
     * isBook
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
     * U002 2017-08-01 19:00~22:00 A C
     * @param msg
     * @return A
     */
    public static String getAddr(String msg){
        return msg.split(" ")[3];
    }
}
