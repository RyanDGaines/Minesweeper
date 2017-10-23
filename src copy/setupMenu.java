import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;


public class setupMenu extends JFrame implements ChangeListener {
    private JSlider bombNumber, heightNumber, widthNumber;
    private JLabel bomb, w, h;
    private JLabel bombNumberfromSlider, heightNumberfromSlider, widthNumberfromSlider;
    private JButton beg, immediate, expert;
    private JPanel buttons, labelpanel, sliderpanel, numberpanel;
    private int chosenB, chosenW, chosenH;

    public setupMenu(int height, int width, int bombs){
        super("Setup");
        chosenH = height; chosenB =bombs; chosenW =width;
        bombNumber = new JSlider();
        bombNumber.setMinimum(1);
        bombNumber.setMaximum(chosenH*chosenW-1);
        bombNumber.setValue(bombs);
        bombNumber.setPaintTicks(true);
        bombNumber.setName("bomb");
        heightNumber = new JSlider();
        heightNumber.setMinimum(5);
        heightNumber.setMaximum(10);
        heightNumber.setValue(height);
        heightNumber.setPaintTicks(true);
        heightNumber.setSnapToTicks(true);
        heightNumber.setName("height");
        widthNumber = new JSlider();
        widthNumber.setMinimum(5);
        widthNumber.setMaximum(10);
        widthNumber.setValue(width);
        widthNumber.setPaintTicks(true);
        widthNumber.setSnapToTicks(true);
        widthNumber.setName("width");


        bombNumberfromSlider = new JLabel(String.valueOf(bombs));
        heightNumberfromSlider = new JLabel(String.valueOf(height));
        widthNumberfromSlider = new JLabel(String.valueOf(width));

        bombNumber.addChangeListener(this);
        heightNumber.addChangeListener(this);
        widthNumber.addChangeListener(this);

        beg = new JButton("Beginner");
        beg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bombNumber.setValue(5);
                chosenB=5;
                bombNumberfromSlider.setText(String.valueOf(5));
                heightNumber.setValue(5);
                chosenH=5;
                heightNumberfromSlider.setText(String.valueOf(5));
                widthNumber.setValue(5);
                widthNumberfromSlider.setText(String.valueOf(5));
                chosenW=5;
            }
        });

        immediate = new JButton("Intermediate");
        immediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bombNumber.setValue(15);
                chosenB=15;
                bombNumberfromSlider.setText(String.valueOf(15));
                heightNumber.setValue(8);
                heightNumberfromSlider.setText(String.valueOf(8));
                chosenH=8;
                widthNumber.setValue(8);
                widthNumberfromSlider.setText(String.valueOf(8));
                chosenW=8;
            }
        });

        expert = new JButton("Expert");
        expert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bombNumber.setValue(30);
                chosenB= 30;
                bombNumberfromSlider.setText(String.valueOf(30));
                heightNumber.setValue(10);
                heightNumberfromSlider.setText(String.valueOf(10));
                chosenH=10;
                widthNumber.setValue(10);
                widthNumberfromSlider.setText(String.valueOf(10));
                chosenW=10;
            }
        });

        bomb = new JLabel("Bombs");
        w = new JLabel("Width");
        h = new JLabel("Height");
        Container C = getContentPane();
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3,0,0));
        buttons.add(beg);
        buttons.add(immediate);
        buttons.add(expert);

        labelpanel = new JPanel();
        labelpanel.setLayout(new GridLayout(3,1,0,5));
        labelpanel.add(bomb);
        labelpanel.add(h);
        labelpanel.add(w);

        sliderpanel = new JPanel();
        sliderpanel.setLayout(new GridLayout(3,1,0,5));
        sliderpanel.add(bombNumber);
        sliderpanel.add(heightNumber);
        sliderpanel.add(widthNumber);

        numberpanel = new JPanel();
        numberpanel.setLayout(new GridLayout(3,1,0,5));
        numberpanel.add(bombNumberfromSlider);
        numberpanel.add(heightNumberfromSlider);
        numberpanel.add(widthNumberfromSlider);

        C.add(buttons,BorderLayout.NORTH);
        C.add(labelpanel,BorderLayout.WEST);
        C.add(sliderpanel, BorderLayout.CENTER);
        C.add(numberpanel, BorderLayout.EAST);
        setSize(400, 200);
        setVisible(true);

    }
    public int getBombs(){
        return chosenB;
    }
    public int getW(){
        return chosenW;
    }
    public int getH(){
        return chosenH;
    }
    public void stateChanged(ChangeEvent e) {
        JSlider slided = (JSlider) e.getSource();
        int value = (int) slided.getValue();
        if (slided.getName().equals("bomb")){
            chosenB = value;
            bombNumberfromSlider.setText(String.valueOf(chosenB));
        }
        else if(slided.getName().equals("height")){
            chosenH = value;
            heightNumberfromSlider.setText(String.valueOf(chosenH));
            bombNumber.setMaximum(chosenH*chosenW-1);
        }
        else if(slided.getName().equals("width")){
            chosenW = value;
            widthNumberfromSlider.setText(String.valueOf(chosenW));
            bombNumber.setMaximum(chosenH*chosenW-1);
        }

    }

}
