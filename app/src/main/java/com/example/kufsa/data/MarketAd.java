/*
  @authors Aaron David Tawil & Eldar Weiss
*/

package com.example.kufsa.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * This class defines the marketAd object used as an ad for sale for each game
 */
public class MarketAd implements Parcelable {


    public static final Creator<MarketAd> CREATOR = new Creator<MarketAd>() {
        /**
         * This method creates an ad from a parcel object
         * @param in parcel project
         * @return the market ad object created
         */
        @Override
        public MarketAd createFromParcel(Parcel in) {
            return new MarketAd(in);
        }

        /**
         * This method sets up an array of ads
         * @param size length of the array
         * @return a new, empty array of ads
         */
        @Override
        public MarketAd[] newArray(int size) {
            return new MarketAd[size];
        }
    };
    private String id;
    private String userID;
    private String gameID;
    private String userName;
    @ServerTimestamp
    private Date publishDate;
    private TradeType tradeType;
    private Condition condition;
    private String city;
    private double sellPrice;
    private double rentalFee;
    private RentalPeriod rentalPeriod;
    private String email;
    private String phone;
    private boolean cash;
    private boolean creditCard;
    private boolean bitcoin;
    private boolean other;
    private boolean phoneContact;
    private boolean emailContact;
    private boolean whatsappContact;
    private String notes;
    private String userPhotoUrl;

    /**
     * This method sets up the market ad
     *
     * @param in parcel object being used
     */
    protected MarketAd(Parcel in) {
        id = in.readString();
        userID = in.readString();
        gameID = in.readString();
        userName = in.readString();
        city = in.readString();
        sellPrice = in.readDouble();
        rentalFee = in.readDouble();
        email = in.readString();
        phone = in.readString();
        cash = in.readByte() != 0;
        creditCard = in.readByte() != 0;
        bitcoin = in.readByte() != 0;
        other = in.readByte() != 0;
        phoneContact = in.readByte() != 0;
        emailContact = in.readByte() != 0;
        whatsappContact = in.readByte() != 0;
        notes = in.readString();
        userPhotoUrl = in.readString();
    }

    /**
     * Constructor
     */
    public MarketAd() {
    }

    /**
     * Write all market Ad info into a parcel obj.
     *
     * @param dest  destionation parcel.
     * @param flags flags meant to initialize.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userID);
        dest.writeString(gameID);
        dest.writeString(userName);
        dest.writeString(city);
        dest.writeDouble(sellPrice);
        dest.writeDouble(rentalFee);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeByte((byte) (cash ? 1 : 0));
        dest.writeByte((byte) (creditCard ? 1 : 0));
        dest.writeByte((byte) (bitcoin ? 1 : 0));
        dest.writeByte((byte) (other ? 1 : 0));
        dest.writeByte((byte) (phoneContact ? 1 : 0));
        dest.writeByte((byte) (emailContact ? 1 : 0));
        dest.writeByte((byte) (whatsappContact ? 1 : 0));
        dest.writeString(notes);
        dest.writeString(userPhotoUrl);
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
     * Get market ad photo url
     *
     * @return photoUrl as a string
     */
    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    /**
     * set review photo url
     *
     * @param userPhotoUrl as a string that you wish to set
     */
    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    /**
     * Get ad ID
     *
     * @return id as a string
     */
    public String getId() {
        return id;
    }

    /**
     * set ad ID
     *
     * @param id as a string
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get user ID
     *
     * @return userID as a string
     */
    public String getUserID() {
        return userID;
    }

    /**
     * set user ID
     *
     * @param userID the user id string that you wish to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Get game ID
     *
     * @return gameID as a string
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * set game ID
     *
     * @param gameID the user id string that you wish to set
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
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
     * Get an ad's publishing date
     *
     * @return publishDate as a date object
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Set an ad's publishing date
     *
     * @param publishDate as a date object you wish to set
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Get an ad's trade type
     *
     * @return tradeType as a trade type enum
     */
    public TradeType getTradeType() {
        return tradeType;
    }

    /**
     * set ad's trade type
     *
     * @param tradeType the type of trade you wish to set
     */
    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Get the game in the ads's condition
     *
     * @return the game's condition enum
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Set the game in the ads's condition
     *
     * @param condition game's condition enum
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * Get the city's name in the ad
     *
     * @return the game's city as a string
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city's name in the ad
     *
     * @param city game's city as a string
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the game's price in the ad
     *
     * @return the game's price as a double
     */
    public double getSellPrice() {
        return sellPrice;
    }

