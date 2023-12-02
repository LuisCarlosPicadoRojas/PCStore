package ProyectoPOO.Main.TL;

import ProyectoPOO.Main.BL.entities.*;
import ProyectoPOO.Main.BL.logic.*;
import ProyectoPOO.Main.Memory.*;
import ProyectoPOO.UI.UI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ProyectoPOO.Utils.Utils.generateRandomCode;


public class Controller {
    private final UI interfaz;
    private static productGestor productGestor;
    private static ramGestor ramGestor;
    private static storageGestor storageGestor;
    private static famillyGestor famillyGestor;
    private static expecificFamillyGestor expecificFamillyGestor;
    private static Connection connection;


    public Controller() throws SQLException {
        connection = DAO.getConnection();
        interfaz = new UI();
        productGestor = new productGestor(new DAOProduct(connection));
        ramGestor = new ramGestor(new DAORam(connection));
        storageGestor = new storageGestor(new DAOStorage(connection));
        famillyGestor = new famillyGestor(new DAOFamilly(connection));
        expecificFamillyGestor = new expecificFamillyGestor(new DAOExpecificFamilly(connection));
    }
    public void start() throws Exception{
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
                        interfaz.printText("Hasta luego.");
                        showMenu = false;
                        break;

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


            int code = generateRandomCode();
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
                interfaz.printText("No hay productos disponibles.");
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
        interfaz.printText("Seleccione el número de la familia para agregar el componente:");
        int selectedFamilyIndex = Integer.parseInt(interfaz.readText()) - 1;
        if (selectedFamilyIndex >= 0 && selectedFamilyIndex < famillyList.size()) {
            return famillyList.get(selectedFamilyIndex).getTypeFamily();
        } else {
            return null;
        }
    }

    public static void addComponentToExpecificFamilly1(UI interfaz, List<Product> productList, List<Ram> ramList, List<Storage> storageList, List<ExpecificFamilly> ExpecificFamilyList) {
        try {
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
        interfaz.printText("Seleccione el número de la familia específica para agregar el componente:");
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
                    interfaz.printText("RAM aceptada por la familia: ");
                    for (Ram ram : ramProductList) {
                        interfaz.printText("- " + ram.getName());
                    }
                }

                if (!storageList.isEmpty()) {
                    interfaz.printText("Almacenamiento aceptado por la familia: ");
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
                    interfaz.printText("RAM aceptada por la familia: ");
                    for (Ram ram : ramProductList) {
                        interfaz.printText("- " + ram.getName());
                    }
                }

                if (!storageList.isEmpty()) {
                    interfaz.printText("Almacenamiento aceptado por la familia: ");
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
}
