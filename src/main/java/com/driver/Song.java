package com.driver;

public class Song {
    private String title;
    private int length;
    private int likes;

    private  boolean isliked;

    private String albumName;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public boolean isIsliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    public Song(String title, String albumName , int length) {
        this.title = title;
        this.length = length;
        this.albumName = albumName;
        this.isliked = false;
    }



    public Song(){

    }

    public Song(String title, int length){
        this.title = title;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
