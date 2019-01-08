/**
 * @author Daniel Lozanu
 */
package GUI;

import DataErrorType.InputError;
import IOManager.UserManager;
import StatesProgramm.FrontendState;
import StatesProgramm.SortTyp;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class InputArea extends JPanel {
    private JFrame window;
    private final int panelwidth = 1024;
    private final int panelheight = 768;
    private UserManager um;
    private JTextField txtPath = new JTextField();
    private JLabel lblPath = new JLabel();
    private JLabel lblParentDirectory = new JLabel();
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
        setBackground(new Color(106, 90, 205));
        setSize(panelwidth , panelheight);
        add(getLblPath());
        add(getTxtPath());
        add(getTxtTopX());
        add(getLblTopX());
        add(getLblParentDirectory());
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
    private JLabel getLblParentDirectory(){
        lblParentDirectory.setBounds(20,120,panelwidth,30);
        lblParentDirectory.setBackground(new Color(255, 99, 71));
        lblParentDirectory.setForeground(new Color(255, 255, 255));
        lblParentDirectory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(!lblParentDirectory.getText().equals( "")) {
                    ShowData(lblParentDirectory.getText());
                }
            }
        });
        lblParentDirectory.setVisible(true);
        return lblParentDirectory;
    }
    private JTextField getTxtTopX(){
        txtTopX.setBounds(140,40,120,30);
        return txtTopX;
    }
    private JButton getBtnOK(){
        btnOK.setText("OK");
        btnOK.setBounds(this.panelwidth - 140,this.panelheight - 80, 120 ,30);
        btnOK.setBackground(new Color(30, 144, 255));
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
        jSortNamebtn.setBackground(new Color(106, 90, 205));
        group.add(jSortNamebtn);
        jSortNamebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortByName);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortNamebtn;
    }
    private JRadioButton getjSortSizebtn(){
        jSortSizebtn.setBounds(140,80,120,30);
        jSortSizebtn.setBackground(new Color(106, 90, 205));
        group.add(jSortSizebtn);
        jSortSizebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortBySize);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortSizebtn;
    }
    private JRadioButton getjSortDatebtn(){
        jSortDatebtn.setBounds(270,80,120,30);
        jSortDatebtn.setBackground(new Color(106, 90, 205));
        group.add(jSortDatebtn);
        jSortDatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortByDate);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortDatebtn;
    }
    private JRadioButton getjSortMaxCountbtn(){
        jSortMaxCountbtn.setBounds(400,80,120,30);
        jSortMaxCountbtn.setBackground(new Color(106, 90, 205));
        group.add(jSortMaxCountbtn);
        jSortMaxCountbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.sortState.setCurrentState(SortTyp.SortMaxFilesNumberDirectory);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortMaxCountbtn;
    }
    public void ShowData(String Path){
            try {
                    um.setInputArea(this);
                    um.setPath(Path);
                    lblParentDirectory.setText(new File(um.getPath()).getParent());
                    Integer number = txtTopX.getText().equals("") ? 0 : Integer.valueOf(txtTopX.getText());
                    um.setTopX(number);
                    if(resultTable == null) {
                        resultTable = um.askForAction();
                    } else {
                        remove(resultTable);
                        resultTable = um.askForAction();
                    }
                    add(resultTable);
                } catch (InputError inputError) {
                    //TODO: .....
                }
                window.revalidate();
                window.repaint();
    }
}
