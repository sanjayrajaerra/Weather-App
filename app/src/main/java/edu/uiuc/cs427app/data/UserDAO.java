package edu.uiuc.cs427app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.uiuc.cs427app.model.UserModel;

/**
 * The User Data Access Object
 *
 * @implNote This DAO is used by the User Service
 */
@Dao
public interface UserDAO
{
    /**
     * Query the database for a user by a specific username
     * @param username The username for lookup
     * @return The user stored in the database with a matching username,
     * null if no user exists in the database with such username
     */
    @Query("SELECT * FROM usermodel WHERE username=:username")
    UserModel getByUsername(String username);

    /**
     * Inserts users into the database
     * @param users User(s) to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(UserModel... users);

    /**
     * Remove a user from the database
     * @param user The user to remove
     */
    @Delete
    void delete(UserModel user);

    /**
     * Set the theme by username
     * @param username The username of the user
     * @param primaryColor The primary color
     * @param secondaryColor The secondary color
     * @return The number of entries updated
     */
    @Query("Update usermodel SET primaryColor = :primaryColor, secondaryColor = :secondaryColor WHERE username=:username")
    int updateThemeByUser(String username, String primaryColor, String secondaryColor);
    
}
