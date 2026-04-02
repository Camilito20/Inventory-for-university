package Action_Buttons;

import Product.*;
import database.ProductRepository;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class BtnAddProduct extends BtnAbstract{

    public BtnAddProduct() throws SQLException {
    }

    @Override
    void actionBtn() throws SQLException{
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtName = new JTextField();

        JLabel lblCode = new JLabel("Code:");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtCode = new JTextField();

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtPrice = new JTextField();

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtStock = new JTextField();

        JButton btnSave = new JButton("Save");

        btnSave.addActionListener(e -> {
            String name = txtName.getText();
            int code;
            double price;
            int stock;

            try {
                code = Integer.parseInt(txtCode.getText());
                price = Double.parseDouble(txtPrice.getText());
                stock = Integer.parseInt(txtStock.getText());

            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(
                        null,
                        "Only numbers are allowed, not words.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                new ManagerProduct().addProduct(name, code, stock, price);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            dispose();
        });

        add(lblName);
        add(txtName);

        add(lblCode);
        add(txtCode);

        add(lblPrice);
        add(txtPrice);

        add(lblStock);
        add(txtStock);

        add(new JLabel());
        add(btnSave);
    }
}
