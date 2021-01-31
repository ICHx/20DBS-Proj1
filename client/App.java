import javax.swing.*;

public class App {
    public static void main(String[] args) {
        
        
        System.out.println("app main");
        AppUI n = new AppUI();
        
        JFrame frame = new JFrame("AppUI");
        frame.setContentPane(n.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
}
