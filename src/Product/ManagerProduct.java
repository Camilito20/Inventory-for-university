package Product;

import database.ProductRepository;

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

    public void addProduct(String name, int code, int stock, double price) throws IllegalArgumentException{
        if (name == null||name.isBlank()) throw new IllegalArgumentException("The product name cannot be blank.");

        for (Product p: products) if (Integer.parseInt(p.getCode()) == code) throw new IllegalArgumentException("The product code already exists in the system.");
        if (stock < 0) throw new IllegalArgumentException("The product stock cannot be negative");
        if (price < 0.0) throw new IllegalArgumentException("The product price cannot be negative");

        Product p = new Product(name, code, stock, price);
        new ProductRepository().save(p);

        products.add(p);
    }

    public String removeProduct(int code) {
        boolean removed = products.removeIf(prod -> Integer.parseInt(prod.getCode()) == code);
        if (!removed) throw new IllegalArgumentException("Product not found in the system");
        return "The product has been removed.";
    }

    public void editProduct(Product p, String name, Integer code, Integer stock, Double price){
        if(p == null) throw new IllegalArgumentException("The product has no data");

        if (name != null)p.setName(name);
        if (code != null)p.setCode(code);
        if (stock != null)p.setStock(stock);
        if (price != null)p.setPrice(price);

    }

    public void sellProduct(Product p, int sale){
        if(p == null) throw new IllegalArgumentException("The product has no data");

        if (sale > p.getStock()) throw new IllegalArgumentException("The quantity exceeds the available stock");

        p.setStock(p.getStock() - sale);

    }

    public void restockProduct(Product p, int restock){
        if(p == null) throw new IllegalArgumentException("The product has no data");

        if (restock < 0) throw new IllegalArgumentException("The restock quantity cannot be negative.");

        p.setStock(p.getStock() + restock);

    }
}
