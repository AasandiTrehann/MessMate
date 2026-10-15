package com.example.messmate.models;

public class MealRating {

    private String id;
    private String mealId;
    private String userId;
    private String mealName;
    private double rating;
    private String comment;
    private long createdAt;

    public MealRating() {
        // Required for Firebase Firestore
    }

    public MealRating(String id, String mealId, String userId, String mealName, double rating, String comment, long createdAt) {
        this.id = id;
        this.mealId = mealId;
        this.userId = userId;
        this.mealName = mealName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMealId() { return mealId; }
    public void setMealId(String mealId) { this.mealId = mealId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
