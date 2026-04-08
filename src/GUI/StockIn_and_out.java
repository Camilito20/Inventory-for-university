package GUI;

import Action_Buttons.BtnAddProduct;
import Action_Buttons.BtnDeleteProduct;
import Product.Product;
import database.ProductRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockIn_and_out {
    public StockIn_and_out(JPanel centralPanel) throws SQLException {
        centralPanel.setLayout(new BorderLayout());


        centralPanel.add(menuBar(), BorderLayout.NORTH);
        centralPanel.add(tableProducts(), BorderLayout.CENTER);
    }

    protected JMenuBar menuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(34, 34, 128));

        JLabel title = new JLabel("  - Stock in and out");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.WHITE);


        menuBar.add(title);

        return menuBar;
    }

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

        ArrayList<Product> products = ProductRepository.show();
        ArrayList<String[]> movements = new ArrayList<>();
        int in = 0;
        int out = 0;

        if (products == null || products.isEmpty()){
            JLabel notProducts = new JLabel("No products found in the database.");

            panelProducts.add(notProducts, java.awt.BorderLayout.CENTER);
        } else {
            for (Product p : products) {
                movements = ProductRepository.showRestockProduct();

                for (String[] m : movements) {
                    System.out.println(m[1]);

                    if (p.getId() == Integer.parseInt(m[3])) {
                        if (m[1].equalsIgnoreCase("IN")) in = Integer.parseInt(m[2]);
                        else if (m[1].equalsIgnoreCase("OUT"))
                            out = Integer.parseInt(m[2]);
                        else {
                            in = 0;
                            out = 0;
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
            panelProducts.add(buttons(tableProducts), BorderLayout.SOUTH);
            panelProducts.add(scrollBar, BorderLayout.CENTER);
        }
        panelProducts.setVisible(true);
        return panelProducts;
    }

    private JPanel buttons(JTable tableProducts) {
        ArrayList<Product> products = ProductRepository.show();

        JButton btnIn = new JButton("IN");
        JButton btnOut = new JButton("OUT");

// Panel para botones
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnIn);
        panelButtons.add(btnOut);

        btnOut.addActionListener(e -> {
            int selectedRow = tableProducts.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Select a product first");
                return;
            }

            int id = products.get(selectedRow).getId();
            int currentStock = products.get(selectedRow).getStock();

            String input = JOptionPane.showInputDialog("Enter quantity to sell:");

            try {
                int quantity = Integer.parseInt(input);

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Quantity must be greater than 0");
                    return;
                }

                if (quantity > currentStock) {
                    JOptionPane.showMessageDialog(null, "Not enough stock");
                    return;
                }

                ProductRepository.sellOrRestockProduct(id, "OUT", quantity);

                JOptionPane.showMessageDialog(null, "Product sold successfully");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });
        panelButtons.setVisible(true);
        return panelButtons;
    }

}