    /**
     * Set the game's price in the ad
     *
     * @param sellPrice game's price as a double
     */
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Get the game's rental fee in the ad
     *
     * @return the game's rental fee as a double
     */
    public double getRentalFee() {
        return rentalFee;
    }

    /**
     * Set the game's rental fee in the ad
     *
     * @param rentalFee game's rental fee as a double
     */
    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    /**
     * Get the game's rental period in the ad
     *
     * @return rentalPeriod game's rental fee as a rentalPeriod object
     */
    public RentalPeriod getRentalPeriod() {
        return rentalPeriod;
    }

    /**
     * Set the game's rental period in the ad
     *
     * @param rentalPeriod game's rental fee as a rentalPeriod object
     */
    public void setRentalPeriod(RentalPeriod rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    /**
     * Get the email posted in the ad
     *
     * @return email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email to be posted in the ad
     *
     * @param email string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the phone posted in the ad
     *
     * @return phone string
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone to be posted in the ad
     *
     * @param phone string
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Check if payment method is cash
     *
     * @return cash enum
     */
    public boolean isCash() {
        return cash;
    }

    /**
     * Set payment method as cash
     *
     * @param cash enum
     */
    public void setCash(boolean cash) {
        this.cash = cash;
    }

    /**
     * Check if payment method is creditCard
     *
     * @return creditCard enum
     */
    public boolean isCreditCard() {
        return creditCard;
    }

    /**
     * Set payment method as creditCard
     *
     * @param creditCard enum
     */
    public void setCreditCard(boolean creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * Check if payment method is bitcoin
     *
     * @return bitcoin enum
     */
    public boolean isBitcoin() {
        return bitcoin;
    }

    /**
     * Set payment method as creditCard
     *
     * @param bitcoin enum
     */
    public void setBitcoin(boolean bitcoin) {
        this.bitcoin = bitcoin;
    }

    /**
     * Check if payment method is other
     *
     * @return other enum
     */
    public boolean isOther() {
        return other;
    }

    /**
     * Set payment method as other
     *
     * @param other enum
     */
    public void setOther(boolean other) {
        this.other = other;
    }

    /**
     * Check if contact for ad is by phone
     *
     * @return phoneContact enum
     */
    public boolean isPhoneContact() {
        return phoneContact;
    }

    /**
     * Set contact for ad As by phone
     *
     * @param phoneContact enum
     */
    public void setPhoneContact(boolean phoneContact) {
        this.phoneContact = phoneContact;
    }

    /**
     * Check if contact for ad is by email
     *
     * @return emailContact enum
     */
    public boolean isEmailContact() {
        return emailContact;
    }

    /**
     * Set contact for ad As by email
     *
     * @param emailContact enum
     */
    public void setEmailContact(boolean emailContact) {
        this.emailContact = emailContact;
    }

    /**
     * Check if contact for ad is by whatsapp
     *
     * @return whatsappContact enum
     */
    public boolean isWhatsappContact() {
        return whatsappContact;
    }

    /**
     * Set contact for ad As by whatsapp
     *
     * @param whatsappContact enum
     */
    public void setWhatsappContact(boolean whatsappContact) {
        this.whatsappContact = whatsappContact;
    }

    /**
     * Get the notes written for an ad
     *
     * @return notes as a string
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set the notes for an ad
     *
     * @param notes as a string
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * This is all possible results for tradeType
     */
    public enum TradeType {SELL, RENT, EXCHANGE}

    /**
     * This is all possible results for Condition
     */
    public enum Condition {
        NEW, LIKE_NEW, VERY_GOOD, GOOD, ACCEPTABLE;

        /**
         * Conert condition to string
         *
         * @return condition set in string form
         */
        @Override
        public String toString() {
            switch (this) {
                case NEW:
                    return "New";
                case LIKE_NEW:
                    return "Like new";
                case VERY_GOOD:
                    return "Very good";
                case GOOD:
                    return "Good";
                case ACCEPTABLE:
                    return "Acceptable";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    /**
     * This is all possible results for Rental Period
     */
    public enum RentalPeriod {
        DAY, WEEK, MONTH;

        /**
         * Conert Rental Period to string
         *
         * @return RentalPeriod set in string form
         */
        @Override
        public String toString() {
            switch (this) {
                case DAY:
                    return "Per day";
                case WEEK:
                    return "Per week";
                case MONTH:
                    return "Per month";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
