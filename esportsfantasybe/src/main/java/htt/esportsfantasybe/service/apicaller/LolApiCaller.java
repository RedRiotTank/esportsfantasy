package htt.esportsfantasybe.service.apicaller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.pojos.EventPOJO;
import htt.esportsfantasybe.service.PlayerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LolApiCaller extends ApiCaller{

    private static String wikiUrl = "https://lol.fandom.com/wiki/";


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

    public Set<EventPOJO> getRLeaguesEvents(String overviewPage) {


        String overviewPageSeason = overviewPage + " Season";
        JsonArray matchSchedule = cargoQuery("MatchSchedule", "Team1,Team2,Tab,Team1Score,Team2Score,DateTime_UTC","&where=OverviewPage=\""+ overviewPageSeason + "\"");

        if (matchSchedule == null || matchSchedule.size() == 0)
            matchSchedule = cargoQuery("MatchSchedule", "Team1,Team2,Tab,Team1Score,Team2Score,DateTime_UTC","&where=OverviewPage=\""+ overviewPage + "\"");

        Set<EventPOJO> events = new HashSet<>();

        for (JsonElement match : matchSchedule){
            String team1 = match.getAsJsonObject().get("title").getAsJsonObject().get("Team1").getAsString();
            String team2 = match.getAsJsonObject().get("title").getAsJsonObject().get("Team2").getAsString();

            JsonElement jsont1Score = match.getAsJsonObject().get("title").getAsJsonObject().get("Team1Score");
            JsonElement jsont2Score = match.getAsJsonObject().get("title").getAsJsonObject().get("Team2Score");

            String team1Score, team2Score;

            if(!jsont1Score.isJsonNull()) team1Score = jsont1Score.getAsString();
            else team1Score = "null";

            if(!jsont2Score.isJsonNull()) team2Score = jsont2Score.getAsString();
            else team2Score = "null";

            String jour = match.getAsJsonObject().get("title").getAsJsonObject().get("Tab").getAsString();

            if(jour.equals("Tiebreakers"))
                jour = "50";
            else if(jour.equals("Playoffs"))
                jour = "100";
            else
                jour = jour.replaceAll("\\D", "");

            String dateTime_UTC = match.getAsJsonObject().get("title").getAsJsonObject().get("DateTime UTC").getAsString();

            events.add(new EventPOJO(team1,team2,jour,team1Score,team2Score,dateTime_UTC));
        }

        return events;
    }

    public int getLeagueCurrentJour(String overviewPage)  {
        int maxJour = 0;
        Date currentDate = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String overviewPageSeason = overviewPage + " Season";
        JsonArray matchSchedule = cargoQuery("MatchSchedule", "Team1,Team2,Tab,DateTime_UTC","&where=OverviewPage=\""+ overviewPageSeason + "\"");

        Set<TeamDTO> teams = new HashSet<>();
        List<String> teamNames = new ArrayList<>();

        if (matchSchedule == null || matchSchedule.size() == 0){
            matchSchedule = cargoQuery("MatchSchedule", "Team1,Team2,MatchDay,DateTime_UTC","&where=OverviewPage=\""+ overviewPage + "\"");
        }


        Date matchDate = null;

        for (JsonElement match : matchSchedule){
            String jour;

            try{
                jour = match.getAsJsonObject().get("title").getAsJsonObject().get("Tab").getAsString();
            } catch (Exception e){
                jour = "Playoffs";
            }





            String dateString = match.getAsJsonObject().get("title").getAsJsonObject().get("DateTime UTC").getAsString();

            try {
                matchDate = formatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
            if(jour.equals("Tiebreakers"))  //50 if is in tierbreakers
                jour = "50";
            else if(jour.equals("Playoffs"))
                jour = "100";
            else
                jour = jour.replaceAll("\\D", "");

            if(jour.equals(""))  //100 if is in playoffs
                System.out.println("JOUR IS EMPTY");

            if (matchDate.before(currentDate) && Integer.parseInt(jour) > maxJour) {
                maxJour = Integer.parseInt(jour);
            }
        }


        return maxJour;
    }

    public List<RealLeagueDTO> getAllLeagues(){ //with no teams
        JsonArray allLeaguesJson = cargoQuery("CurrentLeagues=CL", "Event,OverviewPage");
        List<RealLeagueDTO> allLeaguesDTO = new ArrayList<>();

        for(JsonElement league : allLeaguesJson){
            String event = league.getAsJsonObject().get("title").getAsJsonObject().get("Event").getAsString();
            String overviewPage = league.getAsJsonObject().get("title").getAsJsonObject().get("OverviewPage").getAsString();
            allLeaguesDTO.add(new RealLeagueDTO(null,event, overviewPage, Utils.generateShortName(event),"LOL",0));
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
                "&where=(Role=\"Top\"OR Role=\"Jungle\"OR Role=\"Mid\"OR Role=\"Bot\"OR Role=\"Support\")AND (Team=\""
                        + team.getName() + "\"OR Team2=\""+ team.getName() + "\")" );


        if (teamPlayers == null || teamPlayers.size() == 0) return null;

        for(JsonElement player : teamPlayers){
            String username = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("ID"));
            String name = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("Name"));
            String image = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("Image"));
            String role = Utils.getStringOrNull(player.getAsJsonObject().get("title").getAsJsonObject().get("Role"));

            players.add(new PlayerDTO(username,image,name,role, PlayerService.getDefaultValue() ,team));
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

    public static String getTableImgurl(String overviewPage, String type) throws IOException {
        Document doc = Jsoup.connect(wikiUrl + overviewPage).get();

        Elements tables;

        switch (type) {
            case "Tournament"-> {
                tables = doc.select("table#infoboxTournament");
            }

            case "Team"-> {
                tables = doc.select("table#infoboxTeam");
            }
            case "Player"-> {
                tables = doc.select("table#infoboxPlayer");
            }
            default -> {
                return null;
            }
        }



        if (tables.isEmpty()) return null;
         else {
            Elements divs = tables.get(0).select("div.floatnone");

            if (divs.isEmpty()) {
                return null;
            } else {
                Elements imagenes = divs.get(0).select("img");

                if (imagenes.isEmpty()) return null;
                 else {
                    String imageUrl = imagenes.get(0).attr("src");


                    if (imageUrl.equals("https://static.wikia.nocookie.net/lolesports_gamepedia_en/images/1/1d/Unknown_Infobox_Image_-_Player.png/revision/latest/scale-to-width-down/220?cb=20210522010719")) return null;
                    else{
                        return imageUrl;
                    }

                }
            }
        }

    }
}
