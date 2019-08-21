package cn.zcp.mave.plugin;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        Class cls = Class.forName("cn.zcp.mave.plugin.controller.HelloController");
        System.out.println(cls.getSimpleName());
    }
}
