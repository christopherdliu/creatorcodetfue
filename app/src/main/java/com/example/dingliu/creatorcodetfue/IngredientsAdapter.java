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

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private Context myContext;
    private List<Ingredient> ingredientList;

    public IngredientsAdapter(Context myContext, List<Ingredient> ingredientList) {
        this.myContext = myContext;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(
                LayoutInflater.from(myContext).inflate(R.layout.ingredient_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient myIngredient = ingredientList.get(position);

        holder.ingredientName.setText(myIngredient.getName());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ingredientName;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredient_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // add
        }
    }
}

