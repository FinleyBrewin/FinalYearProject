package com.BJSS.BJSSRest.Bjss;

import java.util.ArrayList;

public class TeamLists {
    // Actual team getters and setters
    private ArrayList<String> ActualTeam = new ArrayList<>();
    private ArrayList<String> ReadableActualTeam = new ArrayList<>();

    public void ActualaddPerson(String Person) {
        ActualTeam.add(Person);
    }

    public void ActualremovePerson(int index) {
        ActualTeam.remove(index);
    }

    public void ActualResetList() {
        ActualTeam.clear();
    }

    public ArrayList ActualgetList() {
        ArrayList<String> actualTeam = ActualTeam;
        return actualTeam;
    }

    // Actual readable List
    public void ReadableActualaddPerson(String Person) {
        ReadableActualTeam.add(Person);
    }

    public void ReadableActualremovePerson(int index) {
        ReadableActualTeam.remove(index);
    }

    public void ReadableActualResetList() {
        ReadableActualTeam.clear();
    }

    public ArrayList<String> ReadableActualgetList() {
        ArrayList<String> readableActualTeam = ReadableActualTeam;
        return readableActualTeam;
    }

    public String ActualTemperament() {
        String Temperament = "";
        System.out.println("ActualTemp: Size of Actual list " + ActualTeam.size() + "\n");
        for (int i = 0; i < ActualTeam.size(); i++) {
            String personString = ActualTeam.get(i);
            System.out.println("Person String:  " + ActualTeam.get(i));
            String[] parts = personString.split("&");
            String temperament = parts[8];
            Temperament = Temperament + temperament + ",";
        }
        return Temperament;
    }

    private String ActualExistingTemperament = "";

    public String getActualExistingTemperament() {
        String Temperament = ActualExistingTemperament;
        return Temperament;
    }

    public void addActualExistingTemperament(String Temperament) {
        ActualExistingTemperament = ActualExistingTemperament + ", " + Temperament;
    }

    private String ActualExistingIDs = "";

    public String getActualExistingIDs() {
        String ID = ActualExistingIDs;
        return ID;
    }

    public void addActualExistingIDs(String ID) {
        ActualExistingIDs = ActualExistingIDs + ", " + ID;
    }

    public String ActualIDs() {
        String IDs = "";
        for (int i = 0; i < ActualTeam.size(); i++) {
            String personString = ActualTeam.get(i);
            String[] parts = personString.split("&");
            String ID = parts[0];
            IDs = IDs + ID + ",";
        }
        return IDs;
    }

    // Ideal team getters and setters
    private ArrayList<String> IdealTeam = new ArrayList<>();
    private ArrayList<String> ReadableIdealTeam = new ArrayList<>();

    public void IdealaddPerson(String Person) {
        IdealTeam.add(Person);
    }

    public void IdealremovePerson(int index) {
        IdealTeam.remove(index);
    }

    public void IdealResetList() {
        IdealTeam.clear();
    }

    public ArrayList IdealgetList() {
        ArrayList<String> idealTeam = IdealTeam;
        return idealTeam;
    }

    // Ideal readable List
    public void ReadableIdealaddPerson(String Person) {
        ReadableIdealTeam.add(Person);
    }

    public void ReadableIdealremovePerson(int index) {
        ReadableIdealTeam.remove(index);
    }

    public void ReadableIdealResetList() {
        ReadableIdealTeam.clear();
    }

    public ArrayList ReadableIdealgetList() {
        ArrayList<String> readableidealTeam = ReadableIdealTeam;
        return readableidealTeam;
    }

    public String IdealTemperament() {
        String Temperament = "";
        for (int i = 0; i < IdealTeam.size(); i++) {
            String personString = IdealTeam.get(i);
            String[] parts = personString.split("&");
            String temperament = parts[8];
            Temperament = Temperament + temperament + ",";
        }
        return Temperament;
    }

    public String IdealIDs() {
        String IDs = "";
        for (int i = 0; i < IdealTeam.size(); i++) {
            String personString = IdealTeam.get(i);
            String[] parts = personString.split("&");
            String ID = parts[0];
            IDs = IDs + ID + ",";
        }
        return IDs;
    }

    private String IdealExistingTemperament = "";

    public String getIdealExistingTemperament() {
        String Temperament = IdealExistingTemperament;
        return Temperament;
    }

    public void addIdealExistingTemperament(String Temperament) {
        ;
    }

    private String IdealExistingIDs = "";

    public String getIdealExistingIDs() {
        String ID = IdealExistingIDs;
        return ID;
    }

    public void addIdealExistingIDs(String ID) {
        IdealExistingIDs = IdealExistingIDs + ", " + ID;
    }

    // Best team getters and setters
    private ArrayList<String> BestTeam = new ArrayList<>();
    private ArrayList<String> ReadableBestTeam = new ArrayList<>();

    public void BestaddPerson(String Person) {
        BestTeam.add(Person);
    }

    public void BestremovePerson(int index) {
        BestTeam.remove(index);
    }

    public void BestResetList() {
        BestTeam.clear();
    }

    public ArrayList BestgetList() {
        ArrayList<String> bestTeam = BestTeam;
        return bestTeam;
    }

    // Best readable List
    public void ReadableBestaddPerson(String Person) {
        ReadableBestTeam.add(Person);
    }

    public void ReadableBestremovePerson(int index) {
        ReadableBestTeam.remove(index);
    }

    public void ReadableBestResetList() {
        ReadableBestTeam.clear();
    }

    public ArrayList ReadableBestgetList() {
        ArrayList<String> readablebestTeam = ReadableBestTeam;
        return readablebestTeam;
    }

    public String BestTemperament() {
        String Temperament = "";
        for (int i = 0; i < BestTeam.size(); i++) {
            String personString = BestTeam.get(i);
            String[] parts = personString.split("&");
            String temperament = parts[8];
            Temperament = Temperament + temperament + ",";
        }
        return Temperament;
    }

    public String BestIDs() {
        String IDs = "";
        for (int i = 0; i < BestTeam.size(); i++) {
            String personString = BestTeam.get(i);
            String[] parts = personString.split("&");
            String ID = parts[0];
            IDs = IDs + ID + ",";
        }
        return IDs;
    }

    private String BestExistingTemperament = "";

    public String getBestExistingTemperament() {
        String Temperament = BestExistingTemperament;
        return Temperament;
    }

    public void addBestExistingTemperament(String Temperament) {
        ;
    }

    private String BestExistingIDs = "";

    public String getBestExistingIDs() {
        String ID = BestExistingIDs;
        return ID;
    }

    public void addBestExistingIDs(String ID) {
        BestExistingIDs = BestExistingIDs + ", " + ID;
    }

    // array list for all teams getters and setters
    private ArrayList<String> TeamsJSON = new ArrayList<String>();

    public void AddTeamJSON(String teamJSON) {
        TeamsJSON.add(teamJSON);
    }

    public ArrayList<String> GetTeamsJSON() {
        ArrayList<String> teamsJSON = TeamsJSON;
        return teamsJSON;
    }
}
