package com.BJSS.BJSSRest.Bjss;

public class Profile {
    private String[] Team;

    public String[] getTeam() { return Team; }
    public void setTeam(String[] value) { this.Team = value; }
    
    @Override
    public String toString()
    {
        return "Team:"+ Team;
                
    }
}
