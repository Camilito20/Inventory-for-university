package logic;

import database.ProductRepository;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta es la clase que conecta la clase de base de datos con las clases que manejan la
 * interfaz gráfica de los productos.
 */

public class ManagerProduct {

    ArrayList<Product> products = new ArrayList<>();

    public ManagerProduct(){};

    /**
     * Pied a la ProductRepository que busque en la base de datos el producto que el usuario desea
     * según el código que se le da
     * @param code Código con el que se buscara el producto
     * @return Devuelve un Producto
     * @throws IllegalArgumentException Pasa la information de sí no se encontró el producto en la base de datos
     */
    public Product searchProduct(int code) throws IllegalArgumentException{
        String codeString = String.format("%03d", code);

        return ProductRepository.show(codeString);
    }

    /**
     *Envia la información de cada uno de los datos validando si cumple con los
     * parámetros que debe tener para poder a la base de datos y no genere un error
     * a la ProductRepository para que ingrese los datos a la base de datos
     *
     * @param name Nombre del producto a agregar
     * @param code Código del producto que se va a agregar
     * @param stock Stock inicial con el que se va a iniciar el producto
     * @param price Precio con el que se vendera el producto
     * @throws IllegalArgumentException Lanza un error con el erro que se tuvo al ingresar alguno de los datos
     * @throws SQLException Error que tubo sql al momento de agregar el nuevo producto
     */
    public void addProduct(String name, int code, int stock, double price) throws IllegalArgumentException, SQLException{
        if (name == null||name.isBlank()) throw new IllegalArgumentException("The product name cannot be blank.");

        for (Product p: products) if (Integer.parseInt(p.getCode()) == code) throw new IllegalArgumentException("The product code already exists in the system.");
        if (stock < 0) throw new IllegalArgumentException("The product stock cannot be negative");
        if (price < 0.0) throw new IllegalArgumentException("The product price cannot be negative");

        Product p = new Product(name, code, stock, price);
        ProductRepository.save(p);

        products.add(p);
    }

    /**
     * Envia el código del producto a eliminar a la clase ProductRepository
     *
     * @param code Código de producto que se quiere eliminar
     * @throws IllegalArgumentException Lanza un erro si ocurrió algún error al eliminar el producto
     * @throws SQLException Error que tubo sql al momento de eliminar el producto
     */
    public void removeProduct(int code) throws IllegalArgumentException, SQLException {
        String codeString = String.format("%03d", code);

        new ProductRepository().deleteProduct(codeString);
    }

    /**
     * Envia los datos que se cambiaran del producto y si los datos que no se cambie los envia con null para
     * saber que no se encambian en la base de datos
     *
     * @param p Producto a editar
     * @param name Nuevo nombre del producto
     * @param code Nuevo código del producto
     * @param stock Nuevo stock del producto
     * @param price Nuevo precio del producto
     * @throws SQLException Error que tubo sql al momento de eliminar el producto
     */
    public void editProduct(Product p, String name, Integer code, Integer stock, Double price) throws SQLException, IllegalArgumentException {
        if(p == null) throw new IllegalArgumentException("The product has no data");

        String newName = null;
        String newCode = null;
        Integer newStock = null;
        Double newPrice = null;

        if (name != null) newName = name;
        if (code != null) newCode = String.format("%03d", code);
        if (stock != null) newStock = stock;
        if (price != null) newPrice = price;

        new ProductRepository().editProduct(p.getId(), newName, newCode, newStock, newPrice);

    }

    /**
     * Envia los datos de ingreso o salida de datos a la clase que maneja la base de datos
     * @param code Código del producto
     * @param type Para saber si el producto salió o entro del inventario
     * @param numIn_Out La cantidad de producto que netra o sale
     * @throws SQLException Por si sale algún error de la base de datos al ingresar los datos
     */
    public void sellOrRestockProduct(int code, String type, int numIn_Out) throws SQLException{
        String codeString = String.format("%03d", code);

        Product product = ProductRepository.show(codeString);
        System.out.println(product.getId());
        if (numIn_Out > product.getStock() && type.equalsIgnoreCase("OUT")) throw new IllegalArgumentException("The quantity exceeds the available stock");

        ProductRepository.sellOrRestockProductBs(product.getId(), type.toUpperCase(), numIn_Out);

    }

    /**
     *
     * @return Lista de todos los productos que hay en la base de datos
     */
    public static ArrayList<Product> allProducts(){
        return ProductRepository.showAllProducts();
    }
}
