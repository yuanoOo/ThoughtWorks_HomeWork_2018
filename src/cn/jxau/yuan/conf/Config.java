package cn.jxau.yuan.conf;

import java.math.BigDecimal;

/**
 * 配置类
 * 1、单例模式
 */
public class Config {
    private static final Config config = new Config();

    private Config(){init();}

    public static Config getInstance(){
        return config;
    }

    //收费标准
    private int[] workDay = new int[14];
    private int[] weekDay = new int[14];

    //初始化系统配置
    private void init(){
        //config workday's price
        for (int i = 9; i < 12; ++i) workDay[i - 9] = 30;
        for (int i = 12; i < 18; ++i) workDay[i - 9] = 50;
        for (int i = 18; i < 20; ++i) workDay[i - 9] = 80;
        for (int i = 20; i < 22; ++i) workDay[i - 9] = 60;

        //config weekDay's price
        for (int i = 9; i < 12; ++i) weekDay[i - 9] = 40;
        for (int i = 12; i < 18; ++i) weekDay[i - 9] = 50;
        for (int i = 18; i < 22; ++i) weekDay[i - 9] = 60;
    }

    /**
     * 算法描述：
     *      1、配置初始化顺序：for example：arr[30, 30, 30, 50, 50]
     *      2、则1:00 ~ 4:00区间money为： arr[1] + arr[2] + arr[3]
     *      3、巧妙且清晰的避免了过多的if else，并且程序结构更加明了易懂
     * @param string 5　：１２
     * @param isWeek
     * @return
     */
    public BigDecimal getTotalByPriceDis(String string, boolean isWeek){
        String[] strings = string.split("-");
        int total = 0;
        if (isWeek)
            for (int i = Integer.parseInt(strings[0]); i < Integer.parseInt(strings[1]); ++i)
                total += weekDay[i - 9];
        else
            for (int i = Integer.parseInt(strings[0]); i < Integer.parseInt(strings[1]); ++i)
                total += workDay[i - 9];

        return new BigDecimal(total);
    }

    /**
     *
     * @param string : 5 : 12
     * @param isWeek
     * @return
     */
    public BigDecimal getCancelMoney(String string, boolean isWeek){
        if (isWeek)
            return getTotalByPriceDis(string,true).multiply(new BigDecimal(0.25));
        else
            return getTotalByPriceDis(string,false).multiply(new BigDecimal(0.5));
    }
}
