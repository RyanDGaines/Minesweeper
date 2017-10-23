import javax.swing.*;
import java.awt.event.ActionListener;

public class MineMenu {
    private JMenuBar menuOptions;
    private JMenu gameOptions, helpOptions;
    private JMenuItem help,setup,game,exit;

    MineMenu(ActionListener AL){

        menuOptions = new JMenuBar();
        gameOptions = new JMenu("Game");
        helpOptions = new JMenu("Help");
        setup= new JMenuItem("Setup");
        gameOptions.add(setup);
        setup.addActionListener(AL);
        game = new JMenuItem("New");
        gameOptions.add(game);
        game.addActionListener(AL);
        gameOptions.addSeparator();
        exit = new JMenuItem("Exit");
        gameOptions.add(exit);
        exit.addActionListener(AL);

        help = new JMenuItem("Help");
        helpOptions.add(help);
        help.addActionListener(AL);
        menuOptions.add(gameOptions);
        menuOptions.add(helpOptions);
    }

    public void fillView(JPanel menuView) {
        menuView.add(menuOptions);
    }
}
