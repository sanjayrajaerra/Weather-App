package edu.uiuc.cs427app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class CityModel
{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "cityId")
    private String cityId = UUID.randomUUID().toString();

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "state")
    private String State;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "latitude")
   private float latitude;

    @ColumnInfo(name = "longitude")
   private float longitude;

    /**
     * The CityModel default constructor
     */
    public CityModel() {
    }

    /**
     * The CityModel parameterized constructor
     * @param name The city name
     * @param state The state to which the city belongs
     * @param country The country to which the city belongs
     * @param latitude The city latitude coordinate
     * @param longitude The city longitude coordinate
     */
    public CityModel(String name, String state, String country, float latitude, float longitude) {
        this.name = name;
        State = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * The overwritten equals operation to compare whether
     * two cities are equivalent by evaluating id or latitude
     * longitude coordinate values
     * @param other The other object to compare
     * @return True or False, whether two cities are deemed equal
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof CityModel))
            return false;
        CityModel c = (CityModel) other;
        if (c.cityId.equals(this.cityId)) return true;
        else if (c.latitude == this.latitude && c.longitude == this.longitude) return true;
        return false;
    }

    @Override
    public String toString()
    {
        return name + ", " + State + ", " + country;
    }

    // Getters

    /**
     * Get the unique ID for the city
     * @return The city ID
     */
    public String getCityId() { return cityId; }

    /**
     * Get the name of the city
     * @return The city name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the state to which the city belongs
     * @return The state
     */
    public String getState() {
        return State;
    }

    /**
     * Get the country to which the city belongs
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Get the latitude coordinate of the city's location
     * @return The latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude coordinate of the city's location
     * @return The longitude
     */
    public float getLongitude() {
        return longitude;
    }

    // Setters

    /**
     * Set the unique ID for the city
     * @param ID Unique city ID
     */
    public void setCityId(String ID) {
        this.cityId = ID;
    }

    /**
     * Set the name of the city
     * @param name The city name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the country to which the city belongs
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Set the state to which the city belongs
     * @param state The state
     */
    public void setState(String state) {
        State = state;
    }

    /**
     * Set the latitude of the city
     * @param latitude The city latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Set the longitude of the city
     * @param longitude The city longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
