
package com.example.messmate.models;

public class Recommendation {

    private Meal meal;
    private double score;
    private String reason;

    public Recommendation() {}

    public Recommendation(Meal meal, double score, String reason) {
        this.meal = meal;
        this.score = score;
        this.reason = reason;
    }

    public Meal getMeal() { return meal; }
    public void setMeal(Meal meal) { this.meal = meal; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
