package server;//the class to open JDBC connection to any Sqlite file(with slight adjust, can connect to mysql server)

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class DBConnect {
    
    // private Connection conn = null;
    private Sql2o sqlObj = null;
    
    public DBConnect() {
        this(new File("./main.db"));
    }
    public DBConnect(String s) {
        this(new File(s));
    }
    
    public DBConnect(File dbFile) {
        if (!dbFile.exists()) {
            try {
                String title = "Open/Create Database location";
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Database (*.db, *.sqlite)", "db", "sqlite");
                chooser.setFileFilter(filter);
                chooser.setDialogTitle(title);
                
                //get file by JFileChooser
                int returnVal, cnt = 0;
                do {
                    System.out.println("Please open a valid db.");
                    returnVal = chooser.showOpenDialog(null);
                    
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                        break;
                    System.out.println("E: File open failed.");
                    
                } while (++cnt < 3);
                if (cnt >= 3) {
                    throw new Exception("\nF: No database chosen.");
                }
                dbFile = chooser.getSelectedFile();
                
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-2);
            }
        }
        
        String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        
        if (Main.DEBUG) {
            System.out.println("FILE: " + dbFile.toString());
            System.out.println("URL: " + url);
            
        }
        
        sqlObj = new Sql2o(url, "server.Student", "");
        System.out.println("Connection to SQLite established.");
        
        // initialize db if is new
            /*
            CREATE TABLE IF NOT EXISTS "Accounts" (
            UID CHAR(20),
            Name CHAR(50),
            Age INTEGER, Gender char(1),
            PRIMARY KEY (UID)
            );
             */
        
        String initialize = "CREATE TABLE IF NOT EXISTS \"Accounts\" (UID CHAR(20) NOT NULL,Name CHAR(50) NOT NULL,Age INTEGER NOT NULL, Gender char(1) NOT NULL, PRIMARY KEY (UID));";
        
        // primary keys implies unique.
        
        //  sql2o beginTransaction ask for isolationLevel, I don't know what it is, I just give a random number.
        try (Connection c = sqlObj.beginTransaction(1)) {
            c.createQuery(initialize).executeUpdate();
            c.commit();
            /**
             * Call commit() or rollback() to either commit or rollback the transaction and close the connection.
             * If you don't explicitly call commit() or rollback() on the Connection object, the transaction will automatically be rolled back when exiting the try-with-resources block.
             */
        }
        
    }
    
    
    public Sql2o getSqlObject() {
        //Sql2o has its own connection class, which is confusing
        return sqlObj;
    }
    
}
