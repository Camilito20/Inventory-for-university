package database;

import Product.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static Product show(String id) throws IllegalArgumentException{
        String sql = "SELECT id, name, code, stock, price FROM products WHERE code = '" + id + "' ORDER BY code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            Product product = null;
            while (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
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

    public static ArrayList<Product> show() throws IllegalArgumentException{
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT id, name, code, stock, price FROM products";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            Product product = null;
            while (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        Integer.parseInt(rs.getString("code")),
                        rs.getInt("stock"),
                        rs.getDouble("price")
                );
                products.add(product);
            }

            if (product == null) return null;//throw new IllegalArgumentException("The product not fount in the database");
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int numVariable() throws SQLException {
        String sql = """
                SELECT COUNT(*)
                FROM information_schema.columns
                WHERE table_name = 'products';
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (!rs.next()) throw new IllegalArgumentException("No tiene culumnas");

            return rs.getInt(1);
        }
    }

    public static void save (Product product) throws IllegalArgumentException, SQLException{
        String sql = "INSERT INTO products (code, name, stock, price) VALUES (?,?,?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ) {
            ManagerProduct managerProduct = new ManagerProduct();

            //Remplaza los "?", por valores para guardar en la base de datos
            statement.setString(1, product.getCode());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getStock());
            statement.setDouble(4, product.getPrice());

            //Sube los cambios a la base de datos
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found in database.");
            }
        }
    }

    //Inserta el ingreso o salida de productos
    public static void sellOrRestockProduct(int id, String type, int numSell) throws SQLException, IllegalArgumentException{
        String sql = "INSERT INTO movements (product_id, type, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);
            statement.setString(2, type);
            statement.setInt(3, numSell);

            statement.executeUpdate();
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found in database.");
            }
        }
    }

    //Muestra la entrada y la salida de los productos
    public static ArrayList<String[]> showRestockProduct() throws SQLException {

        String sql = "SELECT product_id, type, quantity FROM movements";

        ArrayList<String[]> movements = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                movements.add(new String[]{
                        rs.getString("type"),
                        String.valueOf(rs.getInt("quantity")),
                        String.valueOf(rs.getInt("product_id"))
                });
            }
        }

        return movements;
    }

    public void deleteProduct(String code) throws SQLException, IllegalArgumentException {
        String sql = "DELETE FROM products WHERE code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, code);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found in database.");
            }
        }
    }

    public void updateProduct(String validationCode, String newName, String newCode, Integer newStock, Double newPrice)
    throws SQLException{
        ArrayList<String> record = new ArrayList<>();

        if (newName != null) record.add("name = ?");
        if (newCode != null) record.add("code = ?");
        if (newStock != null) record.add("stock = ?");
        if (newPrice != null) record.add("price = ?");

        String sql = "Update products SET " +  String.join("," , record) + " WHERE code = ?" ;

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int index = 1;

            if (newName != null) statement.setString(index++, newName);
            if (newCode != null) statement.setString(index++, newCode);
            if (newStock != null) statement.setInt(index++, newStock);
            if (newPrice != null) statement.setDouble(index++, newStock);

            statement.setString(index, validationCode);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) throw new IllegalArgumentException("Product not found in database.");
        }
    }
}

