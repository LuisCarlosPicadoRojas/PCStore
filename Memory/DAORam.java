package ProyectoPOO.Main.Memory;
import ProyectoPOO.Main.BL.entities.Ram;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAORam {

    private final Connection connection;

    public DAORam(Connection connection) {
        this.connection = connection;
    }

    public String addRam(Ram ram) throws SQLException {
        String sql = "INSERT INTO Ram (code, name, price, GBs, productType, rating) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ram.getCode());
            statement.setString(2, ram.getName());
            statement.setFloat(3, ram.getPrice());
            statement.setInt(4, ram.getGBs());
            statement.setString(5, ram.getProductType());
            statement.setFloat(6, ram.getRating());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "La RAM " + ram.getName() + " fue registrada exitosamente!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return "No se pudo registrar la RAM.";
    }

    public List<Ram> getRamsFromDatabase() {
        List<Ram> rams = new ArrayList<>();
        String sql = "SELECT * FROM Ram";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

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

    public boolean removeRam(int ramCode) throws SQLException {
        try {
            String deleteFamilyRamSql = "DELETE FROM Family_Ram WHERE ram_id = ?";
            try (PreparedStatement deleteFamilyRamStatement = connection.prepareStatement(deleteFamilyRamSql)) {
                deleteFamilyRamStatement.setInt(1, ramCode);
                deleteFamilyRamStatement.executeUpdate();
            }
            String deleteExpecificFamilyRamSql = "DELETE FROM expecificfamily_ram WHERE ram_id = ?";
            try (PreparedStatement deleteExpecificFamilyRamStatement = connection.prepareStatement(deleteExpecificFamilyRamSql)) {
                deleteExpecificFamilyRamStatement.setInt(1, ramCode);
                deleteExpecificFamilyRamStatement.executeUpdate();
            }
            String deleteRamSql = "DELETE FROM Ram WHERE code = ?";
            try (PreparedStatement deleteRamStatement = connection.prepareStatement(deleteRamSql)) {
                deleteRamStatement.setInt(1, ramCode);
                int rowsDeleted = deleteRamStatement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public boolean updateRam(Ram updatedRam) throws SQLException {
        String sql = "UPDATE Ram SET name = ?, price = ?, GBs = ?, productType = ?, rating = ? WHERE code = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedRam.getName());
            statement.setFloat(2, updatedRam.getPrice());
            statement.setInt(3, updatedRam.getGBs());
            statement.setString(4, updatedRam.getProductType());
            statement.setFloat(5, updatedRam.getRating());
            statement.setInt(6, updatedRam.getCode());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
