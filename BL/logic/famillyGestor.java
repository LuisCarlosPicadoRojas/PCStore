package ProyectoPOO.Main.BL.logic;

import ProyectoPOO.Main.BL.entities.Familly;
import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.BL.entities.Ram;
import ProyectoPOO.Main.BL.entities.Storage;
import ProyectoPOO.Main.Memory.DAOFamilly;

import java.sql.SQLException;
import java.util.List;

public class famillyGestor {
    private final DAOFamilly daoFamily;

    public famillyGestor(DAOFamilly daoFamily) {
        this.daoFamily = daoFamily;
    }

    public String registerFamily(Familly family) throws SQLException {
        return daoFamily.insertFamilyData(family);
    }

    public List<Familly> showFamilies() {
        return daoFamily.getFamiliesFromDatabase();
    }

    public boolean doesFamilyExist(String typeFamily) {
        return daoFamily.familyExistsInDatabase(typeFamily);
    }

    public String addProductToFamily(Product product, String typeFamily) {
        return daoFamily.addProductToFamily(product, typeFamily);
    }

    public String addRamToFamily(Ram ram, String typeFamily) {
        return daoFamily.addRamToFamily(ram, typeFamily);
    }

    public String addStorageToFamily(Storage storage, String typeFamily) {
        return daoFamily.addStorageToFamily(storage, typeFamily);
    }

    public boolean deleteFamily(String typeFamily) throws SQLException {
        return daoFamily.deleteFamily(typeFamily);
    }
}
