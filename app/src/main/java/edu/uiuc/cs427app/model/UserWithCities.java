package edu.uiuc.cs427app.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * Entity representing user and city relationship
 * This is necessary since Android Room forbids entities
 * referencing other entities
 */
public class UserWithCities {
    @Embedded
    public UserModel user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "cityId",
            associateBy = @Junction(UserCityCrossRef.class)
    )
    public List<CityModel> cities;
}
