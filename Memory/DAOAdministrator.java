package ProyectoPOO.Main.Memory;
import ProyectoPOO.Main.BL.entities.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOAdministrator {
    private final Connection connection;

    public DAOAdministrator(Connection connection) {
        this.connection = connection;
    }

    public void registerAdmin(Administrator admin) throws SQLException {
        String query = "INSERT INTO administrators (ID, name, lastName, username, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, admin.getID());
            preparedStatement.setString(2, admin.getName());
            preparedStatement.setString(3, admin.getLastName());
            preparedStatement.setString(4, admin.getUsername());
            preparedStatement.setString(5, admin.getPassword());

            preparedStatement.executeUpdate();
        }
    }

    public boolean isAnyAdminRegistered() throws SQLException {
        String query = "SELECT COUNT(*) FROM administrators";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    public String login(String username, String password) throws SQLException {
        String query = "SELECT * FROM administrators WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return "a";
                }
            }
        }
        return null;
    }
}