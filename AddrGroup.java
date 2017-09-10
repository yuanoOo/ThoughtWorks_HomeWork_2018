package interview.thoughtWorks;

import java.util.HashMap;
import java.util.Map;

public class AddrGroup {
    private Map<String, Address> addrConfig;
    private static final AddrGroup group = new AddrGroup();

    public static AddrGroup getInstance(){
        return group;
    }

    private AddrGroup(){
        addrConfig = new HashMap<>();
        addrConfig.put("A", new Address("A"));
        addrConfig.put("B", new Address("B"));
        addrConfig.put("C", new Address("C"));
        addrConfig.put("D", new Address("D"));
    }

    /**
     * 根据不同的msg，返回不同的结果
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
}
