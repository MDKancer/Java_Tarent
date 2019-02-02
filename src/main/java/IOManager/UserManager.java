/**
 * @author Daniel Lozanu
 */
package IOManager;

import DataErrorType.InputError;
import GUI.*;
import ListData.ScanData;
import StatesProgramm.*;
import javax.swing.*;
import java.io.File;
import java.util.List;

public class UserManager implements IO{

    private static StateManager<BackendState> backendState = new StateManager<>();
    private static StateManager<FrontendState> frontendState = new StateManager<>();
    private static StateManager<SortTyp> sortState = new StateManager<>();

    private String path;
    private Integer TopX;
    private ScanData scandata ;
    private JPanel panel;
    private Window window;
    private InputArea ia;
    /**
     * Es wurde eine Liste von Dateien die zum ZielVerzeichnis gehören gesammelt und
     * @return zurück wurde ein schon JPanel mit der fertige Tabelle von alle Dateien generiert
     * @throws InputError wenn es ein Eingabe Fehler triefft ,wurde ein Fehler zurück geworfen.
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
        return this.path;
    }
    public void setInputArea(InputArea inputArea){
        this.ia = inputArea;
    }

    /**
     * Es wurde ein JPanel für die Ergebnistabele erstellt und direkt konfiguriert
     * @param files ist benötigt um die Information zu der Ergebnistabele weitergeleiter werden
     * @return JPanel mit eine Ergebnistabelle
     */
    private JPanel getResultArea(List<File> files){
        this.panel = new JPanel();
        this.panel.setBackground(ia.getBackground());
        this.panel.setBounds(10,160,1004,500);
        this.panel.setLayout(null);
        this.panel.add(new ResultTableFiles(files,this.ia));
        this.panel.setVisible(true);
        return this.panel;
    }


}
