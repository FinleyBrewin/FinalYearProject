package com.BJSS.BJSSRest.Bjss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SqlStatements {
    // Actual people SQL statement
    public String getActualPeopleSQL(String Role, String Location, String Available, String WorkType,
            String Clearance) {
        // Method to create the SQL String to send to the database for - Actual people
        String SQL = "SELECT * FROM Persons WHERE Roles LIKE '%" + Role + "%' and Location ='" + Location
                + "' and Available < '" + Available + "' ";
        if (!"".equals(WorkType)) {
            SQL += "and WorkType NOT LIKE '%" + WorkType + "%'";
        }
        if (!"".equals(Clearance)) {
            SQL += "and Clearance LIKE '%" + Clearance + "%'";
        }
        SQL += ";";
        return SQL;
    }

    // Ideal people SQL statement
    public String getIdealPeopleSQL(String Role, String Location, String WorkType, String Clearance) {
        // Method to create the SQL String to send to the database for - Ideal people
        String SQL = "SELECT * FROM Persons WHERE Roles LIKE '%" + Role + "%' and Location ='" + Location + "'";
        if (!"".equals(WorkType)) {
            SQL += "and WorkType NOT LIKE '%" + WorkType + "%'";
        }
        if (!"".equals(Clearance)) {
            SQL += "and Clearance LIKE '%" + Clearance + "%'";
        }
        SQL += ";";
        return SQL;
    }

    // Best people SQL statement
    public String getBestPeopleSQL(String Role, String Location, String Available, String WorkType, String Clearance) {
        // Method to create the SQL String to send to the database for - Best people
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(Available));
        } catch (ParseException ex) {
        }
        c1.add(Calendar.DAY_OF_MONTH, 1);
        String available = sdf.format(c1.getTime());

        String SQL = "SELECT * FROM Persons WHERE Roles LIKE '%" + Role + "%' and Location ='" + Location
                + "' and Available < '" + available + "' ";
        if (!"".equals(WorkType)) {
            SQL += "and WorkType NOT LIKE '%" + WorkType + "%'";
        }
        if (!"".equals(Clearance)) {
            SQL += "and Clearance LIKE '%" + Clearance + "%'";
        }
        SQL += ";";
        return SQL;
    }
}
