package ProyectoPOO.Main.BL.entities;

public class Administrator {
    private int ID;
    private String name;
    private String lastName;
    private String username;
    private String password;



    public Administrator(int ID, String name, String lastName, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
