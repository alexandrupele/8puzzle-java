package UI.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Alexandru Pele on 4/1/2015.
 */
public class NavigationBar extends JMenuBar {

    private void configureMenu() {
        // File
        JMenu fileMenu = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        add(fileMenu);

        // Help
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String about = "(C) Alexandru Pele, 2015" +
                        System.getProperty("line.separator") + "Contact: pelealexandru@gmail.com";
                JOptionPane.showMessageDialog(null, about, "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutItem);
        add(helpMenu);
    }

    public NavigationBar() {
        super();
        configureMenu();
    }
}
