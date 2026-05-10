package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * En esta clase se crea la barra lateral de la aplicacion
 * con los botones respectivos y sus funciones, que llaman a cada
 * clase que usan para cada botone
 */
public class Sidebar {
    public Sidebar(JPanel sidebar, JPanel centralPanel){
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Inventory System", new ImageIcon("src/images/inventario.png"), SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setIconTextGap(10);
        label.setHorizontalTextPosition(SwingConstants.RIGHT);

        sidebar.add(Box.createVerticalStrut(40));
        sidebar.add(label);
        sidebar.add(Box.createVerticalStrut(40));


        for(JButton button: buttons(centralPanel)){
            button.setBackground(new Color(15, 23, 42));
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
                    button.setBackground(new Color(15, 23, 42));
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
        //Button products
        ImageIcon imgProducts = new ImageIcon("src/images/calendario_blanco.png");
        Image scaledImage = imgProducts.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon iconProducts = new ImageIcon(scaledImage);

        JButton btnProducts = new JButton("Products", iconProducts);

        //Button stock in and out
        ImageIcon imgStock_in_and_out = new ImageIcon("src/images/in_and_out.png");
        Image scaledImageStock_in_and_out = imgStock_in_and_out.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon iconStock_in_and_out = new ImageIcon(scaledImageStock_in_and_out);

        JButton btnStock_in_and_out = new JButton("Stock in and out", iconStock_in_and_out);

        //Button sales of products
        ImageIcon imgPurchasing_and_sales = new ImageIcon("src/images/venta.png");
        Image scaledImagePurchasing_and_sales = imgPurchasing_and_sales.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon iconPurchasing_and_sales = new ImageIcon(scaledImagePurchasing_and_sales);

        JButton btnPurchasing_and_sales = new JButton("Sales of products", iconPurchasing_and_sales);

        //button reports
        ImageIcon imgReports = new ImageIcon("src/images/calendario_blanco.png");
        Image scaledImageReports = imgReports.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon iconReports = new ImageIcon(scaledImageReports);

        JButton btnReports = new JButton("Reports", iconReports);
        //JButton btnEmployees = new JButton("Employees");
        //JButton btnSettings = new JButton("Settings");

        //Action buttons
        btnProducts.addActionListener(e -> {
            try {
                new Panel_Product(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnStock_in_and_out.addActionListener(e ->{
            try {
                new StockIn_and_out(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        btnPurchasing_and_sales.addActionListener(e ->{
            try {
                new SalesOfProducts(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        btnReports.addActionListener( e -> {
            try {
                new Reports(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        /*
        btnEmployees.addActionListener( e -> {

            try {
                new Employees(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSettings.addActionListener( e -> {
            try {
                new Settings(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

         */

        return new JButton[]{
                btnProducts,
                btnStock_in_and_out,
                btnPurchasing_and_sales,
                btnReports
                //btnEmployees,
                //btnSettings
        };
    }
}
