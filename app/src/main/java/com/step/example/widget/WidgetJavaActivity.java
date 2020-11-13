package com.step.example.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.step.example.widget.recyclerview.v2.RecyclerView;
import com.step.example.widget.recyclerview.v2.ViewHolder;
import com.step.example.widget.screenadapter.UIUtils;
import com.step.gankall.R;

public class WidgetJavaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private String TAG = "tuch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_java);
        recyclerView = findViewById(R.id.table2);
        recyclerView.setAdapter(new MyAdapter(this, 50));
        new UIUtils(this).test(this);
    }

    class MyAdapter implements RecyclerView.Adapter<MyViewHolder> {
        LayoutInflater inflater;
        private int height;
        private int count;

        public MyAdapter(Context context, int count) {
            Resources resources = context.getResources();
            height = resources.getDimensionPixelSize(R.dimen.table_height);
            inflater = LayoutInflater.from(context);
            this.count = count;
        }

        @Override
        public int getItemCount() {
            return count;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_table1, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            Log.i(TAG, "onCreateViewHodler: " + viewHolder.itemView.hashCode());
            return viewHolder;
        }


        @Override
        public MyViewHolder onBindViewHolder(MyViewHolder viewHolder, int position) {
            viewHolder.tv.setText("码牛教育 " + position);
            Log.i(TAG, "onBinderViewHodler: " + viewHolder.itemView.hashCode());
            return viewHolder;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getHeight(int index) {
            return height;
        }
    }

    class MyViewHolder extends ViewHolder {
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.text1);
        }
    }

    class ImageViewHolder extends ViewHolder {
        TextView tv;
        ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.text2);
            imageView = (ImageView) view.findViewById(R.id.img);
        }
    }

    class TwoAdapter implements RecyclerView.Adapter<ViewHolder> {
        int count;

        public TwoAdapter(int count) {
            this.count = count;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {  //根据不同的viewtype,加载不同的布局
                view = LayoutInflater.from(WidgetJavaActivity.this).inflate(R.layout.item_table1, parent, false);
                return new MyViewHolder(view);
            } else {
                view = LayoutInflater.from(WidgetJavaActivity.this).inflate(R.layout.item_table2, parent, false);
                return new ImageViewHolder(view);
            }
        }

        @Override
        public ViewHolder onBindViewHolder(ViewHolder viewHodler, int position) {
            switch (getItemViewType(position)) {
                case 0:  //不同的布局，做不同的事
                    MyViewHolder holderOne = (MyViewHolder) viewHodler;
                    holderOne.tv.setText("码牛教育 布局1 ");
                    break;
                case 1:
                    ImageViewHolder holderTwo = (ImageViewHolder) viewHodler;
                    holderTwo.tv.setText("码牛教育 布局2 ");
            }

            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if (position >= 10) {   //根据你的条件，返回不同的type
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }

        @Override
        public int getHeight(int index) {
            return 200;
        }
    }
}

