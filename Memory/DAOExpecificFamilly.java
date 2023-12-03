package ProyectoPOO.Main.Memory;

import ProyectoPOO.Main.BL.entities.ExpecificFamilly;
import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.BL.entities.Ram;
import ProyectoPOO.Main.BL.entities.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOExpecificFamilly {

    private final Connection connection;

    public DAOExpecificFamilly(Connection connection) {
        this.connection = connection;
    }

    public String insertExpecificFamilyData(ExpecificFamilly expecificFamily) {
        String insertQuery = "INSERT INTO ExpecificFamily (typeFamily, sticks, battery, weight, ramList, storage, gpu, expecificFamily) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, expecificFamily.getTypeFamily());
            preparedStatement.setInt(2, expecificFamily.getSticks());
            preparedStatement.setInt(3, expecificFamily.getBattery());
            preparedStatement.setString(4, expecificFamily.getWeight());
            String ramListString = expecificFamily.getRamList().toString();
            preparedStatement.setString(5, ramListString);
            preparedStatement.setString(6, expecificFamily.getStorage());
            preparedStatement.setString(7, expecificFamily.getGpu());
            preparedStatement.setString(8, expecificFamily.getExpecificFamily());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos de familia específica insertados con éxito en la base de datos.");
            } else {
                System.out.println("No se pudieron insertar los datos de familia específica.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertQuery;
    }


    public List<ExpecificFamilly> getExpecificFamiliesFromDatabase() {
        List<ExpecificFamilly> specificFamilies = new ArrayList<>();
        String sql = "SELECT * FROM ExpecificFamily";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int familyId = resultSet.getInt("id");
                String typeFamily = resultSet.getString("typeFamily");
                int sticks = resultSet.getInt("sticks");
                int battery = resultSet.getInt("battery");
                String weight = resultSet.getString("weight");
                String storage = resultSet.getString("storage");
                String gpu = resultSet.getString("gpu");
                String ramListString = resultSet.getString("ramList");
                List<Integer> ramList = convertStringToList(ramListString);
                String expecificFamily = resultSet.getString("expecificFamily");

                ExpecificFamilly specificFamily = new ExpecificFamilly(typeFamily, sticks, battery, weight, ramList, storage, gpu, expecificFamily);

                List<Product> products = getProductsForExpecificFamily(familyId);
                specificFamily.setProductList(products);

                List<Ram> rams = getRamForExpecificFamily(familyId);
                specificFamily.setRamProductListList(rams);

                List<Storage> storages = getStorageForExpecificFamily(familyId);
                specificFamily.setStorageList(storages);

                specificFamilies.add(specificFamily);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specificFamilies;
    }


    private List<Integer> convertStringToList(String ramListString) {
        List<Integer> ramList = new ArrayList<>();
        if (ramListString != null && !ramListString.isEmpty()) {
            String[] ramArray = ramListString.replace("[", "").replace("]", "").split(",");
            for (String ram : ramArray) {

                try {
                    ramList.add(Integer.parseInt(ram.trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return ramList;
    }

    public List<Product> getProductsForExpecificFamily(int familyId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM Product p INNER JOIN ExpecificFamily_Product efp ON p.code = efp.product_id WHERE efp.expecificFamily_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, familyId);
            ResultSet resultSet = preparedStatement.executeQuery();

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

    public List<Ram> getRamForExpecificFamily(int familyId) {
        List<Ram> rams = new ArrayList<>();
        String sql = "SELECT r.* FROM Ram r INNER JOIN ExpecificFamily_Ram efr ON r.code = efr.ram_id WHERE efr.expecificFamily_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, familyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ram ram = new Ram(
                        resultSet.getInt("code"),
                        resultSet.getString("name"),
                        resultSet.getFloat("price"),
                        resultSet.getInt("GBs"),
                        resultSet.getString("productType"),
                        resultSet.getFloat("rating")
                );
                rams.add(ram);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rams;
    }

    public List<Storage> getStorageForExpecificFamily(int familyId) {
        List<Storage> storages = new ArrayList<>();
        String sql = "SELECT s.* FROM Storage s INNER JOIN ExpecificFamily_Storage efs ON s.code = efs.storage_id WHERE efs.expecificFamily_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, familyId);
            ResultSet resultSet = preparedStatement.executeQuery();

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

    public int getIDByName(String familyName) {
        int familyId = -1;

        String query = "SELECT id FROM ExpecificFamily WHERE expecificFamily = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, familyName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                familyId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return familyId;
    }

    public String addProductToExpecificFamily(Product product, String familyName) {
        int familyId = getIDByName(familyName);
        if (familyId != -1) {
            String sql = "INSERT INTO ExpecificFamily_Product (expecificFamily_id, product_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, familyId);
                preparedStatement.setInt(2, product.getCode());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return "Producto añadido exitosamente a la familia específica.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "No se encontró la familia.";
        }

        return "No se pudo añadir el producto a la familia específica.";
    }

    public String addRamToExpecificFamily(Ram ram, String familyName) {
        int familyId = getIDByName(familyName);
        if (familyId != -1) {
            String sql = "INSERT INTO ExpecificFamily_Ram (expecificFamily_id, ram_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, familyId);
                preparedStatement.setInt(2, ram.getCode());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return "RAM añadida exitosamente a la familia específica.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "No se encontró la familia.";
        }

        return "No se pudo añadir la RAM a la familia específica.";
    }

    public String addStorageToExpecificFamily(Storage storage, String familyName) {
        int familyId = getIDByName(familyName);
        if (familyId != -1) {
            String sql = "INSERT INTO ExpecificFamily_Storage (expecificFamily_id, storage_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, familyId);
                preparedStatement.setInt(2, storage.getCode());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return "Almacenamiento añadido exitosamente a la familia específica.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "No se encontró la familia.";
        }

        return "No se pudo añadir el almacenamiento a la familia específica.";
    }

    public boolean expecificFamilyExistsInDatabase(String expecificFamily) {
        String query = "SELECT COUNT(*) FROM ExpecificFamily WHERE expecificFamily = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, expecificFamily);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteExpecificFamily(String expecificFamilyName) throws SQLException {
        int expecificFamilyId = getIDByName(expecificFamilyName);
        if (expecificFamilyId != -1) {
            try {
                String deleteExpecificFamilyProductSql = "DELETE FROM ExpecificFamily_Product WHERE expecificFamily_id = ?";
                try (PreparedStatement deleteExpecificFamilyProductStatement = connection.prepareStatement(deleteExpecificFamilyProductSql)) {
                    deleteExpecificFamilyProductStatement.setInt(1, expecificFamilyId);
                    deleteExpecificFamilyProductStatement.executeUpdate();
                }

                String deleteExpecificFamilyRamSql = "DELETE FROM ExpecificFamily_Ram WHERE expecificFamily_id = ?";
                try (PreparedStatement deleteExpecificFamilyRamStatement = connection.prepareStatement(deleteExpecificFamilyRamSql)) {
                    deleteExpecificFamilyRamStatement.setInt(1, expecificFamilyId);
                    deleteExpecificFamilyRamStatement.executeUpdate();
                }

                String deleteExpecificFamilyStorageSql = "DELETE FROM ExpecificFamily_Storage WHERE expecificFamily_id = ?";
                try (PreparedStatement deleteExpecificFamilyStorageStatement = connection.prepareStatement(deleteExpecificFamilyStorageSql)) {
                    deleteExpecificFamilyStorageStatement.setInt(1, expecificFamilyId);
                    deleteExpecificFamilyStorageStatement.executeUpdate();
                }

                String deleteExpecificFamilySql = "DELETE FROM ExpecificFamily WHERE id = ?";
                try (PreparedStatement deleteExpecificFamilyStatement = connection.prepareStatement(deleteExpecificFamilySql)) {
                    deleteExpecificFamilyStatement.setInt(1, expecificFamilyId);
                    int rowsDeleted = deleteExpecificFamilyStatement.executeUpdate();
                    return rowsDeleted > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("La familia específica no existe en la base de datos.");
            return false;
        }
    }
}
