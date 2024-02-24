package edu.uiuc.cs427app.services;

import android.util.Log;

import java.util.List;

import edu.uiuc.cs427app.data.CityDAO;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.util.AppDb;

/**
 * The City Service provides a service layer with functions to
 * simplify interacting with the database and gets rid of needing to
 * work with the database access objects directly
 */
public class CityService
{
    public static CityService cityService;

    // Add TAG for application logging
    private static final String TAG = CityService.class.toString();
    private CityDAO theCityDao;


    /**
     * Get an instance of the City Service to share the same instance across classes
     */
    public static CityService getInstance(){
        if(cityService == null) {
            cityService = new CityService(AppDb.getInstance());
        }
        return cityService;
    }

    /**
     * Constructor
     * @param database Application database
     */
    public CityService(AppDb database)
    {
        if(database != null)
        {
            theCityDao = database.cityDao();
        }
        else
        {
            Log.e(TAG, "Database is null. Failed to construct CityService.");
        }
    }

    /**
     * Adds the city from the database if it does not already exist
     * @param city The city to add
     * @return true if the city successfully was successfully added
     */
    public boolean addCity(CityModel city)
    {
        // If the city does not already exist in the database, proceed
        // to add city
        if(getCityByLatLon(city.getLatitude(), city.getLongitude()) == null)
        {
            theCityDao.insertAll(city);
            return true;
        }
        return false;
    }

    /**
     * Adds cities for the user if the cities do not already exist for the user
     * @param cities The cities to add
     */
    public void addCities(CityModel ... cities)
    {
        for(CityModel city : cities)
        {
            // If the city does not already exist in the database, proceed
            // to add city
            if(getCityByLatLon(city.getLatitude(), city.getLongitude()) == null)
            {
                theCityDao.insertAll(city);
            }
        }
    }

    /**
     * Get all the cities from the database
     * @return List of cities
     */
    public List<CityModel> getAllCities()
    {
        return theCityDao.getAll();
    }

    /**
     * Get a city from the database by latitude and longitude
     * @param lat latitude
     * @param lon longitude
     * @return The city
     */
    public CityModel getCityByLatLon(float lat, float lon)
    {
        return theCityDao.getByLatLon(lat, lon);
    }
}
