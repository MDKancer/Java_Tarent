package FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    private String text = "";
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public Reader(String file){
        String fileName = file;
        try {
            this.fileReader = new FileReader(fileName);
            this.bufferedReader = new BufferedReader(fileReader);
        } catch(FileNotFoundException ex) {
            System.err.println(
                    "Datei '" +
                            fileName + "' wurde nicht gefunden!!!");
        }
    }

    public String read(){

        try {
            String line;
            while((line = this.bufferedReader.readLine()) != null) {
                this.text += line;
            }
        }
        catch(IOException ex) {
            System.err.println(
                    "Es wurde ein Fehler beim lesen getrofen!!!");
        }
        finally {
            try {
                this.bufferedReader.close();
            } catch (IOException e) {
                System.err.println("Es konnte die Aufgabe nicht abschliesen!!!");
            }
        }
        return text;
    }

}
