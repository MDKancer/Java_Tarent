/**
 * @author Daniel Lozanu
 */
package GUI;

import DataErrorType.InputError;
import IOManager.UserManager;
import StatesProgramm.FrontendState;
import StatesProgramm.SortTyp;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputArea extends JPanel {
    private JFrame window;
    private final int panelwidth = 1024;
    private final int panelheight = 768;
    private UserManager um;
    private JTextField txtPath = new JTextField();
    private JLabel lblPath = new JLabel();
    private JTextField txtTopX = new JTextField();
    private JLabel lblTopX = new JLabel();
    private JButton btnOK = new JButton();

    private JPanel resultTable;

    private ButtonGroup group = new ButtonGroup();
    private JRadioButton jSortNamebtn = new JRadioButton(SortTyp.SortByName.name());
    private JRadioButton jSortSizebtn = new JRadioButton(SortTyp.SortBySize.name());
    private JRadioButton jSortDatebtn = new JRadioButton(SortTyp.SortByDate.name());
    private JRadioButton jSortMaxCountbtn = new JRadioButton(SortTyp.SortMaxFilesNumberDirectory.name());

    InputArea(UserManager userManager, JFrame frame){
        window = frame;
        um = userManager;
        setLayout(null);
        setSize(panelwidth , panelheight);
        add(getLblPath());
        add(getTxtPath());
        add(getTxtTopX());
        add(getLblTopX());
        add(getjSortNamebtn());
        add(getjSortSizebtn());
        add(getjSortDatebtn());
        add(getjSortMaxCountbtn());
        add(getBtnOK());
        UserManager.frontendState.setCurrentState(FrontendState.Input);
        UserManager.sortState.setCurrentState(SortTyp.None);
    }

    private JLabel getLblPath(){
        lblPath.setText("Directory Path: ");
        lblPath.setBounds(10,10,120,30);
        return lblPath;
    }
    private JTextField getTxtPath(){
        txtPath.setBounds(140,10,120,30);
        return txtPath;
    }
    private JLabel getLblTopX(){
        lblTopX.setText("Show Top X: ");
        lblTopX.setBounds(10,40,120,30);
        return lblTopX;
    }
    private JTextField getTxtTopX(){
        txtTopX.setBounds(140,40,120,30);
        return txtTopX;
    }
    private JButton getBtnOK(){
        btnOK.setText("OK");
        btnOK.setBounds(this.panelwidth - 140,this.panelheight - 80, 120 ,30);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ShowData(txtPath.getText());
            }
        });
        return btnOK;
    }
    private JRadioButton getjSortNamebtn(){
        jSortNamebtn.setBounds(10,80,120,30);
        group.add(jSortNamebtn);
        jSortNamebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortByName);
            }
        });
        return jSortNamebtn;
    }
    private JRadioButton getjSortSizebtn(){
        jSortSizebtn.setBounds(140,80,120,30);
        group.add(jSortSizebtn);
        jSortSizebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortBySize);
            }
        });
        return jSortSizebtn;
    }
    private JRadioButton getjSortDatebtn(){
        jSortDatebtn.setBounds(270,80,120,30);
        group.add(jSortDatebtn);
        jSortDatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortByDate);
            }
        });
        return jSortDatebtn;
    }
    private JRadioButton getjSortMaxCountbtn(){
        jSortMaxCountbtn.setBounds(400,80,120,30);
        group.add(jSortMaxCountbtn);
        jSortMaxCountbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortMaxFilesNumberDirectory);
            }
        });
        return jSortMaxCountbtn;
    }
    public void ShowData(String Path){
            try {
                um.setInputArea(this);
                um.setPath(Path);
                um.setTopX(Integer.valueOf(txtTopX.getText()));
                if(resultTable == null) {
                    resultTable = um.askForAction();
                } else {
                    remove(resultTable);
                    resultTable = um.askForAction();
                }
                add(resultTable);
                } catch (InputError inputError) {
                    inputError.getMessage();
                }
                window.revalidate();
                window.repaint();
    }
}
