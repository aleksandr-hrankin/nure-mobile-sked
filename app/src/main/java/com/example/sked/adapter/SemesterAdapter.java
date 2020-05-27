package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.R;
import com.example.sked.domain.Semester;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {
    private List<Semester> semesters = new ArrayList<>();

    private View.OnClickListener mClickListener;

    public SemesterAdapter() {

    }

    public void setItems(Collection<Semester> div) {
        semesters.addAll(div);
        notifyDataSetChanged();
    }

    public void clearItems() {
        semesters.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public SemesterAdapter.SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.semesters_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new SemesterAdapter.SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        holder.bind(semesters.get(position));
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }

    class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView semesterName;
        TextView semesterStart;
        TextView semesterFinish;

        public SemesterViewHolder(@NonNull final View itemView) {
            super(itemView);
            semesterName = itemView.findViewById(R.id.tv_semester_name);
            semesterStart = itemView.findViewById(R.id.tv_semester_start);
            semesterFinish = itemView.findViewById(R.id.tv_semester_finish);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Semester semester) {
            semesterName.setText(semester.getName());
            String start = new SimpleDateFormat("dd.MM.yyyy").format(new Date(semester.getStart()));
            semesterStart.setText(start);
            String finish = new SimpleDateFormat("dd.MM.yyyy").format(new Date(semester.getFinish()));
            semesterFinish.setText(finish);
        }
    }
}
