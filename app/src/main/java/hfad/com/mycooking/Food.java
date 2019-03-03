package hfad.com.mycooking;

import java.util.ArrayList;

public class Food implements Comparable<Food> {
    private String name;
    private String recipe;
    private int imageId;
    private int favorite;

    public Food(String name, String recipe, int imageId, int favorite) {
        this.name = name;
        this.recipe = recipe;
        this.imageId = imageId;
        this.favorite = favorite;
    }



    public String getName() {
        return name;
    }

    public String getRecipe() {
        return recipe;
    }

    public int getImageId() {
        return imageId;
    }

    public int getFavorite() {
        return favorite;
    }

    @Override
    public int compareTo(Food o) {
        return this.getName().compareTo(o.getName());
    }
}
