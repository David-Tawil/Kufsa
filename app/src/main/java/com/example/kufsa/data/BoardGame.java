package com.example.kufsa.data;


import android.os.Parcel;
import android.os.Parcelable;

public class BoardGame implements Parcelable {
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
    // Variables of elements that form a board game
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
    private String id;


    public BoardGame() {// Needed for Firebase
    }

    /**
     * Board game constructor
     *
     * @param name            The name of the board game.
     * @param img             The URL of the game's image.
     * @param description     The game's description text.
     * @param publisher       The name of the game's publisher.
     * @param difficulty      The game's level of difficulty.
     * @param maxNumOfPlayers Maximal number of players in the game.
     * @param minNumOfPlayers Minimal number of players in the game.
     * @param playingTime     The amount of minimal time required for playing.
     * @param releaseYear     The year the game was released in.
     * @param minAge          The minimal age requirement for players of the game.
     */
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

    /**
     * @param in parcel object that contains all board game info
     */
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

    /**
     * Gets a game's Firebase ID.
     *
     * @return The game's ID in the form of a string.
     */
    public String getId() {
        return id;
    }

    /**
     * Set a game's Firebase ID.
     *
     * @param id The ID you wish to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets a game's image URL.
     *
     * @return The game's image URL as a string.
     */
    public String getImg() {
        return img;
    }

    /**
     * Set a game's image URL.
     *
     * @param img The URL you wish to set for the game's image
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Gets a game's publisher.
     *
     * @return The game's publisher as a string.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Set a game's publisher name.
     *
     * @param publisher The name of the publisher you wish to set.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets a game's difficulty.
     *
     * @return The game's difficulty as a float.
     */
    public Float getDifficulty() {
        return difficulty;
    }

    /**
     * Set a game's difficulty level in the form of a float.
     *
     * @param difficulty The game's difficulty level you wish to set.
     */
    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets a game's maximal number of players.
     *
     * @return The game's maximal number of players as an int.
     */
    public int getMaxNumOfPlayers() {
        return maxNumOfPlayers;
    }

    /**
     * Set a game's maximal numbeer of players.
     *
     * @param maxNumOfPlayers The game's number of players you wish to set.
     */
    public void setMaxNumOfPlayers(int maxNumOfPlayers) {
        this.maxNumOfPlayers = maxNumOfPlayers;
    }

    /**
     * Gets a game's minimal number of players.
     *
     * @return The game's minimal number of players as an int.
     */
    public int getMinNumOfPlayers() {
        return minNumOfPlayers;
    }

    /**
     * Set a game's minimal numbeer of players.
     *
     * @param minNumOfPlayers The game's number of players you wish to set.
     */
    public void setMinNumOfPlayers(int minNumOfPlayers) {
        this.minNumOfPlayers = minNumOfPlayers;
    }

    /**
     * Gets a game's playing time.
     *
     * @return The game's playing time as an int.
     */
    public int getPlayingTime() {
        return playingTime;
    }

    /**
     * Set a game's playing time.
     *
     * @param playingTime The game's playing time you wish to set.
     */
    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    /**
     * Gets a game's release year.
     *
     * @return The game's release year as an int.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Set a game's release year.
     *
     * @param releaseYear The game's release year you wish to set.
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Gets a game's minimal age requirement.
     *
     * @return The game's minimal age requirement as an int.
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Set a game's minimal age requirement.
     *
     * @param minAge The game's minimal playing age you wish to set.
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    /**
     * Gets a game's name.
     *
     * @return The game's name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * Set a game's name.
     *
     * @param name The name you wish to set for the game
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a game's description.
     *
     * @return The game's description in the form of a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set a game's description.
     *
     * @param description The description you wish to set for the game.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets a game's average review score.
     *
     * @return The game's average review score in the form of a float.
     */
    public float getAverageReviewScore() {
        return averageReviewScore;
    }

    /**
     * Set a game's average review score.
     *
     * @param averageReviewScore The score you wish to set for the game.
     */
    public void setAverageReviewScore(float averageReviewScore) {
        this.averageReviewScore = averageReviewScore;
    }

    /**
     * Gets a game's number of total reviews.
     *
     * @return The game's total reviews in the form of an int.
     */
    public int getTotalReviews() {
        return totalReviews;
    }

    /**
     * Set a game's num of total reviews.
     *
     * @param totalReviews The number of reviews you wish to set for the game.
     */
    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    /**
     * Gets a game's number of one star reviews.
     *
     * @return The game's number of one star reviews in the form of an int.
     */
    public int getTotalOneStar() {
        return totalOneStar;
    }

    /**
     * Set a game's num of one star reviews.
     *
     * @param totalOneStar The number of one star reviews you wish to set for the game.
     */
    public void setTotalOneStar(int totalOneStar) {
        this.totalOneStar = totalOneStar;
    }

    /**
     * Gets a game's number of two star reviews.
     *
     * @return The game's number of two star reviews in the form of an int.
     */
    public int getTotalTwoStar() {
        return totalTwoStar;
    }

    /**
     * Set a game's num of total two star reviews.
     *
     * @param totalTwoStar The number of two star reviews you wish to set for the game.
     */
    public void setTotalTwoStar(int totalTwoStar) {
        this.totalTwoStar = totalTwoStar;
    }

    /**
     * Gets a game's number of three star reviews.
     *
     * @return The game's number of three star reviews in the form of an int.
     */
    public int getTotalThreeStar() {
        return totalThreeStar;
    }

    /**
     * Set a game's number of three star reviews.
     *
     * @param totalThreeStar The number of three star reviews you wish to set for the game.
     */
    public void setTotalThreeStar(int totalThreeStar) {
        this.totalThreeStar = totalThreeStar;
    }

    /**
     * Gets a game's number of four star reviews.
     *
     * @return The game's number of four star reviews in the form of an int.
     */
    public int getTotalFourStar() {
        return totalFourStar;
    }

    /**
     * Set a game's number of four star reviews.
     *
     * @param totalFourStar The number of four star reviews you wish to set for the game.
     */
    public void setTotalFourStar(int totalFourStar) {
        this.totalFourStar = totalFourStar;
    }

    /**
     * Gets a game's number of five star reviews.
     *
     * @return The game's number of five star reviews in the form of an int.
     */
    public int getTotalFiveStar() {
        return totalFiveStar;
    }

    /**
     * Set a game's number of five star reviews.
     *
     * @param totalFiveStar The number of five star reviews you wish to set for the game.
     */
    public void setTotalFiveStar(int totalFiveStar) {
        this.totalFiveStar = totalFiveStar;
    }

    /**
     * Write all board game info into a parcel obj.
     *
     * @param dest  destionation parcel.
     * @param flags flags meant to initialize.
     */
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

    /**
     * Describe a game's content.
     *
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }
}
