package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LolApiCaller extends ApiCaller{


    public LolApiCaller(){
        super();
        this.setCargoSource("lol.fandom.com/api.php?action=cargoquery&format=json");
    }

    @Override
    protected JsonArray cargoQuery(String tables, String fields, String extended) {
        return this.cargoRequest(this.getCargoSource(),tables,fields,extended);
    }

    @Override
    protected JsonArray cargoQuery(String tables, String fields) {
        return this.cargoRequest(this.getCargoSource(),tables,fields,"");
    }

    @Override
    protected JsonArray rApiQuery(String endpoint, String extended) throws IOException {
        return this.rApiRequest(null,null,null);
    }

    // ------ Queries ------ //



    public List<RealLeagueDTO> getAllLeagues(){ //with no teams
        JsonArray allLeaguesJson = cargoQuery("CurrentLeagues=CL", "Event,OverviewPage");
        List<RealLeagueDTO> allLeaguesDTO = new ArrayList<>();

        for(JsonElement league : allLeaguesJson){
            String event = league.getAsJsonObject().get("title").getAsJsonObject().get("Event").getAsString();
            String overviewPage = league.getAsJsonObject().get("title").getAsJsonObject().get("OverviewPage").getAsString();
            allLeaguesDTO.add(new RealLeagueDTO(null,event, overviewPage, Utils.generateShortName(event),"LOL"));
        }

        return allLeaguesDTO;
    }

    public Set<TeamDTO> getLeagueTeams(String overviewPage){
        String overviewPageSeason = overviewPage + " Season";
        JsonArray matchSchedule = cargoQuery("MatchSchedule", "Team1,Team2","&where=OverviewPage=\""+ overviewPageSeason + "\"");

        Set<TeamDTO> teams = new HashSet<>();
        List<String> teamNames = new ArrayList<>();

        if (matchSchedule == null || matchSchedule.size() == 0){
            matchSchedule = cargoQuery("MatchSchedule", "Team1,Team2","&where=OverviewPage=\""+ overviewPage + "\"");
        }

        for (JsonElement match : matchSchedule){
            String team1 = match.getAsJsonObject().get("title").getAsJsonObject().get("Team1").getAsString();
            String team2 = match.getAsJsonObject().get("title").getAsJsonObject().get("Team2").getAsString();

            if(!teamNames.contains(team1)){
                teamNames.add(team1);
            }
            if(!teamNames.contains(team2)){
                teamNames.add(team2);
            }
        }

        teamNames.forEach(teamName -> {
            teamName = eliminarParteDerechaConParentesis(teamName);
            TeamDTO team = getTeamData(teamName);
            //System.out.println("equipo:" + teamName);
            if(team != null)
                teams.add(team);
        });


        return teams;
    }


    public TeamDTO getTeamData(String teamName){
        JsonArray teamData = this.cargoQuery("Teams", "Name,OverviewPage,Short,Image","&where=Name=\""+ teamName + "\"");

        if (teamData == null || teamData.size() == 0) return null;


        String overviewPage = teamData.get(0).getAsJsonObject().get("title").getAsJsonObject().get("OverviewPage").getAsString();
        String name = teamData.get(0).getAsJsonObject().get("title").getAsJsonObject().get("Name").getAsString();
        String shortName = teamData.get(0).getAsJsonObject().get("title").getAsJsonObject().get("Short").getAsString();
        String image = teamData.get(0).getAsJsonObject().get("title").getAsJsonObject().get("Image").getAsString();

        TeamDTO team = new TeamDTO(name, overviewPage, shortName, image, "LOL",null);

        team.setPlayers(getTeamPlayers(team));

        return team;
    }

    public Set<PlayerDTO> getTeamPlayers(TeamDTO team){ //&where=Team=\""+ team.getName() + "\"
        Set<PlayerDTO> players = new HashSet<>();
        JsonArray teamPlayers = this.cargoQuery("Players",
                "ID," +
                        "OverviewPage," +
                        "Image," +
                        "Name," +
                        "Role",
                "&where=Role!=\"Manager\"AND Role!=\"Owner\"AND Role!=\"Coach\"AND Role!=\"Caster\"AND Role!=\"Streamer\"AND Role!=\"Analyst\"AND Team=\""
                        + team.getName() + "\"OR Team2=\""+ team.getName() + "\"" );


        if (teamPlayers == null || teamPlayers.size() == 0) return null;

        for(JsonElement player : teamPlayers){
            String username = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("ID"));
            String name = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("Name"));
            String image = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("Image"));
            String role = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("Role"));

            players.add(new PlayerDTO(username,image,name,role,0));
        }

        return players;
    }



    public String eliminarParteDerechaConParentesis(String entrada) {
        int indiceParentesisAbierto = entrada.indexOf('(');
        if (indiceParentesisAbierto != -1) { // Si se encontró un paréntesis
            return entrada.substring(0, indiceParentesisAbierto).trim();
        } else { // Si no se encontró ningún paréntesis
            return entrada.trim();
        }
    }


}
