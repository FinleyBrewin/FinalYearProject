package com.BJSS.BJSSRest.Bjss;

import java.util.ArrayList;

public class CalculatePeople {
    public String findBestPerson(ArrayList<String> people, String Role, String skills, String useTemperament,
            String existTemperament, String existIDs) {
        // Method to check people list against input paramaters
        ArrayList<String> People = people;
        String IRoles = Role;
        String ISkills = skills;
        String[] SkillsList = ISkills.split(",");
        String ITemperament = useTemperament;
        String ExistingTemperament = existTemperament;
        String ExistingIDs = existIDs;
        String reply = "";
        // Comparing each person data to get similarity value
        ArrayList<String> PeopleScore = new ArrayList<>();
        for (int i = 0; i < People.size(); i++) {
            // Getting person data
            String personString = People.get(i);
            String[] parts = personString.split("&");
            String ID = parts[0];
            String Name = parts[1];
            String Roles = parts[2];
            String Location = parts[3];
            String Skills = parts[4].toLowerCase();
            String Available = parts[5];
            String WorkType = parts[6];
            String Clearance = parts[7];
            String Temperament = parts[8];
            int BestScore = 0;
            long ScorePercent = 0;
            long Score = 0;
            // Checks if ID is already used in existing list
            if (!ExistingIDs.contains(ID)) {
                // Finds best score to work out percentage
                BestScore = SkillsList.length;
                if ("Yes".equals(ITemperament)) {
                    BestScore = SkillsList.length + 1;
                }
                ScorePercent = 100 / BestScore;
                // Calculates the similarity of the person to the input
                for (int x = 0; x < SkillsList.length; x++) {
                    if (Skills.contains(SkillsList[x].toLowerCase())) {
                        Score = Score + ScorePercent;
                    }
                }
                if ("Yes".equals(ITemperament)) {
                    if (!ExistingTemperament.contains(Temperament)) {
                        Score = Score + ScorePercent;
                    }
                }
                String PersonScore = ID + "," + Score;
                PeopleScore.add(PersonScore);
            }
        }
        if (!PeopleScore.isEmpty()) {
            // Find person ID with highest Percentage
            String BestPerson = "";
            for (int i = 0; i < PeopleScore.size(); i++) {
                if ("".equals(BestPerson)) {
                    BestPerson = PeopleScore.get(i);
                } else {
                    String[] parts = BestPerson.split(",");
                    String ID = parts[0];
                    int percent = Integer.valueOf(parts[1]);

                    String[] parts2 = PeopleScore.get(i).split(",");
                    String ID2 = parts2[0];
                    int percent2 = Integer.valueOf(parts2[1]);

                    if (percent2 > percent) {
                        BestPerson = PeopleScore.get(i);
                    }
                }
            }
            // get person info using ID
            String PersonDetails = "";
            for (int i = 0; i < People.size(); i++) {
                String personString = People.get(i);
                String[] parts = personString.split("&");
                String ID = parts[0];
                // getting ID of best person
                String[] parts2 = BestPerson.split(",");
                String ID2 = parts2[0];
                String score = parts2[1];
                if (ID.equals(ID2)) {
                    PersonDetails = People.get(i);
                    PersonDetails = PersonDetails + "&" + score;
                }
            }
            PeopleScore = new ArrayList<>();
            reply = PersonDetails;
        } else {
            reply = null;
        }
        return reply;
    }
}
