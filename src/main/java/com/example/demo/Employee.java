package com.example.demo;

import java.util.ArrayList;

public class Employee {
    private String FirstName;
    private String LastName;
    private String EmployeeID;
    private String Designation;
    private ArrayList<KnownLanguages> KnownLanguages = new ArrayList<>();

    public Employee(String FirstName, String LastName, String EmployeeID, String Designation,
            ArrayList<KnownLanguages> KnownLanguages) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.EmployeeID = EmployeeID;
        this.Designation = Designation;
        this.KnownLanguages = KnownLanguages;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setKnownLanguages(ArrayList<KnownLanguages> KnownLanguages) {
        this.KnownLanguages = KnownLanguages;
    }

    public ArrayList<KnownLanguages> getKnownLanguages() {
        return KnownLanguages;
    }

    // public int getScoreForLanguage(String language) {
    //     // Get the score for a specific language
    //     for (KnownLanguages lang : KnownLanguages) {
    //         if (lang.getLanguageName().equals(language)) {
    //             return lang.getScore();
    //         }
    //     }
    //     return 0; // Default score if language is not found
    // }

    @Override
    public String toString() {
        return "< " + FirstName + "," + LastName + ", " + EmployeeID + ", " + Designation + ", "
                + KnownLanguages.toString() + ">";
    }
}