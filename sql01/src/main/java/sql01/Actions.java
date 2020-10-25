//the class to static methods to conduct sql manipulation
package sql01;

import java.util.List;

import org.sql2o.*;

public class Actions {
    private Sql2o sqlObj = null;

    Actions() {
        System.out.println("E: Misuse of class Actions");
        System.exit(-1);
    }

    public Actions(Sql2o sqlObject) {
        this.sqlObj = sqlObject;
    }

    //! Section: read

    public List<Student> getStudents() {
        /** 
         * we can fetch all columns and parse the resulting data into a list of objects with the following code. 
         * sql2o matches this with column name, in case-insensitive way
         * 
         * When Column oprphan exist, here is the message.
         * "Exception in thread "main" org.sql2o.Sql2oException: Could not map Gender to any property." 
        */

        try (Connection c = sqlObj.open()) {
            String sql_cmd = "select * from Accounts";
            Query q = c.createQuery(sql_cmd);
            return q.executeAndFetch(Student.class);

            // not making commit(), default to rollback, but no difference.
        }

    }

    public List<Student> getStudents(String critera) {
        /** 
         * we can fetch all columns and parse the resulting data into a list of objects with the following code. 
         * sql2o matches this with column name, in case-insensitive way
         * 
         * When Column oprphan exist, here is the message.
         * "Exception in thread "main" org.sql2o.Sql2oException: Could not map Gender to any property." 
        */

        try (Connection c = sqlObj.open()) {
            String sql_cmd = "select * from Accounts where " + critera;
            //very important to leaves whitespace near sql ^conjunctions.

            Query q = c.createQuery(sql_cmd);
            return q.executeAndFetch(Student.class);

            // not making commit(), default to rollback, but no difference.
        }

    }
    // ? select * from accounts as ac order by ac.gender where ac.gender = 'M'

    public void printStudents() {
        System.out.println("\nStudent List:\n");
        List<Student> ls = this.getStudents();

        for (Student student : ls) {
            System.out.println(student);
        }
    }

    public void printStudents(String critera) {
        System.out.println("\nStudent List: " + critera + "\n");
        List<Student> ls = this.getStudents(critera);
        for (Student student : ls) {
            System.out.println(student);
        }
    }

    //! Section: add

    public boolean addStudent(Student st) {
        //  insert into accounts values ("stu369","Chan Tai Man", 13, 'M'); 
        // insert into accounts values ('stu002', 'SiuFu', 19,'F'); 

        if (st.validate() == false) {
            System.out.println("E: Invalid Record");
            return false;
        }

        try (Connection c = sqlObj.open()) {
            // use the connection
            c.getJdbcConnection().setAutoCommit(false);
            // this is auto-closable https://www.baeldung.com/java-sql2o
            //? as long as autocommit is disabled(but enabled by default.)

            c.createQuery("insert into accounts (UID, NAME, AGE, GENDER) " + " values (:UID, :Name, :Age, :Gender)")
                    .bind(st).executeUpdate();

            //! Stuck here so long because I do not know how Sql2o get data from the pojo.  It turns out, I used private attributes which it couldn't receive.

            //? As a result, I changed Student pojos into using public attributes.
            c.commit();

            System.out.println("I: UID record add suceed. " + st.UID);
            return true;
        } catch (Exception e) {
            String error = e.getMessage();
            if (error.contains("UNIQUE")) {
                //hardcoded for friendlier message
                System.out.println("E: UID Record already exist. " + st.UID);
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

            c.createQuery("update accounts set" + 
            "Name = :Nam, AGE= :Ag, GENDER = :Gende) " + 
            " where UID = :ID;")
            .addParameter("ID", st_new.UID)
            .addParameter("Ag", st_new.Age)
            .addParameter("Gende", st_new.Gender)
            .addParameter("Nam", st_new.Name)
            .executeUpdate();

            c.commit();

            System.out.println("I: UID record amendment suceed. " + st_new.UID);
            return true;
        } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    // change schema (not intended for gui, as it is unnecessary for teacher or students)
    // alter table accounts add/rename to xxx, also not neccessary
    // alter table accounts rename column old to new; not neccessary

    // 

