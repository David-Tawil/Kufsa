package com.example.kufsa.data;


import android.os.Parcel;
import android.os.Parcelable;

public class BoardGame implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String img; // image url
    private String publisher;

    private Float difficulty;
    private int maxNumOfPlayers;
    private int minNumOfPlayers;
    private int playingTime;
    private int releaseYear;
    private int minAge;

    public static final Creator<BoardGame> CREATOR = new Creator<BoardGame>() {
        @Override
        public BoardGame createFromParcel(Parcel in) {
            return new BoardGame(in);
        }

        @Override
        public BoardGame[] newArray(int size) {
            return new BoardGame[size];
        }
    };

    public BoardGame() {// Needed for Firebase
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    public int getMaxNumOfPlayers() {
        return maxNumOfPlayers;
    }

    public void setMaxNumOfPlayers(int maxNumOfPlayers) {
        this.maxNumOfPlayers = maxNumOfPlayers;
    }

    public int getMinNumOfPlayers() {
        return minNumOfPlayers;
    }

    public void setMinNumOfPlayers(int minNumOfPlayers) {
        this.minNumOfPlayers = minNumOfPlayers;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public BoardGame(String name, String img, String description, String publisher, Float difficulty, int maxNumOfPlayers, int minNumOfPlayers, int playingTime, int releaseYear, int minAge) {
        this.name = name;
        this.img = img;
        this.description = description;
        this.publisher = publisher;
        this.difficulty = difficulty;
        this.maxNumOfPlayers = maxNumOfPlayers;
        this.minNumOfPlayers = minNumOfPlayers;
        this.playingTime = playingTime;
        this.releaseYear = releaseYear;
        this.minAge = minAge;
    }

    protected BoardGame(Parcel in) {
        id = in.readString();
        name = in.readString();
        img = in.readString();
        description = in.readString();
        publisher = in.readString();
        difficulty = in.readFloat();
        maxNumOfPlayers = in.readInt();
        minNumOfPlayers = in.readInt();
        playingTime = in.readInt();
        releaseYear = in.readInt();
        minAge = in.readInt();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeString(description);
        dest.writeString(publisher);
        dest.writeFloat(difficulty);
        dest.writeInt(maxNumOfPlayers);
        dest.writeInt(minNumOfPlayers);
        dest.writeInt(playingTime);
        dest.writeInt(releaseYear);
        dest.writeInt(minAge);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
