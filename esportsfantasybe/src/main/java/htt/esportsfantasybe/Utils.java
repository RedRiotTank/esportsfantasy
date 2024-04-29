package htt.esportsfantasybe;

import com.google.gson.JsonElement;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class Utils {

    public static HttpHeaders getImageHeaders(byte[] image){
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return headers;
    }


    public static String generateOPname(String op){

        String newop = op.replace(" ", "_");
        newop += "_Season";
        newop = newop.replace("/","_");

        return newop;

    }


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
        URL url = null;

        try {
            url = new URL(imageUrl);

        } catch (MalformedURLException e) {
            //e.printStackTrace();
            System.out.println("URL malformada");
            url = null;

        }

        if(url != null){
            InputStream inputStream = url.openStream();



            OutputStream outputStream = new FileOutputStream(destinationFile);


            byte[] buffer = new byte[2048];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();
        }
    }




}
