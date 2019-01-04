/**
 * @author Daniel Lozanu
 */
package ListData;

import java.io.File;
import java.util.*;

public abstract class ToList {
    //Den Inhalt der this.address wurde nach jeden gefundenen Unterverzeichnis geendert
        // und den Wert wurde der Pfad zu Unterverzeichnis
    protected String address;
    /**
     *  Ein temporeres Speicher für alle gefundene Dateien
     *   und Unter - /Unter-Unter-/ Verzeichniste in Hauptverzeichnis
     */
    protected List<File> tempFiles = new ArrayList<File>();

    /**
     * Sammelt alle Dateien in diesem verzeichnis und im Unterverzeichnis
     * und speichert diese Dateien in Cache.
     */
    protected abstract void scan();

    /**
     * Es wurden die  ersten (Top_x) Dateien sortiert nach einer bestimmter Moeglichkeit zurrueck gegeben..
     * @param Top_x             ->  liefert die X größten Dateien in dem Verzeichnis.
     * @return List<File>       ->  eine Liste mit alle Dateien von dem Verzeichnis
     */
    public abstract List<File> getTopFiles(int Top_x);

    /**
     * Sammelt und liefert den ganzen Inhlat von dem Verzeichnis.
     * @return
     */
    public abstract List<File> list();

}
