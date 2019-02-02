package GUI;

import IOManager.UserManager;
import javax.swing.*;

public class Window extends JFrame {

    private UserManager um = new UserManager();
    private InputArea inputArea = new InputArea(this);
    private TitelBar titelBar = new TitelBar(this);

    //TODO: sa fac asa o fereastra in Stilul Tarent, Butoanele Rosii si Backgraund Culoarea Alba,
    //      iar sus sa fie o bara cu Logo Tarent la inceput(de culoare rosie) si


    public Window(){
        setLayout(null);
        add(this.titelBar);
        add(this.inputArea);
        setSize(1024 , 768);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    UserManager getUserManager() { return this.um; }

    InputArea getInputArea() { return this.inputArea; }

    TitelBar getTitelBar() { return this.titelBar; }



}
