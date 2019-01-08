package GUI;

import IOManager.UserManager;
import javax.swing.*;

public class Window extends JFrame {
    private static UserManager um = new UserManager();

    public Window(){
        setName("Sortino");
        setLayout(null);
        add(new InputArea(um,this));
        setSize(1024 , 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
