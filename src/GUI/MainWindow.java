package GUI;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{

    public MainWindow() {
        setTitle("Inventory System");
        setSize(1000,800);

        //Window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        //Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(400,0));
        sidebar.setBackground(new Color(44, 44, 44));


        //Central window
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);

        new Sidebar(sidebar, centerPanel);
        add(sidebar,BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
