package GUI;

import logic.Btns;
import model.Product;
import database.ProductRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Panel_Product extends Panel_abstract{

    public Panel_Product(JPanel centralPanel) throws SQLException {
        super(centralPanel);
    }

    @Override
    protected JMenuBar menuBar(JPanel centralPanel){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(15, 23, 42));

        JLabel title = new JLabel("  - Products");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.WHITE);

        JMenu addProduct = new JMenu(" Add ");
        JMenuItem add = new JMenuItem("Add product");
        addProduct.add(add);

        JMenu deleteProduct = new JMenu(" Delete ");
        JMenuItem delete = new JMenuItem("Delete product");
        deleteProduct.add(delete);

        JMenu editProduct = new JMenu(" Edit ");
        JMenuItem edit = new JMenuItem("Edit product");
        editProduct.add(edit);

        addProduct.setForeground(Color.WHITE);
        deleteProduct.setForeground(Color.WHITE);
        editProduct.setForeground(Color.WHITE);

        addProduct.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteProduct.setFont(new Font("Arial", Font.PLAIN, 18));
        editProduct.setFont(new Font("Arial", Font.PLAIN, 18));

        //Action MenuItems
        add.addActionListener( e -> {
            try {
                new Btns().btnAdd(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        delete.addActionListener(e -> {
            try {
                new Btns().btnDelete(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        edit.addActionListener( e -> {
            try {
                new Btns().btnEdit(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuBar.add(title);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(addProduct);
        menuBar.add(deleteProduct);
        menuBar.add(editProduct);

        return menuBar;
    }

    @Override
    protected JPanel tableProducts(){
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());

        String[] columns = {"Name", "Code", "Stock", "Price"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Product> products = ProductRepository.showAllProducts();

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
            tableProducts.getTableHeader().setBackground(titlesTable);
            tableProducts.getTableHeader().setForeground(Color.WHITE);
            tableProducts.getTableHeader().setFont(textTable);
            //Celdas normales
            tableProducts.setRowHeight(rowHeight);
            tableProducts.setFont(textTable);
            tableProducts.setSelectionBackground(new Color(254, 254, 254));

            JScrollPane scrollBar = new JScrollPane(tableProducts);
            panelProducts.add(searchBar(tableProducts, model), BorderLayout.NORTH);
            panelProducts.add(scrollBar, BorderLayout.CENTER);

        }
        panelProducts.setVisible(true);
        return panelProducts;
    }
}
