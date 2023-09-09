package com.example.homework_02.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.homework_02.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}

