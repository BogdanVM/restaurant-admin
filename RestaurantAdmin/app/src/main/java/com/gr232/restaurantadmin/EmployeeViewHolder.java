package com.gr232.restaurantadmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mType;

    private ImageButton mEdit;
    private ImageButton mRemove;
    private ImageButton mInfo;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);
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
