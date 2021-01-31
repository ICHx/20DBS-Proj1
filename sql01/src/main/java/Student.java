package server;

public class Student {
    // POJO class for data tuples.
    
    private String UID;
    private String NAME;
    private int AGE;
    private String GENDER; // [N,M,F]
    //using public just for demo purpose, 
    //should use private and get/set(); in action.
    
    //bug: SQL doesn't have a char data type. All it has are strings — char(n)
    // So gender need to be string
    
    // inorder to bind POJO
    
    
    public Student(){
        UID="stu912299";
        NAME="default";
        AGE=-1;
        GENDER = "N";
    }

    public Student(String UID, String Name, int Age, String Gender) {
        this.UID = UID;
        this.NAME = Name;
        this.AGE = Age;
        this.GENDER = Gender;
        
    }
    
    public boolean validate(){
        boolean ret;
        ret = UID.matches("stu[0-9]+");
        ret &= !NAME.isEmpty();
        ret &= AGE > 0; // assume excluding 0.
        ret &= GENDER.matches("[MFN]");
        
        return ret;
    }

    @Override
    public String toString() {
        return "UID=" + UID + ", Name=" + NAME + ", Age=" + AGE + ", Gender=" + GENDER + " ";
    }
    
    
    public String getUID() {
        return UID;
    }
    
    public void setUID(String UID) {
        this.UID = UID;
    }
    
    public String getNAME() {
        return NAME;
    }
    
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
    
    public int getAGE() {
        return AGE;
    }
    
    public void setAGE(int AGE) {
        this.AGE = AGE;
    }
    
    public String getGENDER() {
        return GENDER;
    }
    
    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }
}
