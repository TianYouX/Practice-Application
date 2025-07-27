package com.example.practiceapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Food {
    Bitmap food [] = new Bitmap[3];
    int foodFrame = 0;
    int foodX, foodY, foodVelocity;
    Random random;
    SharedPreferences sharedPreferences;

    public Food(Context context){
        sharedPreferences = context.getSharedPreferences("my_pref", 0);
        food[0] = BitmapFactory.decodeResource(context.getResources(), sharedPreferences.getInt("current_foodID", R.drawable.seed));
        food[1] = BitmapFactory.decodeResource(context.getResources(), sharedPreferences.getInt("current_foodID", R.drawable.seed));
        food[2] = BitmapFactory.decodeResource(context.getResources(), sharedPreferences.getInt("current_foodID", R.drawable.seed));
        random = new Random();
        resetPosition();
    }

    public Bitmap getFood(int foodFrame) {
        return food[foodFrame];
    }

    public int getFoodWidth() {
        return food[0].getWidth();
    }

    public int getFoodHeight() {
        return food[0].getHeight();
    }

    public void resetPosition(){
        foodX = random.nextInt(GameView.dWidth - getFoodWidth());
        foodY = -200 + random.nextInt(600) * -1;
        foodVelocity = 35 + random.nextInt(16);
    }

}
