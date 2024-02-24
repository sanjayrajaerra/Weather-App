package edu.uiuc.cs427app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.AppDb;

public class WeatherApp extends Application {
    private static Context mContext;
    private CityService theCityService;
    private UserService theUserService;

    /**
     * Called once, when the WeatherApp is first created
     */
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //Initialize DB.
        AppDb.setInstance(mContext);

        theCityService = CityService.getInstance();
        theUserService = UserService.getInstance();
        initDbLoad();
    }

    /**
     * Get the application context
     * @return App context
     */
    public static Context getAppContext() {
        return mContext;
    }

    /**
     * Pre-Populate the database with cities that the user may select from.
     * Only Unique cities (based on latitude/longitude) are added.
     */
    private void initDbLoad()
    {


        // San Francisco, CA, USA
        CityModel sanFran = new CityModel("San Francisco", "CA", "US",
                37.773972f, -122.431297f);

        // Add Champaign, IL, USA
        CityModel champaign = new CityModel("Champaign", "IL", "US",
                40.116421f, -88.243385f);

        // Add Miami
        CityModel miami = new CityModel("Miami", "FL", "US",
                25.76181f, -80.191788f);

        // Add New York
        CityModel nyc = new CityModel("New York City", "NY", "US",
                40.730610f, -73.935242f);

        // Add Chicago
        CityModel chicago = new CityModel("Chicago", "IL", "US",
                41.881832f, -87.623177f);

        // Add Phoenix
        CityModel phoenix = new CityModel("Phoenix", "AZ", "US",
                33.448376f, -112.074036f);

        // Add Boston
        CityModel boston = new CityModel("Boston", "MA", "US",
                42.361145f, -71.057083f);

        // Add Boston
        CityModel orlando = new CityModel("Orlando", "FL", "US",
                28.538336f, -81.379234f);

        // Add SLC
        CityModel slc = new CityModel("Salt Lake City", "UT", "US",
                40.758701f, -111.876183f);

        // Add LA
        CityModel la = new CityModel("Los Angeles", "CA", "US",
                34.052235f, -118.243683f);

        // Add Dallas
        CityModel dallas = new CityModel("Dallas", "TX", "US",
                29.749907f, -95.358421f);

        // Add Denver
        CityModel denver = new CityModel("Denver", "CO", "US",
                39.742043f, -104.991531f);

        // Add Cities to the DB
        theCityService.addCities(sanFran, champaign, miami, nyc, chicago, phoenix, boston, orlando, slc, la, dallas, denver);
        List<CityModel> defaultCitiesForAdmin = new ArrayList<>();

        UserModel admin = new UserModel("TestUser","TestUser", false, defaultCitiesForAdmin);
        theUserService.addUser(admin);


    }
}
