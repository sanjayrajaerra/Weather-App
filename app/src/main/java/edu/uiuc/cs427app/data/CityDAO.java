package edu.uiuc.cs427app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.uiuc.cs427app.model.CityModel;

/**
 * The City Data Access Object
 *
 * @implNote This DAO is used by the City Service
 */
@Dao
public interface CityDAO
{
    /**
     * Query the database for all cities
     * @return The list of cities stored in the database
     */
    @Query("SELECT * from citymodel")
    List<CityModel> getAll();

    /**
     * Query the database for a city with a certain id
     * @param id The city ID
     * @return The city stored in the database with the specified ID, null
     * if no city with that id was found in the database
     */
    @Query("SELECT * FROM citymodel WHERE cityid=:id")
    CityModel getByID(String id);

    /**
     * Query the database for a city based on latitude and longitude coordinate
     * @param lat The latitude
     * @param lon The longitude
     * @return The city stored in the database with matching latitude and longitude,
     * null if no city with that latitude and longitude coordinate exists in the database
     */
    @Query("SELECT * FROM citymodel WHERE latitude=:lat AND longitude=:lon")
    CityModel getByLatLon(float lat, float lon);

    /**
     * Add cities to the database
     * @param cities The cities as arguments
     */
    @Insert
    void insertAll(CityModel... cities);

    /**
     * Remove a city from the database
     * @param city The city to remove
     */
    @Delete
    void delete(CityModel city);
}
