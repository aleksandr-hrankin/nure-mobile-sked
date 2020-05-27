package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.R;
import com.example.sked.domain.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {
    private List<Faculty> faculties = new ArrayList<>();

    private View.OnClickListener mClickListener;

    public FacultyAdapter() {

    }

    public void setItems(Collection<Faculty> div) {
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
    public FacultyAdapter.FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.divisions_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new FacultyAdapter.FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {
        holder.bind(faculties.get(position));
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    class FacultyViewHolder extends RecyclerView.ViewHolder {
        TextView facultyName;

        public FacultyViewHolder(@NonNull final View itemView) {
            super(itemView);
            facultyName = itemView.findViewById(R.id.tv_division_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Faculty faculty) {
            facultyName.setText(faculty.getName());
        }
    }
}
