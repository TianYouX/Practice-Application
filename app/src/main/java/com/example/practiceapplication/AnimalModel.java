package com.example.practiceapplication;

public class AnimalModel {

    private final Integer animalID;
    private final String animalName;
    private final Integer foodID;

    public AnimalModel(Integer animalID, String animalName, Integer foodID){
        this.animalID = animalID;
        this.animalName = animalName;
        this.foodID = foodID;
    }

    public Integer getAnimalID(){
        return animalID;
    }

    public String getAnimalName(){
        return animalName;
    }

    public Integer getFoodID(){
        return foodID;
    }
}
