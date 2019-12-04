package launch;

import java.lang.reflect.InvocationTargetException;

public class ServerApp {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection successful!");
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println("Fail...");
            System.err.println(e);
        }
    }
}
