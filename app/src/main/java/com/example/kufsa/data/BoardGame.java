package com.example.kufsa.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Board game constructor
 */
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
    private String id;
    private String name;
    private String description; // Image description
    private String img; // Image url
    private String publisher; // Game's publisher
    private Float difficulty; // Game's level of difficulty
    private int maxNumOfPlayers;
    private int minNumOfPlayers;
    private int playingTime;
    private int releaseYear;
    private int minAge;

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

    /**
     * Board game constructor.
     *
     * @param in The parcel object that holds the game's info.
     */
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

    /**
     * Retrieves a game's description.
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
     * Retrieves a game's Firebase ID.
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
     * Retrieves a game's name.
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
     * Retrieves a game's image URL.
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
     * Retrieves a game's publisher.
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
     * Retrieves a game's difficulty.
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
     * Retrieves a game's maximal number of players.
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
     * Retrieves a game's minimal number of players.
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
     * Retrieves a game's playing time.
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
     * Retrieves a game's release year.
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
     * Retrieves a game's minimal age requirement.
     *
     * @return The game's minimal age requirementas an int.
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
     * Write all board game info into a parcel obj.
     *
     * @param dest  destionation parcel.
     * @param flags flags meant to initialize.
     */
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
