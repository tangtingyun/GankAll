package com.step.example.widget.layoutmanager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by txw_pc on 2017/4/21.
 */

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
    String[] data = new String[300];

    public Myadapter() {
        initData();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new Button(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.btn.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            btn = (Button) itemView;
        }
    }

    private void initData() {
        String[] str = new String[]{"adf", "gfgfadfaf", "gfgfadfafadf", "gfgfadfafdfa", "gfgfadfafadffad", "gfgfadfafadfasfsfd", "gfg", "gfgfadfafadfadfafadfa"};
        for (int i = 0; i < data.length; i++) {
            data[i] = str[(int) (Math.random() * str.length)];
        }
    }
}
