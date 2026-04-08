package Product;

/**
 * Represents a product in the inventory system.
 * The code acts as a unique identifier.
 * Stock and price cannot be negative.
 */

public class Product {
    private String name;
    private int id;
    private int code;
    private int stock;
    private double price;

    public Product(int id, String name, int code, int stock, double price) {
        setName(name);
        setCode(code);
        setStock(stock);
        setPrice(price);
    }

    public Product(String name, int code, int stock, double price) {
        setName(name);
        setCode(code);
        setStock(stock);
        setPrice(price);
    }

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("No name was written, write the product name");
        this.name = name;
    }

    public void setCode(int code) {
        if (code < 0) throw new IllegalArgumentException("The code cannot be less than 0");
        this.code = code;
    }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("The stock cannot be less than 0");
        this.stock = stock;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("The price cannot be less than 0.00");
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return String.format("%03d", code);
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {return id;}

    @Override
    public String toString(){
        return "Name: " + getName() + "\n"
                + "Code: " + getCode() + "\n"
                + "Stock: " + getStock() + "\n"
                + "Price: " + getPrice() + "\n\n";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof Product product)) return false;
        return this.code == product.code;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(code);
    }
}

