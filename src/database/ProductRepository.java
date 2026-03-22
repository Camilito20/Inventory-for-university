package database;

import Product.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository {

    public Product show(String id) throws IllegalArgumentException{
        String sql = "SELECT name, code, stock, price FROM products WHERE code = '" + id + "' ORDER BY code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            Product product = null;
            while (rs.next()) {
                product = new Product(
                        rs.getString("name"),
                        Integer.parseInt(rs.getString("code")),
                        rs.getInt("stock"),
                        rs.getDouble("price")
                );

            }

            if (product == null) throw new IllegalArgumentException("The product not fount in the database");
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void save (Product product){
        String sql = "INSERT INTO products (code, name, stock, price) VALUES (?,?,?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ManagerProduct managerProduct = new ManagerProduct();

            //Remplaza los "?", por valores para guardar en la base de datos
            statement.setString(1, product.getCode());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getStock());
            statement.setDouble(4, product.getPrice());

            //Sube los cambios a la base de datos
            statement.executeUpdate();

        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
}

