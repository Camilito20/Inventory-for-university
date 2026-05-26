package database;

import logic.ManagerProduct;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta es la clase que se comunica con la base de datos para poder
 * recolectar, insertar, editar o eliminar los datos que el usuario necesite
 * cambiar según sus necesidades
 */

public class ProductRepository {

    /**
     * Busca en la base de datos según el código del producto
     *
     * @param code Código del producto que se busca
     * @return Product Devuelve el producto encontrado en la base de datos
     */
    public static Product show(String code) throws IllegalArgumentException {
        String sql = "SELECT id, name, code, stock, price FROM products WHERE code = '" + code + "' ORDER BY code";

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

    /**
     * Todos los productos de la base de datos
     *
     * @return ArrayList<Product> Con todos los productos
     */
    public static ArrayList<Product> showAllProducts() throws IllegalArgumentException {
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

            if (product == null)
                return null;//throw new IllegalArgumentException("The product not fount in the database");
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Número de cuantos Productos ahi
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

    /**
     * Insterta los productos en la base de datos con los datos que desea el cliente
     *
     * @param product Nuevo producto que se agregara a la base de datos
     * @throws IllegalArgumentException Mensaje de error por si el producto ya existe
     * @throws SQLException             Por si surge un error insertar en la base de datos
     */
    public static void save(Product product) throws IllegalArgumentException, SQLException {
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
    public static void sellOrRestockProductBs(int id, String type, int numSell) throws SQLException, IllegalArgumentException {
        String sql = "INSERT INTO movements (product_id, type, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setString(2, type);
            statement.setInt(3, numSell);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found in database.");
            }
        }
    }

    /**
     * Busca en la tabla de ingresos y salida, los numero de ingreso y salida de datos
     *
     * @return ArrayList<String [ ]> Devuelve una cadena con los parámetros:
     * type: Indica si el producto entro o salió
     * quantity: Cuanto producto entro o salió
     * product_id: Es id del producto, no es lo mismo que el código
     * @throws SQLException Por si surge un error insertar en la base de datos
     */
    public static ArrayList<String[]> showRestockProduct() throws SQLException {

        String sql = "SELECT product_id, type, quantity FROM movements WHERE date >= CURRENT_DATE AND date <  CURRENT_DATE + INTERVAL '1 day'";

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

    public static ArrayList<String[]> showMovementsProduct() throws SQLException {

        String sql = "SELECT product_id, type, quantity, date FROM movements";

        ArrayList<String[]> movements = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                movements.add(new String[]{
                        rs.getString("type"),
                        String.valueOf(rs.getInt("quantity")),
                        String.valueOf(rs.getInt("product_id")),
                        String.valueOf(rs.getTimestamp("date"))
                });
            }
        }

        return movements;
    }

    /**
     * Elimina el producto de la base de datos
     *
     * @param code Código del producto que se quiere eliminar
     * @throws SQLException             Por si surge un error insertar en la base de datos
     * @throws IllegalArgumentException Lanza un error por si no encuentra el producto
     */
    public void deleteProduct(String code) throws SQLException, IllegalArgumentException {
        String sql = "DELETE FROM products WHERE code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found in database.");
            }
        }
    }

    public static void editProduct(int id, String newName, String newCode, Integer newStock, Double newPrice) throws SQLException, IllegalArgumentException {
        StringBuilder sql = new StringBuilder("UPDATE products SET ");
        List<Object> values = new ArrayList<>();

        if (newName != null) {
            sql.append("name = ?,");
            values.add(newName);
        }
        if (newCode != null) {
            sql.append("code = ?,");
            values.add(newCode);
        }
        if (newStock != null) {
            sql.append("stock = ?,");
            values.add(newStock);
        }
        if (newPrice != null) {
            sql.append("price = ?,");
            values.add(newPrice);
        }

        // Evitar error si no se pasa ningún valor
        if (values.isEmpty()) {
            throw new IllegalArgumentException("There is nothing to change");
        }

        // Quitar la última coma
        sql.setLength(sql.length() - 1);

        sql.append(" WHERE id = ?");
        values.add(id);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < values.size(); i++) {
                stmt.setObject(i + 1, values.get(i));
            }

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found");
            }
        }
    }
}


