package htt.esportsfantasybe;

import com.google.gson.JsonElement;

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

    public static void esfPrint(String str) {
        System.out.println("[eSportsFantasy]: " + str);
    }

    public static void esfPrint(String str,int tabs) {
        String tab = "    ".repeat(tabs);

        System.out.println(tab + "[eSportsFantasy]: " + str);
    }

    private static boolean checkMayus(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static String getStringOrNull(JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            return element.getAsString();
        }
        return null;
    }

}
