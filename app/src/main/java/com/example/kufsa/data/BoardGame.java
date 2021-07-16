package com.example.kufsa.data;

public class BoardGame {
    private String id;
    private String name;
    private String img;

    public BoardGame() {// Needed for Firebase
    }

    public BoardGame(String name, String imgUrl) {
        this.name = name;
        this.img = imgUrl;
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
}
