package com.gr232.restaurantadmin.recyclerviews.employee;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gr232.restaurantadmin.R;

/**
 * Clasa de tip ViewHolder care primeste elementele vizuale corespunzatoare fisierului XML:
 *          "employee_view.xml"
 */
public class EmployeeViewHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mType;

    private ImageButton mEdit;
    private ImageButton mRemove;
    private ImageButton mInfo;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.nameTxtViewRecyclerView);
        mType = itemView.findViewById(R.id.empTypeTxtViewRecyclerView);

        mEdit = itemView.findViewById(R.id.editBtnRecyclerView);
        mRemove = itemView.findViewById(R.id.removeBtnRecyclerView);
        mInfo = itemView.findViewById(R.id.infoBtnRecyclerView);
    }

    public TextView getmName() {
        return mName;
    }

    public TextView getmType() {
        return mType;
    }

    public ImageButton getmEdit() {
        return mEdit;
    }

    public ImageButton getmRemove() {
        return mRemove;
    }

    public ImageButton getmInfo() {
        return mInfo;
    }
}
