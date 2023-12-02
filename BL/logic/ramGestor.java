package ProyectoPOO.Main.BL.logic;
import ProyectoPOO.Main.BL.entities.Ram;
import ProyectoPOO.Main.Memory.DAORam;

import java.sql.SQLException;
import java.util.List;;


public class ramGestor {
    private final DAORam daoRam;

    public ramGestor(DAORam daoRam) {
        this.daoRam = daoRam;
    }

    public String registerRam(Ram ram) throws SQLException {
        return daoRam.addRam(ram);
    }

    public List<Ram> showRam() {
        return daoRam.getRamsFromDatabase();
    }

}
