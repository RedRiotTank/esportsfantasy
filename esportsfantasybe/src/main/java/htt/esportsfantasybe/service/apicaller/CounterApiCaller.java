package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import htt.esportsfantasybe.DTO.RealLeagueDTO;

import java.io.IOException;
import java.util.ArrayList;

public class CounterApiCaller extends ApiCaller{


    public CounterApiCaller(){
        super();
        this.setApiSource("esportapi1.p.rapidapi.com/api/");
    }

    @Override
    protected JsonArray cargoQuery(String tables, String fields, String extended) {
        return this.cargoRequest(null,null,null,null);
    }

    @Override
    protected JsonArray cargoQuery(String tables, String fields) {
        return this.cargoRequest(null,null,null,null);
    }


    @Override
    protected JsonArray rApiQuery(String endpoint, String extended) throws IOException {
        return this.rApiRequest(this.getApiSource(),endpoint,extended);
    }

    // ------ Queries ------ //

    public ArrayList<RealLeagueDTO> getAllLeagues() throws IOException {
        JsonArray groupLeagues = rApiQuery("esport/tournament/all/category/", "1572");
        ArrayList<RealLeagueDTO> allLeaguesDTO = new ArrayList<>();

        for(JsonElement gl : groupLeagues){
            JsonArray allLeaguesJson = gl.getAsJsonObject().get("uniqueTournaments").getAsJsonArray();

            for(JsonElement league : allLeaguesJson){
                String event = league.getAsJsonObject().get("name").getAsString();
                String apiId = league.getAsJsonObject().get("id").getAsString();
                allLeaguesDTO.add(new RealLeagueDTO(null,event, null,"CSGO", apiId));

            }
        }
        return allLeaguesDTO;
    }

}
