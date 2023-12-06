package ProyectoPOO.Main.TL;

import ProyectoPOO.Main.BL.entities.*;
import ProyectoPOO.Main.BL.logic.*;
import ProyectoPOO.Main.Memory.*;
import ProyectoPOO.Main.UI.UI;
import ProyectoPOO.Main.Utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Controller {
    private final UI interfaz;
    private static productGestor productGestor;
    private static ramGestor ramGestor;
    private static storageGestor storageGestor;
    private static famillyGestor famillyGestor;
    private static expecificFamillyGestor expecificFamillyGestor;
    private static Connection connection;
    private static clientGestor clientGestor;
    private static adminGestor adminGestor;
    private static billGestor billGestor;


    public Controller() throws SQLException {
        connection = DAO.getConnection();
        interfaz = new UI();
        productGestor = new productGestor(new DAOProduct(connection));
        ramGestor = new ramGestor(new DAORam(connection));
        storageGestor = new storageGestor(new DAOStorage(connection));
        famillyGestor = new famillyGestor(new DAOFamilly(connection));
        expecificFamillyGestor = new expecificFamillyGestor(new DAOExpecificFamilly(connection));
        clientGestor = new clientGestor(new DAOClient(connection));
        adminGestor = new adminGestor(new DAOAdministrator(connection));
        billGestor = new billGestor(new DAOBill(connection));
    }
    public void start() throws Exception{
        String loggedIn = null;

        while (true) {
            try {
                while (loggedIn == null) {
                    interfaz.menuLogin();
                    int option = interfaz.optionReader();

                    switch (option) {
                        case 1:
                            loggedIn = login();
                            break;

                        case 2:
                            NewClient(interfaz);
                            break;

                        case 3:
                            interfaz.printText("Hasta luego.");
                            return;

                        default:
                            interfaz.printText("Opción no válida.");
                            break;
                    }
                }
                if (loggedIn.equals("a")){
                    MainMenuAdmin(interfaz);
                    loggedIn = null;
                }else if (loggedIn.equals("c")){
                    MainMenuClient(interfaz);
                    loggedIn = null;
                }
            } catch (Exception e) {
                interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
            }
        }
    }
    private static void NewAdministrator(UI interfaz) {
        try {
            interfaz.printText("No hay usuario administrador");
            interfaz.printText("Registre uno");
            interfaz.printText("Ingrese el ID del administrador:");
            int ID = Integer.parseInt(interfaz.readText());

            interfaz.printText("Ingrese el nombre del administrador:");
            String name = interfaz.readText();

            if (Utils.containsNumbers(name)) {
                interfaz.printText("Error: El nombre no debe contener números. Por favor, inténtelo de nuevo.");
                return;
            }

            interfaz.printText("Ingrese el apellido del administrador:");
            String lastName = interfaz.readText();

            if (Utils.containsNumbers(lastName)) {
                interfaz.printText("Error: El apellido no debe contener números. Por favor, inténtelo de nuevo.");
                return;
            }

            interfaz.printText("Ingrese el nombre de usuario del administrador:");
            String username = interfaz.readText();

            interfaz.printText("Ingrese la contraseña del administrador:");
            String password = interfaz.readText();



            adminGestor.registerAdmin(new Administrator(ID, name, lastName, username, password));
            interfaz.printText("Administrador registrado con éxito.");

        } catch (Exception ex) {
            interfaz.printText("Ha ocurrido un error. Intente nuevamente.");
        }
    }
    private static void NewClient(UI interfaz) {
        try {
            if (adminGestor.isAnyAdminRegistered()){
                interfaz.printText("__________________________");
                interfaz.printText("Ingrese el ID del cliente:");
                int ID = Integer.parseInt(interfaz.readText());

                interfaz.printText("Ingrese el nombre del cliente:");
                String name = interfaz.readText();

                if (Utils.containsNumbers(name)) {
                    interfaz.printText("Error: El nombre no debe contener números. Por favor, inténtelo de nuevo.");
                    return;
                }

                interfaz.printText("Ingrese el apellido del cliente:");
                String lastName = interfaz.readText();

                if (Utils.containsNumbers(lastName)) {
                    interfaz.printText("Error: El apellido no debe contener números. Por favor, inténtelo de nuevo.");
                    return;
                }

                interfaz.printText("Ingrese el correo electrónico del cliente:");
                String email = interfaz.readText();
                if (!Utils.isValidEmail(email)) {
                    interfaz.printText("Error: El correo electrónico no tiene un formato válido. Por favor, inténtelo de nuevo.");
                    return;
                }
                interfaz.printText("Ingrese el número de teléfono del cliente:");
                String phoneNumber = interfaz.readText();

                interfaz.printText("Ingrese el nombre de usuario del cliente:");
                String username = interfaz.readText();

                interfaz.printText("Ingrese la contraseña del cliente:");
                String password = interfaz.readText();

                boolean clientExists = clientGestor.checkClientExist(ID);

                if (clientExists) {
                    interfaz.printText("El cliente ya existe. Ingrese un ID único.");
                } else {
                    clientGestor.registerClient(new Client(ID, name, lastName, email, phoneNumber, username, password));
                    interfaz.printText("Cliente registrado con éxito.");
                }
            }else{
                NewAdministrator(interfaz);
            }

        } catch (Exception ex) {
            interfaz.printText("Ha ocurrido un error. Intente nuevamente.");
        }
    }
    private String login() throws Exception {
        interfaz.printText("Ingrese su nombre de usuario:");
        String username = interfaz.readText();

        interfaz.printText("Ingrese su contraseña:");
        String password = interfaz.readText();

        try {
            String loggedInClient = clientGestor.login(username, password);
            String loggedInAdmin = adminGestor.login(username, password);

            if ("c".equals(loggedInClient)) {
                interfaz.printText("Inicio de sesión como cliente exitoso. ¡Bienvenido!");
                return "c";
            } else if ("a".equals(loggedInAdmin)) {
                interfaz.printText("Inicio de sesión como administrador exitoso. ¡Bienvenido!");
                return "a";
            } else {
                interfaz.printText("Nombre de usuario o contraseña incorrectos. Inténtelo nuevamente.");
            }
        } catch (SQLException e) {
            interfaz.printText("Se produjo un error al intentar iniciar sesión. Intente nuevamente más tarde.");
        } catch (Exception e) {
            interfaz.printText("Se produjo un error. Inténtelo nuevamente.");
        }
        return null;
    }
    private static void MainMenuClient(UI interfaz) {
        boolean showMenu = true;

        while (showMenu) {
            try {
                int registroOption = 0;
                interfaz.menuClient();

                registroOption = interfaz.optionReader();

                switch (registroOption) {
                    case 1:
                        showFamily(interfaz);
                        break;

                    case 2:
                        showProduct(interfaz);
                        break;

                    case 3:
                        ClientsPC(interfaz);
                        break;

                    case 4:
                        showBillClient(interfaz);
                        break;

                    case 5:
                        interfaz.printText("Hasta luego.");
                        return;

                    default:
                        interfaz.printText("Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
            }
        }
    }
    private static void MainMenuAdmin(UI interfaz) {
        boolean showMenu = true;

        while (showMenu) {
            try {
                int registroOption = 0;
                interfaz.menu();

                registroOption = interfaz.optionReader();

                switch (registroOption) {
                    case 1:
                        NewProduct(interfaz);
                        break;

                    case 2:
                        showProduct(interfaz);
                        break;

                    case 3:
                        NewFamilly(interfaz);
                        break;

                    case 4:
                        showFamily(interfaz);
                        break;
                    case 5:
                        addComponentToFamilly(interfaz);
                        break;
                    case 6:
                        deleteComponent(interfaz);
                        break;

                    case 7:
                        deleteFamily(interfaz);
                        break;

                    case 8:
                        updateComponent(interfaz);
                        break;
                    case 9:
                        updateFamilly(interfaz);
                        break;
                    case 10:
                        showAllBills(interfaz);
                        break;
                    case 11:
                        interfaz.printText("Hasta luego.");
                        return;

                    default:
                        interfaz.printText("Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
            }
        }
    }
    private static void NewProduct(UI interfaz) throws Exception {
        try {
            interfaz.printText("Ingrese el nombre del componente:");
            String name = interfaz.readText();

            interfaz.printText("Ingrese el precio del componente:");
            float price = Float.parseFloat(interfaz.readText());
            int code = productGestor.generateUniqueID();
            interfaz.menuComponents();
            int option = Integer.parseInt(interfaz.readText());
            String typeProduct;
            float rating;
            switch (option) {
                case 1:
                    typeProduct = "Ram";
                    interfaz.printText("Ingrese el rating del producto:");
                    rating = Float.parseFloat(interfaz.readText());
                    interfaz.printText("Ingrese cuántas GBs posee la ram:");
                    int GBs = Integer.parseInt(interfaz.readText());
                    ramGestor.registerRam(new Ram(code, name, price, GBs, typeProduct, rating));
                    interfaz.printText(name + " fue registrado con exito el codigo de producto es: " + code);
                    break;
                case 2:
                    typeProduct = "Procesador";
                    interfaz.printText("Ingrese el rating del producto:");
                    rating = Float.parseFloat(interfaz.readText());

                    productGestor.registerProduct(new Product(code, name, price, typeProduct, rating));
                    interfaz.printText(name + " fue registrado con exito el codigo de producto es: " + code);
                    break;
                case 3:
                    typeProduct = "Almacenamiento";
                    interfaz.printText("Ingrese el rating del producto:");
                    rating = Float.parseFloat(interfaz.readText());
                    interfaz.printText("Tipo de almacenamiento (SSD, HDD):");
                    String TypeStorage = interfaz.readText().toUpperCase();
                    if (TypeStorage.equals("SSD") || TypeStorage.equals("HDD")) {
                        interfaz.printText("Cantidad de almacenamiento(GBs):");
                        int StorageSpace = Integer.parseInt(interfaz.readText());
                        storageGestor.registerStorage(new Storage(code, name, price, typeProduct, rating, TypeStorage, StorageSpace));
                        interfaz.printText(name + " fue registrado con exito el codigo de producto es: " + code);
                    } else {
                        interfaz.printText("Tipo de almacenamiento no válido. Por favor, ingrese SSD o HDD.");
                    }
                    break;
                case 4:
                    typeProduct = "Fuente de poder";
                    interfaz.printText("Ingrese el rating del producto:");
                    rating = Float.parseFloat(interfaz.readText());
                    productGestor.registerProduct(new Product(code, name, price, typeProduct, rating));
                    interfaz.printText(name + " fue registrado con exito el codigo de producto es: " + code);
                    break;
                case 5:
                    typeProduct = "Tarjeta Madre";
                    interfaz.printText("El componente tiene rating? (S/N):");
                    String raitingExist = interfaz.readText();
                    if (raitingExist != null && raitingExist.equalsIgnoreCase("S")) {
                        interfaz.printText("Ingrese el rating del producto:");
                        rating = Float.parseFloat(interfaz.readText());
                        productGestor.registerProduct(new Product(code, name, price, typeProduct, rating));
                        interfaz.printText(name + " fue registrado con exito");
                    } else if (raitingExist != null && raitingExist.equalsIgnoreCase("N")) {
                        productGestor.registerProduct(new Product(code, name, price, typeProduct));
                        interfaz.printText(name + " fue registrado con exito el codigo de producto es: " + code);
                    } else {
                        interfaz.printText("Error: ingrse una opcion valida");
                    }
                    break;
                case 6:
                    typeProduct = "Tarjeta de video.";
                    interfaz.printText("Ingrese el rating del producto:");
                    rating = Float.parseFloat(interfaz.readText());
                    productGestor.registerProduct(new Product(code, name, price, typeProduct, rating));
                    interfaz.printText(name + " fue registrado con exito el codigo de producto es: " + code);
                    break;
                case 7:
                    break;
                default:
                    interfaz.printText("Opción inválida");
            }
        } catch (Exception ex) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }

    private static void NewFamilly(UI interfaz) throws Exception {
        try {
            interfaz.menuTypeFamilly();
            int option = Integer.parseInt(interfaz.readText());
            String typeFamilly;
            String expecificFamilly = null;
            switch (option) {
                case 1:
                    typeFamilly = "Escolar";
                    famillyRegister(interfaz, typeFamilly, expecificFamilly);
                    break;
                case 2:
                    typeFamilly = "Sobremesa";
                    newExpecificFamilly(interfaz, typeFamilly, expecificFamilly);
                    break;
                case 3:
                    typeFamilly = "Portables";
                    newExpecificFamilly(interfaz, typeFamilly, expecificFamilly);
                    break;
                case 4:
                    typeFamilly = "Servidores";
                    famillyRegister(interfaz, typeFamilly, expecificFamilly);
                    break;
                case 5:
                    interfaz.printText("Hasta luego.");
                    break;
                default:
                    interfaz.printText("Opción inválida");
            }
        } catch (Exception ex) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }

    private static void newExpecificFamilly(UI interfaz, String typeFamilly, String expecificFamilly) throws Exception {

        try {
            int option;

            switch (typeFamilly) {
                case "Sobremesa":
                    interfaz.menuDesktop();
                    option = Integer.parseInt(interfaz.readText());
                    if (option >= 1 && option <= 3) {
                        switch (option) {
                            case 1:
                                expecificFamilly = "Oficina";
                                famillyRegister(interfaz, typeFamilly, expecificFamilly);
                                break;
                            case 2:
                                expecificFamilly = "Gaming";
                                famillyRegister(interfaz, typeFamilly, expecificFamilly);
                                break;
                            case 3:
                                expecificFamilly = "Workstation";
                                famillyRegister(interfaz, typeFamilly, expecificFamilly);
                                break;
                        }
                    } else {
                        interfaz.printText("Error: opcion invalida");
                    }
                    break;

                case "Portables":
                    interfaz.menuLaptop();
                    option = Integer.parseInt(interfaz.readText());
                    if (option == 1) {
                        expecificFamilly = "Casa";
                        famillyRegister(interfaz, typeFamilly, expecificFamilly);
                    } else if (option == 2) {
                        expecificFamilly = "Trabajo";
                        famillyRegister(interfaz, typeFamilly, expecificFamilly);
                    } else {
                        interfaz.printText("Error: opcion invalida");
                        return;
                    }
                    break;

                default:
                    interfaz.printText("Tipo de familia no reconocido");
            }

        } catch (Exception ex) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }
    private static void famillyRegister(UI interfaz, String typeFamily, String expecificFamilly) throws Exception {
        try {
            boolean expecificFamilyExist = expecificFamillyGestor.doesExpecificFamilyExist(expecificFamilly);
            if (expecificFamilyExist) {interfaz.printText("Error, ya se a registrado la familia expecifica " + expecificFamilly);}
            else{
                interfaz.printText("Ingrese la cantidad máxima de sticks para la familia:");
                int sticks = Integer.parseInt(interfaz.readText());

                List<Integer> ramList = new ArrayList<>();
                boolean addAnotherRam = true;

                while (addAnotherRam) {
                    interfaz.printText("Ingrese la  RAM aceptada por la familia:");
                    int ram = Integer.parseInt(interfaz.readText());
                    ramList.add(ram);

                    interfaz.printText("¿Desea agregar RAM? (S/N):");
                    String addMoreRam = interfaz.readText();

                    if (addMoreRam != null) {
                        if (addMoreRam.equalsIgnoreCase("N")) {
                            addAnotherRam = false;
                        } else if (!addMoreRam.equalsIgnoreCase("S")) {
                            interfaz.printText("Error: Por favor, ingrese S o N.");
                        }
                    }
                }

                String storage = "";
                boolean validStorage = false;
                while (!validStorage) {
                    interfaz.printText("Ingrese el tipo de almacenamiento (SSD/HDD):");
                    storage = interfaz.readText();
                    if (storage.equalsIgnoreCase("SSD") || storage.equalsIgnoreCase("HDD")) {
                        validStorage = true;
                    } else {
                        interfaz.printText("Tipo de almacenamiento no válido. Por favor, ingrese SSD o HDD.");
                    }
                }

                boolean hasGPU = false;

                interfaz.printText("¿Tiene tarjeta de video? (S/N):");
                String hasGPUStr = interfaz.readText();

                if (hasGPUStr != null && hasGPUStr.equalsIgnoreCase("S")) {
                    hasGPU = true;
                }

                String gpu = hasGPU ? "GPU disponible" : "Sin GPU";
                if (typeFamily.equalsIgnoreCase("Portables")) {
                    interfaz.printText("Ingrese el peso de la familia:");
                    String weight = interfaz.readText();

                    interfaz.printText("Ingrese la capacidad de la batería:");
                    int battery = Integer.parseInt(interfaz.readText());
                    if (expecificFamilly != null) {
                        expecificFamillyGestor.registerExpecificFamilly(new ExpecificFamilly(typeFamily, sticks, battery, weight, ramList, storage, gpu, expecificFamilly));
                        interfaz.printText("Familia registrada con éxito: " + typeFamily);
                    }
                } else {
                    if (expecificFamilly != null) {
                        expecificFamillyGestor.registerExpecificFamilly(new ExpecificFamilly(typeFamily, sticks, ramList, storage, gpu, expecificFamilly));
                        interfaz.printText("Familia registrada con éxito: " + typeFamily);
                    } else {
                        boolean famillyExist = famillyGestor.doesFamilyExist(typeFamily);
                        interfaz.printText(String.valueOf(famillyExist));
                        if (famillyExist) {
                            interfaz.printText("Error, ya se ha registrado la familia " + typeFamily);
                        } else {
                            famillyGestor.registerFamily(new Familly(typeFamily, sticks, ramList, storage, gpu));
                            interfaz.printText("Familia registrada con éxito: " + typeFamily);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }

    public static void showProduct(UI interfaz) {
        List<Product> products = productGestor.showProduct();
        List<Ram> rams = ramGestor.showRam();
        List<Storage> storages = storageGestor.showStorage();
        int count = 0;

        for (Product product : products) {
            count++;
            interfaz.printText("Producto " + count + ":");
            interfaz.printText("Nombre de el componente: " + product.getName());
            interfaz.printText("Precio del componente: " + product.getPrice());
            interfaz.printText("Codigo del componente: " + product.getCode());
            interfaz.printText("Tipo del componente: " + product.getProductType());
            if (product.getRating() != 0.0) {
                interfaz.printText("Rating del componente: " + product.getRating());
            }
            interfaz.printText("");
        }
        for (Storage storage : storages) {
            count++;
            interfaz.printText("Producto " + count + ":");
            interfaz.printText("Nombre de el componente: " + storage.getName());
            interfaz.printText("Precio del componente: " + storage.getPrice());
            interfaz.printText("Codigo del componente: " + storage.getCode());
            interfaz.printText("Raiting del componente: " + storage.getRating());
            interfaz.printText("Tipo del componente: " + storage.getProductType());
            interfaz.printText("Tamaño del almacenamiento: " + storage.getStorageSpace());
            interfaz.printText("Tipo de almacenamiento: " + storage.getTypeStorage());
            interfaz.printText("");
        }
        for (Ram ram : rams) {
            count++;
            interfaz.printText("Producto " + count + ":");
            interfaz.printText("Nombre de el componente: " + ram.getName());
            interfaz.printText("Precio del componente: " + ram.getPrice());
            interfaz.printText("Codigo del componente: " + ram.getCode());
            interfaz.printText("Tipo del componente: " + ram.getProductType());
            interfaz.printText("Raiting del componente: " + ram.getRating());
            interfaz.printText("GBs del ram: " + ram.getGBs());
            interfaz.printText("");
        }

        if (count == 0) {
            interfaz.printText("No hay componentes registrados.");
        }
    }
    public static void addComponentToFamilly(UI interfaz) {
        try {
            List<Product> productList = productGestor.showProduct();
            List<Ram> ramList = ramGestor.showRam();
            List<Storage> storageList = storageGestor.showStorage();
            List<Familly> famillyList = famillyGestor.showFamilies();
            List<ExpecificFamilly> expecificFamillyList = expecificFamillyGestor.showExpecificFamilies();

            if (famillyList.isEmpty() && expecificFamillyList.isEmpty()) {
                interfaz.printText("No hay familias disponibles. No se pueden agregar componentes.");
                return;
            }

            if (productList.isEmpty() && ramList.isEmpty() && storageList.isEmpty()) {
                interfaz.printText("No hay componentes disponibles.");
                return;
            }

            interfaz.printText("¿Qué tipo de familia desea seleccionar?");
            interfaz.printText("1. Familia");
            interfaz.printText("2. Familia Específica");
            interfaz.printText("Ingrese el número correspondiente:");
            int selectedFamilyType = Integer.parseInt(interfaz.readText());

            if (selectedFamilyType == 1) {
                addComponentToFamilly1(interfaz, productList, ramList, storageList, famillyList);
            } else if (selectedFamilyType == 2) {
                addComponentToExpecificFamilly1(interfaz, productList, ramList, storageList, expecificFamillyList);
            } else {
                interfaz.printText("Opción no válida. Por favor, seleccione 1 o 2.");
            }
        } catch (Exception e) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }
    public static void addComponentToFamilly1(UI interfaz, List<Product> productList, List<Ram> ramList, List<Storage> storageList, List<Familly> famillyList) {
        try {
            if (famillyList.isEmpty()) {
                interfaz.printText("No hay familias disponibles. No se pueden agregar componentes.");
                return;
            }
            interfaz.printText("Componentes disponibles:");
            int count = 1;

            if (!productList.isEmpty()) {
                for (Product product : productList) {
                    interfaz.printText(count + ". " + product.getName());
                    count++;
                }
            }

            if (!ramList.isEmpty()) {
                for (Ram ram : ramList) {
                    interfaz.printText(count + ". " + ram.getName());
                    count++;
                }
            }

            if (!storageList.isEmpty()) {
                for (Storage storage : storageList) {
                    interfaz.printText(count + ". " + storage.getName());
                    count++;
                }
            }

            interfaz.printText("Ingrese el número del componente a agregar:");
            int selectedComponentIndex = Integer.parseInt(interfaz.readText()) - 1;

            boolean componentFound = false;

            if (selectedComponentIndex < productList.size()) {
                Product selectedProduct = productList.get(selectedComponentIndex);
                String selectedFamily = selectFamily(interfaz);

                if (selectedFamily != null) {
                    famillyGestor.addProductToFamily(selectedProduct, selectedFamily);
                    interfaz.printText("El producto " + selectedProduct.getName() + " fue agregado a la familia exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("La familia seleccionada no es válida. No se puede agregar el componente.");
                }
            } else if (selectedComponentIndex < productList.size() + ramList.size()) {
                Ram selectedRam = ramList.get(selectedComponentIndex - productList.size());
                String selectedFamily = selectFamily(interfaz);

                if (selectedFamily != null) {
                    famillyGestor.addRamToFamily (selectedRam, selectedFamily);
                    interfaz.printText("La memoria RAM " + selectedRam.getName() + " fue agregada a la familia exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("La familia seleccionada no es válida. No se puede agregar el componente.");
                }
            } else if (selectedComponentIndex < productList.size() + ramList.size() + storageList.size()) {
                Storage selectedStorage = storageList.get(selectedComponentIndex - productList.size() - ramList.size());
                String selectedFamily = selectFamily(interfaz);

                if (selectedFamily != null) {
                    famillyGestor.addStorageToFamily(selectedStorage, selectedFamily);
                    interfaz.printText("El almacenamiento " + selectedStorage.getName() + " fue agregado a la familia exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("La familia seleccionada no es válida. No se puede agregar el componente.");
                }
            }

            if (!componentFound) {
                interfaz.printText("El componente ingresado no es válido o no se encuentra en la lista.");
            }
        } catch (Exception e) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }
    private static String selectFamily(UI interfaz) throws Exception {
        List<Familly> famillyList = famillyGestor.showFamilies();
        interfaz.printText("Familias disponibles:");
        for (int i = 0; i < famillyList.size(); i++) {
            interfaz.printText((i + 1) + ". " + famillyList.get(i).getTypeFamily());
        }
        interfaz.printText("Seleccione el número de la familia:");
        int selectedFamilyIndex = Integer.parseInt(interfaz.readText()) - 1;
        if (selectedFamilyIndex >= 0 && selectedFamilyIndex < famillyList.size()) {
            return famillyList.get(selectedFamilyIndex).getTypeFamily();
        } else {
            return null;
        }
    }
    private static void deleteComponent(UI interfaz) throws Exception {

        List<Product> productList = productGestor.showProduct();
        List<Ram> ramList = ramGestor.showRam();
        List<Storage> storageList = storageGestor.showStorage();
        if (productList.isEmpty() && ramList.isEmpty() && storageList.isEmpty()){
            interfaz.printText("No hay compononentes disponibles");
            return;
        }
        interfaz.printText("Componentes disponibles:");
        int count = 1;
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                interfaz.printText(count + ". " + product.getName());
                count++;
            }
        }
        if (!ramList.isEmpty()) {
            for (Ram ram : ramList) {
                interfaz.printText(count + ". " + ram.getName());
                count++;
            }
        }
        if (!storageList.isEmpty()) {
            for (Storage storage : storageList) {
                interfaz.printText(count + ". " + storage.getName());
                count++;
            }
        }

        interfaz.printText("Ingrese el número del componente a eliminar:");
        int selectedComponentIndex = Integer.parseInt(interfaz.readText()) - 1;

        boolean componentFound = false;
        if (selectedComponentIndex < productList.size()) {
            Product selectedProduct = productList.get(selectedComponentIndex);
            try {
                boolean deleted = productGestor.removeProduct(selectedProduct.getCode());
                if (deleted) {
                    interfaz.printText("Producto eliminado exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("No se pudo eliminar el producto.");
                }
            } catch (Exception e) {
                interfaz.printText("Error al eliminar el producto: " + e.getMessage());
            }
        }
        else if (selectedComponentIndex < productList.size() + ramList.size()) {
            Ram selectedRam = ramList.get(selectedComponentIndex - productList.size());
            try {
                boolean deleted = ramGestor.removeRam(selectedRam.getCode());
                if (deleted) {
                    interfaz.printText("RAM eliminada exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("No se pudo eliminar la RAM.");
                }
            } catch (Exception e) {
                interfaz.printText("Error al eliminar la RAM: " + e.getMessage());
            }
        }
        else if (selectedComponentIndex < productList.size() + ramList.size() + storageList.size()) {
            Storage selectedStorage = storageList.get(selectedComponentIndex - productList.size() - ramList.size());
            try {
                boolean deleted = storageGestor.removeStorage(selectedStorage.getCode());
                if (deleted) {
                    interfaz.printText("Almacenamiento eliminado exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("No se pudo eliminar el almacenamiento.");
                }
            } catch (Exception e) {
                interfaz.printText("Error al eliminar el almacenamiento: " + e.getMessage());
            }
        }

        if (!componentFound) {
            interfaz.printText("Selección inválida.");
        }
    }

    public static void addComponentToExpecificFamilly1(UI interfaz, List<Product> productList, List<Ram> ramList, List<Storage> storageList, List<ExpecificFamilly> ExpecificFamilyList) {
        try {
            if (ExpecificFamilyList.isEmpty()) {
                interfaz.printText("No hay familias disponibles. No se pueden agregar componentes.");
                return;
            }
            interfaz.printText("Componentes disponibles:");
            int count = 1;

            if (!productList.isEmpty()) {
                for (Product product : productList) {
                    interfaz.printText(count + ". " + product.getName());
                    count++;
                }
            }

            if (!ramList.isEmpty()) {
                for (Ram ram : ramList) {
                    interfaz.printText(count + ". " + ram.getName());
                    count++;
                }
            }

            if (!storageList.isEmpty()) {
                for (Storage storage : storageList) {
                    interfaz.printText(count + ". " + storage.getName());
                    count++;
                }
            }

            interfaz.printText("Ingrese el número del componente a agregar:");
            int selectedComponentIndex = Integer.parseInt(interfaz.readText()) - 1;

            boolean componentFound = false;
            String selectedFamily = selectExpecificFamily(interfaz);
            if (selectedComponentIndex < productList.size()) {
                Product selectedProduct = productList.get(selectedComponentIndex);

                if (selectedFamily != null) {
                    expecificFamillyGestor.addProductToExpecificFamily(selectedProduct, selectedFamily);
                    interfaz.printText("El producto " + selectedProduct.getName() + " fue agregado a la familia exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("La familia seleccionada no es válida. No se puede agregar el componente.");
                }
            } else if (selectedComponentIndex < productList.size() + ramList.size()) {
                Ram selectedRam = ramList.get(selectedComponentIndex - productList.size());


                if (selectedFamily != null) {
                    expecificFamillyGestor.addRamToExpecificFamily(selectedRam, selectedFamily);
                    interfaz.printText("La memoria RAM " + selectedRam.getName() + " fue agregada a la familia exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("La familia seleccionada no es válida. No se puede agregar el componente.");
                }
            } else if (selectedComponentIndex < productList.size() + ramList.size() + storageList.size()) {
                Storage selectedStorage = storageList.get(selectedComponentIndex - productList.size() - ramList.size());

                if (selectedFamily != null) {
                    expecificFamillyGestor.addStorageToExpecificFamily(selectedStorage, selectedFamily);
                    interfaz.printText("El almacenamiento " + selectedStorage.getName() + " fue agregado a la familia exitosamente.");
                    componentFound = true;
                } else {
                    interfaz.printText("La familia seleccionada no es válida. No se puede agregar el componente.");
                }
            }

            if (!componentFound) {
                interfaz.printText("El componente ingresado no es válido o no se encuentra en la lista.");
            }
        } catch (Exception e) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }
    private static String selectExpecificFamily(UI interfaz) throws Exception {
        List<ExpecificFamilly> expecificFamilyList = expecificFamillyGestor.showExpecificFamilies();
        interfaz.printText("Familias específicas disponibles:");
        for (int i = 0; i < expecificFamilyList.size(); i++) {
            interfaz.printText((i + 1) + ". " + expecificFamilyList.get(i).getExpecificFamily());
        }
        interfaz.printText("Seleccione el número de la familia específica: ");
        int selectedFamilyIndex = Integer.parseInt(interfaz.readText()) - 1;
        if (selectedFamilyIndex >= 0 && selectedFamilyIndex < expecificFamilyList.size()) {
            return expecificFamilyList.get(selectedFamilyIndex).getExpecificFamily();
        } else {
            return null;
        }
    }


    private static void showFamily(UI interfaz){
        List<Familly> families = famillyGestor.showFamilies();
        List<ExpecificFamilly> specificFamilies = expecificFamillyGestor.showExpecificFamilies();
        int count = 0;

        for (Familly family : families) {
            count++;
            interfaz.printText("Familia " + count + ":");
            interfaz.printText("Tipo de familia: " + family.getTypeFamily());
            interfaz.printText("Cantidad máxima de sticks: " + family.getSticks());
            interfaz.printText("RAM aceptada por la familia: " + family.getRamList());
            interfaz.printText("Tipo de almacenamiento: " + family.getStorage());
            interfaz.printText("GPU: " + family.getGpu());
            List<Product> productList = family.getProductList();
            List<Ram> ramProductList = family.getRamProductList();
            List<Storage> storageList = family.getStorageList();

            if (!(productList.isEmpty() && ramProductList.isEmpty() && storageList.isEmpty())) {
                interfaz.printText("Productos aceptados por la familia: ");

                if (!productList.isEmpty()) {
                    for (Product product : productList) {
                        interfaz.printText("- " + product.getName());
                    }
                }

                if (!ramProductList.isEmpty()) {
                    for (Ram ram : ramProductList) {
                        interfaz.printText("- " + ram.getName());
                    }
                }

                if (!storageList.isEmpty()) {
                    for (Storage storage : storageList) {
                        interfaz.printText("- " + storage.getName());
                    }
                }
            }


            interfaz.printText("");
        }

        for (ExpecificFamilly specificFamily : specificFamilies) {
            count++;
            interfaz.printText("Familia " + count + ":");
            interfaz.printText("Tipo de familia: " + specificFamily.getTypeFamily());
            interfaz.printText("Cantidad máxima de sticks: " + specificFamily.getSticks());
            interfaz.printText("RAM aceptada por la familia: " + specificFamily.getRamList());
            interfaz.printText("Tipo de almacenamiento: " + specificFamily.getStorage());
            interfaz.printText("GPU: " + specificFamily.getGpu());
            interfaz.printText("Especificación: " + specificFamily.getExpecificFamily());
            if (specificFamily.getTypeFamily().equalsIgnoreCase("Portables")) {
                interfaz.printText("Peso: " + specificFamily.getWeight());
                interfaz.printText("Capacidad de la batería: " + specificFamily.getBattery());
            }
            List<Product> productList = specificFamily.getProductList();
            List<Ram> ramProductList = specificFamily.getRamProductList();
            List<Storage> storageList = specificFamily.getStorageList();

            if (!(productList.isEmpty() && ramProductList.isEmpty() && storageList.isEmpty())) {
                interfaz.printText("Productos aceptados por la familia: ");

                if (!productList.isEmpty()) {
                    for (Product product : productList) {
                        interfaz.printText("- " + product.getName());
                    }
                }

                if (!ramProductList.isEmpty()) {
                    for (Ram ram : ramProductList) {
                        interfaz.printText("- " + ram.getName());
                    }
                }

                if (!storageList.isEmpty()) {
                    for (Storage storage : storageList) {
                        interfaz.printText("- " + storage.getName());
                    }
                }
            }

            interfaz.printText("");
        }

        if (count == 0) {
            interfaz.printText("No hay familias registradas.");
        }
    }

    public static void deleteFamily(UI interfaz) {
        try {
            List<Familly> familyList = famillyGestor.showFamilies();
            List<ExpecificFamilly> expecificFamilyList = expecificFamillyGestor.showExpecificFamilies();

            if (familyList.isEmpty() && expecificFamilyList.isEmpty()) {
                interfaz.printText("No hay familias disponibles.");
                return;
            }

            interfaz.printText("¿Qué tipo de familia desea seleccionar para eliminar?");
            interfaz.printText("1. Familia");
            interfaz.printText("2. Familia Específica");
            interfaz.printText("Ingrese el número correspondiente:");
            int selectedFamilyType = Integer.parseInt(interfaz.readText());

            if (selectedFamilyType == 1 && !familyList.isEmpty()) {
                String selectedFamily = selectFamily(interfaz);
                boolean deleted = famillyGestor.deleteFamily(selectedFamily);

                if (deleted) {
                    interfaz.printText("Familia eliminada correctamente: " + selectedFamily);
                } else {
                    interfaz.printText("No se pudo eliminar la familia: " + selectedFamily);
                }
            } else if (selectedFamilyType == 2 && !expecificFamilyList.isEmpty()) {
                String selectedExpecificFamily = selectExpecificFamily(interfaz);
                boolean deleted = expecificFamillyGestor.deleteExpecificFamily(selectedExpecificFamily);

                if (deleted) {
                    interfaz.printText("Familia específica eliminada correctamente: " + selectedExpecificFamily);
                } else {
                    interfaz.printText("No se pudo eliminar la familia específica: " + selectedExpecificFamily);
                }
            } else {
                interfaz.printText("No hay familias del tipo seleccionado.");
            }
        } catch (Exception e) {
            interfaz.printText("Se produjo un error. Por favor, inténtelo de nuevo.");
        }
    }

    private static void updateComponent(UI interfaz) throws Exception {
        interfaz.printText("Componentes disponibles:");
        List<Product> productList = productGestor.showProduct();
        List<Ram> ramList = ramGestor.showRam();
        List<Storage> storageList = storageGestor.showStorage();
        if (productList.isEmpty() && ramList.isEmpty() && storageList.isEmpty()){
            interfaz.printText("No hay compononentes disponibles");
            return;
        }


        int count = 1;
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                interfaz.printText(count + ". " + product.getName());
                count++;
            }
        }
        if (!ramList.isEmpty()) {
            for (Ram ram : ramList) {
                interfaz.printText(count + ". " + ram.getName());
                count++;
            }
        }
        if (!storageList.isEmpty()) {
            for (Storage storage : storageList) {
                interfaz.printText(count + ". " + storage.getName());
                count++;
            }
        }

        interfaz.printText("Ingrese el número del componente a actualizar:");
        int selectedComponentIndex = Integer.parseInt(interfaz.readText()) - 1;
        if (selectedComponentIndex < 0 || selectedComponentIndex >= (productList.size() + ramList.size() + storageList.size())) {
            interfaz.printText("Selección inválida. Por favor, seleccione un número válido.");
            return;
        }
        float rating;
        interfaz.printText("Ingrese el nuevo nombre del producto:");
        String newName = interfaz.readText();
        interfaz.printText("Ingrese el nuevo precio del producto:");
        float newPrice = Float.parseFloat(interfaz.readText());
        boolean success;
        if (selectedComponentIndex < productList.size()) {

            Product selectedProduct = productList.get(selectedComponentIndex);
            interfaz.printText("Ingrese el rating del producto:");
            rating = Float.parseFloat(interfaz.readText());
            success = productGestor.updateProduct(new Product(selectedProduct.getCode(), newName, newPrice, selectedProduct.getProductType(), rating));
            if (success){interfaz.printText(selectedProduct.getName() + " fue actualizado con exito." );}

        } else if (selectedComponentIndex < productList.size() + ramList.size()) {

            Ram selectedRam = ramList.get(selectedComponentIndex - productList.size());
            interfaz.printText("Ingrese el rating del producto:");
            rating = Float.parseFloat(interfaz.readText());
            interfaz.printText("Ingrese cuántas GBs posee la ram:");
            int GBs = Integer.parseInt(interfaz.readText());
            success = ramGestor.updateRam(new Ram(selectedRam.getCode(), newName, newPrice, GBs, selectedRam.getProductType(), rating));
            if (success){interfaz.printText(selectedRam.getName() + " fue actualizado con exito." );}
        } else if (selectedComponentIndex < productList.size() + ramList.size() + storageList.size()) {

            Storage selectedStorage = storageList.get(selectedComponentIndex - productList.size() - ramList.size());
            interfaz.printText("Ingrese el rating del producto:");
            rating = Float.parseFloat(interfaz.readText());
            interfaz.printText("Tipo de almacenamiento (SSD, HDD):");
            String TypeStorage = interfaz.readText().toUpperCase();
            if (TypeStorage.equals("SSD") || TypeStorage.equals("HDD")) {
                interfaz.printText("Cantidad de almacenamiento(GBs):");
                int StorageSpace = Integer.parseInt(interfaz.readText());
                success = storageGestor.updateStorage(new Storage(selectedStorage.getCode(), newName, newPrice, selectedStorage.getProductType(), rating, TypeStorage, StorageSpace));
                if (success){interfaz.printText(selectedStorage.getName() + " fue actualizado con exito." );}
            } else {
                interfaz.printText("Tipo de almacenamiento no válido. Por favor, ingrese SSD o HDD.");
            }

        }
    }

    private static void updateFamilly(UI interfaz) throws Exception {
        interfaz.printText("Familias disponibles:");
        List<Familly> famillyList = famillyGestor.showFamilies();
        List<ExpecificFamilly> expecificFamillyList = expecificFamillyGestor.showExpecificFamilies();

        if (famillyList.isEmpty() && expecificFamillyList.isEmpty()){
            interfaz.printText("No hay familias disponibles");
            return;
        }

        int count = 1;
        if (!famillyList.isEmpty()) {
            for (Familly familly : famillyList) {
                interfaz.printText(count + ". " + familly.getTypeFamily());
                count++;
            }
        }
        if (!expecificFamillyList.isEmpty()) {
            for (ExpecificFamilly expecificFamilly : expecificFamillyList) {
                interfaz.printText(count + ". " + expecificFamilly.getExpecificFamily());
                count++;
            }
        }

        interfaz.printText("Ingrese el número de la familia a actualizar:");
        int selectedComponentIndex = Integer.parseInt(interfaz.readText()) - 1;

        if (selectedComponentIndex < 0 || selectedComponentIndex >= (famillyList.size() + expecificFamillyList.size())) {
            interfaz.printText("Selección inválida. Por favor, seleccione un número válido.");
            return;
        }

        interfaz.printText("Ingrese la cantidad máxima de sticks para la familia:");
        int newSticks = Integer.parseInt(interfaz.readText());

        List<Integer> ramList = new ArrayList<>();
        boolean addAnotherRam = true;

        while (addAnotherRam) {
            interfaz.printText("Ingrese la  RAM aceptada por la familia:");
            int ram = Integer.parseInt(interfaz.readText());
            ramList.add(ram);

            interfaz.printText("¿Desea agregar RAM? (S/N):");
            String addMoreRam = interfaz.readText();

            if (addMoreRam != null) {
                if (addMoreRam.equalsIgnoreCase("N")) {
                    addAnotherRam = false;
                } else if (!addMoreRam.equalsIgnoreCase("S")) {
                    interfaz.printText("Error: Por favor, ingrese S o N.");
                }
            }
        }

        String newStorage = "";
        boolean validStorage = false;
        while (!validStorage) {
            interfaz.printText("Ingrese el tipo de almacenamiento (SSD/HDD):");
            newStorage = interfaz.readText();
            if (newStorage.equalsIgnoreCase("SSD") || newStorage.equalsIgnoreCase("HDD")) {
                validStorage = true;
            } else {
                interfaz.printText("Tipo de almacenamiento no válido. Por favor, ingrese SSD o HDD.");
            }
        }

        boolean hasGPU = false;

        interfaz.printText("¿Tiene tarjeta de video? (S/N):");
        String hasGPUStr = interfaz.readText();

        if (hasGPUStr != null && hasGPUStr.equalsIgnoreCase("S")) {
            hasGPU = true;
        }

        String gpu = hasGPU ? "GPU disponible" : "Sin GPU";
        boolean success;
        if (selectedComponentIndex < famillyList.size()) {
            Familly selectedFamilly = famillyList.get(selectedComponentIndex);
            success = famillyGestor.updateFamily(new Familly(selectedFamilly.getTypeFamily(), newSticks, ramList, newStorage, selectedFamilly.getGpu()));
            if (success) {
                interfaz.printText(selectedFamilly.getTypeFamily() + " fue actualizada con éxito.");
            }
        } else if (selectedComponentIndex < famillyList.size() + expecificFamillyList.size()) {
            ExpecificFamilly selectedExpecificFamilly = expecificFamillyList.get(selectedComponentIndex - famillyList.size());
            if (selectedExpecificFamilly.getTypeFamily().equalsIgnoreCase("Portables")) {
                interfaz.printText("Ingrese el peso de la familia:");
                String newWeight = interfaz.readText();

                interfaz.printText("Ingrese la capacidad de la batería:");
                int newBattery = Integer.parseInt(interfaz.readText());
                success = expecificFamillyGestor.updateExpecificFamily(new ExpecificFamilly(selectedExpecificFamilly.getTypeFamily(), newSticks, newBattery, newWeight, ramList, newStorage, gpu, selectedExpecificFamilly.getExpecificFamily()));
                if (success) {
                    interfaz.printText(selectedExpecificFamilly.getTypeFamily() + " fue actualizada con éxito.");
                }
            }else{
                success = expecificFamillyGestor.updateExpecificFamily(new ExpecificFamilly(selectedExpecificFamilly.getTypeFamily(), newSticks, ramList, newStorage, gpu, selectedExpecificFamilly.getExpecificFamily()));
                if (success) {
                    interfaz.printText(selectedExpecificFamilly.getTypeFamily() + " fue actualizada con éxito.");
                }
            }
        }
    }
    private static void ClientsPC(UI interfaz) throws Exception {
        interfaz.printText("Familias disponibles:");
        List<Familly> famillyList = famillyGestor.showFamilies();
        List<ExpecificFamilly> expecificFamillyList = expecificFamillyGestor.showExpecificFamilies();


        if (famillyList.isEmpty() && expecificFamillyList.isEmpty()) {
            interfaz.printText("No hay familias disponibles");
            return;
        }

        int count = 1;
        if (!famillyList.isEmpty()) {
            for (Familly familly : famillyList) {
                interfaz.printText(count + ". " + familly.getTypeFamily());
                count++;
            }
        }
        if (!expecificFamillyList.isEmpty()) {
            for (ExpecificFamilly expecificFamilly : expecificFamillyList) {
                interfaz.printText(count + ". " + expecificFamilly.getExpecificFamily());
                count++;
            }
        }

        interfaz.printText("Ingrese el número de la familia a actualizar:");
        int selectedComponentIndex = Integer.parseInt(interfaz.readText()) - 1;

        if (selectedComponentIndex < 0 || selectedComponentIndex >= (famillyList.size() + expecificFamillyList.size())) {
            interfaz.printText("Selección inválida. Por favor, seleccione un número válido.");
            return;
        }

        if (selectedComponentIndex < famillyList.size()) {
            Familly selectedFamilly = famillyList.get(selectedComponentIndex);
            List<Product> familiProduct = selectedFamilly.getProductList();
            interfaz.printText("Seleccione un procesador:");
            for (Product producto : familiProduct) {
                if (producto.getProductType().equals("Procesador")) {
                    interfaz.printText(" Nombre: " + producto.getName() + " ID: " + producto.getCode());
                }
            }

            interfaz.printText("Ingrese el código del procesador seleccionado:");
            int selectedProcessorCode = Integer.parseInt(interfaz.readText());
            Product selectedProcessor = null;
            for (Product producto : familiProduct) {
                if (producto.getProductType().equals("Procesador") && producto.getCode() == selectedProcessorCode) {
                    selectedProcessor = producto;
                    break;
                }
            }
            if (selectedProcessor != null) {
                interfaz.printText("Seleccione una tarjeta de video:");
                for (Product producto : familiProduct) {
                    if (producto.getProductType().equals("Tarjeta de video.")) {
                        interfaz.printText(" Nombre: " + producto.getName() + " ID: " + producto.getCode());
                    }
                }

                interfaz.printText("Ingrese el código de la tarjeta de video seleccionada:");
                int selectedGraphicsCardCode = Integer.parseInt(interfaz.readText());

                Product selectedGraphicsCard = null;
                for (Product producto : familiProduct) {
                    if (producto.getProductType().equals("Tarjeta de video.") && producto.getCode() == selectedGraphicsCardCode) {
                        selectedGraphicsCard = producto;
                        break;
                    }
                }
                if (selectedGraphicsCard != null) {
                    interfaz.printText("Seleccione una Tarjeta Madre:");
                    for (Product producto : familiProduct) {
                        if (producto.getProductType().equals("Tarjeta Madre")) {
                            interfaz.printText("Nombre: " + producto.getName() + " ID: " + producto.getCode());
                        }
                    }

                    interfaz.printText("Ingrese el código de la Tarjeta Madre seleccionada:");
                    int selectedMotherboardCode = Integer.parseInt(interfaz.readText());

                    Product selectedMotherboard = null;
                    for (Product producto : familiProduct) {
                        if (producto.getProductType().equals("Tarjeta Madre") && producto.getCode() == selectedMotherboardCode) {
                            selectedMotherboard = producto;
                            break;
                        }
                    }
                    if (selectedMotherboard != null) {
                        interfaz.printText("Seleccione una Fuente de Poder:");
                        for (Product producto : familiProduct) {
                            if (producto.getProductType().equals("Fuente de poder")) {
                                interfaz.printText("Nombre: " + producto.getName() + " ID: " + producto.getCode());
                            }
                        }

                        interfaz.printText("Ingrese el código de la Fuente de Poder seleccionada:");
                        int selectedPowerSupplyCode = Integer.parseInt(interfaz.readText());

                        Product selectedPowerSupply = null;
                        for (Product producto : familiProduct) {
                            if (producto.getProductType().equals("Fuente de poder") && producto.getCode() == selectedPowerSupplyCode) {
                                selectedPowerSupply = producto;
                                break;
                            }
                        }
                        if (selectedPowerSupply != null) {
                            List<Ram> familiRam = selectedFamilly.getRamProductList();
                            interfaz.printText("Seleccione una RAM:");
                            for (Ram ram : familiRam) {
                                interfaz.printText("Nombre: " + ram.getName() + " ID: " + ram.getCode());
                            }

                            interfaz.printText("Ingrese el código de la RAM seleccionada:");
                            int selectedRamCode = Integer.parseInt(interfaz.readText());

                            Ram selectedRam = null;
                            for (Ram ram : familiRam) {
                                if (ram.getCode() == selectedRamCode) {
                                    selectedRam = ram;
                                    break;
                                }
                            }

                            if (selectedRam != null) {
                                List<Storage> familiStorage = selectedFamilly.getStorageList();
                                interfaz.printText("Seleccione un Almacenamiento:");
                                for (Storage storage : familiStorage) {
                                    interfaz.printText("Nombre: " + storage.getName() + " ID: " + storage.getCode());
                                }

                                interfaz.printText("Ingrese el código del Almacenamiento seleccionado:");
                                int selectedStorageCode = Integer.parseInt(interfaz.readText());

                                Storage selectedStorage = null;
                                for (Storage storage : familiStorage) {
                                    if (storage.getCode() == selectedStorageCode) {
                                        selectedStorage = storage;
                                        break;
                                    }
                                }

                                if (selectedStorage != null) {
                                    interfaz.printText("Desea realizar la compra de una PC con los siguientes componentes:");
                                    interfaz.printText("Procesador: " + selectedProcessor.getName() + " Precio: " + selectedProcessor.getPrice());
                                    interfaz.printText("Tarjeta de Video: " + selectedGraphicsCard.getName() + " Precio: " + selectedGraphicsCard.getPrice());
                                    interfaz.printText("Tarjeta Madre: " + selectedMotherboard.getName() + " Precio: " + selectedMotherboard.getPrice());
                                    interfaz.printText("Fuente de Poder: " + selectedPowerSupply.getName() + " Precio: " + selectedPowerSupply.getPrice());
                                    interfaz.printText("RAM: " + selectedRam.getName() + " Precio: " + selectedRam.getPrice());
                                    interfaz.printText("Almacenamiento: " + selectedStorage.getName() + " Precio: " + selectedStorage.getPrice());
                                    float TotalPrice = selectedRam.getPrice() + selectedMotherboard.getPrice() + selectedProcessor.getPrice() + selectedStorage.getPrice() + selectedPowerSupply.getPrice() + selectedGraphicsCard.getPrice();
                                    interfaz.printText("Precio total: " + TotalPrice);
                                    interfaz.printText("");
                                    interfaz.printText("Confirmar la compra (S/N):");
                                    String confirmacionCompra = interfaz.readText().toUpperCase();

                                    if (confirmacionCompra.equals("S")) {
                                        interfaz.printText("Por favor, ingrese a nombre de quien sera la factura:");
                                        String clientName = interfaz.readText();

                                        interfaz.printText("Por favor, ingrese el apellido  de " + clientName);
                                        String clientLastName = interfaz.readText();

                                        interfaz.printText("Por favor, ingrese el numero de telefono de " + clientName + " " + clientLastName);
                                        String clientPhoneNumber = interfaz.readText();
                                        int billNumber = billGestor.generateUniqueBillNumber();
                                        billGestor.createBill(new Bill(billNumber, clientName, clientLastName, clientPhoneNumber, selectedRam.getName(), selectedProcessor.getName(), selectedStorage.getName(), selectedPowerSupply.getName(), selectedMotherboard.getName(), selectedGraphicsCard.getName(), TotalPrice));
                                        interfaz.printText("Compra realizada con éxito. ¡Gracias por su compra! el numero de su factura es: " + billNumber);
                                    } else if (confirmacionCompra.equals("N")) {
                                        interfaz.printText("Compra cancelada.");
                                    } else {
                                        interfaz.printText("Respuesta no válida. Por favor, responda S para confirmar o N para cancelar.");
                                    }
                                } else {
                                    interfaz.printText("Código de Almacenamiento inválido. Por favor, seleccione un código válido.");
                                    return;
                                }
                            } else {
                                interfaz.printText("Código de RAM inválido. Por favor, seleccione un código válido.");
                                return;
                            }
                        } else {
                            interfaz.printText("Código de Fuente de Poder inválido. Por favor, seleccione un código válido.");
                            return;
                        }
                    } else {
                        interfaz.printText("Código de Tarjeta Madre inválido. Por favor, seleccione un código válido.");
                        return;
                    }
                } else {
                    interfaz.printText("Código de tarjeta de video inválido. Por favor, seleccione un código válido.");
                    return;
                }
            } else {
                interfaz.printText("Código de procesador inválido. Por favor, seleccione un código válido.");
                return;
            }


        } else if (selectedComponentIndex < famillyList.size() + expecificFamillyList.size()) {
            ExpecificFamilly selectedExpecificFamilly = expecificFamillyList.get(selectedComponentIndex - famillyList.size());
            List<Product> familiProduct = selectedExpecificFamilly.getProductList();
            interfaz.printText("Seleccione un procesador:");
            for (Product producto : familiProduct) {
                if (producto.getProductType().equals("Procesador")) {
                    interfaz.printText(" Nombre: " + producto.getName() + " ID: " + producto.getCode());
                }
            }

            interfaz.printText("Ingrese el código del procesador seleccionado:");
            int selectedProcessorCode = Integer.parseInt(interfaz.readText());
            Product selectedProcessor = null;
            for (Product producto : familiProduct) {
                if (producto.getProductType().equals("Procesador") && producto.getCode() == selectedProcessorCode) {
                    selectedProcessor = producto;
                    break;
                }
            }
            if (selectedProcessor != null) {
                interfaz.printText("Seleccione una tarjeta de video:");
                for (Product producto : familiProduct) {
                    if (producto.getProductType().equals("Tarjeta de video.")) {
                        interfaz.printText(" Nombre: " + producto.getName() + " ID: " + producto.getCode());
                    }
                }

                interfaz.printText("Ingrese el código de la tarjeta de video seleccionada:");
                int selectedGraphicsCardCode = Integer.parseInt(interfaz.readText());

                Product selectedGraphicsCard = null;
                for (Product producto : familiProduct) {
                    if (producto.getProductType().equals("Tarjeta de video.") && producto.getCode() == selectedGraphicsCardCode) {
                        selectedGraphicsCard = producto;
                        break;
                    }
                }
                if (selectedGraphicsCard != null) {
                    interfaz.printText("Seleccione una Tarjeta Madre:");
                    for (Product producto : familiProduct) {
                        if (producto.getProductType().equals("Tarjeta Madre")) {
                            interfaz.printText("Nombre: " + producto.getName() + " ID: " + producto.getCode());
                        }
                    }

                    interfaz.printText("Ingrese el código de la Tarjeta Madre seleccionada:");
                    int selectedMotherboardCode = Integer.parseInt(interfaz.readText());

                    Product selectedMotherboard = null;
                    for (Product producto : familiProduct) {
                        if (producto.getProductType().equals("Tarjeta Madre") && producto.getCode() == selectedMotherboardCode) {
                            selectedMotherboard = producto;
                            break;
                        }
                    }
                    if (selectedMotherboard != null) {
                        interfaz.printText("Seleccione una Fuente de Poder:");
                        for (Product producto : familiProduct) {
                            if (producto.getProductType().equals("Fuente de poder")) {
                                interfaz.printText("Nombre: " + producto.getName() + " ID: " + producto.getCode());
                            }
                        }

                        interfaz.printText("Ingrese el código de la Fuente de Poder seleccionada:");
                        int selectedPowerSupplyCode = Integer.parseInt(interfaz.readText());

                        Product selectedPowerSupply = null;
                        for (Product producto : familiProduct) {
                            if (producto.getProductType().equals("Fuente de poder") && producto.getCode() == selectedPowerSupplyCode) {
                                selectedPowerSupply = producto;
                                break;
                            }
                        }
                        if (selectedPowerSupply != null) {
                            List<Ram> familiRam = selectedExpecificFamilly.getRamProductList();
                            interfaz.printText("Seleccione una RAM:");
                            for (Ram ram : familiRam) {
                                interfaz.printText("Nombre: " + ram.getName() + " ID: " + ram.getCode());
                            }

                            interfaz.printText("Ingrese el código de la RAM seleccionada:");
                            int selectedRamCode = Integer.parseInt(interfaz.readText());

                            Ram selectedRam = null;
                            for (Ram ram : familiRam) {
                                if (ram.getCode() == selectedRamCode) {
                                    selectedRam = ram;
                                    break;
                                }
                            }

                            if (selectedRam != null) {
                                List<Storage> familiStorage = selectedExpecificFamilly.getStorageList();
                                interfaz.printText("Seleccione un Almacenamiento:");
                                for (Storage storage : familiStorage) {
                                    interfaz.printText("Nombre: " + storage.getName() + " ID: " + storage.getCode());
                                }

                                interfaz.printText("Ingrese el código del Almacenamiento seleccionado:");
                                int selectedStorageCode = Integer.parseInt(interfaz.readText());

                                Storage selectedStorage = null;
                                for (Storage storage : familiStorage) {
                                    if (storage.getCode() == selectedStorageCode) {
                                        selectedStorage = storage;
                                        break;
                                    }
                                }

                                if (selectedStorage != null) {
                                    interfaz.printText("Desea realizar la compra de una PC con los siguientes componentes:");
                                    interfaz.printText("Procesador: " + selectedProcessor.getName() + " Precio: " + selectedProcessor.getPrice());
                                    interfaz.printText("Tarjeta de Video: " + selectedGraphicsCard.getName() + " Precio: " + selectedGraphicsCard.getPrice());
                                    interfaz.printText("Tarjeta Madre: " + selectedMotherboard.getName() + " Precio: " + selectedMotherboard.getPrice());
                                    interfaz.printText("Fuente de Poder: " + selectedPowerSupply.getName() + " Precio: " + selectedPowerSupply.getPrice());
                                    interfaz.printText("RAM: " + selectedRam.getName() + " Precio: " + selectedRam.getPrice());
                                    interfaz.printText("Almacenamiento: " + selectedStorage.getName() + " Precio: " + selectedStorage.getPrice());
                                    float TotalPrice = selectedRam.getPrice() + selectedMotherboard.getPrice() + selectedProcessor.getPrice() + selectedStorage.getPrice() + selectedPowerSupply.getPrice() + selectedGraphicsCard.getPrice();
                                    interfaz.printText("Precio total: " + TotalPrice);
                                    interfaz.printText("");
                                    interfaz.printText("Confirmar la compra (S/N):");
                                    String confirmacionCompra = interfaz.readText().toUpperCase();

                                    if (confirmacionCompra.equals("S")) {
                                        interfaz.printText("Por favor, ingrese a nombre de quien sera la factura:");
                                        String clientName = interfaz.readText();

                                        interfaz.printText("Por favor, ingrese el apellido  de " + clientName);
                                        String clientLastName = interfaz.readText();

                                        interfaz.printText("Por favor, ingrese el numero de telefono de " + clientName + " " + clientLastName);
                                        String clientPhoneNumber = interfaz.readText();
                                        int billNumber = billGestor.generateUniqueBillNumber();
                                        interfaz.printText("PASO EL RANDOM! " );
                                        billGestor.createBill(new Bill(billNumber, clientName, clientLastName, clientPhoneNumber, selectedRam.getName(), selectedProcessor.getName(), selectedStorage.getName(), selectedPowerSupply.getName(), selectedMotherboard.getName(), selectedGraphicsCard.getName(), TotalPrice));
                                        interfaz.printText("Compra realizada con éxito. ¡Gracias por su compra! el numero de su factura es: " + billNumber);
                                    } else if (confirmacionCompra.equals("N")) {
                                        interfaz.printText("Compra cancelada.");
                                    } else {
                                        interfaz.printText("Respuesta no válida. Por favor, responda S para confirmar o N para cancelar.");
                                    }
                                } else {
                                    interfaz.printText("Código de Almacenamiento inválido. Por favor, seleccione un código válido para la familia seleccionada.");
                                    return;
                                }
                            } else {
                                interfaz.printText("Código de RAM inválido. Por favor, seleccione un código válido para la familia seleccionada.");
                                return;
                            }
                        } else {
                            interfaz.printText("Código de Fuente de Poder inválido. Por favor, seleccione un código válido para la familia seleccionada.");
                            return;
                        }
                    } else {
                        interfaz.printText("Código de Tarjeta Madre inválido. Por favor, seleccione un código válido para la familia seleccionada.");
                        return;
                    }
                } else {
                    interfaz.printText("Código de tarjeta de video inválido. Por favor, seleccione un código válido para la familia seleccionada.");
                    return;
                }
            } else {
                interfaz.printText("Código de procesador inválido. Por favor, seleccione un código válido para la familia seleccionada.");
                return;
            }
        }
    }
    public static void showBillClient(UI interfaz) throws Exception {
        interfaz.printText("Ingrese el numero de su factura.");
        int billNumber = interfaz.optionReader();

        try {
            Bill factura = billGestor.getBillByNumber(billNumber);

            if (factura != null) {
                interfaz.printText("Factura encontrada:");
                interfaz.printText("Número de factura: " + factura.getBillNumber());
                interfaz.printText("Fecha: " + factura.getDate());
                interfaz.printText("Nombre: " + factura.getName());
                interfaz.printText("Apellido: " + factura.getLastName());
                interfaz.printText("Teléfono: " + factura.getPhoneNumber());
                interfaz.printText("--------------------------------------");
                interfaz.printText("Productos comprados: ");
                interfaz.printText("RAM: " + factura.getRam());
                interfaz.printText("Procesador: " + factura.getProcessor());
                interfaz.printText("Almacenamiento: " + factura.getStorage());
                interfaz.printText("Fuente de Poder: " + factura.getPowerSupply());
                interfaz.printText("Placa Base: " + factura.getMotherboard());
                interfaz.printText("Tarjeta de Video: " + factura.getVideoCard());
                interfaz.printText("--------------------------------------");
                interfaz.printText("Precio Total: " + factura.getPrice());
                interfaz.printText("");
            } else {
                interfaz.printText("La factura con el número " + billNumber + " no fue encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showAllBills(UI interfaz) {
        try {
            List<Bill> facturas = billGestor.showBills();

            if (!facturas.isEmpty()) {
                interfaz.printText("Lista de facturas:");
                for (Bill factura : facturas) {
                    interfaz.printText("Número de factura: " + factura.getBillNumber());
                    interfaz.printText("Fecha: " + factura.getDate());
                    interfaz.printText("Nombre: " + factura.getName());
                    interfaz.printText("Apellido: " + factura.getLastName());
                    interfaz.printText("Teléfono: " + factura.getPhoneNumber());
                    interfaz.printText("--------------------------------------");
                    interfaz.printText("Productos comprados: ");
                    interfaz.printText("RAM: " + factura.getRam());
                    interfaz.printText("Procesador: " + factura.getProcessor());
                    interfaz.printText("Almacenamiento: " + factura.getStorage());
                    interfaz.printText("Fuente de Poder: " + factura.getPowerSupply());
                    interfaz.printText("Placa Base: " + factura.getMotherboard());
                    interfaz.printText("Tarjeta de Video: " + factura.getVideoCard());
                    interfaz.printText("--------------------------------------");
                    interfaz.printText("Precio Total: " + factura.getPrice());
                    interfaz.printText("");

                }
            } else {
                interfaz.printText("No hay facturas registradas.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
