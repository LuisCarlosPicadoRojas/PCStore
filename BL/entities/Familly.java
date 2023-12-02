package ProyectoPOO.Main.BL.entities;
import java.util.ArrayList;
import java.util.List;

public class Familly {

    private String typeFamily;
    private int sticks;
    private int battery;
    private String weight;
    private List<Integer> ramList;
    private String storage;
    private String gpu;
    private List<Product> productList;
    private List<Storage> storageList;
    private List<Ram> ramProductList;
    public Familly(String typeFamily, int sticks, int battery, String weight, List<Integer> ramList, String storage, String gpu) {
        this.typeFamily = typeFamily;
        this.sticks = sticks;
        this.battery = battery;
        this.weight = weight;
        this.ramList = ramList;
        this.storage = storage;
        this.gpu = gpu;
    }
    public Familly(String typeFamily, int sticks, List<Integer> ramList, String storage, String gpu) {
        this.typeFamily = typeFamily;
        this.sticks = sticks;
        this.ramList = ramList;
        this.storage = storage;
        this.gpu = gpu;
    }


    public String getTypeFamily() {
        return typeFamily;
    }

    public int getBattery() {
        return battery;
    }

    public String getWeight() {
        return weight;
    }


    public String getStorage() {
        return storage;
    }

    public String getGpu() {
        return gpu;
    }

    public void setTypeFamily(String typeFamily) {
        this.typeFamily = typeFamily;
    }

    public int getSticks() {
        return sticks;
    }

    public void setSticks(int sticks) {
        this.sticks = sticks;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<Integer> getRamList() {
        return ramList;
    }

    public void setRamList(List<Integer> ramList) {
        this.ramList = ramList;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setRamProductListList(List<Ram> ramProductListList) {
        this.ramProductList = ramProductListList;
    }

    public void setStorageList(List<Storage> storageList) {
        this.storageList = storageList;
    }


    public void addProduct(Product product) {
        if (productList == null) {
            productList = new ArrayList<>();
        }
        productList.add(product);
    }
    public List<Product> getProductList() {
        return productList;
    }
    public void addRamProductList(Ram ram) {
        if (ramProductList == null) {
            ramProductList = new ArrayList<>();
        }
        ramProductList.add(ram);
    }
    public List<Ram> getRamProductList() {
        return ramProductList;
    }

    public void addStorageList(Storage storage) {
        if (storageList == null) {
            storageList = new ArrayList<>();
        }
        storageList.add(storage);
    }
    public List<Storage> getStorageList() {
        return storageList;
    }


}