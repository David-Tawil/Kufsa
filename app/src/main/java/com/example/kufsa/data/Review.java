/*
  @authors Aaron David Tawil & Eldar Weiss
*/

package com.example.kufsa.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * This class sets up the review object for each game
 */
public class Review implements Parcelable {
    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
    private String userId;
    private String userName;
    private String photoUrl;
    private int starNum;
    private String description;
    @ServerTimestamp
    private Date date;

    public Review() {
    }

    /**
     * This is a review constructor
     *
     * @param userName    reviewer's user name
     * @param photoUrl    photo url
     * @param starNum     number of stars given
     * @param description desc used
     * @param date        date of review
     */
    public Review(String userName, String photoUrl, int starNum, String description, Date date) {
        this.userName = userName;
        this.photoUrl = photoUrl;
        this.starNum = starNum;
        this.description = description;
        this.date = date;
    }

    /**
     * Review constructor from pacel
     *
     * @param in parcel object
     */
    protected Review(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        photoUrl = in.readString();
        starNum = in.readInt();
        description = in.readString();
    }

    /**
     * Write all board game info into a marketAd obj.
     *
     * @param dest  destionation parcel.
     * @param flags flags meant to initialize.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(photoUrl);
        dest.writeInt(starNum);
        dest.writeString(description);
    }

    /**
     * Show contents of object
     *
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Get user ID
     *
     * @return userID as a string
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set user ID
     *
     * @param userId the user id string that you wish to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get user name
     *
     * @return userName as a string
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set user name
     *
     * @param userName the user name string that you wish to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get market ad photo RUL
     *
     * @return photoUrl as a string
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * set market ad photo
     *
     * @param photoUrl as a string that you wish to set
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * Get number of stars (reviewed) for the game
     *
     * @return starNum as an int
     */
    public int getStarNum() {
        return starNum;
    }

    /**
     * Set number of stars (reviewed) for the game
     *
     * @param starNum as an int
     */
    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    /**
     * Gets an ad's description.
     *
     * @return The ad's description in the form of a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set an ad's description.
     *
     * @param description The description you wish to set for the ad.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the date the ad was made on
     *
     * @return Date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date the ad was made on
     *
     * @param date object
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
