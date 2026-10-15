
package com.example.messmate.models;

public class Rating {

    private String id;
    private String mealId;
    private String userId;
    private String mealName;
    private double rating;
    private double tasteRating;
    private double qualityRating;
    private double quantityRating;
    private String comment;
    private long timestamp;

    public Rating() {
        // Required for Firebase Firestore.
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

    public double getTasteRating() { return tasteRating; }
    public void setTasteRating(double tasteRating) {
        this.tasteRating = tasteRating;
    }

    public double getQualityRating() { return qualityRating; }
    public void setQualityRating(double qualityRating) {
        this.qualityRating = qualityRating;
    }

    public double getQuantityRating() { return quantityRating; }
    public void setQuantityRating(double quantityRating) {
        this.quantityRating = quantityRating;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
