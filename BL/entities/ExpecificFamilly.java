package ProyectoPOO.Main.BL.entities;
import java.util.List;

public class ExpecificFamilly extends Familly {
    private String expecificFamily;

    public ExpecificFamilly(String typeFamily, int sticks, int battery, String weight, List<Integer> ramList, String storage, String gpu, String expecificFamily) {
        super(typeFamily, sticks, battery, weight, ramList, storage , gpu);
        this.expecificFamily = expecificFamily;
    }
    public ExpecificFamilly(String typeFamily, int sticks, List<Integer> ramList, String storage, String gpu, String expecificFamily) {
        super(typeFamily, sticks, ramList, storage , gpu);
        this.expecificFamily = expecificFamily;
    }
    public String getExpecificFamily() {
        return expecificFamily;
    }

    public void setExpecificFamily(String expecificFamily) {
        this.expecificFamily = expecificFamily;
    }


}
