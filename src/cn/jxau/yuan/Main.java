package cn.jxau.yuan;

import cn.jxau.yuan.conf.Config;
import cn.jxau.yuan.controller.AddrGroup;
import cn.jxau.yuan.utils.Utils;

import java.util.Scanner;

/**
 * 程序入口，视图层
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //1. 初始化并加载配置
        Config.getInstance();

        //2. 初始化控制中心
        AddrGroup addrGroup = AddrGroup.getInstance();

        while (true) {
            String msg = scanner.nextLine();
            if (msg.equals("")) {
                System.out.println("收入汇总");
                System.out.println("---");
                addrGroup.printTotal();
            }else if (!Utils.checkMsg(msg)) {
                System.out.println("Error: the booking is invalid!");
            }else if (!msg.equals("")) {
                System.out.println(addrGroup.doService(msg));
            }
        }
    }
}
