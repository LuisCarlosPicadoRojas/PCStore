package ProyectoPOO.Main.Memory;

import ProyectoPOO.Main.BL.entities.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAOProduct {
    private final Connection connection;

    public DAOProduct(Connection connection) {
        this.connection = connection;
    }

    public String addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Product (code, name, price, productType, rating) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getCode());
            statement.setString(2, product.getName());
            statement.setFloat(3, product.getPrice());
            statement.setString(4, product.getProductType());
            statement.setFloat(5, product.getRating());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "El producto " + product.getName() + " fue registrado exitosamente!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return "No se pudo registrar el producto.";
    }
    public List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("code"),
                        resultSet.getString("name"),
                        resultSet.getFloat("price"),
                        resultSet.getString("productType"),
                        resultSet.getFloat("rating")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
