package com.example.homework_02.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)

    public int id;
    @ColumnInfo(name = "name")


    public String name;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "image")
    public String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User() {

    }

    public User(String name, String password, String image) {
        this.name = name;
        this.password = password;
        this.image = image;
    }
}
