package ProyectoPOO.Main.Memory;
import ProyectoPOO.Main.BL.entities.Bill;
import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.BL.logic.productGestor;
import ProyectoPOO.Main.BL.logic.ramGestor;
import ProyectoPOO.Main.BL.logic.storageGestor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOBill {
    private final Connection connection;

    public DAOBill(Connection connection) {
        this.connection = connection;
    }

    public void createBill(Bill bill) throws SQLException {
        String query = "INSERT INTO bills (billNumber, date, name, lastName, phoneNumber, ram, processor, storage, powerSupply, motherboard, videoCard, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bill.getBillNumber());
            preparedStatement.setDate(2, new java.sql.Date(bill.getDate().getTime()));
            preparedStatement.setString(3, bill.getName());
            preparedStatement.setString(4, bill.getLastName());
            preparedStatement.setString(5, bill.getPhoneNumber());
            preparedStatement.setString(6, bill.getRam());
            preparedStatement.setString(7, bill.getProcessor());
            preparedStatement.setString(8, bill.getStorage());
            preparedStatement.setString(9, bill.getPowerSupply());
            preparedStatement.setString(10, bill.getMotherboard());
            preparedStatement.setString(11, bill.getVideoCard());
            preparedStatement.setFloat(12, bill.getPrice());
            preparedStatement.executeUpdate();
        }
    }
    public boolean checkBillExistence(int billCode) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM bills WHERE  billNumber = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }
    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM bills";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int billNumber = resultSet.getInt("billNumber");
                Date date = resultSet.getDate("date");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                String phoneNumber = resultSet.getString("phoneNumber");
                String ram = resultSet.getString("ram");
                String processor = resultSet.getString("processor");
                String storage = resultSet.getString("storage");
                String powerSupply = resultSet.getString("powerSupply");
                String motherboard = resultSet.getString("motherboard");
                String videoCard = resultSet.getString("videoCard");
                float price = resultSet.getFloat("price");

                Bill bill = new Bill(billNumber, name, lastName, phoneNumber, ram, processor, storage, powerSupply, motherboard, videoCard, price);
                bill.setDate(date);
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return bills;
    }

    public Bill getBillByNumber(int billNumber) throws SQLException {
        Bill bill = null;
        String query = "SELECT * FROM bills WHERE billNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, billNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String ram = resultSet.getString("ram");
                    String processor = resultSet.getString("processor");
                    String storage = resultSet.getString("storage");
                    String powerSupply = resultSet.getString("powerSupply");
                    String motherboard = resultSet.getString("motherboard");
                    String videoCard = resultSet.getString("videoCard");
                    float price = resultSet.getFloat("price");

                    bill = new Bill(billNumber, name, lastName, phoneNumber, ram, processor, storage, powerSupply, motherboard, videoCard, price);
                    bill.setDate(date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return bill;
    }

}
