

import GUI.MainWindow;
import Product.ManagerProduct;
import Product.ManagerProduct.*;
import Product.Product;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ManagerProduct mp = new ManagerProduct();
    public static void main(String[] args) {
        MainWindow window = new MainWindow();

        while (true){
            try {
                System.out.println("1. Add product");
                System.out.println("2. Remove product");
                System.out.println("3. Edit product");
                System.out.println("4. Sell product");
                System.out.println("5. Restock product");
                System.out.println("6. Show product");
                System.out.println("7. Exit");
                System.out.print("Choos an option: ");
                int option = sc.nextInt();
                sc.nextLine();

                if (option == 7) break;

                switch (option) {
                    case 1 -> addProduct();
                    case 2 -> System.out.println(removeProduct());
                    case 3 -> editProduct();
                    case 4 -> sellProduct();
                    case 5 -> restockProduct();
                    case 6 -> {
                        System.out.print("Product code to show: ");
                        int codeProduct = sc.nextInt();
                        sc.nextLine();
                        Product product = mp.searchProduct(codeProduct);
                        System.out.println(product.toString());
                    }
                    default -> System.out.println("Select one of the available options");
                }
            } catch (InputMismatchException e){
                System.out.println("Only numeric values are allowed");
                sc.nextLine();

            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    public static void addProduct()throws IllegalArgumentException, InputMismatchException{
        Product product;
        System.out.print("Name product: ");
        String name = sc.nextLine();

        System.out.print("Code product: ");
        int code = sc.nextInt();
        sc.nextLine();

        System.out.print("Initial stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        System.out.print("Price product: ");
        double price = sc.nextDouble();
        sc.nextLine();
        mp.addProduct(name, code, stock, price);
    }
    public static String removeProduct() throws IllegalArgumentException, InputMismatchException{
        System.out.print("Product code to remove: ");
        int code = sc.nextInt();
        sc.nextLine();

        return mp.removeProduct(code);

    }
    public static void editProduct() throws IllegalArgumentException, InputMismatchException{
        System.out.print("Product code to edit: ");
        int codeProduct = sc.nextInt();
        sc.nextLine();
        Product product = mp.searchProduct(codeProduct);
        while (true){
            System.out.println("---------" + product.getName() + "---------");
            System.out.println("What do you want to change?");
            System.out.println("1. Name\n2. Code \n3. price\n4. Exit");
            System.out.print("Choos an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option){
                case 1:
                    System.out.print("New name: ");
                    String name = sc.nextLine();
                    mp.editProduct(product, name, null, null, null);
                    break;
                case 2:
                    System.out.print("New code");
                    int code = sc.nextInt();
                    mp.editProduct(product, null, code, null, null);
                    break;
                case 3:
                    System.out.print("New price: ");
                    double price = sc.nextDouble();
                    mp.editProduct(product, null, null, null, price);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Select one of the available options");
                    break;
            }
        }
    }
    public static void sellProduct() throws IllegalArgumentException, InputMismatchException{
        System.out.print("Product code to edit: ");
        int codeProduct = sc.nextInt();
        sc.nextLine();
        Product product = mp.searchProduct(codeProduct);

        System.out.print("Number of products sold: ");
        int sale = sc.nextInt();
        sc.nextLine();

        mp.sellProduct(product, sale);
        System.out.println(sale + " " + product.getName() + " have been sold," + product.getStock() + " products remain.");
    }
    public static void restockProduct() throws IllegalArgumentException, InputMismatchException{
        System.out.print("Product code to edit: ");
        int codeProduct = sc.nextInt();
        sc.nextLine();
        Product product = mp.searchProduct(codeProduct);

        System.out.print("Number of products restocked: ");
        int restock = sc.nextInt();
        sc.nextLine();

        mp.restockProduct(product, restock);
        System.out.println(restock + " " + product.getName() + " were restocked," + product.getStock() + " products remain.");
    }
}