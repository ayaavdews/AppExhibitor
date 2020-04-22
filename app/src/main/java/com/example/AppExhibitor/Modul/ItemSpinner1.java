package com.example.AppExhibitor.Modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemSpinner1 {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("events")
    @Expose
    private String events;
    @SerializedName("description")
    @Expose
    private String description;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
