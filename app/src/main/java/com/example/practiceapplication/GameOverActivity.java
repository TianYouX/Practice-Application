package com.example.practiceapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameOverActivity extends AppCompatActivity {

    TextView tvScore;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_over);

        tvScore = findViewById(R.id.tv_scores);
        tvHighest = findViewById(R.id.tv_highest);
        ivNewHeight = findViewById(R.id.iv_new_highest);

        int scores = getIntent().getExtras().getInt("scores");
        tvScore.setText(""+scores);
        sharedPreferences = getSharedPreferences("my_pref", 0);
        int highest = sharedPreferences.getInt("highest",0);
        if (scores > highest){
            ivNewHeight.setVisibility(View.VISIBLE);
            highest = scores;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest" ,highest);
            editor.commit();
        }
        tvHighest.setText(""+highest);
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}