package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Es la clase principal de la interfaz gráfica y llama a todas
 * las demás clases para poder generar una interfaz funcional
 */
public class MainWindow extends JFrame {

    private JPanel sidebar;
    private JPanel centerPanel;

    public MainWindow() {
        ThemeManager.applyDarkTheme();

        setTitle("Inventory System");
        setSize(1000,800);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(400,0));
        sidebar.setBackground(ThemeManager.sidebarColor);

        centerPanel = new JPanel();
        centerPanel.setBackground(ThemeManager.centerColor);

        new Sidebar(sidebar, centerPanel, this);

        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void reloadTheme() {
        getContentPane().removeAll();

        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(400,0));
        sidebar.setBackground(ThemeManager.sidebarColor);

        centerPanel = new JPanel();
        centerPanel.setBackground(ThemeManager.centerColor);

        new Sidebar(sidebar, centerPanel, this);

        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}