package com.TuneIn.BDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.lifecycle.LiveData;

import com.TuneIn.Entidades.Artista;

import java.util.List;


@Dao
public interface ArtistaDao {

    @Query("Select * from artista")
    LiveData<List<Artista>> getArtistaList();

    @Query("DELETE FROM artista")
    void deleteAll();

    @Insert
    void insert(Artista artista);

    @Update
    void update(Artista artista);

    @Delete
    void delete(Artista artista);

/*
    @Dao
    public interface MyDao {
        @Query("SELECT * FROM user WHERE age > :minAge")
        public User[] loadAllUsersOlderThan(int minAge);
    }

        @Dao
    public interface MyDao {
        @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
        public User[] loadAllUsersBetweenAges(int minAge, int maxAge);

        @Query("SELECT * FROM user WHERE first_name LIKE :search " +
               "OR last_name LIKE :search")
        public List<User> findUserWithName(String search);
    }

 */

}
