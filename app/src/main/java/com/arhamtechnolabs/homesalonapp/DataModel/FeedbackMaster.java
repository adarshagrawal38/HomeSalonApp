package com.arhamtechnolabs.homesalonapp.DataModel;

public class FeedbackMaster {

    String name,  rating    , comments, description;

    public FeedbackMaster(String name, String rating, String comments, String description) {
        this.name = name;
        this.rating = rating;
        this.comments = comments;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
