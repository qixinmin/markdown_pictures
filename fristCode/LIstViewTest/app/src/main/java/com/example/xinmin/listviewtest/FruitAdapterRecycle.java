package com.example.xinmin.listviewtest;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by xinmin on 20/01/2017.
 */

public class FruitAdapterRecycle extends RecyclerView.Adapter<FruitAdapterRecycle.ViewHolder> {

    private List<Fruit> mFruitList;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(pos);
                Toast.makeText(v.getContext(), "You clicked view"+fruit.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parent.getContext(), PlayActivity.class);
                parent.getContext().startActivity(intent);
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(pos);
                Toast.makeText(v.getContext(), "You clicked image"+fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
            fruitName = (TextView)view.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapterRecycle(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }


}
