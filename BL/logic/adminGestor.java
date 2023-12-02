package ProyectoPOO.BL.logic;
import ProyectoPOO.BL.entities.Administrator;
import ProyectoPOO.Memory.DAOAdministrator;

import java.sql.SQLException;

public class adminGestor {
    private final DAOAdministrator daoAdministrator;

    public adminGestor(DAOAdministrator daoAdministrator) {
        this.daoAdministrator = daoAdministrator;
    }

    public void registerAdmin(Administrator admin) {
        try {
            daoAdministrator.registerAdmin(admin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAnyAdminRegistered() {
        try {
            return daoAdministrator.isAnyAdminRegistered();
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public String login(String username, String password) {
        try {
            return daoAdministrator.login(username, password);
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }
}

