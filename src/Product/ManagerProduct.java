package Product;

import database.ProductRepository;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ManagerProduct {

    ArrayList<Product> products = new ArrayList<>();

    public ManagerProduct(){};


    public Product searchProduct(int code) throws IllegalArgumentException{
        String codeString = String.format("%03d", code);

        return new ProductRepository().show(codeString);
    }

    private Product searchProduct(Product product){
        for (Product p: products) {
            if (Objects.equals(p.getCode(), product.getCode())) {
                return p;
            }
        }
        throw new IllegalArgumentException("The product was not found.");
    }

    public void addProduct(String name, int code, int stock, double price) throws IllegalArgumentException, SQLException{
        if (name == null||name.isBlank()) throw new IllegalArgumentException("The product name cannot be blank.");

        for (Product p: products) if (Integer.parseInt(p.getCode()) == code) throw new IllegalArgumentException("The product code already exists in the system.");
        if (stock < 0) throw new IllegalArgumentException("The product stock cannot be negative");
        if (price < 0.0) throw new IllegalArgumentException("The product price cannot be negative");

        Product p = new Product(name, code, stock, price);
        ProductRepository.save(p);

        products.add(p);
    }

    public void removeProduct(int code) throws IllegalArgumentException, SQLException {
        String codeString = String.format("%03d", code);

        new ProductRepository().deleteProduct(codeString);
    }

    public void editProduct(Product p, String name, Integer code, Integer stock, Double price) throws SQLException {
        if(p == null) throw new IllegalArgumentException("The product has no data");

        String newName = null;
        String newCode = null;
        Integer newStock = null;
        Double newPrice = null;
        if (name != null) newName = name;
        if (code != null) newCode = String.format("%03d", code);
        if (stock != null) newStock = stock;
        if (price != null) newPrice = price;

        new ProductRepository().updateProduct(p.getCode(), newName, newCode, newStock, newPrice);

    }

    public void sellOrRestockProduct(int code, String type, int numIn_Out) throws SQLException{
        String codeString = String.format("%03d", code);
        if (type.equalsIgnoreCase("OUT") || type.equalsIgnoreCase("IN")) throw new IllegalArgumentException("Only sell or restock");

        Product product = new ProductRepository().show(codeString);

        if (numIn_Out > product.getStock()) throw new IllegalArgumentException("The quantity exceeds the available stock");
        int newStock = product.getStock() - numIn_Out;

        new ProductRepository().sellOrRestockProduct(product.getId(), type.toUpperCase(), numIn_Out);
        new ProductRepository().updateProduct(codeString, null, null, newStock, null);

    }
}
