package ProyectoPOO.Main.Memory;

import ProyectoPOO.Main.BL.entities.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAOStorage {
    private final Connection connection;

    public DAOStorage(Connection connection) {
        this.connection = connection;
    }

    public String addStorage(Storage storage) throws SQLException {
        String sql = "INSERT INTO Storage (code, name, price, TypeStorage, StorageSpace, productType, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storage.getCode());
            statement.setString(2, storage.getName());
            statement.setFloat(3, storage.getPrice());
            statement.setString(4, storage.getTypeStorage());
            statement.setInt(5, storage.getStorageSpace());
            statement.setString(6, storage.getProductType());
            statement.setFloat(7, storage.getRating());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "El almacenamiento " + storage.getName() + " fue registrado exitosamente!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return "No se pudo registrar el almacenamiento.";
    }

    public List<Storage> getStoragesFromDatabase() {
        List<Storage> storages = new ArrayList<>();
        String sql = "SELECT * FROM Storage";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Storage storage = new Storage(
                        resultSet.getInt("code"),
                        resultSet.getString("name"),
                        resultSet.getFloat("price"),
                        resultSet.getString("productType"),
                        resultSet.getFloat("rating"),
                        resultSet.getString("TypeStorage"),
                        resultSet.getInt("StorageSpace")
                );
                storages.add(storage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storages;
    }
}

