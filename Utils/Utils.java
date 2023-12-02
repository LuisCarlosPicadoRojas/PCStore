package ProyectoPOO.Main.Utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static int generateRandomCode() {
        Random rand = new Random();
        int min = 1000000;
        int max = 9999999;
        return rand.nextInt(max - min + 1) + min;
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    public static boolean containsNumbers(String input) {
        return input.matches(".*\\d.*");
    }

}
