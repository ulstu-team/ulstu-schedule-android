package ru.ulstu_team.ulstuschedule.ui.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;
import ru.ulstu_team.ulstuschedule.ui.main.MainActivity;

public class StartActivity extends AppCompatActivity {
    private AutoCompleteTextView actv;
    private PrefsManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        prefs = new PrefsManager(this);

        actv = (AutoCompleteTextView) findViewById(R.id.groupNameText);
        actv.setGravity(Gravity.CENTER);
        String[] groups = {};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.autocomplete_list_item, groups);
        actv.setAdapter(adapter);
    }

    public void onTypeUserClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.studentSchedule:
                if (!checked) {
                    ((RadioButton) findViewById(R.id.tutorSchedule)).setChecked(false);
                    actv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tutorSchedule:
                if (!checked) {
                    ((RadioButton) findViewById(R.id.studentSchedule)).setChecked(false);
                    actv.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        if (((RadioButton) findViewById(R.id.studentSchedule)).isChecked() && !actv.getText().toString().trim().equals("")) {
            prefs.putString(PrefsKeys.USER_TYPE, "student");
            prefs.putString(PrefsKeys.USER_GROUP, actv.getText().toString().trim());
            startActivity(intent);
        } else if (((RadioButton) findViewById(R.id.tutorSchedule)).isChecked()) {
            prefs.putString(PrefsKeys.USER_TYPE, "tutor");
            startActivity(intent);
        }
    }

}
