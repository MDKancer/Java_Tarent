/**
 * @author Daniel Lozanu
 */
package DataErrorType;

/**
 * Es wurde ein Fehler wehrend der Laufprogramm produziert, wenn der Benutzer eine falsche Eingabe eingegibt hat.
 */
public class InputError extends Exception {
    //TODO eine Methode die ein Fehler zurück werft

    public String getMessage(){
        return "Fehlereingabe !!!";
    }

    /**
     *
     * @throws InputError
     */
    public static void getError () throws InputError{ throw new InputError(); }


}
