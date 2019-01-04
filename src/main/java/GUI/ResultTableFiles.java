package GUI;

import Cloud.Cache;
import IOManager.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class ResultTableFiles extends JTable {
    private DefaultTableModel model;
    private InputArea inputArea;
    public ResultTableFiles(List<File> tempFile, InputArea ia){
        this.inputArea = ia;
        setBounds(10,10,1004,500);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 50));
        //ersetzt Margin left um 10 und top um 1
        setIntercellSpacing(new Dimension(10,0));

        //dass ist für die Cells nicht äendern können
        this.setDefaultEditor(Object.class,null);

        setTableEventListner();
        setAutoscrolls(true);
        setColumns();
        getData(tempFile);
        setVisible(true);
    }

    private void setColumns(){
        this.model = new DefaultTableModel();
        this.setModel(model);
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Size");
        model.addColumn("Typ");
        model.addColumn("Status");

        this.getColumnModel().getColumn(0).setHeaderValue("Name");
        this.getColumnModel().getColumn(1).setHeaderValue("Date");
        this.getColumnModel().getColumn(2).setHeaderValue("Size");
        this.getColumnModel().getColumn(3).setHeaderValue("Typ");
        this.getColumnModel().getColumn(4).setHeaderValue("Status");
    }

    private void getData(List<File> tempFile){
        for (int i = 0; i< tempFile.size(); i++) {
            String[] temp = {
                    tempFile.get(i).getName(),
                    getDate(tempFile.get(i).lastModified()),
                    getTyp(tempFile.get(i).length()),
                    tempFile.get(i).isFile() ? " (File)": " (Directory)",
                    " "
            };
              model.addRow(temp);

        }
    }

    private void setTableEventListner(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    int row = rowAtPoint(mouseEvent.getPoint());
                    int col = columnAtPoint(mouseEvent.getPoint());
                    if(col == 0) {
                        String name = getModel().getValueAt(row, col).toString();
                        inputArea.ShowData(getPathofData(name));
                    }
                }
            }
        });
    }
    /**
     * Es wurde ein String mit der groeße der Datei in Byte /Kb / Mb zurrueck werfen.
     * @param
     * @return String
     */
    private String getTyp(long Byte){
        if(getByteToKiloByte(Byte) > 1000){
            return getKiloByteToMegaByte(getByteToKiloByte(Byte)) + " Mb";
        }
        else if(getByteToKiloByte(Byte) < 1) {
            return Byte + " Byte";
        }
        return  getByteToKiloByte(Byte) + " Kb";
    }
    private float getByteToKiloByte(long Byte){ return  (float)(Byte / 1024.0); }
    private float getKiloByteToMegaByte (float Byte){ return (float) (Byte / 1024.0); }
    private String getDate(long date){ return new SimpleDateFormat("MM/dd/yy").format(date); }

    private String getPathofData(String name){
        for(File file : Cache.getCloud().keySet()){
            if(file.getName().equals(name)){
                return file.getAbsolutePath();
            }
        }
        return null;
    }
}
