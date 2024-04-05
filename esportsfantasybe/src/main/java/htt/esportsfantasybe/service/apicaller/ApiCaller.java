package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiCaller {
    private final OkHttpClient client = new OkHttpClient();

    private final String https = "https://";
    private final String cargo = "api.php?action=cargoquery&format=json";

    private final String tablesHead = "&tables=";
    private final String fieldsHead = "&fields=";



    private String fetchData(String apiUrl) {
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("Error al realizar la solicitud: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private JsonArray fetchCargoQuery(String apiUrl) {
        String responseData = fetchData(apiUrl);
        if (responseData != null) {
            JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();
            if (jsonObject.has("cargoquery")) {
                return jsonObject.getAsJsonArray("cargoquery");
            } else {
                System.out.println("No se encontró el campo 'cargoquery' en la respuesta JSON.");
            }
        }
        return null;
    }

    public JsonArray callApi(String fandom, String tables, String fields, String extended){
        String apiURL = https + fandom + cargo + tablesHead + tables + fieldsHead + fields + extended;
        return fetchCargoQuery(apiURL);
    }
}
