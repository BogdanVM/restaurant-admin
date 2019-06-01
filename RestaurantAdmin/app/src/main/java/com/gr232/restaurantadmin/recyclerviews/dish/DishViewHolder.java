package com.gr232.restaurantadmin.recyclerviews.dish;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gr232.restaurantadmin.R;

public class DishViewHolder extends RecyclerView.ViewHolder {

    private TextView mDishName;
    private TextView mIngredients;
    private TextView mPrice;
    private TextView mWeight;


    public DishViewHolder(@NonNull View itemView) {
        super(itemView);

        mDishName = itemView.findViewById(R.id.dishNameTxtViewRecyclerView);
        mIngredients = itemView.findViewById(R.id.ingredientsTxtViewMenu);
        mWeight = itemView.findViewById(R.id.weightTxtViewMenu);
        mPrice = itemView.findViewById(R.id.priceTxtViewMenu);
    }

    public TextView getmDishName() {
        return mDishName;
    }

    public TextView getmIngredients() {
        return mIngredients;
    }

    public TextView getmPrice() {
        return mPrice;
    }

    public TextView getmWeight() {
        return mWeight;
    }
}
