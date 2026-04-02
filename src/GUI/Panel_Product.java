package GUI;

import Action_Buttons.BtnAddProduct;
import Action_Buttons.BtnDeleteProduct;
import Product.Product;
import database.ProductRepository;
import database.ProductRepository.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Panel_Product {
    public Panel_Product(JPanel centralPanel) throws SQLException {
        centralPanel.setLayout(new BorderLayout());

        //Title
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(34, 34, 128));
        menuBar.setFont(new Font("Arial", Font.PLAIN, 30));

        JLabel title = new JLabel("  - Products");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.WHITE);

        JMenu addProduct = new JMenu(" Add ");
        JMenuItem add = new JMenuItem("Add product");
        addProduct.add(add);

        JMenu deleteProduct = new JMenu(" Delete ");
        JMenuItem delete = new JMenuItem("Delete product");
        deleteProduct.add(delete);

        addProduct.setForeground(Color.WHITE);
        deleteProduct.setForeground(Color.WHITE);

        addProduct.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteProduct.setFont(new Font("Arial", Font.PLAIN, 18));

        //Action MenuItems
        add.addActionListener( e -> {
            try {
                new BtnAddProduct();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        delete.addActionListener(e -> {
            try {
                new BtnDeleteProduct();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuBar.add(title);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(addProduct);
        menuBar.add(deleteProduct);


        //Show products
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());

        String[] columns = {"Name", "Code", "Stock", "Price"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Product> products = ProductRepository.show();

        if (products == null || products.isEmpty()){
            JLabel notProducts = new JLabel("No products found in the database.");

            panelProducts.add(notProducts, java.awt.BorderLayout.CENTER);
        } else {
            for (Product p : products) {
                model.addRow(new Object[]{
                        p.getName(),
                        p.getCode(),
                        p.getStock(),
                        p.getPrice()
                });
            }

            JTable tableProducts = new JTable(model);
            //Titulos de las columnas
            tableProducts.getTableHeader().setBackground(new Color(34, 34, 128));
            tableProducts.getTableHeader().setForeground(Color.WHITE);
            tableProducts.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
            //Celdas normales
            tableProducts.setRowHeight(35);
            tableProducts.setFont(new Font("Arial", Font.PLAIN, 20));
            tableProducts.setSelectionBackground(new Color(254, 254, 254));

            JScrollPane scrollBar = new JScrollPane(tableProducts);
            panelProducts.add(scrollBar, BorderLayout.CENTER);

        }


        centralPanel.add(menuBar, BorderLayout.NORTH);
        centralPanel.add(panelProducts, BorderLayout.CENTER);
    }
}
