package edu.uiuc.cs427app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * The UserCityCrossRef Entity used for defining the
 * many to many relationship between users and cities
 */
@Entity(primaryKeys = {"userId", "cityId"})
public class UserCityCrossRef
{
    @NonNull
    private String userId;

    @NonNull
    private String cityId;

    /**
     * The parameterized UserCityCrossRef constructor
     * @param userId The user ID
     * @param cityId The city ID
     */
    public UserCityCrossRef(@NonNull String userId, @NonNull String cityId) {
        this.userId = userId;
        this.cityId = cityId;
    }

    /**
     * Get the User ID from the entity
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the UserID for the entity
     * @param userId The user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the City ID from the entity
     * @return The city ID
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * Sets the City ID for the entity
     * @param cityId The city ID
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}

