import java.util.Scanner;

/**
 * Main
 * basic tutorial from www.sqlitetutorial.net/sqlite-java
 */
public class Main {
    private final static Scanner sc = new Scanner(System.in);
    public static boolean DEBUG = false;

    public static void main(String[] args)  {

        System.out.println(System.getProperty("java.version"));
        System.out.println("Hello World");
        DBConnect dbLink = new DBConnect();

        Actions dbAct = new Actions(dbLink.getSqlObject());
        
        
        //  Application.launch(UInterface.class, args);
    }
}