package GUI;

import database.ProductRepository;
import logic.Btns;
import logic.ManagerProduct;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Reports extends Panel_abstract{
    public Reports(JPanel centralPanel) throws SQLException {
        super(centralPanel);
    }

    @Override
    JMenuBar menuBar(JPanel centralPanel) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(15, 23, 42));

        JLabel title = new JLabel("  - Reports");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.WHITE);

        //Entrada de productos
        /*
        JMenu reportsOrder = new JMenu("Report order");
        reportsOrder.setForeground(Color.WHITE);
        for (JMenuItem i : getMenuItems()){
            reportsOrder.add(i);
        }

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

         */

        menuBar.add(title);
        menuBar.add(Box.createHorizontalGlue());

        return menuBar;
    }

    private JMenuItem[] getMenuItems(){
        JMenuItem date = new JMenuItem("Date");
        JMenuItem highest_code = new JMenuItem("Highest code");
        JMenuItem lowest_code = new JMenuItem("Lowest code");
        JMenuItem most_frequent = new JMenuItem("Most frequent");
        JMenuItem fewest_frequent = new JMenuItem("Fewest frequent");



        return new JMenuItem[]{
                date,
                highest_code,
                lowest_code,
                most_frequent,
                fewest_frequent
        };
    }

    @Override
    JPanel tableProducts() throws SQLException {
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());

        String[] columns = {"Name", "Code", "IN", "OUT", "Data"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Product> products = ManagerProduct.allProducts();
        ArrayList<String[]> movements = ProductRepository.showMovementsProduct();


        if (products == null || products.isEmpty()) {
            JLabel notProducts = new JLabel("No products found in the database.");
            notProducts.setHorizontalAlignment(SwingConstants.HORIZONTAL);
            panelProducts.add(notProducts, java.awt.BorderLayout.CENTER);
        } else {
            for (Product p : products) {
                int in = 0;
                int out = 0;
                String date = null;
                int idObject = p.getId();

                for (String[] m : movements) {
                    date = m[3];
                    if (Integer.parseInt(m[2]) == idObject) {
                        if (m[0].equalsIgnoreCase("IN")) {
                            in = Integer.parseInt(m[1]);
                        }

                        if (m[0].equalsIgnoreCase("OUT")) {
                            out = Integer.parseInt(m[1]);
                        }
                    }

                    if (in > 0 || out > 0){
                        model.addRow(new Object[]{
                                p.getName(),
                                p.getCode(),
                                in,
                                out,
                                date
                        });
                    }
                }

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
