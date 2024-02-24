package edu.uiuc.cs427app.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.uiuc.cs427app.data.CityDAO;
import edu.uiuc.cs427app.data.UserCityDAO;
import edu.uiuc.cs427app.data.UserDAO;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserCityCrossRef;
import edu.uiuc.cs427app.model.UserModel;

@Database(entities = {UserModel.class, CityModel.class, UserCityCrossRef.class}, version = 4)
public abstract class AppDb extends RoomDatabase
{

    private static AppDb instance;

    /**
     * Abstract method to get reference to the user data access object for
     * interactions with the Room database
     * @return The user data access object
     */
    public abstract UserDAO userDao();

    /**
     * Abstract method to get reference to the UserCity data access object for
     * interactions with the Room database
     * @return The UserCity data access object
     */
    public abstract UserCityDAO userCityDao();

    /**
     * Abstract method to get reference to the city data access object for
     * interactions with the Room database
     * @return The city data access object
     */
    public abstract CityDAO cityDao();

    /**
     * Get the instance of the application database for persistent storage
     * @return The database instance
     */
    public static AppDb getInstance() {
        return instance;
    }

    /**
     * Sets the instance of the application database
     * @implNote Called once on app startup in WeatherApp.java
     * @param context The application context
     * @return The database instance
     */
    public static AppDb setInstance(Context context) {
        if(instance == null) {

            // Build the test database. The database is created if it does not already exist
            // and is pre-populated with data from "init.db" in the project's assets
            // folder
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDb.class, "app.db")
                    .allowMainThreadQueries()
                    .createFromAsset("database/init.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
