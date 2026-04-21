package GUI;

import logic.Btns;
import logic.ManagerProduct;
import model.*;
import database.ProductRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta es la clase que genera el panel al apretar el botton Stock in and out
 * con todas las funciones que tiene el panel
 */
public class StockIn_and_out extends Panel_abstract {
    public StockIn_and_out(JPanel centralPanel) throws SQLException {
        super(centralPanel);
    }

    @Override
    protected JMenuBar menuBar(JPanel centralPanel) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(34, 34, 128));

        JLabel title = new JLabel("  - Stock in and out");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.WHITE);

        //Entrada de productos
        JMenu in = new JMenu("In products");
        in.setForeground(Color.WHITE);
        JMenuItem inProduct = new JMenuItem("In");
        inProduct.addActionListener(e -> {
            try {
                new Btns().btnIn(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        in.add(inProduct);

        //Salida de productos
        JMenu out = new JMenu("Out products");
        out.setForeground(Color.WHITE);
        JMenuItem outProduct = new JMenuItem("Out");
        outProduct.addActionListener(e -> {
            try {
                new Btns().btnOut(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        out.add(outProduct);

        menuBar.add(title);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(in);
        menuBar.add(out);

        return menuBar;
    }

    @Override
    protected JPanel tableProducts() throws SQLException {
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());

        String[] columns = {"Name", "Code", "Stock", "Price", "In", "OUT"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Product> products = ManagerProduct.allProducts();
        ArrayList<String[]> movements = ProductRepository.showRestockProduct();


        if (products == null || products.isEmpty()) {
            JLabel notProducts = new JLabel("No products found in the database.");
            notProducts.setHorizontalAlignment(SwingConstants.HORIZONTAL);
            panelProducts.add(notProducts, java.awt.BorderLayout.CENTER);
        } else {
            for (Product p : products) {
                int in = 0;
                int out = 0;
                int idObject = p.getId();
                System.out.println(idObject);
                for (String[] m : movements) {
                    if (Integer.parseInt(m[2]) == idObject) {
                        if (m[0].equalsIgnoreCase("IN")) {
                            in += Integer.parseInt(m[1]);
                        }

                        if (m[0].equalsIgnoreCase("OUT")) {
                            out += Integer.parseInt(m[1]);
                        }
                    }
                }

                    model.addRow(new Object[]{
                            p.getName(),
                            p.getCode(),
                            p.getStock(),
                            p.getPrice(),
                            in,
                            out
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
                panelProducts.add(searchBar(tableProducts, model), BorderLayout.NORTH);
                panelProducts.add(scrollBar, BorderLayout.CENTER);
            }
        panelProducts.setVisible(true);
        return panelProducts;
    }
}
