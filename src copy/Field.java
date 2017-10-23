import javax.swing.*;
import java.awt.*;


public class Field extends JButton{
    private ClassLoader loader = getClass().getClassLoader();

    private Icon front = new ImageIcon(loader.getResource("res/Minesweeper_unopened_square.svg.png"));

    private Icon clicked;
    private String customName;
    private int x,y;


    public Field() {
        super();
        customName="";
        super.setSize(new Dimension(10,10));
        x=-1;y=-1;
        super.setActionCommand("Field Button");
    }

    public Field(ImageIcon clickedImage, int xc, int yc){
        super();
        super.setSize(new Dimension(10,10));
        super.setActionCommand("Field Button");
        clicked = clickedImage;
        super.setIcon(front);
        x=xc;
        y=yc;
    }

    public void isClicked() { super.setIcon(clicked);}
    public void unClick() {super.setIcon(front);}
    public void setRed(){
        Icon red = new ImageIcon(loader.getResource("res/redbomb.png"));
        super.setIcon(red);
    }
    public String customName() { return customName; }
    public void setCustomName(String s) { customName = s; }
    public int returnX(){return x;}
    public int returnY(){return y;}
}
