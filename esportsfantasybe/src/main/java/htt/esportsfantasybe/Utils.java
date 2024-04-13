package htt.esportsfantasybe;

import com.google.gson.JsonElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;




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







    public static void downloadImage(String imageUrl, String destinationFile) throws IOException {
        // Abrir una conexión a la URL de la imagen
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();

        // Crear un flujo de salida para escribir los bytes de la imagen en un archivo

        OutputStream outputStream = new FileOutputStream(destinationFile);

        // Leer los bytes de la imagen de la entrada y escribirlos en el archivo de salida
        byte[] buffer = new byte[2048];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        // Cerrar los flujos
        inputStream.close();
        outputStream.close();
    }




}
