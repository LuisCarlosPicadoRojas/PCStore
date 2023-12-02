package ProyectoPOO.Main.BL.logic;

import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.Memory.DAOProduct;

import java.sql.SQLException;
import java.util.List;

import java.util.List;

public class productGestor {

    private final DAOProduct daoProduct;

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
}
