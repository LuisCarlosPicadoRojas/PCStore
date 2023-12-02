package ProyectoPOO.Utils;

import java.util.Random;

public class Utils {
    public static int generateRandomCode() {
        Random rand = new Random();
        int min = 1000000;
        int max = 9999999;
        return rand.nextInt(max - min + 1) + min;
    }
}
