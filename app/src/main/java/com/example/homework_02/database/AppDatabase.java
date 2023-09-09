package com.example.homework_02.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.homework_02.dao.UserDAO;
import com.example.homework_02.model.User;

@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
}
