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
     * @param address
     */
    public ScanData(String address){
        this.address = address;
        this.mainDirectory = address;
        scan();
        sf.sortMaxFileNumberDirectory();
        sf.sortMaxFilesSizeDirectory();
    }

    protected void scan() {
        File dir = new File(this.address);
        UserManager.getBackendState().setCurrentState(BackendState.CollectData);
        if(dir.exists()){
            tempFiles = Arrays.asList(dir.listFiles());

            for (File file : tempFiles) {
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
        Cache.setResultData(new ArrayList<File>());
        UserManager.getBackendState().setCurrentState(BackendState.Sort);
        UserManager.getFrontendState().setCurrentState(FrontendState.Wait);
        Top_x = Top_x > list().size() || Top_x == 0 ? list().size() : Top_x;
        List<File> list = list();
        for (int i = 0; i < Top_x; i++) {
            switch (UserManager.getSortState().getCurrentState()) {
                case SortByDate:
                    Cache.getResultData().add(sf.sortDate(list).get(i));
                    break;
                case SortBySize:
                    Cache.getResultData().add(sf.sortSize(list).get(i));
                    break;
                case SortByName:
                    Cache.getResultData().add(sf.sortName(list).get(i));
                    break;
                    //Top X der Verzeichnise mit den meisten Dateien
                case SortMaxFilesNumberDirectory:
                    Cache.getResultData().add(sf.sortMaxFileNumberDirectory().get(i));
                    break;
                    //Top X der Verzeichnise mit den größten Dateien
                case SortMaxFilesSizeDirectory:
                    Cache.getResultData().add(sf.sortMaxFilesSizeDirectory().get(i));
                    break;
                case CopyPaste:
                    // TODO: In Usermanager abfragen SourceFile und TargetFile
                    Writer.CopyPaste(this.mainDirectory,this.mainDirectory+"1");
                    break;
                case None:
                    Cache.getResultData().add(sf.sortName(list).get(i));
                    break;
            }
        }
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
