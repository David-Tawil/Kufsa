package com.example.kufsa.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class MarketAd implements Parcelable {


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

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MarketAd> CREATOR = new Creator<MarketAd>() {
        @Override
        public MarketAd createFromParcel(Parcel in) {
            return new MarketAd(in);
        }

        @Override
        public MarketAd[] newArray(int size) {
            return new MarketAd[size];
        }
    };

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public enum TradeType {SELL, RENT, EXCHANGE}

    public enum Condition {
        NEW, LIKE_NEW, VERY_GOOD, GOOD, ACCEPTABLE;

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

    public enum RentalPeriod {
        DAY, WEEK, MONTH;

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

    public MarketAd() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public RentalPeriod getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(RentalPeriod rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public boolean isCreditCard() {
        return creditCard;
    }

    public void setCreditCard(boolean creditCard) {
        this.creditCard = creditCard;
    }

    public boolean isBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(boolean bitcoin) {
        this.bitcoin = bitcoin;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public boolean isPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(boolean phoneContact) {
        this.phoneContact = phoneContact;
    }

    public boolean isEmailContact() {
        return emailContact;
    }

    public void setEmailContact(boolean emailContact) {
        this.emailContact = emailContact;
    }

    public boolean isWhatsappContact() {
        return whatsappContact;
    }

    public void setWhatsappContact(boolean whatsappContact) {
        this.whatsappContact = whatsappContact;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
