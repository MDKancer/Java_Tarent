/**
 * @author Daniel Lozanu
 */
package IOManager;

import DataErrorType.InputError;

import javax.swing.*;

public interface IO {
    /**
     * Fragt den Benutzer, was er machen möchte und führt diese Aktion aus
     * DELEGIERT die Aufgabe an eine Instanzt der ersten Klasse( die  verantwortlich für das Auflisten des Verzeichnises da ist)
     */
    JPanel askForAction() throws InputError;
}

