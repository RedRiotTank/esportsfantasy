package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import htt.esportsfantasybe.DTO.RealLeagueDTO;

import java.util.ArrayList;

public class LolApiCaller extends ApiCaller{
    private String lolFandom = "lol.fandom.com/";

    ApiCaller api = new ApiCaller();

    private JsonArray lolQuery(String tables, String fields, String extended){
        return api.callApi(lolFandom,tables,fields,extended);
    }

    private JsonArray lolQuery(String tables, String fields){
        return api.callApi(lolFandom,tables,fields,"");
    }


    public ArrayList<RealLeagueDTO> getAllLeagues(){
        JsonArray allLeaguesJson = this.lolQuery("CurrentLeagues=CL", "Event,OverviewPage");
        ArrayList<RealLeagueDTO> allLeaguesDTO = new ArrayList<>();

        for(JsonElement league : allLeaguesJson){
            String event = league.getAsJsonObject().get("title").getAsJsonObject().get("Event").getAsString();
            String overviewPage = league.getAsJsonObject().get("title").getAsJsonObject().get("OverviewPage").getAsString();
            allLeaguesDTO.add(new RealLeagueDTO(event, overviewPage,"LOL"));

        }

        return allLeaguesDTO;
    }




    //test
    public JsonArray getLeagues(){
        //return lolQuery("CurrentLeagues", "Event,OverviewPage");
        return lolQuery("MatchSchedule=MS", "MS.Team1,MS.Team2,MS.Tab","&where=MS.OverviewPage='Liga Regional Norte/2024 Season/Regular Season'");

    }

}
