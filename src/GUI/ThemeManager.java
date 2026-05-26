package GUI;

import java.awt.*;

public class ThemeManager {

    public static boolean darkTheme = true;

    public static Color sidebarColor;
    public static Color centerColor;
    public static Color menuColor;
    public static Color textColor;
    public static Color tableHeaderColor;
    public static Color buttonHoverColor;
    public static Color buttonHoverText;

    public static void applyDarkTheme() {
        darkTheme = true;

        sidebarColor = new Color(15, 23, 42);
        centerColor = new Color(248, 250, 252);
        menuColor = new Color(15, 23, 42);
        textColor = Color.WHITE;
        tableHeaderColor = new Color(43, 60, 87);
        buttonHoverColor = Color.WHITE;
        buttonHoverText = Color.BLACK;
    }

    public static void applyLightTheme() {
        darkTheme = false;

        sidebarColor = new Color(230, 236, 245);
        centerColor = Color.WHITE;
        menuColor = new Color(59, 130, 246);
        textColor = Color.BLACK;
        tableHeaderColor = new Color(59, 130, 246);
        buttonHoverColor = new Color(59, 130, 246);
        buttonHoverText = Color.WHITE;
    }

    public static void switchTheme() {
        if (darkTheme) {
            applyLightTheme();
        } else {
            applyDarkTheme();
        }
    }
}