package server;//the class to static methods to conduct sql manipulation

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;

public class Actions {
    private Sql2o sqlObj;
    
    public Actions(Sql2o sqlObject) {
        this.sqlObj = sqlObject;
    }
    
    //! Section: read
    
    public List<Student> getStudents() {
        /*
          we can fetch all columns and parse the resulting data into a list of objects with the following code.
          sql2o matches this with column name, in case-insensitive way
         
          When Column oprphan exist, here is the message.
          "Exception in thread "main" org.sql2o.Sql2oException: Could not map Gender to any property."
         */
        
        try (Connection c = sqlObj.open()) {
            String sql_cmd = "select * from Accounts";
            Query q = c.createQuery(sql_cmd);
            return q.executeAndFetch(Student.class);
            
            // not making commit(), default to rollback, but no difference.
        }
        
    }
    
    public List<Student> getStudents(String criteria) {
        /*
          we can fetch all columns and parse the resulting data into a list of objects with the following code.
          sql2o matches this with column name, in case-insensitive way
         
          When Column oprphan exist, here is the message.
          "Exception in thread "main" org.sql2o.Sql2oException: Could not map Gender to any property."
         */
        
        try (Connection c = sqlObj.open()) {
            String sql_cmd = "select * from Accounts where " + criteria;
            //very important to leaves whitespace near sql ^conjunctions.
            
            Query q = c.createQuery(sql_cmd);
            return q.executeAndFetch(Student.class);
            
            // not making commit(), default to rollback, but no difference.
        }
        
    }
    // ? select * from accounts as ac order by ac.gender where ac.gender = 'M'
    
    public void printStudents() {
        System.out.println("\nserver.Student List:\n");
        List<Student> ls = this.getStudents();
        
        for (Student student : ls) {
            System.out.println(student);
        }
    }
    
    public void printStudents(String criteria) {
        System.out.println("\nserver.Student List: " + criteria + "\n");
        List<Student> ls = this.getStudents(criteria);
        for (Student student : ls) {
            System.out.println(student);
        }
    }
    
    //! Section: add
    
    public boolean addStudent(Student stud0) {
        //  insert into accounts values ("stu369","Chan Tai Man", 13, 'M'); 
        // insert into accounts values ('stu002', 'SiuFu', 19,'F'); 
        
        if (stud0.validate() == false) {
            System.out.println("E: Invalid Record");
            return false;
        }
        
        try (Connection conn = sqlObj.open()) {
            // use the connection
            conn.getJdbcConnection().setAutoCommit(false);
            // this is auto-closable https://www.baeldung.com/java-sql2o
            //? as long as autocommit is disabled(but enabled by default.)
            
            String query = "insert into accounts(UID,NAME,AGE,GENDER) " + " values(:UID,:NAME,:AGE,:GENDER)";
            
            conn.createQuery(query).bind(stud0).executeUpdate();
            
            //! Check on usage https://www.codota.com/code/java/methods/org.sql2o.Query/bind
            //* https://github.com/iNPUTmice/caas/blob/master/caas-web/src/main/java/im/conversations/compliance/persistence/InternalDBOperations.java#L371
            
            //! Stuck here so long because I do not know how Sql2o get data from the pojo.  It turns out, I used private attributes which it couldn't receive.
            
            //? As a result, I changed server.Student pojos into using public attributes.
            conn.commit();
            
            System.out.println("I: UID record add suceed. " + stud0.getUID());
            System.out.println("============================");
            return true;
        } catch (Exception e) {
            String error = e.getMessage();
            if (error.contains("UNIQUE")) {
                //hardcoded for friendlier message
                System.out.println("E: UID Record already exist. " + stud0.getUID());
                System.out.println("============================");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
    
    //update student
    public boolean updateStudent(Student st_new) {
        
        //  UPDATE COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;

        /* UPDATE table_name
        SET column1 = value1, column2 = value2...., columnN = valueN
        WHERE [condition]; */
        
        if (st_new.validate() == false) {
            System.out.println("E: Invalid Record");
            return false;
        }
        
        try (Connection c = sqlObj.open()) {
            // use the connection
            c.getJdbcConnection().setAutoCommit(false);
            // this is auto-closable https://www.baeldung.com/java-sql2o
            //? as long as autocommit is disabled(but enabled by default.)
            
            // Query q1 = c.createQuery("de" + 
            String cmd2 = "UPDATE ACCOUNTS SET " +
                " NAME=:NAME, AGE=:AGE, GENDER=:GENDER " +
                " WHERE UID = :ID ";
            Query q2 = c.createQuery(cmd2)
                .addParameter("ID", st_new.getUID())
                .addParameter("NAME", st_new.getNAME())
                .addParameter("AGE", st_new.getAGE())
                .addParameter("GENDER", st_new.getGENDER());
            
            q2.executeUpdate();
            
            c.commit();
            
            System.out.println("I: UID record amendment succeed. " + st_new.getUID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void clearAll(){
        try(Connection c = sqlObj.beginTransaction(1)){
            String cmd2 = "delete from accounts where true";
            Query q2 = c.createQuery(cmd2);
    
            q2.executeUpdate();
    
            c.commit();
        }
    }
    
    public void delStudent(Student s){
        delStudent(s.getUID());
    }
    public void delStudent(String Uid){
        try(Connection c = sqlObj.beginTransaction(1)){
            String cmd2 = "delete from accounts where uid=:id";
            Query q2 = c.createQuery(cmd2).addParameter("id", Uid);
            
            q2.executeUpdate();
            
            c.commit();
            System.out.printf("server.Student %s removed.\n==============\n", Uid);
        }catch (Exception e){
            e.printStackTrace();
            System.out.printf("server.Student %s not removed.\n==============\n", Uid);
        }
    }

}
