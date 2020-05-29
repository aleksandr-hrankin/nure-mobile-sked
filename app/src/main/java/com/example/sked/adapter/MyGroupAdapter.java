package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.MainActivity;
import com.example.sked.R;
import com.example.sked.database.Database;
import com.example.sked.domain.MyGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.MyGroupViewHolder> {
    private List<MyGroup> myGroups = new ArrayList<>();

    private View.OnClickListener mClickListener;
    private Context context;

    public MyGroupAdapter(Context context) {
        this.context = context;
    }

    public void setItems(Collection<MyGroup> div) {
        myGroups.addAll(div);
        notifyDataSetChanged();
    }

    public void clearItems() {
        myGroups.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public MyGroupAdapter.MyGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.my_groups_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new MyGroupAdapter.MyGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGroupAdapter.MyGroupViewHolder holder, int position) {
        holder.bind(myGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return myGroups.size();
    }

    class MyGroupViewHolder extends RecyclerView.ViewHolder {
        TextView myGroupName;
        ImageButton btnRemove;
        ImageButton btnInfoGroup;

        public MyGroupViewHolder(@NonNull final View itemView) {
            super(itemView);
            myGroupName = itemView.findViewById(R.id.tv_my_group_name);
            btnInfoGroup = itemView.findViewById(R.id.btn_info_my_group);
            btnRemove = itemView.findViewById(R.id.btn_delete_my_group);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final MyGroup myGroup) {
            myGroupName.setText(myGroup.getName());

            btnInfoGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String groupInfo = " група: " + myGroup.getName()
                            + "\n факультет: " + myGroup.getFacultyName()
                            + "\n кафедра: " + myGroup.getDepartmentName()
                            + "\n курс: " + myGroup.getCourseName();


                    Toast.makeText(context, groupInfo, Toast.LENGTH_LONG).show();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = String.valueOf(myGroup.getId());
                    Database.getInstance(context).deleteGroupById(id);
                    myGroups.clear();
                    myGroups = Database.getInstance(context).getAllGroups();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
