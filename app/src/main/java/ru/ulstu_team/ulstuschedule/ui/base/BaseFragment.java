package ru.ulstu_team.ulstuschedule.ui.base;

import android.app.Fragment;
import android.content.Context;

import ru.ulstu_team.ulstuschedule.App;
import ru.ulstu_team.ulstuschedule.injection.component.DaggerTeacherScheduleComponent;
import ru.ulstu_team.ulstuschedule.injection.component.TeacherScheduleComponent;
import ru.ulstu_team.ulstuschedule.injection.module.TeacherScheduleModule;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseFragment extends Fragment {

    private TeacherScheduleComponent teacherScheduleComponent;

    public TeacherScheduleComponent getTeacherScheduleComponent() {
        if (teacherScheduleComponent == null) {
            teacherScheduleComponent = DaggerTeacherScheduleComponent.builder()
                    .teacherScheduleModule(new TeacherScheduleModule(this))
                    .applicationComponent(App.get(this.getActivity()).getComponent())
                    .build();
        }
        return teacherScheduleComponent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(CalligraphyContextWrapper.wrap(context));
    }
}
