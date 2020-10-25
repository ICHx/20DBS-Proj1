package sql01;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main
 * basic tutorial from www.sqlitetutorial.net/sqlite-java
 */
public class Main {
    private final static Scanner sc = new Scanner(System.in);
    public static boolean DEBUG = false;

    public static void main(String[] args) throws SQLException {

        System.out.println("Hello World");
        DBConnect dbLink = new DBConnect();

        Actions dbAct = new Actions(dbLink.getSqlObject());
        
        //test
        System.out.println(dbAct.getStudents());
        dbAct.printStudents("Gender = 'F'");
        
        Student s1 =(new Student("stu0997", "Real Steve Jobs",60,"M"));
        Student s2 =(new Student("stu0978", "Steve2",13,"N"));
        Student s3 =(new Student("stu0984", "Steve3",-1,"N")); //should fail
        Student s4 =(new Student("stu0994", "Steve3",96,"M"));
        dbAct.addStudent(s1);
        dbAct.addStudent(s2);
        dbAct.addStudent(s3);
        dbAct.addStudent(s4);
        
        dbAct.printStudents();
        
        Student s1N =(new Student("stu0997", "Fake Steve Jobs",61,"M"));
        dbAct.updateStudent(s1N);
        dbAct.printStudents();
        
        
        
        
        
        
        
        
        
        //  Application.launch(UInterface.class, args);
    }
}