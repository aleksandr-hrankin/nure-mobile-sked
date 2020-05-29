package com.example.sked.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.sked.MainActivity;
import com.example.sked.R;
import com.example.sked.database.Database;
import com.example.sked.domain.Institute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InstituteAdapter extends RecyclerView.Adapter<InstituteAdapter.InstituteViewHolder> {

    private List<Institute> institutes = new ArrayList<>();
    private Context context;
    private View.OnClickListener mClickListener;

    public InstituteAdapter(Context context) {
        this.context = context;
    }

    public void setItems(Collection<Institute> insts) {
        institutes.addAll(insts);
        notifyDataSetChanged();
    }

    public void clearItems() {
        institutes.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public InstituteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIfForListItem = R.layout.my_institutes_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIfForListItem, parent, false);
        return new InstituteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstituteViewHolder holder, int position) {
        holder.bind(institutes.get(position));
    }

    @Override
    public int getItemCount() {
        return institutes.size();
    }

    class InstituteViewHolder extends ViewHolder {
        ImageButton btnInfo;
        //ImageButton btnTeacher;

        TextView instituteName;
        ImageButton btnRemove;

        public InstituteViewHolder(@NonNull final View itemView) {
            super(itemView);
            btnInfo = itemView.findViewById(R.id.btn_info_institute);
            //btnTeacher = itemView.findViewById(R.id.btn_teacher);

            instituteName = itemView.findViewById(R.id.tv_institute_name);
            btnRemove = itemView.findViewById(R.id.btn_delete_institute);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v);
                }
            });
        }

        void bind(final Institute institute) {
            instituteName.setText(institute.getName());

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String infoInstitute = " назва: " + institute.getName()
                            + "\n сайт: " + institute.getSite()
                            + "\n пошта: " + institute.getMail()
                            + "\n телефон: " + institute.getPhone();

                    Toast.makeText(context, infoInstitute, Toast.LENGTH_LONG).show();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = String.valueOf(institute.getId());
                    Database.getInstance(context).deleteInstituteById(id);
                    institutes.clear();
                    institutes = Database.getInstance(context).getAllInstituteNames();
                    notifyDataSetChanged();
                }
            });
        }


    }
}
