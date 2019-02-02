/**
 * @author Daniel Lozanu
 */
package GUI;

import IOManager.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitelBar extends JPanel {
    private final int panelwidth = 1024;
    private final int panelheight = 768;

    private JFrame window;
    private InputArea ia;
    private UserManager um;

    private Color red = new Color(189,43,7);

    private boolean mouseDown=false;


    private JLabel lbllogo = new JLabel();
    private JButton btnMinimaze = new JButton();
    private JButton btnMaximaze = new JButton();
    private JButton btnClose = new JButton();

    public TitelBar(Window frame){
        this.window = frame;
        this.ia = frame.getInputArea();
        this.um = frame.getUserManager();

        setLayout(null);
        setBounds(0,0,panelwidth,30);
        setBackground(red);
        add(getLbllogo());
        add(getBtnMinimaze());
        add(getBtnMaximaze());
        add(getBtnClose());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == 1) {
                    while(window.getX() != mouseEvent.getXOnScreen() && window.getY() != mouseEvent.getYOnScreen()) {
                        window.move(mouseEvent.getXOnScreen(), mouseEvent.getYOnScreen());
                    }
                }
            }
        });
    }

    private JLabel getLbllogo(){
        this.lbllogo.setBounds(10,0,130,30);
        this.lbllogo.setForeground(Color.white);
        this.lbllogo.setFont(new Font("Arial",Font.BOLD,14));
        this.lbllogo.setText("tarent");
        return lbllogo;
    }
    private JButton getBtnMinimaze(){

        this.btnMinimaze.setBounds(panelwidth-120,2,30,25);
        this.btnMinimaze.setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
        this.btnMinimaze.setBackground(red);
        this.btnMinimaze.setForeground(Color.white);
        this.btnMinimaze.setFont(getBtnFont());
        this.btnMinimaze.setText("_");
        this.btnMinimaze.addActionListener(actionEvent -> {
            window.setExtendedState(JFrame.ICONIFIED);
            ia.resultTable.setSize(window.getWidth() -20 , ia.resultTable.getHeight());
            ia.resultTable.revalidate();
            ia.resultTable.repaint();
        });
         return this.btnMinimaze;
    }
    private JButton getBtnMaximaze(){

        this.btnMaximaze.setBounds(panelwidth - 85,2,30,25);
        this.btnMaximaze.setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
        this.btnMaximaze.setBackground(red);
        this.btnMaximaze.setForeground(Color.white);
        this.btnMaximaze.setFont(getBtnFont());
        this.btnMaximaze.setText("O");
        this.btnMaximaze.addActionListener(actionEvent -> {
            //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setLocation(0,0);
        });

        return this.btnMaximaze;
    }
    private JButton getBtnClose(){

        this.btnClose.setBounds(panelwidth-50,2,30,25);
        this.btnClose.setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
        this.btnClose.setBackground(red);
        this.btnClose.setForeground(Color.white);
        this.btnClose.setFont(getBtnFont());
        this.btnClose.setText("X");
        this.btnClose.addActionListener(actionEvent -> System.exit(0));
        return this.btnClose;
    }
    private Font getBtnFont(){ return  new Font("Verdana",Font.BOLD,12); }

}
