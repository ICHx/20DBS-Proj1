import org.junit.Test;

public class MainTest {
    public static void main(String[] args) {
    
    }
    @Test
    public void test(){
        DBConnect dbLink = new DBConnect("test.db");
    
        Actions dbAct = new Actions(dbLink.getSqlObject());
        
        dbAct.printStudents();

    
        Student s1 =(new Student("stu0998", "Real Steve Jobs 2",60,"M"));
        Student s2 =(new Student("stu0978", "Steve2",13,"N"));
        Student s3 =(new Student("stu0984", "Steve3",-1,"N")); //should fail
        Student s4 =(new Student("stu0994", "Steve3",96,"M"));
        Student s5 =(new Student("stu0997", "True Steve",61,"M"));
        dbAct.addStudent(s1);
        dbAct.addStudent(s2);
        dbAct.addStudent(s4);
        dbAct.addStudent(s5);
    
        System.out.println("============================\n error cases");
        dbAct.addStudent(s1);//not unique error
        dbAct.addStudent(s3); //should fail
    
        dbAct.printStudents();
    
        System.out.println("============================");
        dbAct.printStudents("Gender = 'F'");
    
        System.out.println("============================\n delete s1");
        dbAct.delStudent(s1);
        dbAct.printStudents();
        
        System.out.println("============================\n edit");
        Student s5N =(new Student("stu0997", "Fake Steve",61,"M"));
        System.out.println("want to change True Steve to Fake Steve "+s5N);
        dbAct.updateStudent(s5N);
        dbAct.printStudents();
        
        System.out.println("============================\n clear all");
        dbAct.clearAll();
        dbAct.printStudents();
    }
}
