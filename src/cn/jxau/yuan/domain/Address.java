package cn.jxau.yuan.domain;

import cn.jxau.yuan.conf.Config;
import cn.jxau.yuan.utils.Utils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 场地类
 */
public class Address {
    private String name;
    private BigDecimal total;
    private List<String> msgList;
    private Map<String, int[]> timeTable;//日期，时间段数组
    //    int[] timeTable = new int[14];
    private TreeSet<String> bookMsg;

    public Address(String name){
        this.name = name;
        this.total = new BigDecimal(0);
        this.msgList = new ArrayList<>();
        this.bookMsg = new TreeSet<>();
        timeTable = new HashMap<>();
    }

    /**
     * 场地预定
     * @param string : {⽤用户ID} {预订⽇日期 yyyy-MM-dd} {预订时间段 HH:mm~HH:mm} {场地}
     * @return
     */
    public boolean book(String string){

        String dateString = Utils.str2DateString(string);
        if (!timeTable.containsKey(dateString))
            timeTable.put(dateString, new int[14]);

        int[] time = new int[2];
        String[] strs = Utils.fixString2time(string.split(" ")[2]).split("-");
        time[0] = Integer.parseInt(strs[0]);
        time[1] = Integer.parseInt(strs[1]);

        //差值为1
        if (time[1] - time[0] == 1 && timeTable.get(dateString)[time[1] - 9] != 0 && timeTable.get(dateString)[time[0] - 9] != 0)
            return false;

        for (int i = time[0] - 9; i < time[1] - 9; ++i){
            if (timeTable.get(dateString)[i] > 0)
                return false;
        }

        for (int i = time[0] - 9; i < time[1] - 9; ++i)
            timeTable.get(dateString)[i] += 1;

        //这里保存：2016-06-03 20:00~22:00 120元
        msgList.add(Utils.fixString2book(string));
        //保存预定信息：U002 2017-08-01 19:00~22:00 A，当取消的时候直接查找
        bookMsg.add(string);
        //预定成功total加钱
        total = total.add(Config.getInstance().getTotalByPriceDis(Utils.fixString2time(string.split(" ")[2]),
                Utils.isWeek(string.split(" ")[1])));
        return true;
    }

    /**
     * 场地违约
     * @param string {⽤用户ID} {预订⽇日期 yyyy-MM-dd} {预订时间段 HH:mm~HH:mm} {场地} {C}
     * @return
     */
    public boolean cancel(String string){
        if (!bookMsg.contains(string.substring(0, string.length() - 2)))
            return false;
        else {
            int[] time = new int[2];
            String[] strs = Utils.fixString2time(string.split(" ")[2]).split("-");
            time[0] = Integer.parseInt(strs[0]);
            time[1] = Integer.parseInt(strs[1]);

            for (int i = time[0] - 9; i < time[1] - 9; ++i)
                timeTable.get(Utils.str2DateString(string))[i] -= 1;

            //扣除total中的钱
            total = total.subtract(Config.getInstance().getTotalByPriceDis(Utils.fixString2time(string.split(" ")[2]),
                    Utils.isWeek(string.split(" ")[1])));
            //移除预定打印的信息：2016-06-03 20:00~22:00 120元
            msgList.remove(Utils.fixString2book(string));
            //移除预定信息
            bookMsg.remove(string.substring(0, string.length() - 2));
            //添加违约金信息
            msgList.add(Utils.fixString2Cancel(string));
            total = total.add(Config.getInstance().getCancelMoney(Utils.fixString2time(string.split(" ")[2]), Utils.isWeek
                    (string.split(" ")[1])));

            return true;
        }
    }

    public List<String> getMsgList(){
        msgList.sort(null);
        return msgList;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
