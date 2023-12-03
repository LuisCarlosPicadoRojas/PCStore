package ProyectoPOO.Main.UI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
public class UI {
    private final PrintStream out = System.out;
    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public void menu() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Registrar un componente");
        System.out.println("2. Listar componentes.");
        System.out.println("3. Registrar una familia de computadoras.");
        System.out.println("4. Listar familias.");
        System.out.println("5. Agregar componentes permitidos a una familia.");
        System.out.println("6. Eliminar componentes.");
        System.out.println("7. Eliminar familias.");
        System.out.println("8. Cerrar sesion.");
        System.out.println("\nIngrese una opcion");
    }
    public void menuClient() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Listar familias.");
        System.out.println("2. Listar componentes.");
        System.out.println("3. Cerrar sesion.");
        System.out.println("\nIngrese una opcion");
    }
    public void menuLogin() {
        System.out.println("Bienvenido a Round Rock Computers");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Crear cuenta.");
        System.out.println("3. Salir.");
        System.out.println("\nIngrese una opcion");
    }
    public void menuComponents() {
        System.out.println("Seleccione el tipo de componente:");
        System.out.println("1. Ram");
        System.out.println("2. Procesador.");
        System.out.println("3. Almacenamiento.");
        System.out.println("4. Fuente de poder.");
        System.out.println("5. Tarjeta Madre.");
        System.out.println("6. Tarjeta de video.");
        System.out.println("7. Salir.");
        System.out.println("\nIngrese una opcion");
    }
    public void menuTypeFamilly() {
        System.out.println("Seleccione el tipo de familia que desea registrar:");
        System.out.println("1. Escolares");
        System.out.println("2. Sobremesa.");
        System.out.println("3. Portables.");
        System.out.println("4. Servidores.");
        System.out.println("5. Salir.");
        System.out.println("\nIngrese una opcion");
    }
    public void menuDesktop() {
        System.out.println("Seleccione el tipo expecifico de familia que desea registrar:");
        System.out.println("1. Oficina");
        System.out.println("2. Gaming.");
        System.out.println("3. Workstation.");
        System.out.println("\nIngrese una opcion");
    }
    public void menuLaptop() {
        System.out.println("Seleccione el tipo expecifico de familia que desea registrar:");
        System.out.println("1. Casa");
        System.out.println("2. Trabajo.");
        System.out.println("\nIngrese una opcion");
    }

    public int optionReader() throws Exception {
        return Integer.parseInt(in.readLine());
    }

    public void printText(String mensaje){
        out.println(mensaje);
    }

    public String readText() throws Exception {
        return in.readLine();
    }
}
