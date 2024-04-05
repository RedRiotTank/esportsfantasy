package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;

public class CounterApiCaller {
    private String counterFandom = "????????";

    ApiCaller api = new ApiCaller();

    private JsonArray counterQuery(String tables, String fields, String extended){
        return api.callApi(counterFandom,tables,fields,extended);
    }

    private JsonArray lolQuery(String tables, String fields){
        return api.callApi(counterFandom,tables,fields,"");
    }




    public JsonArray getLeagues(){
        return lolQuery("???", "???");
    }

}
