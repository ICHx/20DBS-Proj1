import javax.swing.*;
import java.util.List;

public class AppUI {
    private final DefaultListModel<String> model = new DefaultListModel<>();
    public JPanel panel1;
    private JPanel leftPan;
    private JPanel midPan;
    private JButton btnAdd;
    private JList<String> jList1;
    private JButton btnDel;
    private JButton btnEdit;
    
    private DBConnect dbLink;
    private Actions dbAct;
    public AppUI() {
        dbLink = new DBConnect();
        dbAct = new Actions(dbLink.getSqlObject());
        
        setData();
    }
    
    public String getData() {
        return jList1.getSelectedValue();
    }
    
    public void setData() {
        List<Student> data = dbAct.getStudents();
        model.clear();
    
        for (Student obj : data) {
        model.addElement(obj.toString());
        }
        
        jList1.setModel(model);
        jList1.updateUI();

        
    }
    
    public boolean isModified() {
//        unused, optimizing import
        Student s = new Student();
        return false;
    }
    
}
