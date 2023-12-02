package ProyectoPOO.Main.BL.entities;

public class Storage extends Product {
    private String TypeStorage;
    private int StorageSpace;

    public Storage(int code, String name, float price, String productType, float raiting, String TypeStorage, int StorageSpace) {
        super(code, name, price, productType, raiting);
        this.TypeStorage = TypeStorage;
        this.StorageSpace = StorageSpace;
    }

    public String getTypeStorage() {
        return TypeStorage;
    }

    public void setTypeStorage(String TypeStorage) {
        this.TypeStorage = TypeStorage;
    }

    public int getStorageSpace() {
        return StorageSpace;
    }

    public void setStorageSpace(int StorageSpace) {
        this.StorageSpace = StorageSpace;
    }
}