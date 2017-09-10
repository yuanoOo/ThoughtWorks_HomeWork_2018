package cn.jxau.yuan.controller;

import java.math.BigDecimal;
import java.util.*;

/**
 * 控制层：调度Address，根据视图层的信息，处理相应的逻辑
 * 1.单例模式
 */
public class AddrGroup {
    private Map<String, Address> addrConfig;
    private static final AddrGroup group = new AddrGroup();

    public static AddrGroup getInstance(){
        return group;
    }

    private AddrGroup(){
        addrConfig = new TreeMap<>();
        addrConfig.put("A", new Address("A"));
        addrConfig.put("B", new Address("B"));
        addrConfig.put("C", new Address("C"));
        addrConfig.put("D", new Address("D"));
    }

    /**
     * 根据不同的msg，处理不同的逻辑，返回不同的结果
     * @param msg
     */
    public String doService(String msg){
        String addr = Utils.getAddr(msg);
        if (Utils.isBook(msg)){
            //book
            if (addrConfig.get(addr).book(msg))
                return "Success: the booking is accepted!";
            else
                return "Error: the booking conflicts with existing bookings!";
        }else {
            //cancel
            if (addrConfig.get(addr).cancel(msg))
                return "Success: the booking is accepted!";
            else
                return "Error: the booking being cancelled does not exist!";
        }
    }

    /**
     * 遍历打印汇总信息
     */
    public void printTotal(){
        BigDecimal total = new BigDecimal(0);//所有场地的汇总金额
        int num = 0;
        for (String key : addrConfig.keySet()){
            System.out.println("场地:" + key);
            List<String> msgList = addrConfig.get(key).getMsgList();
            for (String s : msgList)
                System.out.println(s);
            ++num;
            if (num == addrConfig.keySet().size())
                System.out.println("小计："+addrConfig.get(key).getTotal().intValue()+"元");
            else
                System.out.println("小计："+addrConfig.get(key).getTotal().intValue()+"元\n");
            total = total.add(addrConfig.get(key).getTotal());
        }

        System.out.println("---");
        System.out.println("总计："+total.intValue()+"元");
    }
}
