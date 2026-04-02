package Action_Buttons;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public abstract class BtnAbstract extends JFrame {
    public BtnAbstract() throws SQLException {
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(300,200);
        setResizable(false);

        actionBtn();

        setVisible(true);
    }

    abstract void actionBtn() throws SQLException;
}
