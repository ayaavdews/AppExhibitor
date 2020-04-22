package com.example.AppExhibitor.Modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemPengunjung {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class Data {

    @SerializedName("exhibitor_name")
    @Expose
    private String exhibitorName;
    @SerializedName("exhibitor_stand")
    @Expose
    private String exhibitorStand;
    @SerializedName("guest_name")
    @Expose
    private String guestName;
    @SerializedName("guest_email")
    @Expose
    private String guestEmail;
    @SerializedName("visit_date")
    @Expose
    private String visitDate;
    @SerializedName("visit_time")
    @Expose
    private String visitTime;
    @SerializedName("purpose")
    @Expose
    private String purpose;

    public String getExhibitorName() {
        return exhibitorName;
    }

    public void setExhibitorName(String exhibitorName) {
        this.exhibitorName = exhibitorName;
    }

    public String getExhibitorStand() {
        return exhibitorStand;
    }

    public void setExhibitorStand(String exhibitorStand) {
        this.exhibitorStand = exhibitorStand;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}

