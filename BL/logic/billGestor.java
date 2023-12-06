package ProyectoPOO.Main.BL.logic;
import ProyectoPOO.Main.BL.entities.Bill;
import ProyectoPOO.Main.Memory.DAOBill;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class billGestor {
    private final DAOBill daoBill;
    public billGestor(DAOBill daoBill) {
        this.daoBill = daoBill;
    }

    public int generateUniqueBillNumber() throws SQLException {
        Random random = new Random();
        int newBillNumber;
        int min = 1000000;
        int max = 9999999;

        do {
            newBillNumber = min + random.nextInt(max - min + 1);
        } while (daoBill.checkBillExistence(newBillNumber));

        return newBillNumber;
    }

    public void createBill(Bill bill) throws SQLException {
        daoBill.createBill(bill);
    }

    public boolean checkBillExistence(int billNumber) throws SQLException {
        return daoBill.checkBillExistence(billNumber);
    }

    public List<Bill> showBills() throws SQLException {
        return daoBill.getAllBills();
    }

    public Bill getBillByNumber(int billNumber) throws SQLException {
        return daoBill.getBillByNumber(billNumber);
    }
}
