package ProyectoPOO.Main.BL.logic;

import ProyectoPOO.Main.BL.entities.ExpecificFamilly;
import ProyectoPOO.Main.BL.entities.Product;
import ProyectoPOO.Main.BL.entities.Ram;
import ProyectoPOO.Main.BL.entities.Storage;
import ProyectoPOO.Main.Memory.DAOExpecificFamilly;

import java.sql.SQLException;
import java.util.List;

public class expecificFamillyGestor {
    private final DAOExpecificFamilly daoExpecificFamily;

    public expecificFamillyGestor(DAOExpecificFamilly daoExpecificFamily) {
        this.daoExpecificFamily = daoExpecificFamily;
    }

    public String registerExpecificFamilly(ExpecificFamilly expecificFamilly) {
        return daoExpecificFamily.insertExpecificFamilyData(expecificFamilly);
    }

    public List<ExpecificFamilly> showExpecificFamilies() {
        return daoExpecificFamily.getExpecificFamiliesFromDatabase();
    }

    public boolean doesExpecificFamilyExist(String expecificFamily) {
        return daoExpecificFamily.expecificFamilyExistsInDatabase(expecificFamily);
    }

    public String addProductToExpecificFamily(Product product, String expecificFamily) {
        return daoExpecificFamily.addProductToExpecificFamily(product, expecificFamily);
    }

    public String addRamToExpecificFamily(Ram ram, String expecificFamily) {
        return daoExpecificFamily.addRamToExpecificFamily(ram, expecificFamily);
    }

    public String addStorageToExpecificFamily(Storage storage, String expecificFamily) {
        return daoExpecificFamily.addStorageToExpecificFamily(storage, expecificFamily);
    }
    public boolean deleteExpecificFamily(String expecificFamily) throws SQLException {
        return daoExpecificFamily.deleteExpecificFamily(expecificFamily);
    }

}
