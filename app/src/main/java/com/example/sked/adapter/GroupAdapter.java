package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.R;
import com.example.sked.domain.Group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<Group> faculties = new ArrayList<>();

    private View.OnClickListener mClickListener;

    public GroupAdapter() {

    }

    public void setItems(Collection<Group> div) {
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
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.divisions_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new GroupAdapter.GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int position) {
        holder.bind(faculties.get(position));
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;

        public GroupViewHolder(@NonNull final View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.tv_division_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Group group) {
            groupName.setText(group.getName());
        }
    }
}
