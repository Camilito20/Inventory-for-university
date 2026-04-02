package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

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
            button.setBackground(new Color(0, 38, 165));
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
                    button.setBackground(new Color(0, 38, 165));
                    button.setForeground(Color.WHITE);
                }
            });

            button.addActionListener( e-> {
                        centralPanel.removeAll();
                        centralPanel.revalidate();
                        centralPanel.repaint();
                    }
            );

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(Box.createVerticalStrut(15));
            sidebar.add(button);
        }
    }

    private JButton[] buttons(JPanel centralPanel){
        JButton btnProducts = new JButton("Products");
        JButton btnInput_and_output = new JButton("Stock in and out");
        JButton btnPurchasing_and_sales = new JButton("Sales of products");
        JButton btnReports = new JButton("Reports");
        JButton btnEmployees = new JButton("Employees");
        JButton btnSettings = new JButton("Settings");

        btnProducts.addActionListener(e -> {
            try {
                new Panel_Product(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

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
