package ProyectoPOO.Main.Memory;

import ProyectoPOO.Main.BL.entities.Familly;
import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.BL.entities.Ram;
import ProyectoPOO.Main.BL.entities.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOFamilly {
    private final Connection connection;

    public DAOFamilly(Connection connection) {
        this.connection = connection;
    }



    public String insertFamilyData(Familly family) throws SQLException {
        String insertQuery = "INSERT INTO Family (typeFamily, sticks, ramList, storage, gpu) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, family.getTypeFamily());
            preparedStatement.setInt(2, family.getSticks());
            String ramListString = family.getRamList().toString();
            preparedStatement.setString(3, ramListString);
            preparedStatement.setString(4, family.getStorage());
            preparedStatement.setString(5, family.getGpu());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos de familia insertados con éxito en la base de datos.");
            } else {
                System.out.println("No se pudieron insertar los datos de familia.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return insertQuery;
    }

    public List<Familly> getFamiliesFromDatabase() {
        List<Familly> families = new ArrayList<>();
        String sql = "SELECT * FROM Family";

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

                Familly family = new Familly(typeFamily, sticks, battery, weight, ramList, storage, gpu);

                List<Product> products = getProductsForFamily(familyId);
                family.setProductList(products);

                List<Ram> rams = getRamForFamily(familyId);
                family.setRamProductListList(rams);

                List<Storage> storages = getStorageForFamily(familyId);
                family.setStorageList(storages);

                families.add(family);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return families;
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

    public boolean familyExistsInDatabase(String familyType) {
        String query = "SELECT COUNT(*) FROM Family WHERE typeFamily = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, familyType);
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

    public String addProductToFamily(Product product,String familyName) {
        int familyId = getIDByName(familyName);
        if (familyId != -1) {
            String sql = "INSERT INTO Family_Product (family_id, product_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, familyId);
                preparedStatement.setInt(2, product.getCode());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return "Producto añadido exitosamente a la familia.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "No se encontró la familia.";
        }

        return "No se pudo añadir el producto a la familia.";
    }

    public String addRamToFamily(Ram ram,String familyName) {
        int familyId = getIDByName(familyName);
        if (familyId != -1) {
            String sql = "INSERT INTO Family_Ram (family_id, ram_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, familyId);
                preparedStatement.setInt(2, ram.getCode());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return "RAM añadida exitosamente a la familia.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "No se encontró la familia.";
        }

        return "No se pudo añadir la RAM a la familia.";
    }

    public String addStorageToFamily(Storage storage,String familyName) {
        int familyId = getIDByName(familyName);
        String sql = "INSERT INTO Family_Storage (family_id, storage_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, familyId);
            preparedStatement.setInt(2, storage.getCode());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "Almacenamiento añadido exitosamente a la familia.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No se pudo añadir el almacenamiento a la familia.";
    }

    public List<Product> getProductsForFamily(int familyId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM Product p INNER JOIN Family_Product fp ON p.code = fp.product_id WHERE fp.family_id = ?";

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


    public List<Ram> getRamForFamily(int familyId) {
        List<Ram> rams = new ArrayList<>();
        String sql = "SELECT r.* FROM Ram r INNER JOIN Family_Ram fr ON r.code = fr.ram_id WHERE fr.family_id = ?";

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

    public List<Storage> getStorageForFamily(int familyId) {
        List<Storage> storages = new ArrayList<>();
        String sql = "SELECT s.* FROM Storage s INNER JOIN Family_Storage fs ON s.code = fs.storage_id WHERE fs.family_id = ?";

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

        String query = "SELECT id FROM Family WHERE typeFamily = ?";

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
    public boolean deleteFamily(String familyType) throws SQLException {
        int familyId = getIDByName(familyType);
        if (familyId != -1) {
            try {
                String deleteFamilyProductSql = "DELETE FROM Family_Product WHERE family_id = ?";
                try (PreparedStatement deleteFamilyProductStatement = connection.prepareStatement(deleteFamilyProductSql)) {
                    deleteFamilyProductStatement.setInt(1, familyId);
                    deleteFamilyProductStatement.executeUpdate();
                }

                String deleteFamilyRamSql = "DELETE FROM Family_Ram WHERE family_id = ?";
                try (PreparedStatement deleteFamilyRamStatement = connection.prepareStatement(deleteFamilyRamSql)) {
                    deleteFamilyRamStatement.setInt(1, familyId);
                    deleteFamilyRamStatement.executeUpdate();
                }

                String deleteFamilyStorageSql = "DELETE FROM Family_Storage WHERE family_id = ?";
                try (PreparedStatement deleteFamilyStorageStatement = connection.prepareStatement(deleteFamilyStorageSql)) {
                    deleteFamilyStorageStatement.setInt(1, familyId);
                    deleteFamilyStorageStatement.executeUpdate();
                }

                String deleteFamilySql = "DELETE FROM Family WHERE id = ?";
                try (PreparedStatement deleteFamilyStatement = connection.prepareStatement(deleteFamilySql)) {
                    deleteFamilyStatement.setInt(1, familyId);
                    int rowsDeleted = deleteFamilyStatement.executeUpdate();
                    return rowsDeleted > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("La familia no existe en la base de datos.");
            return false;
        }
    }
}
