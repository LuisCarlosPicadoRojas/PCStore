package ProyectoPOO.Main.Memory;
import ProyectoPOO.Main.BL.entities.Familly;
import ProyectoPOO.Main.BL.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DAO {
    private Connection connection;
    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        String url = "jdbc:mysql://localhost:3306/PCStore";
        String username = "root";
        String password = "1q2w3e4r5t.";

        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
