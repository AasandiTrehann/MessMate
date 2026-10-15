
package com.example.messmate.models;

public class Meal {

    private String id;
    private String name;
    private String description;
    private String category;
    private String foodType;
    private String spiceLevel;
    private String imageUrl;
    private double rating;
    private double price;
    private boolean available;
    private boolean special;
    private boolean recommended;

    public Meal() {
        // Required for Firebase Firestore.
    }

    public Meal(String id, String name, String description,
                String category, String foodType, String spiceLevel,
                String imageUrl, double rating, double price,
                boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.foodType = foodType;
        this.spiceLevel = spiceLevel;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.price = price;
        this.available = available;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }

    public String getSpiceLevel() { return spiceLevel; }
    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isSpecial() { return special; }
    public void setSpecial(boolean special) { this.special = special; }

    public boolean isRecommended() { return recommended; }
    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }
}
