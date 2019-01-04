/**
 * @author Daniel Lozanu
 */
package IOManager;

import DataErrorType.InputError;
import GUI.InputArea;
import GUI.ResultTableFiles;
import ListData.ScanData;
import StatesProgramm.BackendState;
import StatesProgramm.FrontendState;
import StatesProgramm.SortTyp;
import StatesProgramm.StateManager;
import javax.swing.*;
import java.io.File;
import java.util.List;

public class UserManager implements IO{
    public static StateManager<BackendState> backendState = new StateManager<BackendState>();
    public static StateManager<FrontendState> frontendState = new StateManager<FrontendState>();
    public static StateManager<SortTyp> sortState = new StateManager<SortTyp>();
    private String path;
    private Integer TopX;
    private ScanData scandata ;
    private JPanel panel;
    private InputArea ia;

//  TODO : Ich muss erstmal alles strukturieren und abtrakt tun und vieleich gibts eine andere Möglichkeit dieser Datenzu speichern

    @Override
    public JPanel askForAction() throws InputError {
        return getResultArea(this.scandata.getTopFiles(this.TopX));
    }
    public void setTopX(Integer TopX){ this.TopX = TopX; }

    /**
     * Es wurde den Pfad gespeichert und alle Dateien und Verzeichnise was gibts in Cache gespeichert und sortiert.
     * @param path  ->  ein Pfad zu einem Verzeichnis
     */
    public void setPath(String path){
        this.path = path;
        this.scandata = new ScanData(this.path);
    }
    public void setInputArea(InputArea inputArea){
        this.ia = inputArea;
    }

    public JPanel getResultArea(List<File> files){
        panel = new JPanel();
        panel.setBounds(10,120,1004,500);
        panel.setLayout(null);
        panel.add(new ResultTableFiles(files,this.ia));
        panel.setVisible(true);
        return panel;
    }


}
