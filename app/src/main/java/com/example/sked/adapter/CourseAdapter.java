package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.R;
import com.example.sked.domain.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> faculties = new ArrayList<>();

    private View.OnClickListener mClickListener;

    public CourseAdapter() {

    }

    public void setItems(Collection<Course> div) {
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
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.divisions_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new CourseAdapter.CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        holder.bind(faculties.get(position));
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;

        public CourseViewHolder(@NonNull final View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.tv_division_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Course course) {
            courseName.setText(course.getName());
        }
    }
}
