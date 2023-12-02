package ProyectoPOO.Main.BL.logic;

import ProyectoPOO.Main.BL.entities.Storage;
import ProyectoPOO.Main.Memory.DAOStorage;

import java.sql.SQLException;
import java.util.List;



public class storageGestor {

    private final DAOStorage dataAccess;

    public storageGestor(DAOStorage dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void registerStorage(Storage storage) throws SQLException {
        dataAccess.addStorage(storage);
    }

    public List<Storage> showStorage() {
        return dataAccess.getStoragesFromDatabase();
    }
    public boolean removeStorage(int storageCode) throws SQLException {
        return dataAccess.removeStorage(storageCode);
    }
}



        

