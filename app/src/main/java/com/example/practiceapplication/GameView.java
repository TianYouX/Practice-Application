package com.example.practiceapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {

    Bitmap background, ground, animal;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int scores = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float animalX, animalY;
    float oldX;
    float oldAnimalX;
    ArrayList<Food> foods;
    ArrayList<Explosion> explosions;
    SharedPreferences sharedPreferences;

    // Constructor of GameView
    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        sharedPreferences = context.getSharedPreferences("my_pref", 0);
        animal = BitmapFactory.decodeResource(getResources(), sharedPreferences.getInt("current_animalID", R.drawable.panda));
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0, 0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenvector_future));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        animalX = dWidth / 2 - animal.getWidth() / 2;
        animalY = dHeight - ground.getHeight() - animal.getHeight();
        foods = new ArrayList<>();
        explosions = new ArrayList<>();
        for (int i=0; i<3; i++){
            Food food = new Food(context);
            foods.add(food);
        }
    }

    // Handle the drawing and rendering of graphics on the screen
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(animal, animalX, animalY, null);
        for (int i=0; i< foods.size(); i++){
            canvas.drawBitmap(foods.get(i).getFood(foods.get(i).foodFrame), foods.get(i).foodX, foods.get(i).foodY, null);
            foods.get(i).foodFrame++;
            if(foods.get(i).foodFrame > 2){
                foods.get(i).foodFrame = 0;
            }
            foods.get(i).foodY += foods.get(i).foodVelocity;
            if(foods.get(i).foodY + foods.get(i).getFoodHeight() >= dHeight - ground.getHeight()){
                scores += 10;
                Explosion explosion = new Explosion(context);
                explosion.explosionX = foods.get(i).foodX;
                explosion.explosionY = foods.get(i).foodY;
                explosions.add(explosion);
                foods.get(i).resetPosition();
            }
        }

        for (int i=0; i < foods.size(); i++){
            if (foods.get(i).foodX + foods.get(i).getFoodWidth() >= animalX && foods.get(i).foodX <= animalX + animal.getWidth() && foods.get(i).foodY + foods.get(i).getFoodWidth() >= animalY && foods.get(i).foodY + foods.get(i).getFoodWidth() <= animalY + animal.getHeight()){
                life--;
                foods.get(i).resetPosition();
                if(life == 0){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("scores", scores);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        }

        for (int i=0; i<explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).explosionX, explosions.get(i).explosionY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 3){
                explosions.remove(i);
            }
        }

        if(life == 2){
            healthPaint.setColor(Color.YELLOW);
        } else if(life == 1){
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth-200, 30, dWidth-200+60*life, 80, healthPaint);
        canvas.drawText("" + scores, 20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    // Handling the touch event on the animal
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= animalY){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldAnimalX = animalX;
            }
            if(action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newAnimalX = oldAnimalX - shift;
                if(newAnimalX <= 0){
                    animalX = 0;
                }else if(newAnimalX >= dWidth - animal.getWidth()){
                    animalX = dWidth - animal.getWidth();
                }else{
                    animalX = newAnimalX;
                }
            }
        }
        return true;
    }
}

