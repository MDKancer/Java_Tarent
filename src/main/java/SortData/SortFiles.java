/**
 * @author Daniel Lozanu
 */
package SortData;

import Cloud.Cache;
import java.io.File;
import java.util.*;

public class SortFiles {

    /**
     * Es wurde HashMap nach Werte  aufsteigent sortiert.
     * @param map
     * @param <K>
     * @param <V>
     * @return  ->  ein schon sortiertes Map
     */
    public static <K,V extends Comparable<V>> Map<K,V> sortByValues(final Map<K,V> map) {
        Comparator<K> valuesComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));

                if(compare == 0) {
                    return  1;
                } else {
                    return compare;
                }
            }
        };

        Map<K,V> SortedByValues = new TreeMap<K,V>(valuesComparator);
        SortedByValues.putAll(map);

        return SortedByValues;
    }

    /**
     * Es wurde HashMap nach Schluessel aufsteigent sortiert.
     * @param map
     * @param <K>
     * @param <V>
     * @return  ->  ein schon sortiertes Map
     */
    public static <K,V extends Comparable<K>> Map<K,V> sortByKeys(final Map<K,V> map) {
        map.entrySet().stream().sorted();
        return map;
    }
    public List<File> sortDate(final List<File> list) {

        File temp;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < list.size()-1; j++) {
                    if (list.get(j).lastModified() > list.get(j + 1).lastModified()) {

                        temp = list.get(j);
                        list.set(j, list.get(j+1));
                        list.set(j+1, temp);
                    }
            }
        }
        return list;
    }

    public List<File> sortSize(final List<File> list) {

        File temp;
        for (int i = 1; i < list.size(); i++ ){
            for (int j = 0; j < list.size()-1; j++){
                if(!list.get(i).isDirectory()){
                    if (list.get(j).length() > list.get(j + 1).length()) {
                        temp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set(j + 1, temp);
                    }
                }
            }
        }
        return list;
    }

    /**
     * Es wurde eine Liste generiert aus einer Verzeichnis mit dem großen Anzahl der Dataeien
     * @return Es wurde eine Liste von Dateien
     */
    public List<File> sortMaxFileNumberDirectory(){
        File temp;
        for (int i = 1; i < Cache.getMaxCountDirectory().size(); i++) {
            for (int j = 0; j < Cache.getMaxCountDirectory().size()-1; j++) {
                    if (Cache.getMaxCountDirectory().get(j).listFiles().length > Cache.getMaxCountDirectory().get(j + 1).listFiles().length) {
                        temp = Cache.getMaxCountDirectory().get(j);
                        Cache.getMaxCountDirectory().set(j, Cache.getMaxCountDirectory().get(j+1));
                        Cache.getMaxCountDirectory().set(j+1, temp);
                    }
            }
        }
        return Cache.getMaxCountDirectory();
    }
    public List<File> sortMaxFilesSizeDirectory(){
        addDirectorysSize();
        List<File> tempFile = new ArrayList<File>();
        tempFile.addAll(SetSizeAndCountOfDirectorys().keySet());

        return tempFile;
    }


    public List <File> sortName(final List<File> list){
            Collections.sort(list);
        return list;
    }

    private Map<File,Long>  SetSizeAndCountOfDirectorys(){
        return sortByValues(Cache.getSizeDirectorys());
    }
    private void addDirectorysSize(){
        for (File file : Cache.getMaxCountDirectory()) {
            long size = getDirectorySize(file);
            File parentDir = file.getParentFile();
            if(Cache.getSizeDirectorys().containsKey(parentDir)) {
                Cache.getSizeDirectorys().replace(parentDir, Cache.getSizeDirectorys().get(parentDir)+size) ;
            } else {
                Cache.getSizeDirectorys().put(file,size);
            }

        }
        sortByValues(Cache.getSizeDirectorys());

    }

    /**
     * Es wurde die groeße aller Dateien in diesem Verzeichnis zusammen gerechnet.
     * @param dir   ->  Verzeichnis
     * @return      ->  die groeße aller Dateien in diesem Verzeichnis.
     */
    private long getDirectorySize(final File dir){
        long size = 0;
        for (File file : dir.listFiles()){
            if(file.isFile()){
                size += file.length();
            }
        }
        return size;
    }

}
