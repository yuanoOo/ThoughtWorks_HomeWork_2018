package interview.thoughtWorks;

import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        //1. init and load conf
        Config.getInstance();

        //2. init AddrGroup
        AddrGroup addrGroup = AddrGroup.getInstance();

        while (true)
        {
            String msg = scanner.nextLine();
            if (!Utils.checkMsg(msg))
            {
                System.out.println("Error: the booking is invalid!");
                continue;
            }

            if (msg != "\n")
            {
                System.out.println(addrGroup.doService(msg));
                continue;
            }

            if (msg == "\n")
            {

            }
        }
    }
}
