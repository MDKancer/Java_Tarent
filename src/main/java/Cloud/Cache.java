/**
 * @author Daniel Lozanu
 */
package Cloud;

import java.io.File;
import java.util.*;

/**
 * Es wurde ein generisches Container erstellt um die Dataien und ihre Verzeichnise  gepeichert.
 */
public class Cache  {


    private static Map<File, File> cloud = new HashMap<File, File>();
    private static List<File> maxCountDirectory = new ArrayList<File>();
    private static Map<File,Long> SizeDirectorys = new HashMap<File, Long>();
    private static List<File> resultData = new ArrayList<>();

    // hier wird temporär alle Dataiei die einer Verzeichnis gehören
    private static List<File> tempFile = new ArrayList<File>();

    public static List<File> getResultData() {
        return resultData;
    }

    public static void setResultData(List<File> resultData) {
        Cache.resultData = resultData;
    }


    /**
     * Hier speichern der Datei oder Verzeichnis als Index / Schluessel
     *  und den Wert soll sein Elternverzeichnis sein.
     * @param file
     * @param Verzeichnis
     */
    public static void addFile(File file, File Verzeichnis){

        if(!cloud.containsKey(file)){ cloud.put(file,Verzeichnis); }
    }
    public static void addDirectory(File dir){

        if(!maxCountDirectory.contains(dir)){ maxCountDirectory.add(dir); }
    }
    public static void addSizeDirectory(File dir,Long size){
        size = size == null ? 0 : size;
        if(!SizeDirectorys.containsKey(dir)){ SizeDirectorys.put(dir,size); }
    }
    public static List<File> getTempFile() {
        return tempFile;
    }

    public static List<File> getMaxCountDirectory() {
        return maxCountDirectory;
    }

    public static Map<File, File> getCloud() {
        return cloud;
    }
    public static Map<File, Long> getSizeDirectorys() {
        return SizeDirectorys;
    }
    public static File getFile(String Name){
        for (File file: cloud.keySet() ) {
            if(Name.equals( file.getName())){
                return file;
            }
        }
        return null;
    }
    public static List<File> getAllFilesFromDirectory(String DirectoryName){
        tempFile.clear();
        for (Map.Entry<File,File> item : cloud.entrySet() ) {
            if(DirectoryName.equals(item.getValue())){
                tempFile.add(item.getKey());
            }
        }
        return tempFile;
    }
    public static File getDirectory(String FileName){
        for (Map.Entry<File ,File> item: cloud.entrySet() ) {
            if(FileName.equals( item.getKey().getName())) {
                return item.getValue();
            }
        }
        return null;
    }
    public static File getDirectory(File File){
        for (Map.Entry<File ,File> item: cloud.entrySet() ) {
            if(File.equals( item.getKey())) {
                return item.getValue();
            }
        }
        return null;
    }


}
