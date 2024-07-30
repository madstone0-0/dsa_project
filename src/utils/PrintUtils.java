package utils;

public class PrintUtils {
    public static void println(Object t) {
        System.out.println(t);
    }

    public static void print(Object t) {
        System.out.print(t);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

}
