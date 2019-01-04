package GUI;

import DataErrorType.InputError;
import IOManager.UserManager;
import StatesProgramm.BackendState;

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



    public void Start(){
        try {
            um.askForAction();
        } catch (InputError inputError) {
            System.err.println(inputError.getMessage());
            UserManager.backendState.setCurrentState(BackendState.CriticalError);
            try {
                um.askForAction();
            } catch (InputError inputError1) {
                System.err.println(inputError.getMessage());
            }
        }
    }
}
