package ru.ulstu_team.ulstuschedule.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.databinding.ScheduleListItemBinding;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private List<Lesson> mLessons;

    public TeacherAdapter(List<Lesson> lessons) {
        mLessons = lessons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = mLessons.get(position);
        holder.getBinding().setLesson(lesson);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mLessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ScheduleListItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ScheduleListItemBinding getBinding() {
            return binding;
        }
    }
}
