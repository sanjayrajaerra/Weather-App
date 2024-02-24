package edu.uiuc.cs427app.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.data.UserCityDAO;
import edu.uiuc.cs427app.data.UserDAO;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserCityCrossRef;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.util.AppDb;

/**
 * The User Service provides a service layer with functions to
 * simplify interacting with the database and gets rid of needing to
 * work with the database access objects directly
 */
public class UserService
{

    public static UserService userService;

    // Add TAG for application logging
    private static final String TAG = UserService.class.toString();
    private UserCityDAO theUserCityDao;
    private UserDAO theUserDao;


    /**
     * Get an instance of the City Service to share the same instance across classes
     */
     public static UserService getInstance(){
         if(userService == null) {
            userService = new UserService(AppDb.getInstance());
         }
         UserModel admin = new UserModel("Admin","Admin",false,new ArrayList<>());
         userService.addUser(admin);
         return userService;
     }

    /**
     * Constructor
     * @param database Application database
     */
    public UserService(AppDb database)
    {

        if(database != null)
        {
            theUserCityDao = database.userCityDao();
            theUserDao = database.userDao();
        }
        else
        {
            Log.e(TAG, "Database is null. Failed to construct UserService.");
        }
    }

    /**
     * Add a user to the database
     * @param user The user to add
     */
    public void addUser(UserModel user)
    {
        theUserDao.insertUsers(user);
        List<CityModel> cities = user.getFavoriteCities();
        for(CityModel city: cities)
        {
            addCityForUser(user, city);
        }
    }

    /**
     * Get user by username
     * @param username The username
     */
    public UserModel getUserByUsername(String username)
    {
        return theUserDao.getByUsername(username);
    }

    /**
     * Get user by username and Password
     * @param username The username
     * @param hashPass The user password
     */
    public UserModel getUserByUsernamePass(String username, String hashPass)
    {
        // TODO implement getting user by username and
        //  password and return the user
        return null;
    }

    /**
     * Get all the cities associated with a user
     * @param user The user
     * @return List of cities
     */
    public List<CityModel> getCitiesForUser(UserModel user)
    {
        return theUserCityDao.getUserCities(user.getUserId());
    }

    /**
     * Remove a user from the database
     * @param user The user to delete
     * @return true if the user was successfully deleted, false otherwise
     */
    public boolean removeUser(UserModel user)
    {
        if(user != null)
        {
            theUserDao.delete(user);
            // TODO delete user id from crossref table using theUserCityDao
        }
        else
        {
            Log.e(TAG, "User is null. Delete user failed.");
        }
        return false;
    }

    /**
     * Add a city to a user
     * @param user The user to add the city for
     * @param city The city to add
     * @return true if the city successfully added
     */
    public boolean addCityForUser(UserModel user, CityModel city)
    {
        boolean success = false;
        List<CityModel> citiesOnUser = getCitiesForUser(user);
        if(citiesOnUser != null)
        {
            if (citiesOnUser.size() == 0 || !citiesOnUser.contains(city))
            {
                UserCityCrossRef crossRef = new UserCityCrossRef(user.getUserId(), city.getCityId());
                theUserCityDao.insert(crossRef);
                success = true;
            }
            else
            {
                Log.i(TAG, "Add city failed. User already has this city.");
            }
        }
        else
        {
            Log.e(TAG, "List of cities for user is null. Failed to add city to user.");
        }
        return success;
    }

    /**
     * Removes a city from a user's list
     * @param user The user to remove the city from
     * @param city The city to remove
     */
    public boolean removeCity(UserModel user, CityModel city)
    {
        boolean success = false;
        List<CityModel> citiesOnUser = getCitiesForUser(user);
        if(citiesOnUser != null)
        {
            if (citiesOnUser.size() != 0  && citiesOnUser.contains(city))
            {
                UserCityCrossRef crossRef = new UserCityCrossRef(user.getUserId(), city.getCityId());
                theUserCityDao.delete(crossRef);
                success = true;
            }
            else
            {
                Log.i(TAG, "Remove city failed. User does not have this city.");
            }
        }
        else
        {
            Log.e(TAG, "List of cities for user is null. Failed to remove city from user.");
        }
        return success;
    }

    /**
     * Update the user's preferred application theme
     * @param username The username of the user
     * @param primaryColor The primary theme color
     * @param secondaryColor The secondary theme color
     * @return True or False, whether the update is successful
     */
    public boolean updateTheme(String username, String primaryColor, String secondaryColor){
        boolean success = false;
        int numUpdated = theUserDao.updateThemeByUser(username, primaryColor, secondaryColor);
        if (numUpdated > 0) {
            success = true;
        }
        return success;
    }
}
