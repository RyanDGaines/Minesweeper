import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bombs extends JFrame implements ActionListener {
    private Board gameBoard; //creates board
    private JPanel boardView, menuView, clickView;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private int clickCount;
    private int width, height, bombs, gameTime;
    private MineMenu menu;
    private Container c;
    private setupMenu Setup;
    private JButton restart;
    private JLabel timer, clicks;
    private ClassLoader loader = getClass().getClassLoader();
    private Timer time;

    public Bombs(){
        super("Minesweeper");
        clickCount=0;
        width = 8; height = 8; bombs = 15; gameTime=0;
        gameBoard = new Board(this,height,width,bombs);
        menu = new MineMenu(this);
        boardView = new JPanel();
        gameBoard.fillBoardView(boardView);
        menuView = new JPanel();
        menu.fillView(menuView);
        Icon restartIcon = new ImageIcon(loader.getResource("res/normal.png"));
        restart = new JButton(restartIcon);
        restart.setSize(40,40);
        timer = new JLabel("Time:\n0",SwingConstants.CENTER);
        restart.setActionCommand("New");
        restart.addActionListener(this);
        clicks = new JLabel("Clicks:\n"+String.valueOf(clickCount),SwingConstants.CENTER);
        time = new Timer (1000,this);
        time.setActionCommand("timer");
        clickView = new JPanel();
        clickView.setLayout(new GridLayout(1,3,0,0));
        clickView.add(clicks);
        clickView.add(restart);
        clickView.add(timer);
        menuView.setLayout(new GridLayout(1,1,0,0));
        boardView.setLayout(new GridLayout(8,8,-10,-10));
        c = getContentPane();
        c.add(menuView,BorderLayout.NORTH);
        c.add(clickView,BorderLayout.CENTER);
        c.add(boardView,BorderLayout.AFTER_LAST_LINE);


        setSize(32*width, 50*height);
        setVisible(true);

    }
    public static void main(String args[])
    {
        Bombs B = new Bombs();
        B.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }

        else if (e.getActionCommand().equals("New")) {//needs work use.remove for board
            time.stop();
            gameBoard.reset(height, width, bombs);
            timer.setText("Time:\n0");
            clickCount=0;
            gameTime=0;
            boardView.removeAll();
            gameBoard.fillBoardView(boardView);
            setSize(32*width, 50*height);
            setVisible(true);
            gameBoard.addListener(this);
            gameOver=false;
            clickCount=0;
            restart.setIcon(new ImageIcon(loader.getResource("res/normal.png")));
            clicks.setText("Clicks:\n"+String.valueOf(clickCount));
        }
        else if (e.getActionCommand().equals("Setup")){
            ActionListener AL = this;
            Setup = new setupMenu(height, width, bombs);
            Setup.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    bombs = Setup.getBombs();
                    height = Setup.getH();
                    width = Setup.getW();
                    gameBoard.reset(height, width, bombs);
                    boardView.removeAll();
                    gameBoard.fillBoardView(boardView);
                    gameBoard.addListener(AL);
                    boardView.setLayout(new GridLayout(height,width,-10,-10));
                    setSize(32*width, 50*height);
                    setVisible(true);
                    gameOver = false;
                    gameWon = false;
                    clickCount = 0;
                    gameTime = 0;
                    restart.setIcon(new ImageIcon(loader.getResource("res/normal.png")));
                    clicks.setText("Clicks:\n"+String.valueOf(clickCount));
                }
            });

        }
        else if (e.getActionCommand().equals("timer")){
            gameTime++;
            timer.setText("Time:\n"+gameTime);
        }
        else if (e.getActionCommand().equals("Help")){
            JFrame helpScreen= new JFrame("Help");
            JLabel title = new JLabel("Minesweeper Help", SwingConstants.CENTER);
            title.setFont(new Font("ComicSans", Font.BOLD, 30));
            JLabel description1 = new JLabel("You are given a board of squares,",SwingConstants.CENTER);
            JLabel description2 = new JLabel("you are to find all of the bombs.",SwingConstants.CENTER);
            JLabel desc3 = new JLabel("The number corresponds to the number of", SwingConstants.CENTER);
            JLabel desc4 = new JLabel("bombs around this point. Change game play", SwingConstants.CENTER);
            JLabel desc5 = new JLabel("by selecting the game option.", SwingConstants.CENTER);
            helpScreen.add(title,BorderLayout.NORTH);
            JPanel desc = new JPanel();
            desc.setLayout(new GridLayout(5,1,0,-10));
            desc.add(description1);
            desc.add(description2);
            desc.add(desc3);
            desc.add(desc4);
            desc.add(desc5);
            helpScreen.add(desc, BorderLayout.CENTER);
            helpScreen.setSize(500,200);
            helpScreen.setVisible(true);

        }
        else if (e.getActionCommand().equals("Field Button")) {
            Field clicked = (Field) e.getSource();
            if(clickCount==0){
                time.start();
            }
            if (!gameOver&& !gameBoard.hasBeenVisited(clicked.returnX(),clicked.returnY())) {
                if (clicked.customName().equals("bomb")) {
                    clickCount++;
                    clicks.setText("Clicks:\n" + clickCount);
                    clickCount=0;
                    time.stop();
                    gameBoard.revealBombs();
                    clicked.setRed();
                    gameOver = true;
                    Icon restartIcon =new ImageIcon(loader.getResource("res/lost.png"));
                    restart.setIcon(restartIcon);
                }
                else if (clicked.customName().equals("0")) {
                    gameBoard.isVisited(clicked);
                    gameBoard.revealZeros(clicked);
                    clickCount++;
                    clicks.setText("Clicks:\n"+String.valueOf(clickCount));
                }
                else if (clicked.customName() != "0" && clicked.customName() != "bomb") {
                    gameBoard.isVisited(clicked);
                    clickCount++;
                    clicks.setText("Clicks:\n"+String.valueOf(clickCount));
                }
                gameWon = gameBoard.hasWon(bombs);
                if (gameWon){
                    gameOver=true;
                    Icon restartIcon =new ImageIcon(loader.getResource("res/win.png"));
                    restart.setIcon(restartIcon);
                    gameBoard.revealBombs();
                    time.stop();
                    gameTime=0;

                }
            }
        }
    }
}
