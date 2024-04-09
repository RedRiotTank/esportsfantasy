package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Getter
@Setter

public abstract class ApiCaller {
    private final OkHttpClient client = new OkHttpClient();

    private String cargoSource, apiSource;


    private final String tablesHead = "&tables=";
    private final String fieldsHead = "&fields=";

    protected ApiCaller(){
        this.cargoSource = null;
        this.apiSource = null;
    }

    public JsonArray breakQuery(JsonObject jsonObject,String queryType) {

        if (jsonObject.has(queryType)) return jsonObject.getAsJsonArray(queryType);
        else System.out.println("queryType not found");

        return null;
    }

    //  ------- CARGOQUERYS -------
    protected JsonArray cargoRequest(String apiSource, String tables, String fields, String extended){
        String apiUrl = "https://" + apiSource + tablesHead + tables + fieldsHead + fields + extended;
        //System.out.println(apiUrl);
        JsonObject jsonObject = null;

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
             else
                System.out.println("Error in request: " + response.code() + " " + response.message());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return breakQuery(jsonObject, "cargoquery");
    }

    protected abstract JsonArray cargoQuery(String tables, String fields, String extended);
    protected abstract JsonArray cargoQuery(String tables, String fields);


    // ------- APIQUERYS -------

    protected JsonArray rApiRequest(String apiSource, String endPoint, String extended) throws IOException {
        String apiUrl = "https://" + apiSource + endPoint + extended;
        JsonObject jsonObject = null;

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .addHeader("X-RapidAPI-Key", "46508388dcmsh8286f7120f97350p15b16ejsn6c8a431f9406")
                .addHeader("X-RapidAPI-Host", "esportapi1.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
            else
                System.out.println("Error in request: " + response.code() + " " + response.message());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return breakQuery(jsonObject,"groups");


    }

    protected abstract JsonArray rApiQuery(String endpoint, String extended) throws IOException;

}
