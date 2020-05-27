package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.R;
import com.example.sked.domain.Department;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
    private List<Department> faculties = new ArrayList<>();

    private View.OnClickListener mClickListener;

    public DepartmentAdapter() {

    }

    public void setItems(Collection<Department> div) {
        faculties.addAll(div);
        notifyDataSetChanged();
    }

    public void clearItems() {
        faculties.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public DepartmentAdapter.DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.divisions_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new DepartmentAdapter.DepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentAdapter.DepartmentViewHolder holder, int position) {
        holder.bind(faculties.get(position));
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {
        TextView departmentName;

        public DepartmentViewHolder(@NonNull final View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.tv_division_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Department department) {
            departmentName.setText(department.getName());
        }
    }
}
