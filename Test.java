package interview.thoughtWorks;

public class Test {

    @org.junit.Test
    public void fun1(){
        String string = "U002 2017-08-01 19:00~22:00 A C";
        System.out.println(string.substring(0, string.length() - 2));
    }
}
