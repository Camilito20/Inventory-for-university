//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Product.ManagerProduct;
import Product.ManagerProduct.*;
import Product.Product;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello word!!!");
        Product product = new Product("Perro", 11, 49, 1.5);

        ManagerProduct managerProduct = new ManagerProduct();

        /*
        try {

            managerProduct.addProduct(product);
        } catch (IllegalAccessException e){
            System.out.println("Error: " + e.getMessage());
        }
         */
        managerProduct.showAllProducts();
    }
}