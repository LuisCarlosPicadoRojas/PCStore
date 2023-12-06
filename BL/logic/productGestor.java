package ProyectoPOO.Main.BL.logic;

import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.Memory.DAOProduct;

import java.sql.SQLException;
import java.util.List;

import java.util.Random;

public class productGestor {

    private final DAOProduct daoProduct;

    public int generateUniqueID() throws SQLException {
        Random random = new Random();
        int newID;
        int min = 1000000;
        int max = 9999999;

        do {
            newID = min + random.nextInt(max - min + 1);
        } while (daoProduct.checkProductExistence(newID));

        return newID;
    }
    public productGestor(DAOProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    public String registerProduct(Product product) throws SQLException {
        return daoProduct.addProduct(product);
    }

    public List<Product> showProduct() {
        return daoProduct.getProductsFromDatabase();
    }

    public boolean removeProduct(int productCode) throws SQLException {
        return daoProduct.removeProduct(productCode);
    }
    public boolean updateProduct(Product updatedProduct) throws SQLException {

        return daoProduct.updateProduct(updatedProduct);

    }

}
