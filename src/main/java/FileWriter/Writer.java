package FileWriter;



import java.io.*;

public class Writer {

    private String targetFile;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;


    public Writer(String TargetFile){
        this.targetFile = TargetFile;

        try {
            this.fileWriter = new FileWriter(targetFile);
            this.bufferedWriter = new BufferedWriter(this.fileWriter);
        } catch (IOException e) {
            System.err.println("Datei "+this.targetFile+" wurde nicht gefunden");
        }
    }
    public void write(String Text){
        try {
            this.bufferedWriter.write(Text);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                this.bufferedWriter.flush();
                this.bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void CopyPaste(String SourceFile, String TargetFile){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            fileInputStream = new FileInputStream(SourceFile);
            fileOutputStream = new FileOutputStream(TargetFile);

            fileOutputStream.write(fileInputStream.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
