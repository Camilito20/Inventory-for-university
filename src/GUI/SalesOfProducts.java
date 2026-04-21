package GUI;

import database.ProductRepository;
import logic.Btns;
import logic.ButtonEditor;
import logic.ButtonRenderer;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesOfProducts extends Panel_abstract{
    public SalesOfProducts(JPanel centralPanel) throws SQLException {
        super(centralPanel);
    }

    @Override
    JMenuBar menuBar(JPanel centralPanel) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(34, 34, 128));

        JLabel title = new JLabel("  - Seles of products");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.WHITE);

        menuBar.add(title);

        return menuBar;
    }

    @Override
    JPanel tableProducts() throws SQLException {
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());

        String[] columns = {"Name", "Code", "Stock", "Price", "Sell"};

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
                        p.getPrice(),
                        "Sell"
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
            //Crea el boton en la tabla
            tableProducts.getColumn("Sell").setCellRenderer(new ButtonRenderer());
            tableProducts.getColumn("Sell").setCellEditor(new ButtonEditor(new JCheckBox(), tableProducts));



            JScrollPane scrollBar = new JScrollPane(tableProducts);
            panelProducts.add(searchBar(tableProducts, model), BorderLayout.NORTH);
            panelProducts.add(scrollBar, BorderLayout.CENTER);

        }
        panelProducts.setVisible(true);
        return panelProducts;
    }
}
