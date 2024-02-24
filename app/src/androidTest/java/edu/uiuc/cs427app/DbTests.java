package edu.uiuc.cs427app;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.AppDb;

/**
 * JUnit tests for testing the application database
 */
@RunWith(AndroidJUnit4.class)
public class DbTests {
    // The user and city services
    private UserService theUserService;
    private CityService theCityService;

    // The database
    private AppDb db;

    /**
     * Create a new database before each test
     */
    @Before
    public void createDb()
    {
        // Build the test database. The database is created if it does not already exist
        db = Room.databaseBuilder(ApplicationProvider.getApplicationContext(),
                AppDb.class, "test.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        File dbFile = new File(db.getOpenHelper().getWritableDatabase().getPath());

        // Just in case the database already existed, delete it and create again
        // to start every test with a clean default database
        if(dbFile.delete())
        {
            db = Room.databaseBuilder(ApplicationProvider.getApplicationContext(),
                    AppDb.class, "test.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        // Create the services
        theUserService = new UserService(db);
        theCityService = new CityService(db);

        // Pre-Populate Database
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
    }

    /**
     * Shut down the database after each test
     */
    @After
    public void closeDb()
    {
        db.close();
    }

    /**
     * Verify the database is populated with default data from init.db
     */
    @Test
    public void verifyPrePopulatedDatabase()
    {
        // Verify the database contains default cities
        Assert.assertTrue(theCityService.getAllCities().size() > 0);
    }

    /**
     * Test adding a user to the database
     */
    @Test
    public void addUser()
    {
        // Create a test user
        UserModel testUser = new UserModel("testuser","testuser", false, new ArrayList<>());
        // Add the user to the database
        theUserService.addUser(testUser);
        // Get a user from the database by username
        UserModel userByUsername = theUserService.getUserByUsername(testUser.getUsername());
        // Verify users are the same
        Assert.assertNotNull(userByUsername);
        Assert.assertEquals(testUser.getUserId(), userByUsername.getUserId());
        Assert.assertEquals(testUser.getUsername(), userByUsername.getUsername());
    }

    /**
     * Tests adding a user that has cities
     */
    @Test
    public void addUserWithCities()
    {
        // Create a test user
        UserModel testUser = new UserModel("testuser","testuser", false, new ArrayList<>());

        // Get the cities from the database which are available to add to a user's list
        List<CityModel> citiesFromDb = theCityService.getAllCities();

        // Add a couple of the cities to the user's list
        List<CityModel> cities = new ArrayList<>();
        cities.add(citiesFromDb.get(0));
        cities.add(citiesFromDb.get(1));

        // Set the user's cities
        testUser.setFavoriteCities(cities);

        // Add the user to the database
        theUserService.addUser(testUser);

        // Get the user from the database by username
        UserModel userByUsername = theUserService.getUserByUsername(testUser.getUsername());
        // Since Room does not allow entities to reference entities, the user retrieved from the database
        // will not have the cities on the object itself. Therefore set the user's cities by
        // using the city service and get the cities for this user from the database..
        userByUsername.setFavoriteCities(theUserService.getCitiesForUser(userByUsername));

        // Verify users are the same
        Assert.assertNotNull(userByUsername);
        Assert.assertEquals(testUser.getUserId(), userByUsername.getUserId());
        Assert.assertEquals(testUser.getUsername(), userByUsername.getUsername());
        // Verify the list of cities were stored
        Assert.assertEquals(cities.size(), userByUsername.getFavoriteCities().size());
        Assert.assertTrue(userByUsername.getFavoriteCities().contains(cities.get(0)));
        Assert.assertTrue(userByUsername.getFavoriteCities().contains(cities.get(1)));
    }

    /**
     * Test adding a city to the database that already exists in the database
     */
    @Test
    public void AddCityExists()
    {
        // Add Miami
        CityModel miami = new CityModel("Miami", "FL", "US",
                25.76181f, -80.191788f);

        // Add miami to the database
        Assert.assertFalse(theCityService.addCity(miami));
    }

    /**
     * Test adding a new city to the database
     */
    @Test
    public void AddCityDoesNotExist()
    {
        // Create Nashville city model
        CityModel nashville = new CityModel("Nashville", "TN", "US",
                36.174465f, -86.767960f);

        // Add nashville to the database
        Assert.assertTrue(theCityService.addCity(nashville));

        // Retrieve nashville from the database
        CityModel getCityByLatLon = theCityService.getCityByLatLon(nashville.getLatitude(), nashville.getLongitude());
        Assert.assertNotNull(getCityByLatLon);
        Assert.assertEquals(nashville.getCityId(), getCityByLatLon.getCityId());
    }
}
