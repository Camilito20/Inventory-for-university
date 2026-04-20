package GUI;

import javax.swing.*;
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
}
