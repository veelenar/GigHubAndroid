package com.example.gighub;

public class Gig {
    private int id;
    private String artist;
    private String genre;
    private String venue;
    private String price;
    private int image;
    private String address;
    private String shortDesc;
    private String date;

    public Gig(){
    }

    public Gig(String artist, String genre, String venue, String price, String address, String shortDesc, String date) {
        this.artist = artist;
        this.genre = genre;
        this.venue = venue;
        this.price = price;
        this.address = address;
        this.shortDesc = shortDesc;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}