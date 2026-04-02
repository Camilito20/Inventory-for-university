package Product;

import database.ProductRepository;

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

    public void editProduct(Product p, String name, Integer code, Integer stock, Double price){
        if(p == null) throw new IllegalArgumentException("The product has no data");

        if (name != null)p.setName(name);
        if (code != null)p.setCode(code);
        if (stock != null)p.setStock(stock);
        if (price != null)p.setPrice(price);

    }

    public void sellProduct(int code, int sale) throws SQLException{
        String codeString = String.format("%03d", code);

        Product product = new ProductRepository().show(codeString);

        if (sale > product.getStock()) throw new IllegalArgumentException("The quantity exceeds the available stock");
        int newStock = product.getStock() - sale;

        new ProductRepository().updateProduct(codeString, null, null, newStock, null);

    }

    public void restockProduct(Product p, int restock){
        if(p == null) throw new IllegalArgumentException("The product has no data");

        if (restock < 0) throw new IllegalArgumentException("The restock quantity cannot be negative.");

        p.setStock(p.getStock() + restock);

    }
}
