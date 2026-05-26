package logic;

import GUI.Panel_Product;
import GUI.StockIn_and_out;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Btns extends JFrame {
    public Btns() throws SQLException {
        setLayout(new GridLayout(0, 1, 10, 10));
        setSize(300,400);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void btnAdd(JPanel centralPanel) throws SQLException{
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtName = new JTextField();
        txtName.setText("Example: Saco");
        functionJTxtFiled(txtName, "Example: Saco");


        JLabel lblCode = new JLabel("Code:");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtCode = new JTextField();
        txtCode.setText("Example: 2");
        functionJTxtFiled(txtCode, "Example: 2");


        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtPrice = new JTextField();
        txtPrice.setText("Example: 2.5");
        functionJTxtFiled(txtPrice, "Example: 2.5");

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtStock = new JTextField();
        txtStock.setText("Example: 30");
        functionJTxtFiled(txtStock, "Example: 30");


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
                new Panel_Product(centralPanel);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Repeated product",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
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

    };

    public void btnDelete(JPanel centralPanel) throws SQLException{
        JLabel lblCode = new JLabel("Code:");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
    JTextField txtCode = new JTextField();

    JButton btn = new JButton("Delete");
        btn.addActionListener( e -> {

        try {
            int code = Integer.parseInt(txtCode.getText());
            new ManagerProduct().removeProduct(code);
            new Panel_Product(centralPanel);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Only numbers are allowed, not words.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;

        }
        dispose();
    });

    add(lblCode);
    add(txtCode);

    add(new JLabel());
    add(btn);
}

    public void btnEdit(JPanel centralPanel) throws SQLException {
        JLabel lblCodeP = new JLabel("Code product");
        JTextField txtCodeP = new JTextField();
        txtCodeP.setText("Code product");
        functionJTxtFiled(txtCodeP, "Code product");

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField txtName = new JTextField();
        txtName.setText("New name");
        functionJTxtFiled(txtName, "New name");


        JLabel lblCode = new JLabel("Code:");
        lblCode.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField txtCode = new JTextField();
        txtCode.setText("New code");
        functionJTxtFiled(txtCode, "New code");


        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField txtPrice = new JTextField();
        txtPrice.setText("New price");
        functionJTxtFiled(txtPrice, "New price");

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField txtStock = new JTextField();
        txtStock.setText("New price");
        functionJTxtFiled(txtStock, "New price");


        JButton btnSave = new JButton("Save");

        btnSave.addActionListener(e -> {
            int codeP;
            String name = txtName.getText();
            int code;
            double price;
            int stock;

            try {
                codeP = Integer.parseInt(txtCodeP.getText());
                code = Integer.parseInt(txtCode.getText());
                price = Double.parseDouble(txtPrice.getText());
                stock = Integer.parseInt(txtStock.getText());

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(
                        null,
                        "Only numbers are allowed, not words.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            Product p = new ManagerProduct().searchProduct(codeP);
            try {
                new ManagerProduct().editProduct(p, name, code, stock, price);
                new Panel_Product(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            dispose();
        });

        add(lblCodeP, BorderLayout.NORTH);
        add(txtCodeP, BorderLayout.NORTH);

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

    public void btnIn(JPanel centralPanel) throws SQLException {
        JLabel lblCode = new JLabel("Code product");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtCode = new JTextField();
        txtCode.setText("Example: 2");

        functionJTxtFiled(txtCode, "Example: 2");

        JLabel lblProductsIn = new JLabel("Products in");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtIn = new JTextField();
        txtIn.setText("Example: 20");

        functionJTxtFiled(txtIn, "Example: 20");

        JButton btnSave = getJButton(txtCode, txtIn, "IN", centralPanel);

        add(lblCode);
        add(txtCode);
        add(lblProductsIn);
        add(txtIn);
        add(btnSave);
    }

    public void btnOut(JPanel centralPanel) throws SQLException {
        JLabel lblCode = new JLabel("Code product");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtCode = new JTextField();
        txtCode.setText("Example: 2");

        functionJTxtFiled(txtCode, "Example: 2");

        JLabel lblProductsOut = new JLabel("Products 0ut");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtOut = new JTextField();
        txtOut.setText("Example: 20");

        functionJTxtFiled(txtOut, "Example: 20");
        JButton btnSave = getJButton(txtCode, txtOut, "OUT", centralPanel);

        add(lblCode);
        add(txtCode);
        add(lblProductsOut);
        add(txtOut);
        add(btnSave);
    }


    private JButton getJButton(JTextField txtCode, JTextField txtInOrOut, String inOrOut, JPanel centralPanel) {
        JButton btnSave = new JButton("Save");

        btnSave.addActionListener(e->{
            int code;
            int productsIn;
            try {
                code = Integer.parseInt(txtCode.getText());
                productsIn = Integer.parseInt(txtInOrOut.getText());
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
                new ManagerProduct().sellOrRestockProduct(code, inOrOut, productsIn);
                new Panel_Product(centralPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            dispose();

        });
        return btnSave;
    }

    private void functionJTxtFiled(JTextField textField, String txt){
        textField.setMaximumSize(new Dimension(200, 20));
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(txt)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(txt);
                }
            }
        });
    }
}
