package com.example.practiceapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<AnimalModel> {
    public GridViewAdapter(@NonNull Context context, ArrayList<AnimalModel> animalModels) {
        super(context, 0, animalModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HolderView holderView;

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item_card, parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }

        AnimalModel animalModel = getItem(position);
        holderView.ivAnimal.setImageResource(animalModel.getAnimalID());
        holderView.tvAnimalName.setText(animalModel.getAnimalName());

        return convertView;
    }

    private static class HolderView{
        private final ImageView ivAnimal;
        private final TextView tvAnimalName;

        public HolderView(View view) {
            ivAnimal = view.findViewById(R.id.iv_animal);
            tvAnimalName = view.findViewById(R.id.tv_animalName);
        }
    }

}
