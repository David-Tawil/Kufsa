package com.example.kufsa.data;


import android.os.Parcel;
import android.os.Parcelable;

public class BoardGame implements Parcelable {
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
    private String id;

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
    private float averageReviewScore;
    private int totalReviews;
    private int totalOneStar;
    private int totalTwoStar;
    private int totalThreeStar;
    private int totalFourStar;
    private int totalFiveStar;

    public BoardGame(String id, String name, String description, String img, String publisher, Float difficulty, int maxNumOfPlayers, int minNumOfPlayers, int playingTime, int releaseYear, int minAge, float averageReviewScore, int totalReviews, int totalOneStar, int totalTwoStar, int totalThreeStar, int totalFourStar, int totalFiveStar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.publisher = publisher;
        this.difficulty = difficulty;
        this.maxNumOfPlayers = maxNumOfPlayers;
        this.minNumOfPlayers = minNumOfPlayers;
        this.playingTime = playingTime;
        this.releaseYear = releaseYear;
        this.minAge = minAge;
        this.averageReviewScore = averageReviewScore;
        this.totalReviews = totalReviews;
        this.totalOneStar = totalOneStar;
        this.totalTwoStar = totalTwoStar;
        this.totalThreeStar = totalThreeStar;
        this.totalFourStar = totalFourStar;
        this.totalFiveStar = totalFiveStar;
    }

    public BoardGame() {// Needed for Firebase
    }

    protected BoardGame(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        img = in.readString();
        publisher = in.readString();
        if (in.readByte() == 0) {
            difficulty = null;
        } else {
            difficulty = in.readFloat();
        }
        maxNumOfPlayers = in.readInt();
        minNumOfPlayers = in.readInt();
        playingTime = in.readInt();
        releaseYear = in.readInt();
        minAge = in.readInt();
        averageReviewScore = in.readFloat();
        totalReviews = in.readInt();
        totalOneStar = in.readInt();
        totalTwoStar = in.readInt();
        totalThreeStar = in.readInt();
        totalFourStar = in.readInt();
        totalFiveStar = in.readInt();
    }

    public static Creator<BoardGame> getCREATOR() {
        return CREATOR;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAverageReviewScore() {
        return averageReviewScore;
    }

    public void setAverageReviewScore(float averageReviewScore) {
        this.averageReviewScore = averageReviewScore;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getTotalOneStar() {
        return totalOneStar;
    }

    public void setTotalOneStar(int totalOneStar) {
        this.totalOneStar = totalOneStar;
    }

    public int getTotalTwoStar() {
        return totalTwoStar;
    }

    public void setTotalTwoStar(int totalTwoStar) {
        this.totalTwoStar = totalTwoStar;
    }

    public int getTotalThreeStar() {
        return totalThreeStar;
    }

    public void setTotalThreeStar(int totalThreeStar) {
        this.totalThreeStar = totalThreeStar;
    }

    public int getTotalFourStar() {
        return totalFourStar;
    }

    public void setTotalFourStar(int totalFourStar) {
        this.totalFourStar = totalFourStar;
    }

    public int getTotalFiveStar() {
        return totalFiveStar;
    }

    public void setTotalFiveStar(int totalFiveStar) {
        this.totalFiveStar = totalFiveStar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(img);
        dest.writeString(publisher);
        if (difficulty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(difficulty);
        }
        dest.writeInt(maxNumOfPlayers);
        dest.writeInt(minNumOfPlayers);
        dest.writeInt(playingTime);
        dest.writeInt(releaseYear);
        dest.writeInt(minAge);
        dest.writeFloat(averageReviewScore);
        dest.writeInt(totalReviews);
        dest.writeInt(totalOneStar);
        dest.writeInt(totalTwoStar);
        dest.writeInt(totalThreeStar);
        dest.writeInt(totalFourStar);
        dest.writeInt(totalFiveStar);
    }
}
