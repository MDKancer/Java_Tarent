/**
 * @author Daniel Lozanu
 */
package GUI;

import Cloud.Cache;
import DataErrorType.InputError;
import IOManager.UserManager;
import StatesProgramm.FrontendState;
import StatesProgramm.SortTyp;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class InputArea extends JPanel {
    private final int panelwidth = 1024;
    private final int panelheight = 768;
    private Color red = new Color(189,43,7);
    private UserManager um;
    private Window window;
    private TitelBar titelBar;
    private JTextField txtPath = new JTextField();
    private JLabel lblPath = new JLabel();
    private JLabel lblParentDirectory = new JLabel();
    private JLabel lblDataError = new JLabel();
    private JLabel lblInfo = new JLabel();
    private JTextField txtTopX = new JTextField();
    private JLabel lblTopX = new JLabel();
    private JButton btnOK = new JButton();

    JPanel resultTable;

    private ButtonGroup group = new ButtonGroup();
    private JRadioButton jSortNamebtn = new JRadioButton(SortTyp.SortByName.name());
    private JRadioButton jSortSizebtn = new JRadioButton(SortTyp.SortBySize.name());
    private JRadioButton jSortDatebtn = new JRadioButton(SortTyp.SortByDate.name());
    private JRadioButton jSortMaxCountbtn = new JRadioButton(SortTyp.SortMaxFilesNumberDirectory.name());

    /**
     * Es wurde ein JPanel mit allen Input und Output Komponente erstelt
     * @param frame um die Verbindung zwischen Objekten zu haben.
     */
    InputArea(Window frame){
        this.window = frame;
        this.um = frame.getUserManager();
        this.titelBar = frame.getTitelBar();
        setLayout(null);
        setBackground(Color.white);
        setBounds(0,30,panelwidth , panelheight);
        add(getLblPath());
        add(getTxtPath());
        add(getTxtTopX());
        add(getLblTopX());
        add(getLblParentDirectory());
        add(getLblDataError());
        add(getLblInfo());
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
     * @param Path der Pfad zu gewünstes Verzeichnis
     */
    void ShowData(String Path){
        File targetData = new File(Path);
        if (targetData.isDirectory()) {
            try {
                this.um.setInputArea(this);
                this.um.setPath(Path);
                this.lblParentDirectory.setText(targetData.getParent());
                //wenns kein Fehler getrofen wurde, wurde ErrorLabelText auf null gesetzt
                this.lblDataError.setText("");
                //Es wurde überprüft ob dass was eingegibt ist , ist null oder nicht,
                //Wenn ja dann auf 0 ersetzt,
                // wenn nicht dann wurde der Zahl in Integer Parsen
                Integer number = this.txtTopX.getText().equals("") ? 0 : Integer.valueOf(this.txtTopX.getText());
                this.um.setTopX(number);
                if (this.resultTable == null) {
                    this.resultTable = this.um.askForAction();
                } else {
                    //Bevor dass die neue daten in der Tabele kommen
                    // soll die Tabele gelöscht werden
                    // und dann wieder mit den aktuellen daten erstellt.
                    remove(this.resultTable);
                    this.resultTable = this.um.askForAction();
                }
                add(this.resultTable);

//TODO : Es muss immer wenn ich das Verzeichnis wechsle
// dass die akktuelle Verzeichnisgröße in lblInfo angezeigt werden,

                lblInfo.setText("Total Files : "+Cache.getCloud().size() +", Size of Directory");

            } catch(InputError ignored) {}
        } else {
            //Wenns kein Fehler getrofen wurde,
            // wurde ErrorLabelText ein Fehler Text bekommen.
            this.lblDataError.setText("Die Datei kann nicht geöffnet werden!!!");
        }
    }
    private Border getWhiteBorder(){ return new LineBorder(Color.white,2); }
    Border getRedBorder(){ return new LineBorder(red,2); }

    private JLabel getLblPath(){
        this.lblPath.setText("Directory Path: ");
        this.lblPath.setBounds(10,10,120,30);
        this.lblPath.setForeground(red);
        return this.lblPath;
    }
    private JTextField getTxtPath(){
        this.txtPath.setBounds(140,10,120,30);
        this.txtPath.setBackground(new Color(255, 255, 255, 255));
        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                txtPath.requestFocus();
            }
        });
        this.txtPath.addKeyListener(getEvent());

        return this.txtPath;
    }
    private JLabel getLblTopX(){
        this.lblTopX.setText("Show Top X: ");
        this.lblTopX.setForeground(red);
        this.lblTopX.setBounds(10,40,120,30);
        return this.lblTopX;
    }

    /**
     * Es wurde ein Label konfiguriert und in dem  wird den Pfad dieser Verzeichnis eingezeigt.
     * @return ein fertiges Label
     */
    private JLabel getLblParentDirectory(){
        this.lblParentDirectory.setBounds(20,120,panelwidth,30);
        this.lblParentDirectory.setBackground(Color.white);
        this.lblParentDirectory.setForeground(red);
        this.lblParentDirectory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(!lblParentDirectory.getText().equals("")) {
                    ShowData(lblParentDirectory.getText());
                }
            }
        });
        this.lblParentDirectory.setVisible(true);
        return this.lblParentDirectory;
    }

    /**
     * Es wurde ein Label konfiguriert extra für Auswahlfehler.
     * @return ein fertiges Label
     */
    private JLabel getLblDataError(){
        this.lblDataError.setBounds(20,this.panelheight-100,this.panelwidth / 2,30);
        this.lblDataError.setForeground(red);
        return this.lblDataError;
    }
    private JLabel getLblInfo(){
        this.lblInfo.setBounds(20,this.panelheight-70,this.panelwidth / 2,30);
        this.lblInfo.setForeground(red);
        return this.lblInfo;
    }
    private JTextField getTxtTopX(){
        this.txtTopX.setBounds(140,40,120,30);
        this.txtTopX.addKeyListener(getEvent());
        return this.txtTopX;
    }
    private JButton getBtnOK(){
        this.btnOK.setText("OK");
        this.btnOK.setBounds(this.panelwidth - 140,this.panelheight - 80, 120 ,30);
        this.btnOK.setBackground(red);
        this.btnOK.setForeground(Color.white);
        this.btnOK.setBorder(getWhiteBorder());
        this.btnOK.addActionListener(actionEvent -> ShowData(txtPath.getText()));
        return this.btnOK;
    }

    /**
     * Es wurde für den Zustand Sortierung nach Name ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return  ein fertiges RadioButton
     */
    private JRadioButton getjSortNamebtn(){
        this.jSortNamebtn.setBounds(10,80,120,30);
        this.jSortNamebtn.setBackground(Color.white);
        this.jSortNamebtn.setForeground(this.red);
        this.group.add(this.jSortNamebtn);
        this.jSortNamebtn.addActionListener(actionEvent -> {
            UserManager.getSortState().setCurrentState(SortTyp.SortByName);
            if(this.um.getPath() != null){
                ShowData(this.um.getPath());
            }
        });
        this.jSortNamebtn.addKeyListener(getEvent());
        return this.jSortNamebtn;
    }
    /**
     * Es wurde für den Zustand Sortierung nach Groeße ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return  ein fertiges RadioButton
     */
    private JRadioButton getjSortSizebtn(){
        this.jSortSizebtn.setBounds(140,80,120,30);
        this.jSortSizebtn.setBackground(Color.white);
        this.jSortSizebtn.setForeground(this.red);
        this.group.add(this.jSortSizebtn);
        this.jSortSizebtn.addActionListener(actionEvent -> {
            UserManager.getSortState().setCurrentState(SortTyp.SortBySize);
            if(this.um.getPath() != null){
                ShowData(this.um.getPath());
            }
        });
        this.jSortSizebtn.addKeyListener(getEvent());
        return this.jSortSizebtn;
    }
    /**
     * Es wurde für den Zustand Sortierung nach Datum (wann war es letzes mal geändert)
     * ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return  ein fertiges RadioButton
     */
    private JRadioButton getjSortDatebtn(){
        this.jSortDatebtn.setBounds(270,80,120,30);
        this.jSortDatebtn.setBackground(Color.white);
        this.jSortDatebtn.setForeground(this.red);
        this.group.add(this.jSortDatebtn);
        this.jSortDatebtn.addActionListener(actionEvent -> {
            UserManager.getSortState().setCurrentState(SortTyp.SortByDate);
            if(this.um.getPath() != null){
                ShowData(this.um.getPath());
            }
        });
        this.jSortDatebtn.addKeyListener(getEvent());
        return this.jSortDatebtn;
    }
    /**
     * Es wurde für den Zustand Sortierung nach Anzahl der Dateien in das Verzeichnis
     * ein Radio Button erstelt und konfiguriert.
     * nach einem klick wird dem aktuellen Verzeichnis Sortiert.
     * @return  ein fertiges RadioButton
     */
    private JRadioButton getjSortMaxCountbtn(){
        this.jSortMaxCountbtn.setBounds(400,80,120,30);
        this.jSortMaxCountbtn.setBackground(Color.white);
        this.jSortMaxCountbtn.setForeground(this.red);
        this.group.add(this.jSortMaxCountbtn);
        this.jSortMaxCountbtn.addActionListener(actionEvent -> {
            UserManager.getSortState().setCurrentState(SortTyp.SortMaxFilesNumberDirectory);
            if(this.um.getPath() != null){
                ShowData(this.um.getPath());
            }
        });
        this.jSortMaxCountbtn.addKeyListener(getEvent());
        return this.jSortMaxCountbtn;
    }

    private KeyAdapter getEvent(){
       return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    ShowData(txtPath.getText());
                }
            }
        };
    }

}
