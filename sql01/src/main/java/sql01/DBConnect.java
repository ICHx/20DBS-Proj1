//the class to open JDBC connection to any Sqlite file(with slight adjust, can connect to mysql server)
package sql01;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class DBConnect {

    // private Connection conn = null;
    private Sql2o sqlObj = null;

    public DBConnect() {

        try {
            String title = "Open/Create Database location";
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Database (*.db, *.sqlite)", "db", "sqlite");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle(title);

            { //get file by JFileChooser
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
            }

            File dbfile = chooser.getSelectedFile();
            String url = "jdbc:sqlite:" + dbfile.getAbsolutePath();

            if (Main.DEBUG) {
                System.out.println("FILE: " + dbfile.toString());
                System.out.println("URL: " + url);

            }

            sqlObj = new Sql2o(url, "Student Database", "");
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
            
             String initialize="CREATE TABLE IF NOT EXISTS \"Accounts\" (UID CHAR(20) NOT NULL,Name CHAR(50) NOT NULL,Age INTEGER NOT NULL, Gender char(1) NOT NULL, PRIMARY KEY (UID));";
            
             // primary keys implies unique.
             
            //  sql2o beginTransaction ask for isolationLevel, I don't know what it is, I just give a random number.
             try(Connection c=sqlObj.beginTransaction(1)){
                 c.createQuery(initialize).executeUpdate();
                 c.commit();
                 /**
                  * Call commit() or rollback() to either commit or rollback the transaction and close the connection.
                  * If you don't explicitly call commit() or rollback() on the Connection object, the transaction will automatically be rolled back when exiting the try-with-resources block.
                  */
             }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return;
    }

    public Sql2o getSqlObject() {
        //Sql2o has its own connection class, which is confusing
        return sqlObj;
    }

}
