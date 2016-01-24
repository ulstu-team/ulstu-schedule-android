package ru.ulstu_team.ulstuschedule.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Faculty;

public class FacultiesAdapter extends RecyclerView.Adapter<FacultiesAdapter.ViewHolder> {

    private List<Faculty> mFaculties;

    public FacultiesAdapter(List<Faculty> faculties) {
        mFaculties = faculties;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Faculty faculty = mFaculties.get(position);
        holder.name.setText(faculty.getName());
    }

    @Override
    public int getItemCount() {
        return mFaculties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
