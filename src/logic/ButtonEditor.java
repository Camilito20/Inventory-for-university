package logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private int row;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);

        button = new JButton("Sell");
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> {

            row = table.getSelectedRow();

            // 🔥 aquí obtienes el producto
            Object code = table.getValueAt(row, 1);

            System.out.println("Eliminar producto con código: " + code);

            // 🔥 aquí puedes llamar a tu repo
            // ProductRepository.delete(...);

        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        return button;
    }
}