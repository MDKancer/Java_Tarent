Dieses Paket soll dafür  verantwortlich sein, die Dateien in ein Verzeichnis aufzulisten können und als Rückgabe eine Dateiliste bereit zu stellen.

Beispiel-Atributte:
                    ->  Pfad des Verzeichnisses
                    ->  Dateien in dem Verzeichnis
Beispiel-Methoden:
                    ->  Scan()              -   durchforstet das Verzeichnis inklusive Unterverzeichnisse
                    ->  getTopFiles(Top X)  -   liefert die X größten Dateien in dem Verzeichnis (muss zuvor Scan() aufgerufen werde)
                    ->  List()              -   liefert alle Dateien zurück
