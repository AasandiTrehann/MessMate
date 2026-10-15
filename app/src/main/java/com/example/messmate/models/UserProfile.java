package com.example.messmate.models;

public class UserProfile {

    private String uid;
    private String name;
    private String email;
    private String hostel;
    private String room;
    private String foodType;
    private String spiceLevel;
    private String favoriteFoods;
    private String role;
    private long createdAt;

    public UserProfile() {
        // Required for Firebase Firestore
    }

    public UserProfile(String uid, String name, String email, String hostel, String room, String foodType, String spiceLevel, String favoriteFoods, String role, long createdAt) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.hostel = hostel;
        this.room = room;
        this.foodType = foodType;
        this.spiceLevel = spiceLevel;
        this.favoriteFoods = favoriteFoods;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHostel() { return hostel; }
    public void setHostel(String hostel) { this.hostel = hostel; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }

    public String getSpiceLevel() { return spiceLevel; }
    public void setSpiceLevel(String spiceLevel) { this.spiceLevel = spiceLevel; }

    public String getFavoriteFoods() { return favoriteFoods; }
    public void setFavoriteFoods(String favoriteFoods) { this.favoriteFoods = favoriteFoods; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
