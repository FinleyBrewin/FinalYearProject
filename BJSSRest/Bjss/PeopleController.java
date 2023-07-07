package com.BJSS.BJSSRest.Bjss;

import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/BJSS")
public class PeopleController {

    @PostMapping("/POST")
    public String POSTTeams(@RequestBody String content) {
        String JSON = content;
        // Code to set up GSON
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        PeopleController Team = new PeopleController();

        TeamLists Teams = new TeamLists();
        System.out.println("Retrieved Request...");
        System.out.println(JSON);
        // adds profile json to profile variable
        Profiles Profiles = gson.fromJson(JSON, Profiles.class);
        Profile[] profiles = Profiles.getProfiles();
        System.out.println("Profile length:" + Profiles.getProfiles().length);
        // Amount of teams in the profile
        for (int i = 0; i < profiles.length; i++) {
            // gets team from profile
            System.out.println("Amount of Roles in Team:" + profiles[i].getTeam().length);
            // going through roles in team array
            for (int x = 0; x < profiles[i].getTeam().length; x++) {
                String personString = profiles[i].getTeam()[x];
                String[] parts = personString.split("&");
                String Role = parts[0];
                String Location = parts[1];
                String Skills = parts[2];
                String Available = parts[3];
                String WorkType = parts[4];
                String Clearance = parts[5];
                String Temperament = parts[6];
                Team.getTeamOutput(Role, Location, Available, WorkType, Clearance, Skills, Temperament, Teams);
            }
            String teamJSON = Team.createJSON(Teams);
            Teams.AddTeamJSON(teamJSON);
            System.out.println("Resetting team lists...");
            Teams.ActualResetList();
            Teams.BestResetList();
            Teams.IdealResetList();
            Teams.ReadableActualResetList();
            Teams.ReadableBestResetList();
            Teams.ReadableIdealResetList();
        }
        String JSONreply = Team.createTeamsJson(Teams);
        System.out.println("====================================================================");
        return JSONreply;
    }

