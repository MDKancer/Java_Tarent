/**
 * @author Daniel Lozanu
 */
package GUI;

import DataErrorType.InputError;
import IOManager.UserManager;
import StatesProgramm.FrontendState;
import StatesProgramm.SortTyp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class InputArea extends JPanel {
    private JFrame window;
    private final int panelwidth = 1024;
    private final int panelheight = 768;
    private Color bgColor = new Color(134, 112, 198);
    private UserManager um;
    private JTextField txtPath = new JTextField();
    private JLabel lblPath = new JLabel();
    private JLabel lblParentDirectory = new JLabel();
    private JLabel lblDataError = new JLabel();
    private JTextField txtTopX = new JTextField();
    private JLabel lblTopX = new JLabel();
    private JButton btnOK = new JButton();

    private JPanel resultTable;

    private ButtonGroup group = new ButtonGroup();
    private JRadioButton jSortNamebtn = new JRadioButton(SortTyp.SortByName.name());
    private JRadioButton jSortSizebtn = new JRadioButton(SortTyp.SortBySize.name());
    private JRadioButton jSortDatebtn = new JRadioButton(SortTyp.SortByDate.name());
    private JRadioButton jSortMaxCountbtn = new JRadioButton(SortTyp.SortMaxFilesNumberDirectory.name());

    /**
     * Es wurde ein JPanel mit allen Input und Output Komponente erstelt
     * @param userManager
     * @param frame
     */
    public InputArea(UserManager userManager, JFrame frame){
        window = frame;
        um = userManager;
        setLayout(null);
        setBackground(bgColor);
        setSize(panelwidth , panelheight);
        add(getLblPath());
        add(getTxtPath());
        add(getTxtTopX());
        add(getLblTopX());
        add(getLblParentDirectory());
        add(getLblDataError());
        add(getjSortNamebtn());
        add(getjSortSizebtn());
        add(getjSortDatebtn());
        add(getjSortMaxCountbtn());
        add(getBtnOK());
        UserManager.getFrontendState().setCurrentState(FrontendState.Input);
        UserManager.getSortState().setCurrentState(SortTyp.None);
    }

    /**
     * Es wurde alle Daten gesammelt zu Usermanager geschickt,
     * und dann die Liste aller Dateien in das Verzeichnis in einer Tabele hinzugefügt,
     * aber dafür braucht man den Pfad zu dem ZielVerzeichnis.
     * @param Path
     */
    public void ShowData(String Path){
        File targetData = new File(Path);
        if (targetData.isDirectory()) {
            try {
                    um.setInputArea(this);
                    um.setPath(Path);
                    lblParentDirectory.setText(targetData.getParent());
                    //wenns kein Fehler getrofen wurde, wurde ErrorLabelText auf null gesetzt
                    lblDataError.setText("");
                    //Es wurde überprüft ob dass was eingegibt ist , ist null oder nicht,
                    //Wenn ja dann auf 0 ersetzt,
                    // wenn nicht dann wurde der Zahl in Integer Parsen
                    Integer number = txtTopX.getText().equals("") ? 0 : Integer.valueOf(txtTopX.getText());
                    um.setTopX(number);
                    if (resultTable == null) {
                        resultTable = um.askForAction();
                    } else {
                        //Bevor dass die neue daten in der Tabele kommen
                        // soll die Tabele gelöscht werden
                        // und dann wieder mit den aktuellen daten erstellt.
                        remove(resultTable);
                        resultTable = um.askForAction();
                    }
                    add(resultTable);
                } catch(InputError inputError){
                    //TODO: .....
                }
            //Es wurde wieder alle Objekten auf dem aktuellen/neuen Stand erstellt.
                window.revalidate();
                window.repaint();
        } else {
            //Wenns kein Fehler getrofen wurde,
            // wurde ErrorLabelText ein Fehler Text bekommen.
            lblDataError.setText("Die Datei kann nicht geöffnet werden!!!");
        }
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

    /**
     * Es wurde ein Label konfiguriert und in dem  wird den Pfad dieser Verzeichnis eingezeigt.
     * @return
     */
    private JLabel getLblParentDirectory(){
        lblParentDirectory.setBounds(20,120,panelwidth,30);
        lblParentDirectory.setBackground(new Color(255, 99, 71));
        lblParentDirectory.setForeground(new Color(255, 255, 255));
        lblParentDirectory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(!lblParentDirectory.getText().equals("")) {
                    ShowData(lblParentDirectory.getText());
                }
            }
        });
        lblParentDirectory.setVisible(true);
        return lblParentDirectory;
    }

    /**
     * Es wurde ein Label konfiguriert extra für Auswahlfehler.
     * @return
     */
    private JLabel getLblDataError(){
        lblDataError.setBounds(20,this.panelheight-100,this.panelwidth / 2,30);
        lblDataError.setForeground(Color.red);
        return lblDataError;
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

    /**
     * Es wurde für den Zustand Sortierung nach Name ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return
     */
    private JRadioButton getjSortNamebtn(){
        jSortNamebtn.setBounds(10,80,120,30);
        jSortNamebtn.setBackground(bgColor);
        group.add(jSortNamebtn);
        jSortNamebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.getSortState().setCurrentState(SortTyp.SortByName);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortNamebtn;
    }
    /**
     * Es wurde für den Zustand Sortierung nach Groeße ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return
     */
    private JRadioButton getjSortSizebtn(){
        jSortSizebtn.setBounds(140,80,120,30);
        jSortSizebtn.setBackground(bgColor);
        group.add(jSortSizebtn);
        jSortSizebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.getSortState().setCurrentState(SortTyp.SortBySize);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortSizebtn;
    }
    /**
     * Es wurde für den Zustand Sortierung nach Datum (wann war es letzes mal geändert)
     * ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return
     */
    private JRadioButton getjSortDatebtn(){
        jSortDatebtn.setBounds(270,80,120,30);
        jSortDatebtn.setBackground(bgColor);
        group.add(jSortDatebtn);
        jSortDatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.getSortState().setCurrentState(SortTyp.SortByDate);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortDatebtn;
    }
    /**
     * Es wurde für den Zustand Sortierung nach Anzahl der Dateien in das Verzeichnis
     * ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return
     */
    private JRadioButton getjSortMaxCountbtn(){
        jSortMaxCountbtn.setBounds(400,80,120,30);
        jSortMaxCountbtn.setBackground(bgColor);
        group.add(jSortMaxCountbtn);
        jSortMaxCountbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserManager.getSortState().setCurrentState(SortTyp.SortMaxFilesNumberDirectory);
                if(um.getPath() != null){
                    ShowData(um.getPath());
                }
            }
        });
        return jSortMaxCountbtn;
    }
}
