package com.example.practiceapplication;

import android.app.Dialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //The screen stays awake while this activity is visible
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ivAnimal = findViewById(R.id.iv_animal);
    }

    public void startGame(View view){

    }

    public void changeAnimal(View view){
        dDialog = new Dialog(this);
        // Set the custom layout for the dialog
        dDialog.setContentView(R.layout.popup_animals_dialog);
        // Make the background of popup dialog transparent (去掉白色边边角角)
        dDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //
        ArrayList<AnimalModel> animalModelList = new ArrayList<>();
        animalModelList.add(new AnimalModel(R.drawable.monkey, "Monkey", 0));
        animalModelList.add(new AnimalModel(R.drawable.elephant, "Elephant", 0));
        animalModelList.add(new AnimalModel(R.drawable.giraffe, "Giraffe", 0));
        animalModelList.add(new AnimalModel(R.drawable.hippo, "Hippo", 0));
        animalModelList.add(new AnimalModel(R.drawable.panda, "Panda", 0));
        animalModelList.add(new AnimalModel(R.drawable.penguin, "Penguin", 0));
        animalModelList.add(new AnimalModel(R.drawable.pig, "Pig", 0));
        animalModelList.add(new AnimalModel(R.drawable.rabbit, "Rabbit", 0));
        animalModelList.add(new AnimalModel(R.drawable.snake, "Snake", 0));

        GridView gridView = dDialog.findViewById(R.id.gv_myGridView);
        gridView.setAdapter(new GridViewAdapter(MainActivity.this, animalModelList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AnimalModel animalModel = (AnimalModel) adapterView.getItemAtPosition(position);
                ivAnimal.setImageResource(animalModel.getAnimalID());
                dDialog.cancel();
            }
        });
        dDialog.show();
    }
}