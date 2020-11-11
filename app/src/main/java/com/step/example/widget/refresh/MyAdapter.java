package com.step.example.widget.refresh;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private Context context;

    RecyclerView.ViewHolder holder;

    public MyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    private static final int TYPE_BODY = 1;
    private static final int TYPE_FOOT = 2;

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;

        if (position == 50) {
            viewType = TYPE_FOOT;
        } else {
            viewType = TYPE_BODY;
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_BODY:
                holder = new BodyViewHolder(new TextView(parent.getContext()));
                break;
            case TYPE_FOOT:
                holder = new FootViewHolder(new TextView(parent.getContext()));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BodyViewHolder) {

            bindBodyView((BodyViewHolder) holder, position);

        } else {

            bindFootView((FootViewHolder) holder, position);

        }


    }


    //数据绑定
    private void bindBodyView(BodyViewHolder holder, final int position) {
        ((TextView) holder.itemView).setText(position + "     hello1     ");
    }


    private void bindFootView(FootViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50 + 1;
    }


    class BodyViewHolder extends RecyclerView.ViewHolder {


        public BodyViewHolder(View itemView) {
            super(itemView);

        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
