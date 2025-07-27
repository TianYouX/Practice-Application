package com.example.practiceapplication;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Dialog dDialog;
    ImageView ivAnimal;
    SharedPreferences sharedPreferences;
    Integer currentAnimalID;
    Integer currentFoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //The screen stays awake while this activity is visible
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sharedPreferences = getSharedPreferences("my_pref", 0);
        currentAnimalID = sharedPreferences.getInt("current_animalID", R.drawable.panda);
        ivAnimal = findViewById(R.id.iv_animal);
        ivAnimal.setImageResource(currentAnimalID);
    }

    public void startGame(View view){
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    public void changeAnimal(View view){
        dDialog = new Dialog(this);
        // Set the custom layout for the dialog
        dDialog.setContentView(R.layout.popup_animals_dialog);
        // Make the background of popup dialog transparent (去掉白色边边角角)
        dDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //
        ArrayList<AnimalModel> animalModelList = new ArrayList<>();
        animalModelList.add(new AnimalModel(R.drawable.monkey, "Monkey", R.drawable.banana));
        animalModelList.add(new AnimalModel(R.drawable.elephant, "Elephant", R.drawable.watermelon));
        animalModelList.add(new AnimalModel(R.drawable.giraffe, "Giraffe", R.drawable.leaf));
        animalModelList.add(new AnimalModel(R.drawable.hippo, "Hippo", R.drawable.watermelon));
        animalModelList.add(new AnimalModel(R.drawable.panda, "Panda", R.drawable.bamboo));
        animalModelList.add(new AnimalModel(R.drawable.penguin, "Penguin", R.drawable.seed));
        animalModelList.add(new AnimalModel(R.drawable.pig, "Pig", R.drawable.burger));
        animalModelList.add(new AnimalModel(R.drawable.rabbit, "Rabbit", R.drawable.carrot));
        animalModelList.add(new AnimalModel(R.drawable.snake, "Snake", R.drawable.rabbit_food));

        GridView gridView = dDialog.findViewById(R.id.gv_myGridView);
        gridView.setAdapter(new GridViewAdapter(MainActivity.this, animalModelList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AnimalModel animalModel = (AnimalModel) adapterView.getItemAtPosition(position);
                currentAnimalID = animalModel.getAnimalID();
                currentFoodID = animalModel.getFoodID();
                ivAnimal.setImageResource(currentAnimalID);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("current_animalID", currentAnimalID);
                editor.putInt("current_foodID", currentFoodID);
                editor.commit();

                dDialog.cancel();
            }
        });
        dDialog.show();
    }
}