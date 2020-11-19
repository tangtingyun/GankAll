package com.step.example.widget.douyin;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class DouYinAdapter extends RecyclerView.Adapter<DouYinAdapter.DouYinViewHolder> {


    @NonNull
    @Override
    public DouYinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        return new DouYinViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull DouYinViewHolder holder, int position) {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.itemView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class DouYinViewHolder extends RecyclerView.ViewHolder {

        public DouYinViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
