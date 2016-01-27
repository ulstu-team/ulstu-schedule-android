package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.ui.faculties.FacultiesAdapter;

@Module
public class FacultiesModule {

    protected final Activity mFacultiesActivity;

    public FacultiesModule(Activity facultiesActivity) {
        mFacultiesActivity = facultiesActivity;
    }


}
