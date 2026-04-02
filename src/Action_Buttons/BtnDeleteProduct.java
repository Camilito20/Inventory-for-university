package Action_Buttons;

import Product.ManagerProduct;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class BtnDeleteProduct extends BtnAbstract{
    public BtnDeleteProduct() throws SQLException {
    }

    @Override
    void actionBtn() throws SQLException{
        JLabel lblCode = new JLabel("Code:");
        lblCode.setFont(new Font("Arial",  Font.PLAIN, 18));
        JTextField txtCode = new JTextField();

        JButton btn = new JButton("Delete");
        btn.addActionListener( e -> {

            try {
                int code = Integer.parseInt(txtCode.getText());
                new ManagerProduct().removeProduct(code);

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
}
