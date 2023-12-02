package ProyectoPOO.Main.Memory;

import ProyectoPOO.Main.BL.entities.Client;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOClient {
    private final Connection connection;

    public DAOClient(Connection connection) {
        this.connection = connection;
    }

    public void registerClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (ID, name, lastName, email, phoneNumber, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, client.getID());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getLastName());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.setString(6, client.getUsername());
            preparedStatement.setString(7, client.getPassword());

            preparedStatement.executeUpdate();
        }
    }

    public boolean checkClientExist(int ID) throws SQLException {
        String query = "SELECT * FROM clients WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the client with the ID exists
            }
        }
    }

    public String login(String username, String password) throws SQLException {
        String query = "SELECT * FROM clients WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return "c";
                }
            }
        }
        return null;
    }
}