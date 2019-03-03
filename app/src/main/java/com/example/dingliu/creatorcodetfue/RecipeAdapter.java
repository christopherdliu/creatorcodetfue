package com.example.dingliu.creatorcodetfue;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context myContext;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context myContext, List<Recipe> recipeList) {
        this.myContext = myContext;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(
                LayoutInflater.from(myContext).inflate(R.layout.recipe_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe myRecipe = recipeList.get(position);

        holder.recipeName.setText(myRecipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // add
        }
    }
}

