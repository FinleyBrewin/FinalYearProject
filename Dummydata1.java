package com.mycompany.dummydata1;
import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Dummydata1 { 
    public static void main(String[] args) throws ParseException, InterruptedException {
        String[] ROLES = {"Project Manager", "Project Sponsor", "Business analyst","Team leader","Software Engineer", "Test Engineer","Platform engineer","Data engineer","Data architects", "User research","Consultant"};
        String[] LOCATION = {"Aberdeen","Birmingham","Bristol","Cardiff","Edinburgh","Exeter","Glasgow","Leeds","Lincoln","Liverpool","London","Manchester","Milton Keynes","Newcastle","Nottingham","Reading","Sheffield","Swansea","York"};
        String[] SKILLS = {"Communication","Approachability","Empathy","Teamwork","Creativity","Problem Solving","Patience","Decision Making","Resilience","cooperation","time management","organization"};
        String[] TSKILS = {"Python", "C#","C++","Javascript","Java","SQL","PHP","NODE JS","Django","React JS","Spring Boot","Booststrap","Flask","Android SDK","Cloud","Mobile","Distributed Systems"};
        String[] WORKTYPE = {"Climate","Military","Fashion","Monitoring"};
        String[] CLEARANCE = {"Accreditation Check","Counter Terrorist Check","Security Check ","Enhanced Security Check","Developed Vetting","Enhanced Developed Vetting"};
        String[] TEMPERAMANT = {"INFP","ISFP","ESFP","ESTP","ISTP","INTP","ENTP","ENFP","ENFJ","ESFJ","ISFJ","ISTJ","ESTJ","ENTJ","INTJ","INFJ"};
        //https://www.myersbriggs.org/my-mbti-personality-type/mbti-basics/
        for (int x=0;x<1;x++) {
      String Name ="";
      String Roles = "";
      String Location = "";
      String Skills = "";
      String workType = "";
      String Clearance = "";
      String Temperamant = "";
      String Available = "";
      Random rand = new Random();
      Faker faker = new Faker(new Locale("en-GB"));
      //get name
      String Fname = faker.name().firstName();
      String Sname = faker.name().lastName();
      Name = Fname + " " + Sname;
      //Get roles
      int v = rand.nextInt(11);
      if (v>=8) {
         List<String> role = new ArrayList<>(Arrays.asList(ROLES));
         for (int i=0;i<2;i++) {
             v = rand.nextInt(role.size());
             Roles += role.get(v) + ", ";
             role.remove(v);
         }
      }else {
          List<String> role = new ArrayList<>(Arrays.asList(ROLES));
          v = rand.nextInt(role.size());
          Roles = role.get(v);
      }
      //get Location
      List<String> location = new ArrayList<>(Arrays.asList(LOCATION));
      v = rand.nextInt(location.size());
      Location = location.get(v);
      //get Skills
      String NSkills = "";
      String TSkills = "";
      int d = rand.nextInt(11);
      int amount = 0;
      if (d<=2) {  
          amount = 1;
      }
      if (d<=8 && d>=3) {
          amount = 2;
      } else {
          amount = 3;
      }
      List<String> skill = new ArrayList<>(Arrays.asList(SKILLS));
         for (int i=0;i<amount;i++) {
             v = rand.nextInt(skill.size());
             NSkills += skill.get(v) + ", ";
             skill.remove(v);
         }
      int e = rand.nextInt(11);
      int Amount1 = 0;
      if (e<=e) {  
          Amount1 = 1;
      } else {
          Amount1 = 2;
      }
      List<String> Tskill = new ArrayList<>(Arrays.asList(TSKILS));
         for (int i=0;i<Amount1;i++) {
             v = rand.nextInt(Tskill.size());
             TSkills += Tskill.get(v) + ", ";
             Tskill.remove(v);
         }
      Skills = NSkills + TSkills;
      //get workType
      int p = rand.nextInt(11);
      if (p>=9) {
          List<String> work = new ArrayList<>(Arrays.asList(WORKTYPE));
          v = rand.nextInt(work.size());
          workType = work.get(v);
      }
      //get Clearance
      int l = rand.nextInt(11);
      List<String> Clear = new ArrayList<>(Arrays.asList(CLEARANCE));
      if (l<=6) {
            for (int i=0;i<2;i++) {
                v = rand.nextInt(Clear.size());
                Clearance += Clear.get(v) + ", ";
                Clear.remove(v);
            }
      } else {
          v = rand.nextInt(Clear.size());
          Clearance = Clear.get(v);
      }
       //get temperament
       Temperamant = TEMPERAMANT[rand.nextInt(TEMPERAMANT.length)];
       //get availability
       int g = rand.nextInt(11);
       String Default = "2023-01-01";    
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
       Calendar c1 = Calendar.getInstance();
       c1.setTime(sdf.parse(Default)); 
       Available = sdf.format(c1.getTime());  
       if (g<=5) {
           v = rand.nextInt(200);
           c1.add(Calendar.DAY_OF_MONTH, v);
           Available = sdf.format(c1.getTime()); 
       }
       //send to server
        String password = creds.MyCreds.getPassword();
        String username = creds.MyCreds.getUsername();
        String cnnString = 
                "jdbc:sqlserver://[REMOVED]"
                + "database=PeopleDatabase;"
                + "user=" + username + ";"
                + "password=" + password + ";"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "login=TimeOut=30;";
        azuredbtest azure = new azuredbtest();
        //System.out.println("Connecting...");
        String sql = "INSERT INTO Persons (Name, Roles, Location, Skills, WorkType, Clearance, Temperament, Available) VALUES ('"+Name+"', '"+Roles+"', '"+Location+"', '"+Skills+"', '"+workType+"', '"+Clearance+"','"+Temperamant+"','"+Available+"');";
        azure.ExecuteAzureSQL(username, password, sql, cnnString);
        //azure.SelectAzureSQL(username, password, sql, cnnString);
        TimeUnit.MILLISECONDS.sleep(250);
        }
    }
    private static class azuredbtest {
        private void SelectAzureSQL(String username, String password, String sql, String cnnStr) {
        //return reseult from a select statement
        System.out.println("Sending select statement...");
        ResultSet resultSet = null;
        try (Connection cnn = DriverManager.getConnection(cnnStr);
                Statement statement = cnn.createStatement();) {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                System.out.println(resultSet.getString(2) + " - " + resultSet.getString(3) + " - " + resultSet.getString(4)+ " - " + resultSet.getString(5)+ " - " + resultSet.getString(6)+ " - " + resultSet.getString(7)+ " - " + resultSet.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        private void ExecuteAzureSQL(String username, String password, String sql, String cnnStr){
            System.out.println("executing SQL insert statement...");
            try (Connection cnn = DriverManager.getConnection(cnnStr);
                PreparedStatement statement = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                ResultSet resultSet = null;
                statement.execute();
                resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    System.out.println("Key: "  + resultSet.getString(1));
                }
        }   catch (SQLException e) {
                e.printStackTrace();
            }         
        }
    }
}