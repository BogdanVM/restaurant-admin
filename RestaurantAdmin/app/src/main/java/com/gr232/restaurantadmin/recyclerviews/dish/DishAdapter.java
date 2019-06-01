package com.gr232.restaurantadmin.recyclerviews.dish;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gr232.restaurantadmin.R;
import com.gr232.restaurantadmin.models.Dish;

import java.util.List;

/**
 * Clasa de tip Adapter necesara RecyclerView-ului din activitatea MenuActivity.
 * Primeste o lista de obiecte de tip Dish, pe care le afiseaza intr-un DishViewHolder.
 */
public class DishAdapter extends RecyclerView.Adapter<DishViewHolder> {
    private List<Dish> dishList;

    public DishAdapter(List<Dish> dishList) {
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dish_view, viewGroup, false);

        return new DishViewHolder(itemView);
    }

    /**
     * Se creeaza cardul corespunzator obiectului de tip Dish de pe pozitia i
     * @param dishViewHolder obiect de tip DishViewHolder = sablonul unui card
     * @param i indexul obiectului de tip Dish care trebuie afisat
     */
    @Override
    public void onBindViewHolder(@NonNull DishViewHolder dishViewHolder, int i) {
        Dish dish = dishList.get(i);
        if (dish != null) {
            if (dish.getName() != null) {
                dishViewHolder.getmDishName().setText(dish.getName());
            }

            if (dish.getIngredients() != null) {
                dishViewHolder.getmIngredients().setText(dish.getIngredients());
            }

            if (dish.getPrice() != null) {
                dishViewHolder.getmPrice().setText(dish.getPrice().toString() + " lei");
            }

            if (dish.getWeight() != null) {
                dishViewHolder.getmWeight().setText(dish.getWeight().toString() + " g");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dishList != null) {
            return dishList.size();
        }

        return 0;
    }
}