    public void getTeamOutput(String InputRole, String InputLocation, String InputAvailable, String InputWorkType,
            String InputClearance, String InputSkills, String Temperament, TeamLists Teams) {
        // Method to find out best person for the team
        // Create class variables
        DatabaseAccess dbAccess = new DatabaseAccess();
        SqlStatements SqlStatements = new SqlStatements();
        CalculatePeople Calculate = new CalculatePeople();
        // get database password and username
        String password = myCreds.getPassword();
        String username = myCreds.getUsername();
        // the string to connect to the database
        String cnnString = "jdbc:sqlserver://[REMOVED];"
                + "database=PeopleDatabase;"
                + "user=" + username + ";"
                + "password=" + password + ";"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "login=TimeOut=30;";
        // create actual team sql
        String Actualsql = SqlStatements.getActualPeopleSQL(InputRole, InputLocation, InputAvailable, InputWorkType,
                InputClearance);
        // create ideal team sql
        String Idealsql = SqlStatements.getIdealPeopleSQL(InputRole, InputLocation, InputWorkType, InputClearance);
        // create best team sql
        String Bestsql = SqlStatements.getBestPeopleSQL(InputRole, InputLocation, InputAvailable, InputWorkType,
                InputClearance);

        // Get actual team people similarity
        ArrayList<String> allActualPeople = new ArrayList<>();
        allActualPeople = dbAccess.SelectAzureSQL(username, password, Actualsql, cnnString);
        String ActualTemperament = Teams.getActualExistingTemperament();
        String ActualIDs = Teams.getActualExistingIDs();
        String ActualPerson = Calculate.findBestPerson(allActualPeople, InputRole, InputSkills, Temperament,
                ActualTemperament, ActualIDs);
        if (ActualPerson != null) {
            System.out.print("Adding to Actual list: " + ActualPerson + "\n");
            Teams.ActualaddPerson(ActualPerson);
            // adding ID & temperament to existing actual list
            String personString = ActualPerson;
            String[] parts = personString.split("&");
            String ID = parts[0];
            String Name = parts[1];
            String Roles = parts[2];
            // String Location = parts[3];
            String Skills = parts[4].toLowerCase();
            String Available = parts[5];
            // String WorkType = parts[6];
            // String Clearance = parts[7];
            String temperament = parts[8];
            String score = parts[9];
            String readablePersonString = "{\"name\": \"" + Name + "\",\"selected\": \"" + InputRole
                    + "\",\"roles\": \"" + Roles + "\",\"skills\": \""
                    + Skills + "\",\"availability\": \"" + Available + "\",\"personality\": \"" + temperament
                    + "\",\"SimilarityScore\": \"" + score + "%\"}";
            Teams.ReadableActualaddPerson(readablePersonString);
            Teams.addActualExistingIDs(ID);
            Teams.addActualExistingTemperament(temperament);
        } else {
            Teams.ActualaddPerson("No Person found for this role & requirements");
            Teams.ReadableActualaddPerson(
                    "{\"name\": \"" + "No Person found" + "\",\"selected\": \"" + InputRole + "\",\"roles\": \"" + "N/A"
                            + "\",\"skills\": \"" + "N/A" + "\",\"availability\": \"" + "N/A" + "\",\"personality\": \""
                            + "N/A" + "\",\"SimilarityScore\": \"" + "N/A" + "\"}");
        }
        // Get Ideal team people similarity
        ArrayList<String> allIdealPeople = new ArrayList<>();
        allIdealPeople = dbAccess.SelectAzureSQL(username, password, Idealsql, cnnString);
        String IdealTemperament = Teams.getIdealExistingTemperament();
        String IdealIDs = Teams.getIdealExistingIDs();
        String IdealPerson = Calculate.findBestPerson(allIdealPeople, InputRole, InputSkills, Temperament,
                IdealTemperament, IdealIDs);
        if (IdealPerson != null) {
            System.out.print("Adding to Ideal list: " + IdealPerson + "\n");
            Teams.IdealaddPerson(IdealPerson);
            // adding ID & temperament to existing ideal list
            String personString = IdealPerson;
            String[] parts = personString.split("&");
            String ID = parts[0];
            String Name = parts[1];
            String Roles = parts[2];
            // String Location = parts[3];
            String Skills = parts[4].toLowerCase();
            String Available = parts[5];
            // String WorkType = parts[6];
            // String Clearance = parts[7];
            String temperament = parts[8];
            String score = parts[9];
            String readablePersonString = "{\"name\": \"" + Name + "\",\"selected\": \"" + InputRole
                    + "\",\"roles\": \"" + Roles + "\",\"skills\": \""
                    + Skills + "\",\"availability\": \"" + Available + "\",\"personality\": \"" + temperament
                    + "\",\"SimilarityScore\": \"" + score + "%\"}";
            Teams.ReadableIdealaddPerson(readablePersonString);
            Teams.addIdealExistingIDs(ID);
            Teams.addIdealExistingTemperament(temperament);
        } else {
            Teams.IdealaddPerson("No Person found for this role & requirements");
            Teams.ReadableIdealaddPerson(
                    "{\"name\": \"" + "No Person found" + "\",\"selected\": \"" + InputRole + "\",\"roles\": \"" + "N/A"
                            + "\",\"skills\": \"" + "N/A" + "\",\"availability\": \"" + "N/A" + "\",\"personality\": \""
                            + "N/A" + "\",\"SimilarityScore\": \"" + "N/A" + "\"}");
        }
        // Get Best team people similarity
        ArrayList<String> allBestPeople = new ArrayList<>();
        allBestPeople = dbAccess.SelectAzureSQL(username, password, Bestsql, cnnString);
        String BestTemperament = Teams.getBestExistingTemperament();
        String BestIDs = Teams.getBestExistingIDs();
        String BestPerson = Calculate.findBestPerson(allBestPeople, InputRole, InputSkills, Temperament,
                BestTemperament, BestIDs);
        if (BestPerson != null) {
            System.out.print("Adding to Best list: " + BestPerson + "\n");
            Teams.BestaddPerson(BestPerson);
            // adding ID & temperament to existing best list
            String personString = BestPerson;
            String[] parts = personString.split("&");
            String ID = parts[0];
            String Name = parts[1];
            String Roles = parts[2];
            // String Location = parts[3];
            String Skills = parts[4].toLowerCase();
            String Available = parts[5];
            // String WorkType = parts[6];
            // String Clearance = parts[7];
            String temperament = parts[8];
            String score = parts[9];
            String readablePersonString = "{\"name\": \"" + Name + "\",\"selected\": \"" + InputRole
                    + "\",\"roles\": \"" + Roles + "\",\"skills\": \""
                    + Skills + "\",\"availability\": \"" + Available + "\",\"personality\": \"" + temperament
                    + "\",\"SimilarityScore\": \"" + score + "%\"}";
            Teams.ReadableBestaddPerson(readablePersonString);
            Teams.addBestExistingIDs(ID);
            Teams.addBestExistingTemperament(temperament);
        } else {
            Teams.BestaddPerson("No Person found for this role & requirements");
            Teams.ReadableBestaddPerson(
                    "{\"name\": \"" + "No Person found" + "\",\"selected\": \"" + InputRole + "\",\"roles\": \"" + "N/A"
                            + "\",\"skills\": \"" + "N/A" + "\",\"availability\": \"" + "N/A" + "\",\"personality\": \""
                            + "N/A" + "\",\"SimilarityScore\": \"" + "N/A" + "\"}");
        }
    }

    public String createJSON(TeamLists Teams) {

        ArrayList<String> Actual = Teams.ReadableActualgetList();
        ArrayList<String> Ideal = Teams.ReadableIdealgetList();
        ArrayList<String> Best = Teams.ReadableBestgetList();

        String actual = "";
        for (int i = 0; i < Actual.size(); i++) {
            actual = actual + "{\"Person\":" + Actual.get(i) + "},";
        }
        actual = actual.substring(0, actual.length() - 1);

        String ideal = "";
        for (int i = 0; i < Ideal.size(); i++) {
            ideal = ideal + "{\"Person\":" + Ideal.get(i) + "},";
        }
        ideal = ideal.substring(0, ideal.length() - 1);

        String best = "";
        for (int i = 0; i < Best.size(); i++) {
            best = best + "{\"Person\":" + Best.get(i) + "},";
        }
        best = best.substring(0, best.length() - 1);

        String JSON = "{\"Actual\":[" + actual + "],\"Ideal\":[" + ideal + "],\"Best\":[" + best
                + "]}";

        return JSON;
    }

    public String createTeamsJson(TeamLists Teams) {
        // Adds all team jsons into a single json to send back
        ArrayList<String> TeamsJSON = Teams.GetTeamsJSON();
        String JSON = "{\"Teams\":[";
        for (int i = 0; i < TeamsJSON.size(); i++) {
            JSON = JSON + TeamsJSON.get(i) + ",";
        }
        JSON = JSON.substring(0, JSON.length() - 1);
        JSON = JSON + "]}";

        return JSON;
    }
}
