package cn.jxau.yuan.test;

import cn.jxau.yuan.utils.Utils;

import java.math.BigDecimal;

public class Test {

    @org.junit.Test
    public void fun1(){
        String string = "U002 2017-08-01 19:00~22:00 A C";
        System.out.println(string.substring(0, string.length() - 2));
    }

    @org.junit.Test
    public void fun2(){
        BigDecimal bigDecimal = new BigDecimal(0);
        bigDecimal = bigDecimal.add(new BigDecimal(5));
        System.out.println(bigDecimal.intValue());
    }

    @org.junit.Test
    public void fun3(){
        String s = "2017-08-05";
        System.out.println(Utils.isWeek(s));
    }
}
