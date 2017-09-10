package interview.thoughtWorks;

import java.math.BigDecimal;
import java.util.*;

public class Address {
    private String name;
    private BigDecimal total;
    private List<String> msgList;
    int[] timeTable = new int[14];
    private TreeSet<String> bookMsg;

    protected Address(String name){
        this.name = name;
        this.total = new BigDecimal(0);
        this.msgList = new ArrayList<>();
        this.bookMsg = new TreeSet<>();
    }

    /**
     *
     * @param string : {⽤用户ID} {预订⽇日期 yyyy-MM-dd} {预订时间段 HH:mm~HH:mm} {场地}
     * @return
     */
    public boolean book(String string){
        int[] time = new int[2];
        String[] strs = Utils.fixString2time(string.split(" ")[2]).split("-");
        time[0] = Integer.parseInt(strs[0]);
        time[1] = Integer.parseInt(strs[1]);

        //差值为1
        if (time[1] - time[0] == 1 && timeTable[time[1] - 9] != 0 && timeTable[time[0] - 9] != 0)
            return false;

        for (int i = time[0] - 9; i <= time[1] - 9; ++i){
            //判断是否可以预定
            if (timeTable[time[0] - 9] > 0 && timeTable[time[1] - 9] > 0 && timeTable[i] == 1){
                return false;
            }
        }

        for (int i = time[0] - 9; i <= time[1] - 9; ++i){
            timeTable[i] += 1;
            //这里保存：2016-06-03 20:00~22:00 120元
            msgList.add(Utils.fixString2book(string));
            //保存预定信息：U002 2017-08-01 19:00~22:00 A，当取消的时候直接查找
            bookMsg.add(string);
        }
        return true;
    }

    /**
     *
     * @param string {⽤用户ID} {预订⽇日期 yyyy-MM-dd} {预订时间段 HH:mm~HH:mm} {场地} {C}
     * @return
     */
    public boolean cancel(String string){
        if (!bookMsg.contains(string.substring(0, string.length() - 2)))
            return false;
        else {
            //TODO：取消预定的操作
        }
        return true;
    }

    public List<String> printTotal(){
        return msgList;
    }
}
