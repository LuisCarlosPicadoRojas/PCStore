package ProyectoPOO.BL.logic;
import ProyectoPOO.BL.entities.Client;
import ProyectoPOO.Memory.DAOClient;
import ProyectoPOO.Memory.DAOProduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class clientGestor {
    private final DAOClient DAOClient;

    public clientGestor(DAOClient DAOClient) {
        this.DAOClient = DAOClient;
    }



    public void registerClient(Client client) throws SQLException {
        DAOClient.registerClient(client);
    }

    public boolean checkClientExist(int ID) throws SQLException {
        return DAOClient.checkClientExist(ID);
    }

    public String login(String username, String password) throws SQLException {
        return DAOClient.login(username, password);
    }
}
