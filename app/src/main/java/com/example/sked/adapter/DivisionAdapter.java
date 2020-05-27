package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.R;
import com.example.sked.domain.Division;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.DivisionViewHolder> {
    private List<Division> divisions = new ArrayList<>();

    private View.OnClickListener mClickListener;

    public DivisionAdapter() {

    }

    public void setItems(Collection<Division> div) {
        divisions.addAll(div);
        notifyDataSetChanged();
    }

    public void clearItems() {
        divisions.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public DivisionAdapter.DivisionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.divisions_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new DivisionAdapter.DivisionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DivisionViewHolder holder, int position) {
        holder.bind(divisions.get(position));
    }

    @Override
    public int getItemCount() {
        return divisions.size();
    }

    class DivisionViewHolder extends RecyclerView.ViewHolder {

        TextView divisionName;

        public DivisionViewHolder(@NonNull final View itemView) {
            super(itemView);
            divisionName = itemView.findViewById(R.id.tv_division_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Division division) {
            divisionName.setText(division.getName());
        }
    }
}
