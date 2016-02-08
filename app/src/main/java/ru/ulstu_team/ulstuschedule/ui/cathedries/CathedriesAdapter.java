package ru.ulstu_team.ulstuschedule.ui.cathedries;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Cathedra;

public class CathedriesAdapter extends RecyclerView.Adapter<CathedriesAdapter.ViewHolder> {

    private List<Cathedra> mCathedries;

    public void setCathedries(List<Cathedra> cathedries) {
        mCathedries = cathedries;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cathedra cathedra = mCathedries.get(position);
        holder.name.setText(cathedra.getName());
    }

    @Override
    public int getItemCount() {
        return mCathedries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
