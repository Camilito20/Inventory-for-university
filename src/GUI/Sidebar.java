package GUI;

import javax.swing.*;
import java.awt.*;

public class Sidebar {

    public Sidebar(JPanel sidebar, JPanel centralPanel){
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Welcome to \nInventory System");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(Box.createVerticalStrut(40));
        sidebar.add(label);
        sidebar.add(Box.createVerticalStrut(40));

        for(JButton button: buttons(centralPanel)){
            button.setBackground(new Color(44, 44, 44));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 24));

            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            //Cambia de color al pasar el mouse por encima
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(255, 255, 255));
                    button.setForeground(Color.BLACK);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(44, 44, 44));
                    button.setForeground(Color.WHITE);
                }
            });

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(Box.createVerticalStrut(15));
            sidebar.add(button);
        }
    }

    private JButton[] buttons(JPanel centralPanel){
        JButton btnProducts = new JButton("\uD83C\uDF8E Products");
        JButton btnInput_and_output = new JButton("\uD83D\uDCD3 Product input and output");
        JButton btnPurchasing_and_sales = new JButton("\uD83D\uDD16 Product purchasing and sales");
        JButton btnReports = new JButton("\uD83D\uDCC8 Reports");
        JButton btnEmployees = new JButton("\uD83D\uDC65 Employees");
        JButton btnSettings = new JButton("\uFE0F\u200B Settings");


        return new JButton[]{
                btnProducts,
                btnInput_and_output,
                btnPurchasing_and_sales,
                btnReports,
                btnEmployees,
                btnSettings
        };
    }
}
