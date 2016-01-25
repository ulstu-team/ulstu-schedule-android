package ru.ulstu_team.ulstuschedule.ui.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.databinding.TeacherListItemBinding;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.data.model.LessonComparator;

public class StickyListTeacherAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Lesson> mLessons;
    private LayoutInflater mInflater;
    private Context mContext;
    private String[] mDaysOfWeek;
    private String[] mMonths;
    private Calendar calendar;

    @Inject
    public StickyListTeacherAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mLessons = Collections.emptyList();

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
    }

    public StickyListTeacherAdapter(Context context, List<Lesson> lessons) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mLessons = Collections.emptyList();

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        setLessons(lessons);
    }

    public void setLessons(List<Lesson> lessons) {
        ArrayList<Lesson> l = new ArrayList<>(lessons.size());
        for (Lesson lesson : lessons) {
            l.add(lesson);
        }
        Collections.sort(l, new LessonComparator());
        mLessons = l;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = DataBindingUtil
                    .inflate(mInflater, R.layout.sticky_list_header, parent, false).getRoot();
            holder.headerText = (TextView) convertView.findViewById(R.id.headerTitle);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        Lesson lesson = mLessons.get(position);
        calendar.add(Calendar.DAY_OF_MONTH, getDayIncrement(lesson));

        String date = getDaysOfWeek()[lesson.getDayOfWeek() - 1] + ", " +
                calendar.get(Calendar.DAY_OF_MONTH) + " " +
                getMonths()[calendar.get(Calendar.MONTH)];
        holder.headerText.setText(date);
        calendar.add(Calendar.DAY_OF_MONTH, -getDayIncrement(lesson));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        Lesson lesson = mLessons.get(position);
        return lesson.getDayOfWeek() + lesson.getNumberOfWeek() * 7;
    }

    @Override
    public int getCount() {
        return mLessons.size();
    }

    @Override
    public Object getItem(int position) {
        return mLessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = DataBindingUtil.
                    inflate(mInflater, R.layout.teacher_list_item, parent, false).getRoot();
            // mInflater.inflate(R.layout.teacher_list_item, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Lesson lesson = mLessons.get(position);
        holder.getBinding().setLesson(lesson);
        holder.getBinding().executePendingBindings();

        return convertView;
    }

    private String[] getDaysOfWeek() {
        if (mDaysOfWeek == null)
            mDaysOfWeek = mContext.getResources().getStringArray(R.array.short_days_of_week);
        return mDaysOfWeek;
    }

    private String[] getMonths() {
        if (mMonths == null)
            mMonths = mContext.getResources().getStringArray(R.array.months);
        return mMonths;
    }

    private int getDayIncrement(Lesson lesson) {
        return (lesson.getNumberOfWeek() - 1) * 7 + (lesson.getDayOfWeek() - 1);
    }

    class HeaderViewHolder {
        TextView headerText;
    }

    class ViewHolder {
        final TeacherListItemBinding binding;

        public ViewHolder(View itemView) {
            binding = DataBindingUtil.bind(itemView);
        }

        public TeacherListItemBinding getBinding() {
            return binding;
        }
    }
}
