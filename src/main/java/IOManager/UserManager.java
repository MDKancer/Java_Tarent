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
import java.awt.*;
import java.io.File;
import java.util.List;

public class UserManager implements IO{

    private static StateManager<BackendState> backendState = new StateManager<BackendState>();
    private static StateManager<FrontendState> frontendState = new StateManager<FrontendState>();
    private static StateManager<SortTyp> sortState = new StateManager<SortTyp>();

    private String path;
    private Integer TopX;
    private ScanData scandata ;
    private JPanel panel;
    private InputArea ia;

    /**
     * Es wurde eine Liste von Dateien die zum ZielVerzeichnis gehören gesammelt und
     * @return zurück wurde ein schon JPanel mit der fertige Tabelle von alle Dateien generiert
     * @throws InputError
     */
    @Override
    public JPanel askForAction() throws InputError {
        return getResultArea(this.scandata.getTopFiles(this.TopX));
    }

    public static StateManager<BackendState> getBackendState() { return backendState; }

    public static StateManager<FrontendState> getFrontendState() { return frontendState; }

    public static StateManager<SortTyp> getSortState() { return sortState; }

    public void setTopX(Integer TopX){ this.TopX = TopX; }

    /**
     * Es wurde den Pfad gespeichert und alle Dateien und Verzeichnise was gibts in Cache gespeichert und sortiert.
     * @param path  ->  ein Pfad zu einem Verzeichnis
     */
    public void setPath(String path){
        this.path = path;
        this.scandata = new ScanData(this.path);
    }

    public String getPath() {
        return path;
    }
    public void setInputArea(InputArea inputArea){
        this.ia = inputArea;
    }

    /**
     * Es wurde ein JPanel für die Ergebnistabele erstellt und direkt konfiguriert
     * @param files ist benötigt um die Information zu der Ergebnistabele weitergeleiter werden
     * @return
     */
    private JPanel getResultArea(List<File> files){
        panel = new JPanel();
        panel.setBackground(ia.getBackground());
        panel.setBounds(10,160,1004,500);
        panel.setLayout(null);
        panel.add(new ResultTableFiles(files,this.ia));
        panel.setVisible(true);
        return panel;
    }


}
