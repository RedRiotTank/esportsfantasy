package htt.esportsfantasybe;

public class Utils {

    public static String generateShortName(String str) {
        for (int i = 0; i <= str.length() - 3; i++) {
            String sub = str.substring(i, i + 3);
            if (checkMayus(sub)) {
                return sub;
            }
        }
        return str;
    }

    private static boolean checkMayus(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }
}
