package com.example.practiceapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Dialog dDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //The screen stays awake while this activity is visible
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void startGame(View view){

    }

    public void changeAnimal(View view){
        dDialog = new Dialog(this);
        // Set the custom layout for the dialog
        dDialog.setContentView(R.layout.popup_animals_window);
        // Make the background of popup dialog transparent (去掉白色边边角角)
        dDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dDialog.show();
    }
}