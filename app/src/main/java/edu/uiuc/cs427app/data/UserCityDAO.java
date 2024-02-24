package edu.uiuc.cs427app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserCityCrossRef;

/**
 * The UserCity Data Access Object
 *
 * @implNote This DAO is used by the User Service
 */
@Dao
public interface UserCityDAO
{
    /**
     * Gets the cities associated with a user ID
     * @param userId the user ID
     * @return List of CityModels associated with the user
     */
    @Query("SELECT cm.*\n" +
            "FROM citymodel cm " +
            "JOIN usercitycrossref uccr ON uccr.cityId = cm.cityId\n" +
            "JOIN usermodel um ON uccr.userId = um.userId WHERE um.userId IS :userId\n")
    List<CityModel> getUserCities(String userId);

    /**
     * Insert a cross reference between user id and location id to the
     * join table in the database
     * @param userCrossRef The cross reference object
     */
    @Insert
    void insert(UserCityCrossRef userCrossRef);

    /**
     * Insert multiple cross reference between user id and location id to the
     * join table in the database
     * @param userCrossRefs The cross reference objects
     */
    @Insert
    void insertAll(UserCityCrossRef... userCrossRefs);

    /**
     * Delete multiple cross reference between user id and location id to the
     * join table in the database
     * @param userCrossRefs The cross reference objects
     */
    @Delete
    void deleteAll(UserCityCrossRef... userCrossRefs);

    /**
     * Delete a cross reference between user id and location id to the
     * join table in the database
     * @param userCrossRef The cross reference object
     */
    @Delete
    void delete(UserCityCrossRef userCrossRef);
}
