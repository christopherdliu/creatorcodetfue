package com.example.dingliu.creatorcodetfue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {

    String name;
    ArrayList<Ingredient> ingredients;

    public Recipe() {
        this.ingredients = new ArrayList<Ingredient>();
        this.name = "sample name";
    }

    public Recipe(List<Ingredient> ingredients, String name){
        this.ingredients = new ArrayList<Ingredient>(ingredients);
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients){
        this.ingredients = new ArrayList<Ingredient>(ingredients);
    }
}
