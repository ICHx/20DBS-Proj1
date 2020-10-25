package sql01;

public class Student {
    // POJO class for data tuples.
    
    public String UID="stu999";
    public String Name="default";
    public int Age=-1;
    public String Gender = "N"; // [N,M,F]
    //using public just for demo purpose, 
    //should use private and get/set(); in action.
    
    //bug: SQL doesn't have a char data type. All it has are strings — char(n)
    // So gender need to be string
    
    // inorder to bind POJO
    
    
    public Student(){System.out.println("Dummy student created." + this.toString());}

    public Student(String uID, String name, int age, String G) {
        this.UID = uID;
        this.Name = name;
        this.Age = age;
        this.Gender = G;
        
    }
    
    public boolean validate(){
        boolean ret = true;
        ret &= UID.matches("stu[0-9]+");
        ret &= !Name.isEmpty();
        ret &= (Age>0)?true:false; // assume excluding 0.
        ret &= Gender.matches("[MFN]");
        
        return ret;
    }

    @Override
    public String toString() {
        return "UID=" + UID + ", Name=" + Name + ", Age=" + Age + ", Gender=" + Gender + " ";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
    
    

    
   
}
