package com.example.kufsa.data;


import android.os.Parcel;
import android.os.Parcelable;

public class BoardGame implements Parcelable {
    private String id;
    private String name;
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
    private String img; // image url

    public BoardGame() {// Needed for Firebase
    }

    private String description;

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

    public BoardGame(String name, String img, String description) {
        this.name = name;
        this.img = img;
        this.description = description;
    }

    protected BoardGame(Parcel in) {
        id = in.readString();
        name = in.readString();
        img = in.readString();
        description = in.readString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
