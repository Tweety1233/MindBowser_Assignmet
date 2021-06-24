package com.mindbowser.assignmet.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contacts {
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "favourite")
    private String favourite;
    @ColumnInfo(name = "delete")
    private String deleted;

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    @ColumnInfo(name = "contact_id")
    private String contact_id;
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
