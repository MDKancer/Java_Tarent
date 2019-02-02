/**
 * @author Daniel Lozanu
 */
package ListData;

import Cloud.Cache;
import DataErrorType.InputError;
import FileWriter.Writer;
import IOManager.UserManager;
import SortData.SortFiles;
import StatesProgramm.BackendState;
import StatesProgramm.FrontendState;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScanData extends ToList {

    private SortFiles sf = new SortFiles();
    private FileReader fr;
    //Den Inhalt der this.mainDirectory wurde nur ein mal Defieniert.
    // und den Wert wurde der Pfad von Hauptverzeichnis.
    private String mainDirectory;

    /**
     * Es wurde beimm Initialiesieren aller Dataien und Unter- und Verzeichnise in Cache gespeichert
     * und sortiert nach Groesse und noch nach Anzahl der Dateien.
     * @param address Pfad zu Zieldatei
     */
    public ScanData(String address){
        this.address = address;
        this.mainDirectory = address;
        scan();
        this.sf.sortMaxFileNumberDirectory();
        this.sf.sortMaxFilesSizeDirectory();
    }

    protected void scan() {
        File dir = new File(this.address);
        UserManager.getBackendState().setCurrentState(BackendState.CollectData);
        if(dir.exists()){
            this.tempFiles = Arrays.asList(dir.listFiles());

            for (File file : this.tempFiles) {
                if (file.isFile()) {
                  Cache.addFile(file, file.getParentFile());
                } else if (file.isDirectory()) {
                    this.address = file.getAbsolutePath();
                    Cache.addFile(file, file.getParentFile());
                    Cache.addDirectory(file);
                    scan();
                }
            }
        } else {
            System.err.println(new InputError().getMessage());
        }
    }

    public List<File> getTopFiles(int Top_x) {
        Cache.getResultData().clear();
        UserManager.getBackendState().setCurrentState(BackendState.Sort);
        UserManager.getFrontendState().setCurrentState(FrontendState.Wait);
        Top_x = Top_x > this.list().size() || Top_x == 0 ? this.list().size() : Top_x;
        List<File> templist = null;

        switch (UserManager.getSortState().getCurrentState()) {
            case SortByDate:
                templist = this.sf.sortDate(list());
                break;
            case SortBySize:
                templist = this.sf.sortSize(list());
                break;
            case SortByName:
                templist = this.sf.sortName(list());
                break;
            //Top X der Verzeichnise mit den meisten Dateien
            case SortMaxFilesNumberDirectory:
                templist = this.sf.sortMaxFileNumberDirectory();
                break;
            //Top X der Verzeichnise mit den größten Dateien
            case SortMaxFilesSizeDirectory:
                templist = this.sf.sortMaxFilesSizeDirectory();
                break;
            case CopyPaste:
                // TODO: In Usermanager abfragen SourceFile und TargetFile
                Writer.CopyPaste(this.mainDirectory,this.mainDirectory+"1");
                break;
            case None:
                templist = this.sf.sortName(list());
                break;
        }
        for (int i = 0; i < Top_x; i++) { Cache.getResultData().add(templist.get(i)); }
        UserManager.getFrontendState().setCurrentState(FrontendState.Request);
        return Cache.getResultData();
    }

    public List<File> list() {
        final List<File> tempFile = new ArrayList<>();
        final File dir = new File(this.mainDirectory);
        for(Map.Entry<File ,File> item : Cache.getCloud().entrySet()){
            if(item.getValue().equals(dir)){
                tempFile.add(item.getKey());
            }
        }
        UserManager.getFrontendState().setCurrentState(FrontendState.Request);
        return tempFile;
    }
}
