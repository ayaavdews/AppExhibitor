package com.example.AppExhibitor.Modul;

public class ModelRecycler {
    private String guest_name, purpose, visit_date, visit_time;

    //Model For Guest name
    public String getGuest_name(){
        return guest_name;
    }
    public void setGuest_name(String guest_name){
        this.guest_name = guest_name;
    }

    //Model For Purpose
    public String getPurpose(){
        return purpose;
    }
    public void setPurpose(String purpose){
        this.purpose = purpose;
    }

    //Model For Visit Date
    public String getVisit_date(){
        return visit_date;
    }
    public void setVisit_date(String visit_date){
        this.visit_date = visit_date;
    }

    //Model For Visit Time
    public String getVisit_time(){
        return visit_time;
    }
    public void setVisit_time(String visit_time){
        this.visit_time = visit_time;
    }
}
