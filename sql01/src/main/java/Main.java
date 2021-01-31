package server;

import java.util.Scanner;

/**
 * server.Main
 * basic tutorial from www.sqlitetutorial.net/sqlite-java
 */
public class Main {
    public static boolean DEBUG = false;
    
    public static boolean isStatusReady() {
        return statusReady;
    }
    
    private static boolean statusReady = false;
    

    private static Actions dbAct;
    
    public static void main(String[] args)  {
    
        System.out.println(System.getProperty("java.version"));
        System.out.println("Hello World");
        
        statusReady=true;
    }
    
}