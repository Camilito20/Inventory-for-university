package database;

import java.util.Properties;
import java.io.InputStream;
import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Conexión exitosa a PostgreSQL");
        } catch (Exception e) {
            System.out.println("Error al conectar");
            e.printStackTrace();
        }
    }
}
