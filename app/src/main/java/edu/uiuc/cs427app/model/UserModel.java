package edu.uiuc.cs427app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.UUID;

@Entity
public class UserModel
{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "userId")
    private String userId = UUID.randomUUID().toString();

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "loggedIn")
    private boolean loggedIn;

    @ColumnInfo(name = "primaryColor")
    private String primaryColor;

    @ColumnInfo(name = "secondaryColor")
    private String secondaryColor;

    @Ignore
    private List<CityModel> favoriteCities;

    /**
     * The UserModel default constructor
     */
    public UserModel() {
    }

    /**
     * The UserModel parameterized constructor
     * @param username The username of the user
     * @param password The password of the user
     * @param loggedIn Flag denoting whether the user is logged in
     * @param favoriteCities The list of cities the user has selected
     */
    public UserModel(String username, String password, boolean loggedIn, List<CityModel> favoriteCities) {
        this.username = username;
        this.loggedIn = loggedIn;
        this.password = password;
        this.favoriteCities = favoriteCities;
        this.primaryColor = "purple";
        this.secondaryColor = "white";
    }

    /**
     * The overwritten equals operation to compare whether
     * two users are equivalent by evaluating id or username
     * @param other The other user to compare
     * @return True or False, whether the users are deemed equal
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof UserModel))
            return false;
        UserModel u = (UserModel) other;
        if (u.userId.equals(this.userId)) return true;
        else if (u.username.equals(this.username)) return true;
        return false;
    }

    /**
     * Get the unique ID for the user
     * @return The user ID
     */
    public String getUserId() { return userId; }

    /**
     * Set the user ID
     * @param uid The ID
     */
    public void setUserId(String uid) { this.userId = uid; }

    /**
     * Get the user's username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's password
     * @return The password set by the user
     */
    public String getPassword() {return password;}

    /**
     * Get whether the user is logged in
     * @return True or False, whether the user is logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Get the list of selected cities by the user
     * @return The list of cities
     */
    public List<CityModel> getFavoriteCities() {
        return favoriteCities;
    }

    /**
     * Set the user's username
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the user's password
     * @param password The password
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Set whether the user is logged in
     * @param loggedIn True or False, denoting whether the user is logged in
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Set the selected cities for the user
     * @param favoriteCities List of cities
     */
    public void setFavoriteCities(List<CityModel> favoriteCities) {
        this.favoriteCities = favoriteCities;
    }

    /**
     * Get the primary color selected by the user for application theme
     * customization
     * @return The primary color
     */
    public String getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Set the primary color for the user for application theme
     * customization
     * @param primaryColor The primary color
     */
    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    /**
     * Get the secondary color selected by the user for application theme
     * customization
     * @return The secondary color
     */
    public String getSecondaryColor() {
        return secondaryColor;
    }

    /**
     * Set the secondary color for the user for application theme
     * customization
     * @param secondaryColor The secondary color
     */
    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    /**
     * Overwritten toString method to display the user properties
     * @return The UserModel represented by a String
     */
    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", loggedIn=" + loggedIn +
                ", primaryColor='" + primaryColor + '\'' +
                ", secondaryColor='" + secondaryColor + '\'' +
                ", favoriteCities=" + favoriteCities +
                '}';
    }
}
