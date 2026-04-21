package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;

/**
 * Clase abstracta para todos los paneles y barras superiores que se tendra
 * por cada clase que se usa para mostrar los productos del programa
 */
abstract public class Panel_abstract {
    public Panel_abstract(JPanel centralPanel) throws SQLException {
        centralPanel.setLayout(new BorderLayout());

        centralPanel.add(menuBar(centralPanel), BorderLayout.NORTH);
        centralPanel.add(tableProducts(), BorderLayout.CENTER);
    }

    /**
     * Menu superiror con el titulo del panel y los botones que tiene
     * @return un JMenuBar para agregarlo al panel central y se pueda ver en la pantalla
     */
    abstract JMenuBar menuBar(JPanel centralPanel);

    /**
     *  Tabla con todos los productos y los diferentes datos que tendra la tabla
     * @return el panel de la tabla de productos y los demás información que irá en este panel
     */
    abstract JPanel tableProducts() throws SQLException;

    /**
     * Barra de busqueda para todos los paneles
     * @param tableProducts La tabla donde se encuentran todos los productos que queremos buscar
     * @param model Las columnas de los productos que hay
     * @return Panel para agregarlo a panel central o al panel necesario
     */
    protected JPanel searchBar(JTable tableProducts, DefaultTableModel model) {
        JTextField txtSearch = new JTextField(20);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableProducts.setRowSorter(sorter);

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            private void filter() {
                String text = txtSearch.getText();

                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));
                }
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search by name: "));
        topPanel.add(txtSearch);

        return topPanel;
    }
}
